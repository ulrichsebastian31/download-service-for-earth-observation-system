/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.dseodbinitialiser.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static net.eads.astrium.dseo.dseodbinitialiser.TestConnexion.insert;
import net.eads.astrium.dseo.util.DateHandler;

/**
 *
 * @author re-sulrich
 */
public class InsertTablesWithNames {

    public static void insertTables() throws SQLException {
        
        String table = "MMFASTable";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("tableId");
        fields.add("name");
        fields.add("lastUpdateTime");
        
        List<List<String>> values = new ArrayList<>();
        List<String> table1;
        
        /**
         * User database
         */
        table1 = new ArrayList<String>();
        table1.add("'userdatabase.mmfasuser'");
        table1.add("'MMFA System User'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        /**
         * MIPS Database
         */table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.mips'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.ips'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.librarydependencies'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.ipsprocesses'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.mipsips'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.applicationserver'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.asds'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.library'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.os'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.processlibraries'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.processparameter'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.filetype'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.parameterfiletypes'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.process'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.downloadserver'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.file'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.execution'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        

        table1 = new ArrayList<String>();
        table1.add("'mipsdatabase.parametervalue'");
        table1.add("''");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        /**
         * Download Manager database
         */
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.dataaccessrequest'");
        table1.add("'Data Access Request'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.download'");
        table1.add("'Product Download'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.downloadmanager'");
        table1.add("'Download Manager'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.error'");
        table1.add("'Download errors'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.lnk_product_user'");
        table1.add("'User Products'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        table1 = new ArrayList<String>();
        table1.add("'downloadmanagerdatabase.product'");
        table1.add("'Product'");
        table1.add("'"+ DateHandler.formatDate(Calendar.getInstance().getTime())+"'");
        values.add(table1);
        
        insert(
                table, 
                fields, 
                values);
    }
}
