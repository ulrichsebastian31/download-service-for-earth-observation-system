/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.ds.wps.workers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import net.eads.astrium.dseo.dbhandler.DBOperations;
import net.eads.astrium.dseo.ds.database.DSDBHandler;
import net.eads.astrium.dseo.util.logging.DreamLogger;
import net.eads.astrium.dseo.wps.ProcessesLoader;
import net.eads.astrium.dseo.wps.WPSProcessMap;
import net.eads.astrium.dseo.wps.processes.FileInputsDatabaseWPSProcess;
import net.eads.astrium.dseo.wps.processes.SimpleInputsDatabaseWPSProcess;
import net.eads.astrium.dseo.wps.workers.GenericWPSWorker;

/**
 *
 * @author re-sulrich
 */
public class DSWorker extends GenericWPSWorker {

    private final String loggerId;

    @Override
    public String getLoggerId() {
        return loggerId;
    }

    private final String serverBaseURI;
    
    private final DSDBHandler databaseConfiguration;
    
    @Override
    public DSDBHandler getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Override
    public String getServerBaseURI() {
        return serverBaseURI;
    }

    @Override
    public String getService() {
        return databaseConfiguration.getService();
    }

    @Override
    public String getServiceInstance() {
        return databaseConfiguration.getServiceId();
    }
    
    @Override
    public String getInstanceDescription() throws SQLException, ParseException {
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("");
        
        String description = "<ds>\n";
        description += "<dsId>" + databaseConfiguration.getServiceId() + "</dsId>\n";
        description += "<description>";
         description += "Image Download Server " + databaseConfiguration.getServiceId() + "";
        description += "</description>\n";
        description += "</ds>";
        
        return description;
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    public DSWorker(String dsId, String serverBaseURI, DBOperations dboperations) 
            throws Exception
    {
        this.serverBaseURI = serverBaseURI;
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Creating DBHandler for " + dsId);
        
        databaseConfiguration = new DSDBHandler(dsId, dboperations);
        
        WPSProcessMap procClasses = ProcessesLoader.getProcesses("net.eads.astrium.dseo.ds.wps.processes");
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Found  " + procClasses.size() + " processes in package.");
        
        
        //WPS processes instanciation
        this.addProcesses(procClasses.getNoDatabaseInstances());
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Nb no db Inst : " + this.getProcesses().size());
        
        Map<String, SimpleInputsDatabaseWPSProcess> dbproc = procClasses.getDatabaseInstances();
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Nb db Inst : " + dbproc.size());
        for (String string : dbproc.keySet()) {

            dbproc.get(string).setHandler(databaseConfiguration);
            this.addProcess(dbproc.get(string));
        }
        
        Map<String, FileInputsDatabaseWPSProcess> dbfileproc = procClasses.getDatabaseFileInputInstances();
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Nb db file Inst : " + dbfileproc.size());
        for (String string : dbfileproc.keySet()) {

            dbfileproc.get(string).setHandler(databaseConfiguration);
            this.addProcess(dbfileproc.get(string));
        }
        
        //Beware : has to be done after call to the assignment of the databaseConfiguration as it uses getService() which uses databaseConfiguration
        loggerId = DreamLogger.createLogger(this.databaseConfiguration.getService(), this.databaseConfiguration.getServiceId());
    }
    
    
    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
