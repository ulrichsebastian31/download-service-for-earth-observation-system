/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.dseo;

import net.eads.astrium.dseo.dbhandler.FileHandler;
import net.eads.astrium.dseo.util.structures.DBFile;
import net.eads.astrium.dseo.util.structures.FileAvailibility;
import static net.eads.astrium.dseo.util.structures.FileAvailibility.IN_PROGRESS;
import net.eads.astrium.ips.xmlparsing.OGCNamespacesXmlOptions;
import net.opengis.dseo.x10.ProductDownloadResponseDocument;
import net.opengis.dseo.x10.ProductDownloadResponseType;

/**
 *
 * @author re-sulrich
 */
public class ProductDownloadResponseGenerator {

    public static final int RETRY_AFTER = 60;
    
    
    public static ProductDownloadResponseDocument getProductDownloadResponse(String fileId, FileAvailibility availibility) {
        
        ProductDownloadResponseDocument doc = ProductDownloadResponseDocument.Factory.newInstance(OGCNamespacesXmlOptions.getInstance());
        ProductDownloadResponseDocument.ProductDownloadResponse resp = doc.addNewProductDownloadResponse();
        
        switch (availibility) {
            case IN_PROGRESS:
                resp.setResponseCode(ProductDownloadResponseType.IN_PROGRESS);
                resp.setRetryAfter(RETRY_AFTER);
            break;
            case FAILED:
                resp.setResponseCode(ProductDownloadResponseType.ERROR);
            break;
        }
        
        return doc;
    }
}
