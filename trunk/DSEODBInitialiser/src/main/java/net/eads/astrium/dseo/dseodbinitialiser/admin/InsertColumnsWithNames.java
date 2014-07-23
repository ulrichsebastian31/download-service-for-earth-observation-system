/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.dseodbinitialiser.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexion;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexionParameter;

/**
 *
 * @author re-sulrich
 */
public class InsertColumnsWithNames {
    
    public static void insertMmfasuserColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'userdatabase.mmfasuser.userid'");
        column1.add("''");
        column1.add("'userdatabase.mmfasuser'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'userdatabase.mmfasuser.name'");
        column1.add("''");
        column1.add("'userdatabase.mmfasuser'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'userdatabase.mmfasuser.email'");
        column1.add("''");
        column1.add("'userdatabase.mmfasuser'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'userdatabase.mmfasuser.phonenumber'");
        column1.add("''");
        column1.add("'userdatabase.mmfasuser'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'userdatabase.mmfasuser.maxdownloadmanagers'");
        column1.add("''");
        column1.add("'userdatabase.mmfasuser'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertMipsColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mips.mipsid'");
        column1.add("''");
        column1.add("'mipsdatabase.mips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mips.name'");
        column1.add("''");
        column1.add("'mipsdatabase.mips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mips.description'");
        column1.add("''");
        column1.add("'mipsdatabase.mips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mips.asid'");
        column1.add("''");
        column1.add("'mipsdatabase.mips'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertIpsColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ips.ipsid'");
        column1.add("''");
        column1.add("'mipsdatabase.ips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ips.name'");
        column1.add("''");
        column1.add("'mipsdatabase.ips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ips.description'");
        column1.add("''");
        column1.add("'mipsdatabase.ips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ips.asid'");
        column1.add("''");
        column1.add("'mipsdatabase.ips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ips.dsid'");
        column1.add("''");
        column1.add("'mipsdatabase.ips'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertLibrarydependenciesColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.librarydependencies.libid'");
        column1.add("''");
        column1.add("'mipsdatabase.librarydependencies'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.librarydependencies.dependencyid'");
        column1.add("''");
        column1.add("'mipsdatabase.librarydependencies'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertIpsprocessesColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ipsprocesses.processid'");
        column1.add("''");
        column1.add("'mipsdatabase.ipsprocesses'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.ipsprocesses.ipsid'");
        column1.add("''");
        column1.add("'mipsdatabase.ipsprocesses'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertMipsipsColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mipsips.mipsid'");
        column1.add("''");
        column1.add("'mipsdatabase.mipsips'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.mipsips.ipsid'");
        column1.add("''");
        column1.add("'mipsdatabase.mipsips'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertApplicationserverColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.applicationserver.asid'");
        column1.add("''");
        column1.add("'mipsdatabase.applicationserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.applicationserver.name'");
        column1.add("''");
        column1.add("'mipsdatabase.applicationserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.applicationserver.description'");
        column1.add("''");
        column1.add("'mipsdatabase.applicationserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.applicationserver.serverbaseaddress'");
        column1.add("''");
        column1.add("'mipsdatabase.applicationserver'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertAsdsColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.asds.dsid'");
        column1.add("''");
        column1.add("'mipsdatabase.asds'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.asds.asid'");
        column1.add("''");
        column1.add("'mipsdatabase.asds'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.asds.mountpath'");
        column1.add("''");
        column1.add("'mipsdatabase.asds'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertLibraryColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.library.libid'");
        column1.add("''");
        column1.add("'mipsdatabase.library'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.library.name'");
        column1.add("''");
        column1.add("'mipsdatabase.library'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.library.description'");
        column1.add("''");
        column1.add("'mipsdatabase.library'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.library.lib'");
        column1.add("''");
        column1.add("'mipsdatabase.library'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertOsColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.os.osid'");
        column1.add("''");
        column1.add("'mipsdatabase.os'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.os.name'");
        column1.add("''");
        column1.add("'mipsdatabase.os'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.os.version'");
        column1.add("''");
        column1.add("'mipsdatabase.os'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.os.manufacturer'");
        column1.add("''");
        column1.add("'mipsdatabase.os'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.os.architecture'");
        column1.add("''");
        column1.add("'mipsdatabase.os'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertProcesslibrariesColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processlibraries.libid'");
        column1.add("''");
        column1.add("'mipsdatabase.processlibraries'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processlibraries.processid'");
        column1.add("''");
        column1.add("'mipsdatabase.processlibraries'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processlibraries.osid'");
        column1.add("''");
        column1.add("'mipsdatabase.processlibraries'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertProcessparameterColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.parameterid'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.name'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.description'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.type'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.wpspurpose'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.execorder'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.processparameter.processid'");
        column1.add("''");
        column1.add("'mipsdatabase.processparameter'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertFiletypeColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.filetype.filetypeid'");
        column1.add("''");
        column1.add("'mipsdatabase.filetype'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.filetype.name'");
        column1.add("''");
        column1.add("'mipsdatabase.filetype'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.filetype.filetype'");
        column1.add("''");
        column1.add("'mipsdatabase.filetype'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.filetype.extension'");
        column1.add("''");
        column1.add("'mipsdatabase.filetype'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.filetype.filedefaultname'");
        column1.add("''");
        column1.add("'mipsdatabase.filetype'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertParameterfiletypesColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parameterfiletypes.parameterid'");
        column1.add("''");
        column1.add("'mipsdatabase.parameterfiletypes'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parameterfiletypes.filetypeid'");
        column1.add("''");
        column1.add("'mipsdatabase.parameterfiletypes'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertProcessColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.process.processid'");
        column1.add("''");
        column1.add("'mipsdatabase.process'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.process.name'");
        column1.add("''");
        column1.add("'mipsdatabase.process'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.process.description'");
        column1.add("''");
        column1.add("'mipsdatabase.process'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertDownloadserverColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.downloadserver.dsid'");
        column1.add("''");
        column1.add("'mipsdatabase.downloadserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.downloadserver.name'");
        column1.add("''");
        column1.add("'mipsdatabase.downloadserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.downloadserver.description'");
        column1.add("''");
        column1.add("'mipsdatabase.downloadserver'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.downloadserver.asid'");
        column1.add("''");
        column1.add("'mipsdatabase.downloadserver'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertFileColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.fileid'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.name'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.creationtime'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.parent'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.filetypeid'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.dsid'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.file.userid'");
        column1.add("''");
        column1.add("'mipsdatabase.file'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertExecutionColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.execid'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.starttime'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.endtime'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.status'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.log'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.processid'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.execution.userid'");
        column1.add("''");
        column1.add("'mipsdatabase.execution'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertParametervalueColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parametervalue.pvid'");
        column1.add("''");
        column1.add("'mipsdatabase.parametervalue'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parametervalue.value'");
        column1.add("''");
        column1.add("'mipsdatabase.parametervalue'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parametervalue.fileid'");
        column1.add("''");
        column1.add("'mipsdatabase.parametervalue'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parametervalue.parameterid'");
        column1.add("''");
        column1.add("'mipsdatabase.parametervalue'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'mipsdatabase.parametervalue.execid'");
        column1.add("''");
        column1.add("'mipsdatabase.parametervalue'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertDataaccessrequestColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.darid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.clientip'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.sensortaskid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.creationtime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.status'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.dataaccessrequest.manager'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.dataaccessrequest'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertDownloadColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.downloadid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.status'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.percentcompleted'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.message'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.lastactivitytime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.starttime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.endtime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.dar'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.download.product'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.download'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertDownloadmanagerColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.downloadmanagerid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.name'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.status'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.refreshperiod'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.deconnectionperiod'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.lastactivitytime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.starttime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.downloadmanager.userid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.downloadmanager'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertErrorColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.errorid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.errorcode'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.errormessage'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.errordescription'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.downloadmanager'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.error.download'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.error'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertLnk_product_userColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.lnk_product_user.userid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.lnk_product_user'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.lnk_product_user.product'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.lnk_product_user'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertProductColumns() throws SQLException {
        String table = "MMFASColumn";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("columnId");
        fields.add("name");
        fields.add("tableId");
        
        List<List<String>> values = new ArrayList<>();
        List<String> column1;

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.productid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.downloadurl'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.mmfassegmentid'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.availibility'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.nbfiles'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.size'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        column1 = new ArrayList<>();
        column1.add("'downloadmanagerdatabase.product.lastupdatetime'");
        column1.add("''");
        column1.add("'downloadmanagerdatabase.product'");
        values.add(column1);

        TestConnexion.insert(
                table, 
                fields, 
                values);
    }



    public static void insertAllColumns() throws SQLException {
        

//        TestConnexionParameter.setUrl("localhost:5432");
//        TestConnexionParameter.setUser("postgres");
//        TestConnexionParameter.setPass("postgres");
//        
//        TestConnexionParameter.setAdminDatabase("AdminDatabase");
        
        
        TestConnexion.conn = TestConnexion.createAdminDBConnexion();
                insertMmfasuserColumns();
        insertMipsColumns();
        insertIpsColumns();
        insertLibrarydependenciesColumns();
        insertIpsprocessesColumns();
        insertMipsipsColumns();
        insertApplicationserverColumns();
        insertAsdsColumns();
        insertLibraryColumns();
        insertOsColumns();
        insertProcesslibrariesColumns();
        insertProcessparameterColumns();
        insertFiletypeColumns();
        insertParameterfiletypesColumns();
        insertProcessColumns();
        insertDownloadserverColumns();
        insertFileColumns();
        insertExecutionColumns();
        insertParametervalueColumns();
        insertDataaccessrequestColumns();
        insertDownloadColumns();
        insertDownloadmanagerColumns();
        insertErrorColumns();
        insertLnk_product_userColumns();
        insertProductColumns();
    }

}
