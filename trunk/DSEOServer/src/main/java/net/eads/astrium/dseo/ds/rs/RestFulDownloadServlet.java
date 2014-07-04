/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.rs;

import com.sun.jersey.api.core.HttpContext;
import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.eads.astrium.dseo.dbhandler.FileHandler;
import net.eads.astrium.dseo.ds.dseo.CapabilitiesGenerator;
import net.eads.astrium.dseo.ds.dseo.MetalinkGenerator;
import net.eads.astrium.dseo.ds.dseo.ProductDownloadResponseGenerator;
import net.eads.astrium.dseo.ds.wps.workers.DSWorker;
import net.eads.astrium.dseo.exceptions.DreamOWSException;
import net.eads.astrium.dseo.util.Constants;
import net.eads.astrium.dseo.util.DateHandler;
import net.eads.astrium.dseo.util.FormatHandler;
import net.eads.astrium.dseo.util.logging.DreamLogger;
import net.eads.astrium.dseo.util.structures.DBFile;
import net.eads.astrium.dseo.util.structures.DBProduct;
import net.eads.astrium.dseo.util.structures.FileAvailibility;
import net.eads.astrium.dseo.util.structures.usermanagement.MMFASUser;
import net.eads.astrium.dseo.workers.IPSBasicWorker;
import net.eads.astrium.ips.xmlparsing.OGCNamespacesXmlOptions;
import net.opengis.dseo.x10.CapabilitiesDocument;
import net.opengis.dseo.x10.ProductDownloadResponseDocument;
import org.metalinker.MetalinkDocument;

/**
 *
 * @author re-sulrich
 */
@Path("{service : (ds)}{serviceId : (/[^/]+?)?}/{interfaceId : (dseo)}/1.0.0{type : (/[^/]+?)?}{fileId : (/[^/]+?)?}")//{fileId : (/[^/]+?)?}{type : (/[^/]+?)?}
public class RestFulDownloadServlet {

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
    public Response executeDSEO() throws Exception {
          
        Response response = null;
        
        
        MultivaluedMap<String, String> params = ui.getPathParameters();
        String fileId = params.getFirst("fileId");
        String type = params.getFirst("type");

        try {
        
            if (type == null || type.equals("")) {

                MultivaluedMap<String, String> p = ui.getQueryParameters();
                String service = p.getFirst("service");

                if (service == null) {

                    throw new Exception("Could not find the 'service' parameter in the request");
                }
                else if (!service.toLowerCase().contains("dseo")) {
                    
                    throw new Exception("Wrong input for parameter 'service' : expected 'dseo' found '" + service + "'.");
                }
                else {
                    CapabilitiesDocument capsDoc = CapabilitiesGenerator.getCapabilities();

                    response = Response.ok(
                            capsDoc.xmlText(OGCNamespacesXmlOptions.getInstance()), 
                            "application/xml").build();
                }
            }
            else {
                type = type.substring(1);
                fileId = fileId.substring(1);

                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                        "FileId : " + fileId + "\r\n" +
                        "Type : " + type);

                DSWorker w = (DSWorker)worker;
                FileHandler fileLoader = w.getDatabaseConfiguration().getFileLoader();

                String uploadDirPath = w.getDatabaseConfiguration().getDsLoader().getDsShareFolder();

                
                FileAvailibility availibility = fileLoader.getFileAvailibility(fileId);

                if (availibility.equals(FileAvailibility.AVAILABLE)) {
                    

                    switch (type.toLowerCase()) {
                        case "product":
                            if (!fileLoader.isProductFile(fileId)) {
                                throw new Exception("File " + fileId + " is not a product.");
                            }


                            DBProduct product = fileLoader.getFilesByProduct(fileId);

                            MetalinkGenerator metGen = new MetalinkGenerator(w.getDatabaseConfiguration());
                            MetalinkDocument met = metGen.getMetalink(product);

                            response = Response.ok(
                                    met.xmlText(OGCNamespacesXmlOptions.getInstance()), 
    //                                "application/metalink+xml"
                                    "application/xml"
                                    ).build();

                        break;
                        case "file":
                        default:

                            DBFile file = fileLoader.getFile(fileId);

                            if (file == null) {
                                throw new Exception("File with ID " + fileId + " was not found on this service.");
                            }

                            String fileDateFolder=  DateHandler.formatDateToSimpleNumbersOnly(file.getCreationTime());

                            String fileFolder = uploadDirPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                                    fileDateFolder + File.separator + 
                                    file.getId() + File.separator;

                            File fic = new File(fileFolder + file.getName() + "." + file.getExtension());

                            response = Response.ok(
                                    new FileStreamOutput(fic), 
    //                                "application/octet-stream"
                                    FormatHandler.extensionToMimeType(file.getExtension())
                                    ).build();
                        break;
                    }
                    
                } else { //If file not available
                    ProductDownloadResponseDocument pdr = 
                            ProductDownloadResponseGenerator.getProductDownloadResponse(fileId, availibility);
                    
                            response = Response.ok(
                                    pdr.xmlText(OGCNamespacesXmlOptions.getInstance()), 
    //                                "application/metalink+xml"
                                    "application/xml"
                                    ).build();
                }
            }
            
        } catch (Exception e) {
            DreamOWSException resp = new DreamOWSException(e.getMessage());

            response = Response.ok(
                    resp.getFaultInfo().xmlText(OGCNamespacesXmlOptions.getInstance()), 
                    "application/xml").build();
        }
        
        return response;
    }
}
