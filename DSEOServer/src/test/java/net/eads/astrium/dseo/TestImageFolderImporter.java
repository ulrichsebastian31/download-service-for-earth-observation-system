/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo;

import java.io.File;
import java.util.Map;
import net.eads.astrium.ips.database.DBHandler;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.dseo.ds.fileimport.FileImporter;
import net.eads.astrium.ips.dseo.ds.fileimport.ImageFolderImporter;
import net.eads.astrium.ips.util.structures.DBFile;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author re-sulrich
 */
public class TestImageFolderImporter {

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
    
    @Test
    public void test() throws Exception {
        DSDBHandler dbh = new DSDBHandler("Download_Server_1", url, user, passwd, schema);
        
//        ImageFolderImporter iFI = new ImageFolderImporter(dbh);
        FileImporter iFI = new FileImporter(dbh);
        
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Create file importer finished");
        System.out.println();
        System.out.println();
        System.out.println();
        
        Map<String, DBFile> files = iFI.importContent(
                new File("C:\\Users\\re-sulrich\\Desktop\\S1A_IW_GRDM_1SSH_20120109T054406_20120109T054424_001889_000001_ACD9.SAFE\\"), 
                null, null, "product", null);
        
        System.out.println();
        System.out.println();
        System.out.println();
        for (String string : files.keySet()) {
            
            System.out.println(" - " + string + " : " + files.get(string));
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
