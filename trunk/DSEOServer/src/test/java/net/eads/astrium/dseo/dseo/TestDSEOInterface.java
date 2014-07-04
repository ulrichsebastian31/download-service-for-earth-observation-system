/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.dseo;

import net.eads.astrium.dseo.ds.database.DSDBHandler;
import net.eads.astrium.dseo.ds.dseo.MetalinkGenerator;
import net.eads.astrium.ips.xmlparsing.OGCNamespacesXmlOptions;
import net.opengis.dseo.x10.CapabilitiesDocument;
import net.opengis.ows.x20.CodeType;
import net.opengis.ows.x20.OperationDocument;
import net.opengis.ows.x20.OperationsMetadataDocument;
import net.opengis.ows.x20.ResponsiblePartySubsetType;
import net.opengis.ows.x20.ServiceProviderDocument;
import org.junit.Before;
import org.junit.Test;
import org.metalinker.MetalinkDocument;
import org.metalinker.MetalinkType;

/**
 *
 * @author re-sulrich
 */
public class TestDSEOInterface {

    private String url;
    private String user;
    private String passwd;
    private String schema;
    
    @Before
    public void init() {
        
//        url = "jdbc:postgresql://10.2.200.247:5432/IPSDatabase";
//        url = "jdbc:postgresql://192.168.0.20:5432/IPSDatabase";
        url = "jdbc:postgresql://127.0.0.1:5432/IPSDatabase";

        user = "postgres";
        passwd = "postgres";
        schema = "MIPSDatabase";
    }
    
//    @Test
    public void testGetCapabilities() {
        CapabilitiesDocument doc = CapabilitiesDocument.Factory.newInstance(OGCNamespacesXmlOptions.getInstance());
        CapabilitiesDocument.Capabilities caps = doc.addNewCapabilities();
        ServiceProviderDocument.ServiceProvider provider = caps.addNewServiceProvider();
        provider.setProviderName("ESA");
        provider.addNewProviderSite();
        ResponsiblePartySubsetType contact = provider.addNewServiceContact();
        contact.setIndividualName("John Smith");
        contact.setPositionName("EO Operator");
        contact.addNewContactInfo().addNewPhone();
        CodeType role = contact.addNewRole();
        role.setCodeSpace("http://www.xmlspy.com");
        role.setStringValue("String");
        
        OperationsMetadataDocument.OperationsMetadata opMet = caps.addNewOperationsMetadata();
        
        OperationDocument.Operation getCaps = opMet.addNewOperation();
        getCaps.setName("GetCapabilities");
        getCaps.addNewDCP().addNewHTTP().addNewGet();
        
        OperationDocument.Operation getProd = opMet.addNewOperation();
        getProd.setName("GetProduct");
        getProd.addNewDCP().addNewHTTP().addNewGet();
        
        
        
        System.out.println("" + doc.xmlText(OGCNamespacesXmlOptions.getInstance()));
        

        
        
        
    }
    
    
    @Test
    public void testGetMetalink() throws Exception {
        DSDBHandler dsdbhandler = new DSDBHandler("Download_Server_1", url, user, passwd, schema);
        MetalinkGenerator metGen = new MetalinkGenerator(dsdbhandler);
        MetalinkDocument met = metGen.getMetalink(dsdbhandler.getFileLoader().getFilesByProduct("1"));
        
        System.out.println("" + met.xmlText(OGCNamespacesXmlOptions.getInstance()));
    }
}
