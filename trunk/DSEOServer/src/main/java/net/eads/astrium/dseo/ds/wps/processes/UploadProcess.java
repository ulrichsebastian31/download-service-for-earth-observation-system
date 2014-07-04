/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.ds.wps.processes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import net.eads.astrium.dseo.database.DBHandler;
import net.eads.astrium.dseo.ds.database.DSDBHandler;
import net.eads.astrium.dseo.ds.fileimport.FileImporter;
import net.eads.astrium.dseo.structures.WebFileInputStream;
import net.eads.astrium.dseo.util.DateHandler;
import net.eads.astrium.dseo.util.FormatHandler;
import net.eads.astrium.dseo.util.logging.DreamLogger;
import net.eads.astrium.dseo.util.structures.DBFile;
import net.eads.astrium.dseo.util.structures.usermanagement.MMFASUser;
import net.eads.astrium.dseo.wps.processes.FileInputsDatabaseWPSProcess;
import net.eads.astrium.dseo.wps.processes.inout.FileInput;
import net.eads.astrium.dseo.wps.processes.inout.Input;
import net.eads.astrium.dseo.wps.processes.inout.Output;
import net.opengis.wps.x100.ExecuteDocument;
import net.opengis.wps.x100.ExecuteResponseDocument;
import net.opengis.wps.x100.InputType;
import net.opengis.wps.x100.LiteralDataType;
import net.opengis.wps.x100.OutputDataType;
import net.opengis.wps.x100.ProcessFailedType;

/**
 *
 * @author re-sulrich
 */
public class UploadProcess extends FileInputsDatabaseWPSProcess {
    
    private static Map<String,Input> createInputs() {
        Map<String,Input> inputs = new HashMap<String,Input>();
        
        inputs.put("file", new FileInput("", "", "file", "File", "A file to be uploaded."));
//        inputs.put("zip", new ZipFileInput("zip", "Zip", "A zip file containing an image with metadata files."));
        
        return inputs;
    }

    private static Map<String,Output> createOutputs() {
        
        Map<String,Output> outputs = new HashMap<String,Output>();
        
        outputs.put("fileId", new Output("fileId", "File ID", 
                "The database ID of the given uploaded file.", 
                "String"));
        
        return outputs;
    }
    
    public UploadProcess() throws Exception {
        
        super(
                "This function permits to upload files to the system.",
                createInputs(),
                createOutputs());
    }

    private DSDBHandler handler;

    @Override
    public DSDBHandler getHandler() {
        return handler;
    }

    @Override
    public void setHandler(DBHandler handler) throws SQLException, ParseException, UnsupportedEncodingException, IOException {
        if (handler instanceof DSDBHandler) {
            this.handler = (DSDBHandler)handler;
        }
    }
    
   @Override
    public ExecuteResponseDocument executeProcessGET(MultivaluedMap<String, String> parameters, MMFASUser userId) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (String string : parameters.keySet()) {
            params.put(string, parameters.getFirst(string));
        }
        
        return executeProcessPOSTMultipart(params, null, userId);
    }

    @Override
    public ExecuteResponseDocument executeProcessPOSTXML(ExecuteDocument document, MMFASUser userId) throws Exception {
        InputType[] ins = document.getExecute().getDataInputs().getInputArray();
        
        Map<String, String> params = new HashMap<>();
        for (InputType string : ins) {
            params.put(
                    string.getIdentifier().getStringValue(), 
                    string.getData().getLiteralData().getStringValue()
                );
        }
        
        return executeProcessPOSTMultipart(params, null, userId);
    }

    @Override
    public ExecuteResponseDocument executeProcessPOSTMultipart(Map<String, String> parameters, Map<String, WebFileInputStream> files, MMFASUser userId) throws Exception {
        
        Calendar creationTime = DateHandler.getCalendar();
        
        String uploadDirPath = this.handler.getDsLoader().getDsShareFolder();
        
//        String uploadDirPath = Constants.IPS_WS_CONF_FOLDER + 
//                "files" + File.separator;
        
        String dirPath = uploadDirPath + 
                DateHandler.formatDateToSimpleNumbersOnly(creationTime.getTime()) + File.separator;
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Nb files : " + files.size());
        
        WebFileInputStream fileParam = files.get("file");
//        WebFileInputStream metadataParam = files.get("zip");
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("File : " + fileParam);
//        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Metadata : " + metadataParam);
        
        Map<String, String> filesIds = new HashMap<>();
        if (fileParam != null) {
            Map<String, String> fileId = this.saveFile(fileParam, dirPath, userId);
            filesIds.putAll(fileId);
        }
        
//        if (metadataParam != null) {
//            Map<String, String> zipFilesIds = this.saveFilesFromZip(metadataParam, dirPath, userId);
//            filesIds.putAll(zipFilesIds);
//        }
        
        //Creating response
        ExecuteResponseDocument responseDocument = ExecuteResponseDocument.Factory.newInstance();
        ExecuteResponseDocument.ExecuteResponse response = responseDocument.addNewExecuteResponse();
        
        if (!filesIds.isEmpty()) {
        
            response.addNewStatus().setProcessSucceeded("true");
            ExecuteResponseDocument.ExecuteResponse.ProcessOutputs outs = response.addNewProcessOutputs();

            Output imageIdOutput = this.getOutputs().get("fileId");
            
            for (String fileName : filesIds.keySet()) {
                
                OutputDataType imageIdOut = outs.addNewOutput();
                imageIdOut.addNewIdentifier().setStringValue(imageIdOutput.getIdentifier());
                imageIdOut.addNewTitle().setStringValue(imageIdOutput.getName());
                imageIdOut.addNewAbstract().setStringValue(imageIdOutput.getDescription());
                LiteralDataType data = imageIdOut.addNewData().addNewLiteralData();
                
                //Data type is fileName, Value is fileId
                data.setDataType(fileName);
                data.setStringValue("" + filesIds.get(fileName) + "");
            }
        }
        else {
            ProcessFailedType failed = response.addNewStatus().addNewProcessFailed();
            failed.addNewExceptionReport().addNewException().addExceptionText("The image file could not be found.");
        }
        
        return responseDocument;
    }
    
    /**
     * Returns a Map containing 
     * an unique key which is the name of the file 
     * and a value which is the id created in the database for said file
     * @param input
     * @param dirPath
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    private Map<String,String> saveFile(WebFileInputStream input, String dirPath, MMFASUser userId) throws Exception {
        Map<String, String> filesUploadsResults = new HashMap<>();
        //        ImageFolderProcessor folderProcessor = new ImageFolderProcessor(handler);
        //        DBFile result = folderProcessor.uploadFileToSystem(input, userId);
        FileImporter fileImporter = this.handler.getFileImporter();
        Map<String, DBFile> res = fileImporter.importContent(
                input, 
                null, 
                null, 
                "product",
                userId);
        
        if (res != null) {
            for (String string : res.keySet()) {
                DBFile result = res.get(string);
                if (result != null) {
                    filesUploadsResults.put(
                            result.getName() + "." + result.getExtension(), 
                            result.getId() + " - " + FormatHandler.extensionToMimeType(result.getExtension()));
                }
            }
        }
        
        return filesUploadsResults;
    }
}
