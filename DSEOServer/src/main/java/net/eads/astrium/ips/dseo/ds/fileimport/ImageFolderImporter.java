/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.fileimport;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.structures.WebFileInputStream;
import net.eads.astrium.ips.util.FormatHandler;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;

/**
 *
 * @author re-sulrich
 */
public class ImageFolderImporter extends FolderImporter {

    public ImageFolderImporter(DSDBHandler dbHandler) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        super(dbHandler);
    }

    @Override
    boolean canImportFile(WebFileInputStream inputFile) {
        return false;
    }

    @Override
    boolean canImportFile(File inputFile) {
        //        return ((inputFile != null) && (inputFile.isDirectory()));
        File[] files = listAllFiles(inputFile);
        File mainImage = this.findMainImageFile(files);
        
        if (mainImage != null) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Map<String, DBFile> importFile(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) {
        Map<String, DBFile> files = new HashMap<>();
        
        return files;
    }

    @Override
    public Map<String, DBFile> importFile(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        Map<String, DBFile> files = new HashMap<>();
        
        String folderPath = inputFile.getPath();
        
//        String folderName = inputFile.getName();
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(""
                + "Loading Image Folder : " + folderPath);
        
        File[] inputFiles = listAllFiles(inputFile);
        File mainImage = this.findMainImageFile(inputFiles);
        String mainImageFolderPath = mainImage.getParent();
        String mainImageName = mainImage.getName();
        String productMainImageFolderPath = mainImageFolderPath.substring(folderPath.length());
        
        Map<String, DBFile> mainImageInsertRes = new ImageImporter(dbHandler).
                                    importContent(mainImage, productMainImageFolderPath, parentFileId, "product", user);
        files.putAll(mainImageInsertRes);
        DBFile dbMainImage = mainImageInsertRes.get(mainImageName);
        
        DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(""
                + "Inserted main image : " + mainImageName + " in " + dbMainImage + "\r\n"
                    + "fileFolderPath : " + mainImageFolderPath + "\r\n"
                    + "fileName : " + mainImageName + "\r\n"
                    + "productFileFolderPath : " + productMainImageFolderPath + "\r\n");

        String preview = null;
        String insertUse = "";
        
        for (int i = 0; i < inputFiles.length; i++) {
            
            File file = inputFiles[i];
            String fileName = file.getName();
            if (!mainImageName.equals(fileName)) {
                
                String withoutExt = fileName.substring(0, fileName.lastIndexOf("."));
                String tlc = fileName.toLowerCase();
                
                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info(""
                        + "Filename : " + fileName
                        + "withoutExt : " + withoutExt
                        + "toLowerCase : " + tlc);
                
                if (//If only one image and the one quicklook, it could be named only "quicklook"
                        withoutExt.equalsIgnoreCase("quick-look") || 
                        withoutExt.equalsIgnoreCase("quick_look") || 
                        withoutExt.equalsIgnoreCase("preview") || 
                        withoutExt.equalsIgnoreCase("quicklook") ||
                        (//Or it could be named <imagename>-quicklook
                            fileName.contains(dbMainImage.getName()) && 
                            (
                                tlc.contains("quick-look") || 
                                tlc.contains("quick_look") || 
                                tlc.contains("preview") || 
                                tlc.contains("quicklook")
                            )
                        )
                    ) {
                    preview = fileName;
                    insertUse = "preview";
                }
                else {
                    
                    insertUse = "metadata";
                }
                
                String fileFolderPath = file.getParent();
                String productFileFolderPath = fileFolderPath.substring(folderPath.length());

                Map<String, DBFile> fileInsertRes = new FileImporter(dbHandler).
                        importContent(file, productFileFolderPath, dbMainImage.getId(), insertUse, user);

                files.putAll(fileInsertRes);


                DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Inserted : "+files.get(fileName)+"\r\n"
                        + "fileFolderPath : " + fileFolderPath + "\r\n"
                        + "fileName : " + fileName + "\r\n"
                        + "productFileFolderPath : " + productFileFolderPath + "\r\n");
            
            }
        }
        
        if (preview == null) {
            //Create preview
        }
        
        inputFile.delete();
        
        return files;
    }
    
    
    private static File[] listAllFiles(File folder) {
        
        List<File> files = new ArrayList<>();
        
        File[] f = folder.listFiles();
        if (f != null && f.length > 0) {
            for (int i = 0; i < f.length; i++) {
                File file = f[i];
                if (file.isDirectory()) {
                    File[] subFiles = listAllFiles(file);
                    for (int j = 0; j < subFiles.length; j++) {
                        File file1 = subFiles[j];
                        files.add(file1);
                    }
                }
                else {
                    files.add(file);
                }
            }
        }
        
        return files.toArray(new File[files.size()]);
    }
    
    /**
     * Permits to scan a folder and find the file that : 
     *  - is an image
     *  - is bigger in size than all the other images
     * @return 
     */
    private File findMainImageFile(File[] files) {
        
        File mainImage = null;
        long maxSize = 0;
        
        for (int i = 0; i < files.length; i++) {
            File inFile = files[i];
            DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("File : " + inFile.getName());
            
            if (!inFile.isDirectory()) {
                String extension = inFile.getName().substring(inFile.getName().lastIndexOf(".") + 1);
                String mimeType = FormatHandler.extensionToMimeType(extension);

                if (mimeType != null && mimeType.contains("image")) {
                    long size = inFile.length();
                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).info("Found file : length : " + size);

                    if (size > maxSize) {

                        mainImage = inFile;
                        maxSize = size;
                    }
                }
            }
        }
        
        return mainImage;
    } 
    
}
