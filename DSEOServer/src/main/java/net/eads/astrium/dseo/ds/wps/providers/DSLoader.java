/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.wps.providers;

import java.io.File;
import net.eads.astrium.dseo.util.logging.DreamLogger;
import java.util.List;
import net.eads.astrium.dseo.DreamEngine;
import net.eads.astrium.dseo.dbhandler.ApplicationServerLoader;
import net.eads.astrium.dseo.dbhandler.DBOperations;
import net.eads.astrium.dseo.providers.GenericProvider;
import net.eads.astrium.dseo.util.urlhandling.URIHandling;
import net.eads.astrium.dseo.ds.wps.workers.DSWorker;
import net.eads.astrium.dseo.rs.GenericRESTService;
import net.eads.astrium.dseo.util.Constants;
import net.eads.astrium.dseo.util.FileHandler;
import net.eads.astrium.dseo.wps.rs.parsers.RestWPSFileInputParser;

/**
 *
 * @author re-sulrich
 */
public class DSLoader implements GenericProvider {
    
    @Override
    public boolean reload() throws Exception {
        List<String> res = load();
        return (res != null);
    }
    
    public List<String> load() throws Exception {
    
        String serviceName = "ds";
        
        String url = null;
        try {
            
            url = URIHandling.Factory.getHandler("tomcat").getServerBaseURL();
        } catch (Exception e) {
            e.printStackTrace();
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Not possible to get the local host address : error is " + e.getClass().getName());
        }
        
        DBOperations dbop = new DBOperations("DSEODatabase");
        
        ApplicationServerLoader asLoader = ApplicationServerLoader.createApplicationServerLoaderFromURL(dbop, url);
        
        List<String> dss = asLoader.getDSIds();
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Found :  " + dss.size() + " ds servers.");

        DreamEngine.registerService(serviceName, "http", DSWorker.class);
        
        for (String ds : dss) {
            DSWorker worker = new DSWorker(ds, url, dbop);
            
            DreamEngine.addServiceInstance(serviceName, ds, worker);
            //Re-init file inputs if some new ones have been loaded
            if (!GenericRESTService.postMPParsers.containsKey("wps")) {
                ((RestWPSFileInputParser)GenericRESTService.postMPParsers.get("wps")).
                        reloadFileParameters(worker.getProcesses());
            }
        }
        
        File tempDir = new File(Constants.IPS_WS_CONF_FOLDER + "temp" + File.separator);
        if (tempDir.exists() && tempDir.isDirectory()) {
            FileHandler.deleteFolder(tempDir);
        }
        
        File prodDir = new File(Constants.IPS_WS_CONF_FOLDER + "products" + File.separator);
        if (prodDir.exists() && prodDir.isDirectory()) {
            FileHandler.deleteFolder(prodDir);
        }
        
        return dss;
    }
}
