/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.fileimport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.eads.astrium.dseo.ds.database.DSDBHandler;
import static net.eads.astrium.dseo.ds.fileimport.ImageImporter.imageExtensions;
import net.eads.astrium.dseo.structures.WebFileInputStream;
import net.eads.astrium.dseo.util.Constants;
import net.eads.astrium.dseo.util.DateHandler;
import net.eads.astrium.dseo.util.FileHandler;
import net.eads.astrium.dseo.util.ZipHandler;
import net.eads.astrium.dseo.util.logging.DreamLogger;
import net.eads.astrium.dseo.util.structures.DBFile;
import net.eads.astrium.dseo.util.structures.usermanagement.MMFASUser;

/**
 *
 * @author re-sulrich
 */
public class ZipImporter extends FileImporter {

    public ZipImporter(DSDBHandler dbHandler) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        super(dbHandler);
    }

    @Override
    boolean canImportFile(WebFileInputStream inputFile) {
        return inputFile.getMimeType().contains("zip");
    }

    @Override
    boolean canImportFile(File inputFile) {
        String extension = inputFile.getName().substring(inputFile.getName().lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("zip");
    }

    @Override
    public Map<String, DBFile> importFile(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        
        Map<String, DBFile> files = new HashMap<>();
        
        
        String fileName = inputFile.getFileName().substring(0, inputFile.getFileName().lastIndexOf("."));

        String zipFileName = "temp_" + fileName + "_" + 
                DateHandler.formatDateToNumbersOnly(DateHandler.getCalendar().getTime());
        String tempFolder = Constants.IPS_WS_CONF_FOLDER + "temp" + File.separator;
        InputStream metadata = inputFile.getContent();

        File tmpDir = new File(tempFolder);
        if (!tmpDir.exists()) tmpDir.mkdirs();
        
        String metadataFileName = tempFolder + zipFileName + ".zip";

        //Create the metadata zip file on the drive
        File zipFile = new File(metadataFileName);
        zipFile.createNewFile();
        FileOutputStream zipOutput = new FileOutputStream(zipFile);

        byte[] bz = new byte[metadata.available()];
        metadata.read(bz);
        zipOutput.write(bz);

        metadata.close();
        zipOutput.flush();
        zipOutput.close();

        String outputFolder = tempFolder + zipFileName + File.separator;
        
        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) outputDir.mkdirs();

        ZipHandler.unzip(metadataFileName, outputFolder);
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Unzipping to  : " + outputFolder);

        FolderImporter folderProcessor = new FolderImporter(dbHandler);
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Unzipping to  : " + outputFolder);
        
        files = folderProcessor.importContent(outputDir, productFolderPath, parentFileId, use, user);
        
        zipFile.delete();
        outputDir.delete();
        
        return files;
    }

    @Override
    public Map<String, DBFile> importFile(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) throws Exception {
        Map<String, DBFile> files = null;
        
        String tempFolder = Constants.IPS_WS_CONF_FOLDER + "temp" + File.separator;
        String zipFileName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf("."));
        
        String outputFolder = tempFolder + zipFileName + File.separator;

        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) outputDir.mkdirs();
        
        ZipHandler.unzip(inputFile.getPath(), outputFolder);
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Unzipping to  : " + outputFolder);

        FolderImporter folderProcessor = new FolderImporter(dbHandler);
        
        files = folderProcessor.importContent(outputDir, productFolderPath, parentFileId, use, user);
        
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Deleting temps...");
        inputFile.delete();
        FileHandler.deleteFolder(outputDir);
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Deleting temps...");
        
        
        
        return files;
    }
}
