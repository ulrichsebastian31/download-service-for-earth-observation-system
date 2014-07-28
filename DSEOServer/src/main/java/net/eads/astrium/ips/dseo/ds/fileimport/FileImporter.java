/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.fileimport;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.structures.WebFileInputStream;
import net.eads.astrium.ips.util.Constants;
import net.eads.astrium.ips.util.DateHandler;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;

/**
 * This class and all its subclasses are based on the following concept :
 * - The purpose is to import a File, or a WebFileInputStream (see GenericService), in the Download Server system, by :
 *   - entering the file information in the database
 *   - saving the file in the share folder
 * - The main class (this one) contains the default processes : 
 *   - In the constructor, initialized with an instance of the DSDBHandler, that contains all the information about the given
 *     download server instance, we look for all the subclasses of the current class (i.e instanciate FolderImporter will find all the children of that class)
 * - In the canImportFile() function, the current instance determines if it can handle this type of file 
 *   (i.e XML file, directory, image, etc). The default implementation, held in this class, is that the file is not supported.
 * - In the importFile() function, the instance handles the import of the file in the DB + file system, and to send back 
 *   a Map containing for all the files imported (the imported file can be a folder, a zip, etc), the entered db information.
 *   The default implementation held in this class is to send back the name of the import file, with a "null" DBFile
 *   that shows that the file could not be imported.
 * - 
 * @author re-sulrich
 */
public class FileImporter {

    /**
     * Map that contains, for each file extension, the according FileImporter instance
     */
    private final Map<String, FileImporter> subInstances;
    protected final DSDBHandler dbHandler;
    protected final String shareFolderPath;

    public String getSubInstancesTree() {
        
        return getSubInstancesTree(0);
    }

    String getSubInstancesTree(int spaces) {
        
        String tree = getSpaces(spaces) + " - " + this.getClass().getName() + "\r\n";
        
        for (String string : this.subInstances.keySet()) {
            tree += this.subInstances.get(string).getSubInstancesTree(spaces + 1);
        }
        
        return tree;
    }
    
    String getSpaces(int spaces) {
        String spaceString = "";
        for (int i = 0; i < spaces; i++) {
            spaceString += " ";
        }
        return spaceString;
    }
    
    public String getDSSystemFilePath(DBFile file) {
        String outputFilePath = shareFolderPath + Constants.IPS_PRODUCTS_FOLDER_NAME + File.separator +
                           DateHandler.formatDateToSimpleNumbersOnly(file.getCreationTime()) + File.separator + 
                           file.getId() + File.separator + 
                           file.getName() + "." + file.getExtension();
        
        return outputFilePath;
    }
    
    public FileImporter(DSDBHandler dbHandler) 
            throws NoSuchMethodException, InstantiationException, 
                    IllegalAccessException, IllegalArgumentException, 
                    InvocationTargetException,
                    SQLException {
        
        this.dbHandler = dbHandler;
        this.shareFolderPath = dbHandler.getDsLoader().getDsShareFolder();
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("share folder : " + this.shareFolderPath);

        Map<String, Class<? extends FileImporter>> subClasses = 
                getDirectSubclasses(this.getClass());

        this.subInstances = new HashMap<>();
        

        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Class : " + this.getClass().getName());
        
        if (subClasses != null) {
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Subclasses : " + subClasses.size());
            for (String string : subClasses.keySet()) {
                //Call to the constructor which all subclasses should implement, 
                //that takes only the dbHandler instance as parameter
                
                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Adding subclass : " + string);
                this.subInstances.put(
                        string, 
                        subClasses.get(string).
                                getConstructor(DSDBHandler.class).newInstance(dbHandler)    
                    );
            }
        }
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("End Class : " + this.getClass().getName());
    }
    
    public Map<String, DBFile> importContent(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        
        Map<String, DBFile> files = null;
        
        FileImporter importer = null;
        for (String string : subInstances.keySet()) {
            FileImporter current = subInstances.get(string);
            if (current.canImportFile(inputFile)) {
                importer = current;
            }
        }
        if (importer == null) {
            files = this.importFile(inputFile, productFolderPath, parentFileId,use, user);
        }
        else {
            files = importer.importContent(inputFile, productFolderPath, parentFileId,use, user);
        }
        
        return files;
    }
    
    public Map<String, DBFile> importContent(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        
        Map<String, DBFile> files = null;
        
        FileImporter importer = null;
        for (String string : subInstances.keySet()) {
            FileImporter current = subInstances.get(string);
            if (current.canImportFile(inputFile)) {
                importer = current;
                break;
            }
        }
        if (importer == null) {//If no subclass handles the given input file, import in current instance
            
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                        "--> Handled by : " + this.getClass() + "\r\n\r\n\r\n");
            
            files = this.importFile(inputFile, productFolderPath, parentFileId, use, user);
        }
        else {//Else, delegate to subclass
            
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                        "--> Delegating to : " + importer.getClass());
            files = importer.importContent(inputFile, productFolderPath, parentFileId, use, user);
        }
        
        return files;
    }
    
    /**
     * 
     * @param inputFile
     * @return 
     */
    boolean canImportFile(WebFileInputStream inputFile) 
            throws Exception {
        return true;
    }
    
    Map<String, DBFile> importFile(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user)
            throws Exception {
        Map<String, DBFile> files = new HashMap<>();
        
        files.put(inputFile.getFileName(), null);
        
        return files;
    }
    
    boolean canImportFile(File inputFile) 
            throws Exception {
        return true;
    }
    
    Map<String, DBFile> importFile(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        
        Map<String, DBFile> files = new HashMap<>();
        
        files.put(inputFile.getName(), null);
        
        return files;
    }
    
    
    private static Map<String, Class<? extends FileImporter>> getDirectSubclasses(Class abstractWPSProcessClass) {

        Map<String, Class<? extends FileImporter>> processes = new HashMap<>();
        String packageName = "net.eads.astrium.ips.dseo.ds.fileimport";
        try {
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                        "Find subclasses of : " + abstractWPSProcessClass.getName()  + ""
                    + " in " + packageName);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            
            String packName = packageName.replace(".", "/");
            
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                    "packName : " + packName
                );
            Enumeration<URL> packagesURLs = classLoader.getResources(packName);
            
            
            while (packagesURLs.hasMoreElements()) {
                
                URL packageURL = packagesURLs.nextElement();
                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
                        "Scanning : " + packageURL.getPath());
