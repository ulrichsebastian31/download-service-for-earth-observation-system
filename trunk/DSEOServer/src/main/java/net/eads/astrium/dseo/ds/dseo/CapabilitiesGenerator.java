/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.dseo;

import net.eads.astrium.ips.xmlparsing.OGCNamespacesXmlOptions;
import net.opengis.dseo.x10.CapabilitiesDocument;
import net.opengis.ows.x20.CodeType;
import net.opengis.ows.x20.OperationDocument;
import net.opengis.ows.x20.OperationsMetadataDocument;
import net.opengis.ows.x20.ResponsiblePartySubsetType;
import net.opengis.ows.x20.ServiceProviderDocument;

/**
 *
 * @author re-sulrich
 */
public class CapabilitiesGenerator {

    public static CapabilitiesDocument getCapabilities() {
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
        
        
        return doc;
    }
}
