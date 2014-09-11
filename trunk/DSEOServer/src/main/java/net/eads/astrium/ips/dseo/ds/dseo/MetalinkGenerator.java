/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.dseo;

import java.sql.SQLException;
import java.util.Map;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.util.Constants;
import net.eads.astrium.ips.util.structures.DBProduct;
import net.eads.astrium.ips.xmlparsing.OGCNamespacesXmlOptions;
import org.metalinker.FileType;
import org.metalinker.FilesType;
import org.metalinker.MetalinkDocument;
import org.metalinker.MetalinkType;
import org.metalinker.PublisherType;
import org.metalinker.ResourcesType;

/**
 *
 * @author re-sulrich
 */
public class MetalinkGenerator {

    private DSDBHandler handler;

    public MetalinkGenerator(DSDBHandler handler) {
        this.handler = handler;
    }
    
    
    public MetalinkDocument getMetalink(DBProduct product) throws SQLException {
        
        MetalinkDocument doc = MetalinkDocument.Factory.newInstance(OGCNamespacesXmlOptions.getInstance());
        MetalinkType metalink = doc.addNewMetalink();
        
//        String url = handler.getDsLoader().getAsBaseAddress() + "/" + 
//                Constants.WAR_FILE_PATH + "/" + 
//                this.handler.getService() + "/" + 
//                this.handler.getServiceId() + "/" + 
//                "dseo/1.0.0/";
        
        Map<String, String> urls = this.handler.getFileLoader().getDownloadServersBaseAddresses();
        
        
//        PublisherType publisher = metalink.addNewPublisher();
//        publisher.setUrl(url);
//        publisher.setName(handler.getServiceId());
        
        FilesType files = metalink.addNewFiles();
        for (String string : product.getFiles().keySet()) {
            DBProduct.DBProductFile file = product.getFiles().get(string);
            FileType f = files.addNewFile();
            
            if (file.getProductPath() == null || file.getProductPath().equals("")) {
                f.setName(file.getName() + "." + file.getExtension());
            }
            else {
                f.setName(file.getProductPath() + "/" + file.getName() + "." + file.getExtension());
            }
            
            ResourcesType resources = f.addNewResources();
            
            for (String string1 : urls.keySet()) {
                
                String url = urls.get(string1) + "dseo/1.0.0/";
                
                ResourcesType.Url resource = resources.addNewUrl();
                resource.setType(ResourcesType.Url.Type.HTTP);
                resource.setStringValue(url + "file/" + file.getId());
            }
        }
        
        
        return doc;
    }
}
