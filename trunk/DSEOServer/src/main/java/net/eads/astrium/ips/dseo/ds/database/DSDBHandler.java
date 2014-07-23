/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.ips.dseo.ds.database;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import net.eads.astrium.ips.database.DBHandler;
import net.eads.astrium.ips.dbhandler.DBOperations;
import net.eads.astrium.ips.dbhandler.DSHandler;
import net.eads.astrium.ips.dbhandler.FileHandler;
import net.eads.astrium.ips.dbhandler.usermanagement.MMFASUserLoader;
import net.eads.astrium.ips.dseo.ds.fileimport.FileImporter;
import net.eads.astrium.ips.util.logging.DreamLogger;

/**
 *
 * @author re-sulrich
 */
public class DSDBHandler extends DBHandler {

    @Override
    public String getService() {
        return "ds";
    }

    @Override
    public String getServiceId() {
        return dsId;
    }
    
    private final String dsId;
    
    private final FileHandler fileLoader;
    private final DSHandler dsLoader;
    private final MMFASUserLoader userLoader;

    private FileImporter fileImporter;
    
    public FileHandler getFileLoader() {
        return fileLoader;
    }

    public DSHandler getDsLoader() {
        return dsLoader;
    }

    public MMFASUserLoader getUserLoader() {
        return userLoader;
    }

    public FileImporter getFileImporter() {
        return fileImporter;
    }

    public DSDBHandler(String dsId, DBOperations dboperations) 
            throws Exception {
        super(dboperations);
        this.dsId = dsId;
        fileLoader = new FileHandler(this.getDboperations());
        dsLoader = new DSHandler(dsId, this.getDboperations());
        //User loader does not connect to the same schema, so don't use same dboperations !
        userLoader = new MMFASUserLoader();
        
        startFileImporter();
    }

    public DSDBHandler(String dsId, String databaseName) 
            throws Exception {
        super(databaseName);
        this.dsId = dsId;
        fileLoader = new FileHandler(this.getDboperations());
        dsLoader = new DSHandler(dsId, this.getDboperations());
        //User loader does not connect to the same schema, so don't use same dboperations !
        userLoader = new MMFASUserLoader();
        
        startFileImporter();
    }

    public DSDBHandler(
            String dsId, String databaseURL, String user, String pass, String schema) 
            throws Exception {
        super(databaseURL, user, pass, schema);
        this.dsId = dsId;
        fileLoader = new FileHandler(this.getDboperations());
        dsLoader = new DSHandler(dsId, this.getDboperations());
        //User loader does not connect to the same schema, so don't use same dboperations !
        userLoader = new MMFASUserLoader(databaseURL, user, pass, "UserDatabase");
        
        startFileImporter();
    }
    
    private void startFileImporter() throws Exception {
        fileImporter = new FileImporter(this);
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(""
                + "Created file importer");
    }
}
