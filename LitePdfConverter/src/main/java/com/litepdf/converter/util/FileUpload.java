package com.litepdf.converter.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.litepdf.converter.model.FileUploadResponseVO;

@Component
public class FileUpload {
    //private static final String[] ALLOWED_FILE_TYPES = {"image/jpeg", "image/jpg", "image/gif"};
	private List<String> allowedFileTypes = new ArrayList<String>();
    private static final Long MAX_FILE_SIZE = 5242880L; //1MB
    private static final String UPLOAD_FILE_PATH = "/images/";
    
    @Autowired
	ServletContext servletContext;
  
    //String uploadFilePath = "C:\\assets\\";
    private static final String BASE_FILE_PATH = "/WEB-INF/files/";
    private static final String INPUT_PATH = "/input";
    private static final String OUTPUT_PATH = "/output";
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.sss");

    private String createFolderNameWithTime() {
    	
    	//return servletContext.getRealPath(BASE_FILE_PATH)+sdf.format(new Date());
    	return sdf.format(new Date());
    	
    	
    }
    
    
    public FileUploadResponseVO process(MultipartFile[] files, List<String> allowedFileTypes) {
    	
    	this.allowedFileTypes = allowedFileTypes; 
    	
    	FileUploadResponseVO fileUploadResponseVO = new FileUploadResponseVO();
    	List<String> errorList = new ArrayList<>();
    	List<String> fileNames = new ArrayList<>();
    	String directory = "";
    	
    	String inputFileDir = createFolderNameWithTime()+INPUT_PATH; 
    	String oututFileDir = createFolderNameWithTime()+OUTPUT_PATH;
    	
    	Integer count = 0;
    	
    	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    	//need to get all file as Array of MultipartFile[] and add a for loop right below to iterate all the files
    	//for(MultipartFile file : files) {
    		//add everything here
    	//}
    	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	System.out.println(createFolderNameWithTime());
    	
    	/*
    	 * Assigning the input and output directories
    	 */
    	File fileSaveDir = new File(servletContext.getRealPath(BASE_FILE_PATH)+inputFileDir);
    	File fileOutDir = new File(servletContext.getRealPath(BASE_FILE_PATH)+oututFileDir);
    	
    	/*
    	 * creating the input and output directories
    	 */
        if (!fileSaveDir.exists()) fileSaveDir.mkdirs();
        if (!fileOutDir.exists()) fileOutDir.mkdirs();
    	
    	for(MultipartFile file : files) {
    		count++;
    		String fileName = sdf.format(new Date())+"-"+count.toString()+"-"+file.getOriginalFilename();
    		if (!file.isEmpty()) {
                String contentType = file.getContentType().toString().toLowerCase();
               
                if (isValidContentType(contentType)) {
                	
                    if (belowMaxFileSize(file.getSize())) {
                        
                        System.out.println("fileSaveDir---"+fileSaveDir.toString());
                        String newFile = fileSaveDir.toString()+"/" + fileName;
                        System.out.println("newFile---"+newFile);
                        try {
                            file.transferTo(new File(newFile));
                            //return "You have successfully uploaded " + file.getOriginalFilename() + "!";
                            //return "success";
                            directory = inputFileDir;
                            fileNames.add(fileName);
                            
                        } catch (IllegalStateException e) {
                            //return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                        	errorList.add("There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage());
                        } catch (IOException e) {
                            //return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                            errorList.add("There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage());
                        }
                        catch (Exception e) {
                            //return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                            errorList.add("There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage());
                        }
                    } else {
                        //return "Error. " + file.getOriginalFilename() + " file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.";
                    	errorList.add("Error. " + file.getOriginalFilename() + " file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.");
                    }
                } else {
                    //return "Error. " + contentType + " is not a valid content type.";
                	errorList.add("Error. " + contentType + " is not a valid content type.");
                }
            } else {
                //return "Error. No file choosen.";
            	errorList.add("Error. No file choosen.");
            }
    	}
    	
    	fileUploadResponseVO.setDirectory(directory);
    	fileUploadResponseVO.setFileNames(fileNames);
    	fileUploadResponseVO.setErrorList(errorList);
    	
    	return fileUploadResponseVO;
        
    }
    
    private Boolean isValidContentType(String contentType) {
        if (!allowedFileTypes.contains(contentType)) {
            return false;
        }
        
        return true;
    }
    
    private Boolean belowMaxFileSize(Long fileSize) {
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        
        return true;
    }
}