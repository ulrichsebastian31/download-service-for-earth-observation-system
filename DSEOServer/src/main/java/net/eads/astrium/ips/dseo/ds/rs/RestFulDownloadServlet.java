/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.rs;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.header.ContentDisposition;
import java.io.File;
import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.eads.astrium.ips.dbhandler.FileHandler;
import net.eads.astrium.ips.dseo.ds.dseo.CapabilitiesGenerator;
import net.eads.astrium.ips.dseo.ds.dseo.DSEOOWSException;
import net.eads.astrium.ips.dseo.ds.dseo.MetalinkGenerator;
import net.eads.astrium.ips.dseo.ds.dseo.ProductDownloadResponseGenerator;
import net.eads.astrium.ips.dseo.ds.wps.workers.DSWorker;
import net.eads.astrium.ips.exceptions.DreamOWSException;
import net.eads.astrium.ips.util.Constants;
import net.eads.astrium.ips.util.DateHandler;
import net.eads.astrium.ips.util.FormatHandler;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.DBProduct;
import net.eads.astrium.ips.util.structures.FileAvailibility;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;
import net.eads.astrium.ips.workers.IPSBasicWorker;
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
        
        URI baseURI = httpContext.getUriInfo().getAbsolutePath();
        String serversAdress = null;
        
        if (baseURI.getPort() == -1) {
            serversAdress = "http://" + baseURI.getHost() + baseURI.getPath();
        }
        else {
            serversAdress = "http://" + baseURI.getHost() + ":" + baseURI.getPort() + baseURI.getPath();
        }
        
        if (serversAdress.contains("dseo/1.0.0")) {
            serversAdress = serversAdress.substring(0, 
                    serversAdress.indexOf("dseo/1.0.0") + "dseo/1.0.0".length());
        }
        if (!serversAdress.endsWith("/")) serversAdress += "/";

        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Server base address : " + serversAdress);
        
        
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
                    CapabilitiesDocument capsDoc = CapabilitiesGenerator.getCapabilities(serversAdress);

                    response = Response.ok(
                            capsDoc.xmlText(OGCNamespacesXmlOptions.getInstance()), 
                            "application/xml").build();
                }
            }
            else {
                type = type.substring(1);
                
                if ((type.equalsIgnoreCase("product") || type.equalsIgnoreCase("file")) && 
                        fileId != null && !fileId.equals("")) {

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
                                String metText = met.xmlText(OGCNamespacesXmlOptions.getInstance());
                                int size = metText.length();
                                ContentDisposition dd = ContentDisposition.type("file").fileName(fileId + "_metalink.xml").build();

                                response = Response.ok(
                                        metText, 
                                        Constants.METALINK_MIME_TYPE
    //                                    "application/xml"
                                        )
                                        .header(Constants.CONTENT_DISPOSITION_HEADER_PARAMETER, dd)
                                        .header(Constants.CONTENT_LENGTH_HEADER_PARAMETER, size)
                                        .build();

                            break;
                            case "file":

                                DBFile file = fileLoader.getFile(fileId);

                                if (file == null) {
                                    throw new Exception("File with ID " + fileId + " was not found on this service.");
                                }

                                String fileDateFolder=  DateHandler.formatDateToSimpleNumbersOnly(file.getCreationTime());

                                String fileFolder = uploadDirPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                                        fileDateFolder + File.separator + 
                                        file.getId() + File.separator;

                                File fic = new File(fileFolder + file.getName() + "." + file.getExtension());


                                ContentDisposition cd = ContentDisposition.type("file").fileName(fic.getName()).build();

                                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(""
                                        + Constants.CONTENT_DISPOSITION_HEADER_PARAMETER + " : " + cd
                                        + Constants.CONTENT_LENGTH_HEADER_PARAMETER + " : " + fic.length());



                                response = Response.ok(
                                        new FileStreamOutput(fic), 
//                                        "application/octet-stream"
                                        FormatHandler.extensionToMimeType(file.getExtension())
                                        ).header(Constants.CONTENT_DISPOSITION_HEADER_PARAMETER, cd)
                                        .header(Constants.CONTENT_LENGTH_HEADER_PARAMETER, "" + fic.length())
                                        .build();
                            break;
                        }

                    } else { //If file not available
                        ProductDownloadResponseDocument pdr = 
                                ProductDownloadResponseGenerator.getProductDownloadResponse(fileId, availibility);
                        String pdrText = pdr.xmlText(OGCNamespacesXmlOptions.getInstance());

    //                    int size = pdrText.length();
    //                    ContentDisposition dd = ContentDisposition.type("file").fileName(fileId + "_productDownloadResponse.xml").build();

                        response = Response.status(Response.Status.ACCEPTED)
                                .entity(pdrText)
                                .type("application/xml")
    //                            .header(Constants.CONTENT_DISPOSITION_HEADER_PARAMETER, dd)
    //                            .header(Constants.CONTENT_LENGTH_HEADER_PARAMETER, size)
                                .build();
                    
                    }
                }
                else {
                    DreamOWSException resp = new DreamOWSException("'" + type + "/" + fileId + "' is not a valid product/file path.");

                    DSEOOWSException res = new DSEOOWSException(resp);

                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(res.getFaultInfo().xmlText(OGCNamespacesXmlOptions.getInstance()))
                            .type("application/xml")
                            .build();
                }
            }
            
        } catch (Exception e) {
            DreamOWSException resp = new DreamOWSException(e.getMessage());

            DSEOOWSException res = new DSEOOWSException(resp);
            
            response = Response.ok(
                    res.getFaultInfo().xmlText(OGCNamespacesXmlOptions.getInstance()), 
                    "application/xml").build();
        }
        
        return response;
    }
}
