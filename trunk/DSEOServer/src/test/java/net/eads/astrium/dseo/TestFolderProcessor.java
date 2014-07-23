/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author re-sulrich
 */
public class TestFolderProcessor {

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
//    public void test() throws Exception {
//        DSDBHandler dbhandler = new DSDBHandler("Download_Server_1", url, user, passwd, schema);
//        ImageFolderProcessor imgProc = new ImageFolderProcessor(dbhandler);
//        MMFASUser dbuser = dbhandler.getUserLoader().getDMUser("f9565cf6dba885cf5e25150f936fd9c1");
//        
//        Map<String, DBFile> results = imgProc.processImageFolder(new File("C:\\Users\\re-sulrich\\Desktop\\logs\\"), dbuser);
//    
//        for (String string : results.keySet()) {
//            System.out.println("" + string + " - " + results.get(string));
//        }
//    }
}
