/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo;

import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.dseo.ds.fileimport.FileImporter;
import net.eads.astrium.ips.dseo.ds.fileimport.FolderImporter;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author re-sulrich
 */
public class TestFileImporterHeritage {

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
    public void testGetSuperclass() throws Exception {
        
        FolderImporter importer = new FolderImporter(new DSDBHandler("Download_Server_1", url, user, passwd, schema));
        
        System.out.println("" + importer.getClass().getSuperclass().getName());
    }
    @Test
    public void test() throws Exception {
        FileImporter importer = new FileImporter(new DSDBHandler("Download_Server_1", url, user, passwd, schema));
        System.out.println("" + importer.getSubInstancesTree());
    }
}
