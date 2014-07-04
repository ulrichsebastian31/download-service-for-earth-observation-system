/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.ds.wps.providers;

import net.eads.astrium.dseo.util.logging.DreamLogger;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.model.AbstractResourceModelContext;
import com.sun.jersey.api.model.AbstractResourceModelListener;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import net.eads.astrium.dseo.utilityservices.RestartService;
import net.eads.astrium.dseo.wps.providers.WPSServiceStarter;
import net.eads.astrium.dseo.wps.providers.WPSServiceStarter;

/**
 *
 * @author re-sulrich
 */
@Provider
public final class DSStarter implements AbstractResourceModelListener {
    
    @Context static ResourceConfig context;
    
    @Override
    public void onLoaded(AbstractResourceModelContext modelContext) {
        DreamLogger.bindThreadToStartLogger(Thread.currentThread().getId());
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("##### Loading Download server...");
        
        try {
            DSLoader loader = new DSLoader();
            
            //Registering starter for restarting service
            RestartService.setServiceStarter("ds", loader);

            List<String> loaded = loader.load();
            if (loaded != null) {
                WPSServiceStarter.ParserRegistrator.registerWPSRestParsers();
            }
        } catch (Exception ex) {
            
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("" + ex.getMessage() + " : " + ex.getStackTrace()[0].toString());
            ex.printStackTrace();
        }
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("##### Loaded Download server.");
    }
}