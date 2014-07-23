/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.rs;

import com.sun.jersey.api.core.HttpContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.eads.astrium.ips.dbhandler.FileHandler;
import net.eads.astrium.ips.dseo.ds.wps.workers.DSWorker;
import net.eads.astrium.ips.exceptions.DreamOWSException;
import net.eads.astrium.ips.util.Constants;
import net.eads.astrium.ips.util.DateHandler;
import net.eads.astrium.ips.util.FormatHandler;
import net.eads.astrium.ips.util.ZipHandler;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.Image;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;
import net.eads.astrium.ips.workers.IPSBasicWorker;

/**
 *
 * @author re-sulrich
 */
@Path("{service : (ds)}{serviceId : (/[^/]+?)?}/{interfaceId : (download)}{fileId : (/[^/]+?)?}{type : (/[^/]+?)?}")
public class DownloadServlet {

    @Context
    protected volatile MMFASUser userId;

    @Context
    protected volatile IPSBasicWorker worker;

    /**
     * Map containing the URL parameters
     * through ui.getQueryParameters() and ui.getPathParameters()
     * Will be filled in automatically by the service when a request is received
     */ 
    @Context 
    public volatile UriInfo ui;
    
    @Context
    public volatile HttpContext httpContext;

    @GET
    public Response DownloadImage() throws Exception {
          
        Response response = null;
        
        MultivaluedMap<String, String> params = ui.getPathParameters();
        String fileId = params.getFirst("fileId").substring(1);
        String type = params.getFirst("type");

        if (type != null && !type.equals("")) type = type.substring(1);
        else type = "image";
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                "FileId : " + fileId + "\r\n" +
                "Type : " + type);
        
        DSWorker w = (DSWorker)worker;
        FileHandler fileLoader = w.getDatabaseConfiguration().getFileLoader();
        
        
        String uploadDirPath = w.getDatabaseConfiguration().getDsLoader().getDsShareFolder();

//        String tempFolder = Constants.IPS_WS_CONF_FOLDER + "temp";
        
        switch (type.toLowerCase()) {
            case "preview":
                DBFile imagePreview = fileLoader.getImagePreviewFile(fileId);

//                if (imagePreview == null) {
//                    
//                    response = Response.ok(
//                            new DreamOWSException("This product ("+fileId+") does not contain a preview."), 
//                            "text/xml").build();
//                }
//                else {
                    //If no preview, just send the whole image
                    //TODO: replace with call to preview process
                    if (imagePreview == null) {
                        imagePreview = fileLoader.getFile(fileId);
                    }

                    String imagePreviewdateFolder = DateHandler.formatDateToSimpleNumbersOnly(imagePreview.getCreationTime());

                    String imagePreviewfileFolder = uploadDirPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                            imagePreviewdateFolder + File.separator + 
                            imagePreview.getId() + File.separator;

                    File imagePreviewfile = new File(imagePreviewfileFolder + imagePreview.getName() + "." + imagePreview.getExtension());

                    response = Response.ok(
                            new FileStreamOutput(imagePreviewfile), 
                            FormatHandler.extensionToMimeType(imagePreview.getExtension())).build();
//                }
                
            break;
            case "metadata":
                DBFile metadataFic = fileLoader.getImageMetadataFile(fileId);

                if (metadataFic == null) {
                    
                    response = Response.ok(
                            new DreamOWSException("This product ("+fileId+") does not contain metadata."), 
                            "text/xml").build();
                }
                else {

                    String metadataFicdateFolder = DateHandler.formatDateToSimpleNumbersOnly(metadataFic.getCreationTime());

                    String metadataFicfileFolder = uploadDirPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                            metadataFicdateFolder + File.separator + 
                            metadataFic.getId() + File.separator;

                    File metadataFicfile = new File(metadataFicfileFolder + metadataFic.getName() + "." + metadataFic.getExtension());

                    response = Response.ok(
                            new FileStreamOutput(metadataFicfile), 
                            FormatHandler.extensionToMimeType(metadataFic.getExtension())).build();
                }
                
            break;
            case "image":
            default:
            
                DBFile image = fileLoader.getFile(fileId);

                String imagedateFolder=  DateHandler.formatDateToSimpleNumbersOnly(image.getCreationTime());

                String imagefileFolder = uploadDirPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                        imagedateFolder + File.separator + 
                        image.getId() + File.separator;

                File imagefile = new File(imagefileFolder + image.getName() + "." + image.getExtension());
                
                response = Response.ok(
                        new FileStreamOutput(imagefile), 
                        FormatHandler.extensionToMimeType(image.getExtension())).build();
                
            break;
        }
        
//        if (type.equalsIgnoreCase("preview")) {
//            File file = new File(fileFolder + image.getName() + "." + image.getExtension());
//            response = Response.ok(
//                    new ImagePreviewStreamOutput(file), 
//                    FormatHandler.extensionToMimeType(image.getExtension())).build();
//        }
        
//        if (type.equalsIgnoreCase("metadata")) {
//            File tempDir = new File(tempFolder);
//            if (!tempDir.exists()) {
//                tempDir.mkdirs();
//                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
//                        "Created temp dir : " + tempDir.getPath() + "  " + tempDir.exists());
//            }
//
//            File file = new File(tempFolder + File.separator + "img_" + fileId + "_with_metadata.zip");
//            ZipHandler.zip(fileFolder, file.getPath());
//            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
//                    "Created ZIP file : " + file.getPath() + "  " + file.exists());
//            
//            response = Response.ok(new FileStreamOutput(file), "application/zip").build();
//        }
        
        return response;
    }
    
    
}
