/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.urihandling;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.eads.astrium.ips.concurrence.DreamThread;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.urlhandling.URIHandling;

/**
 *
 * @author re-sulrich
 */
public class ConfURIHandler implements URIHandling {
    
    static {
        try {
            URIHandling.Factory.addHandler("conf", new ConfURIHandler());
        } catch (Exception ex) {
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServerBaseURL() throws UnknownHostException, IOException {
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream content = classLoader.getResourceAsStream("conf.properties");
        
        Properties props = new Properties();
        props.load(content);
        
        String serverBaseAdress = props.getProperty("serverbaseadress");
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Base Address from conf : " + serverBaseAdress);
        
        return serverBaseAdress;
    }
}