//                
                if(packageURL.getProtocol().equals("jar")){
//                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Scanning jar : " + packageURL.getPath());
//                
                    Map<String, Class<? extends FileImporter>> processJar = 
                            processJar(packageName, packageURL, abstractWPSProcessClass);
                    processes.putAll(processJar);
                // loop through files in classpath
                }else{
//                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Scanning folder : " + packageURL.getPath());
                
                    Map<String, Class<? extends FileImporter>> processFolder = 
                            processFolder(packageName, packageURL, abstractWPSProcessClass);
                    processes.putAll(processFolder);
                }
            }
            
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
        return processes;
    }
    
    private  static Map<String, Class<? extends FileImporter>> processJar(
            String packageName, URL packageURL, Class abstractFileImporterClass) 
            throws UnsupportedEncodingException, IOException, ClassNotFoundException {
        
        Map<String, Class<? extends FileImporter>> processes = new HashMap<>();
        String packName = packageName.replace(".", "/");
        
        String jarFileName;
        JarFile jf ;
        Enumeration<JarEntry> jarEntries;
        String className;
        // build jar file name, then loop through zipped entries
        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));

//        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("> "+jarFileName);

        jf = new JarFile(jarFileName);
        jarEntries = jf.entries();
        while(jarEntries.hasMoreElements()){
            className = jarEntries.nextElement().getName();
//            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Class : " + className);
            if(
                    className.startsWith(packName) && 
                    className.length()>packName.length()+5){

                if (className.contains(".")) {
                    
                    String classPackName = className.substring(0, className.lastIndexOf('/'));
                    String classPackageName = classPackName.replaceAll("/", ".");
                    

                    className = className.substring(classPackName.length() + 1,className.lastIndexOf('.'));
                    
//                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
//                            " -> " + classPackageName + "." + className);
                
                    //Get class from name 
                    //and add it to the processes if the class is not abstract
                    Class processClass = Class.forName(classPackageName + "." + className);

                    boolean isAbstract = Modifier.isAbstract(processClass.getModifiers());
    
                    Class superClass = processClass.getSuperclass();
                    if (superClass != null) {
                        boolean isExtendingSuperclass = 
                                superClass.equals(abstractFileImporterClass);

                        if (!isAbstract && isExtendingSuperclass) {
//                            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(" -> " + classPackageName + "." + className + "");
                            processes.put(className, processClass);
                        }
                    }
                }
                else {
//                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Folder : " + className);
                }
            }
        }

        return processes;
    }
    
    
    private static Map<String, Class<? extends FileImporter>> processFolder(
            String packageName, URL packageURL, Class abstractFileImporterClass) 
            throws URISyntaxException, ClassNotFoundException {
        
        Map<String, Class<? extends FileImporter>> processes = new HashMap<>();
        
        URI uri = new URI(packageURL.toString());
        File folder = new File(uri.getPath());
        // won't work with path which contains blank (%20)
        // File folder = new File(packageURL.getFile());
        List<File> files = getAllFiles(folder);
        
        String className;
        for(File actual: files){
            
            String classPath = actual.getParent();
            className = actual.getName();
            
//            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("  --  " + className);
            
            if (className.contains(".")) {
                    
                className = className.substring(0, className.lastIndexOf('.'));
                
                String cp = null;
                if (File.separator.equals("/")) cp = classPath.replaceAll("/", ".");
                else cp = classPath.replaceAll("\\\\", ".");
                
                String classPackageName = cp.substring(cp.indexOf("net.eads.astrium.ips.dseo"));
                
//                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(
//                        " -> " + classPackageName + "." + className);
                //Get class from name 
                //and add it to the processes if the class is not abstract
                Class processClass = Class.forName(classPackageName + "." + className);

                boolean isAbstract = Modifier.isAbstract(processClass.getModifiers());
                
                Class superClass = processClass.getSuperclass();
                if (superClass != null) {
                    
                    boolean isExtendingSuperclass = 
                            processClass.getSuperclass().equals(abstractFileImporterClass);

                    if (!isAbstract && isExtendingSuperclass) {
//                        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(" -> " + classPackageName + "." + className + "");
                        processes.put(className, processClass);
                    }
                }
                else {
//                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Interface : " + className);
                }
            }
            else {
//                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Folder : " + className);
                
            }
        }
        
        return processes;
    }
    
    private static  List<File> getAllFiles(File folder) {
        
        List<File> files = new ArrayList<>();
        
//        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Directory : " + folder.getName());
        
        File[] f = folder.listFiles();
        for (int i = 0; i < f.length; i++) {
            File file = f[i];
            if (file.isDirectory()) {
                files.addAll(getAllFiles(file));
            }
            else {
//                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("-> Adding : " + file.getName());
                files.add(file);
            }
        }
        
        return files;
    }
}
