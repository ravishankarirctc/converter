package com.litepdf.converter.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.litepdf.converter.entity.LitePdfConverterTrack;
import com.litepdf.converter.model.FileUploadResponseVO;
import com.litepdf.converter.service.ConverterTrackService;
import com.litepdf.converter.service.DocToPdfConverter;
import com.litepdf.converter.service.PptToPdfConverter;
import com.litepdf.converter.service.PptxToPdfConverter;
import com.litepdf.converter.util.FileUpload;

/**
 * 
 * @author RAVI
 *
 */
@Controller
public class ConverterController {
	
	@Autowired
	ConverterTrackService converterTrackService;

	@Autowired
	DocToPdfConverter docToPdfConverter;
	@Autowired
	PptxToPdfConverter pptxToPdfConverter;
	@Autowired
	PptToPdfConverter pptToPdfConverter;
	
	@Autowired
	ServletContext servletContext;
	@Autowired
	FileUpload fileUpload;
	
	private static final List<String> DOC_TO_PDF = new ArrayList<>(Arrays.asList("application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
	private static final List<String> PPT_TO_PDF = new ArrayList<>(Arrays.asList("application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation"));
	private static final String BASE_FILE_PATH = "/WEB-INF/files/";
	private static final String INPUT_PATH = "\\input";
	private static final String OUTPUT_PATH = "\\output";
	private List<String> allowedFileTypes;
	
	@RequestMapping(value = "/doctopdf1", method = RequestMethod.GET)
    public void docToPdf( HttpServletRequest request, HttpServletResponse response/*, @PathVariable("fileName") String fileName*/) throws FileNotFoundException, IOException, Exception{
        //If user is not authorized - he should be thrown out from here itself
		
		String inpath = "/WEB-INF/doctopdf/1.docx";
		String outPath = "/WEB-INF/doctopdf/1.pdf";
		
		docToPdfConverter.docToPdf(inpath, outPath);
         
        //Authorized user will download the file
        String dataDirectory = servletContext.getRealPath(outPath);
        Path file = Paths.get(dataDirectory);
        if (Files.exists(file))
        {
            //response.setContentType("application/pdf");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename="+"myDOCfile.pdf");
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	
	
	@RequestMapping(value = "/ppttopdf", method = RequestMethod.GET)
    public void pptToPdf( HttpServletRequest request, HttpServletResponse response/*, @PathVariable("fileName") String fileName*/) throws FileNotFoundException, IOException, Exception{
        //If user is not authorized - he should be thrown out from here itself
		
		String inPath = "/WEB-INF/ppttopdf/1.pptx";
		String outPath = "/WEB-INF/ppttopdf/1.pdf";
		
		if(inPath.endsWith("ppt")) {
			pptToPdfConverter.pptToPdf(inPath, outPath);
		}else if(inPath.endsWith("pptx")){
			pptxToPdfConverter.pptxToPdf(inPath, outPath);
		}
		
         
        //Authorized user will download the file
        String dataDirectory = servletContext.getRealPath(outPath);
        Path file = Paths.get(dataDirectory);
        if (Files.exists(file))
        {
            //response.setContentType("application/pdf");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename="+"myPPTfile.pdf");
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	 @RequestMapping(value="/upload", method=RequestMethod.POST, produces = "application/json")
	    @ResponseBody
	    public FileUploadResponseVO handleFileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file , @RequestParam("type") String type) throws FileNotFoundException, IOException, Exception {

	        List<String> result = new ArrayList<String>();
	        //FileUpload fileUpload = new FileUpload();
	        System.out.println(type);
	        
	        /////////////////////////////////////////////////////////
	        //need to send the array of all the files
	        //also send the valid file type names based on conversion
	        //List validFileTypes;
	        //if(doc-to-pdf){ validFileTypes = {doc,docx} };
	        //result.add(fileUpload.process(files, validFileTypes));
	        ////////////////////////////////////////////////////////
	        
	        //for (int i = 0; i < file.length; i++) {
	            //result.add(fileUpload.process(file[i]));
	        //}
	        if(type.equalsIgnoreCase("doc-to-pdf")) {
	        	allowedFileTypes = DOC_TO_PDF;
	        }else if(type.equalsIgnoreCase("ppt-to-pdf")) {
	        	allowedFileTypes = PPT_TO_PDF;
	        }
	         return fileUpload.process(file, allowedFileTypes);        
	        /*String inpath = "/WEB-INF/doctopdf/1.docx";
			String outPath = "/WEB-INF/doctopdf/1.pdf";
			
			

	        //if upload is success then convert file based on conversion type
	        if(result.contains("success")) {
	        	if(type.equalsIgnoreCase("doctopdf")) {
	        		docToPdfConverter.docToPdf(inpath, outPath);
	        	}
	        }
	        
	        //this will allow us to download the file after conversion  
	        String dataDirectory = servletContext.getRealPath(outPath);
	        Path dnldFile = Paths.get(dataDirectory);
	        if (Files.exists(dnldFile))
	        {
	            //response.setContentType("application/pdf");
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader("Content-Disposition", "attachment; filename="+"myDOCfile.pdf");
	            try
	            {
	                Files.copy(dnldFile, response.getOutputStream());
	                response.getOutputStream().flush();
	            }
	            catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }*/
	        
	        //return new Response(result);
	    }
	 
	 @RequestMapping(value = "/converter", method = RequestMethod.POST)
	 @ResponseBody
	    public String convert( HttpServletRequest request, HttpServletResponse response, @RequestParam("directory") String directory, @RequestParam("fileNames") List<String> fileNames, @RequestParam("type") String type) throws FileNotFoundException, IOException, Exception{
	        //If user is not authorized - he should be thrown out from here itself
			
			String inPath = "";
			String outPath = "";
			String outFileName = ""; 
			
			
			if(type.equalsIgnoreCase("doc-to-pdf")) {//doc-to-pdf
				
				outFileName = fileNames.get(0).split("-")[2].split("\\.")[0]+".pdf";
				inPath = BASE_FILE_PATH + directory+"/"+fileNames.get(0);
				outPath = BASE_FILE_PATH + directory.replace("input", "output")+ "/"+outFileName;
				
				docToPdfConverter.docToPdf(inPath, outPath);
				
			}else if(type.equalsIgnoreCase("ppt-to-pdf")) {//ppt-to-pdf
				
				outFileName = fileNames.get(0).split("-")[2].split("\\.")[0]+".pdf";				
				inPath = BASE_FILE_PATH + directory+"/"+fileNames.get(0);
				outPath = BASE_FILE_PATH + directory.replace("input", "output")+"/"+fileNames.get(0).split("-")[2].split("\\.")[0]+".pdf";
				
				if(inPath.endsWith("ppt")) {
					pptToPdfConverter.pptToPdf(inPath, outPath);
				}else if(inPath.endsWith("pptx")){
					pptxToPdfConverter.pptxToPdf(inPath, outPath);
				}
				
			}
			
	         
	        //Authorized user will download the file
	        /*String dataDirectory = servletContext.getRealPath(outPath);
	        Path file = Paths.get(dataDirectory);
	        if (Files.exists(file))
	        {
	            //response.setContentType("application/pdf");
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader("Content-Disposition", "attachment; filename="+outFileName);
	            try
	            {
	                Files.copy(file, response.getOutputStream());
	                response.getOutputStream().flush();
	            }
	            catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }*/
			
			//return directory.replace("input", "output")+ "/"+outFileName.replace(".docx", "").replace(".doc", "");
			
			LitePdfConverterTrack litePdfConverterTrack= new LitePdfConverterTrack();
			
			litePdfConverterTrack.setConvertRequestType(type);
			litePdfConverterTrack.setCreateDate(new Date());
			litePdfConverterTrack.setIsConverted(1);
			litePdfConverterTrack.setUserIp("11");
			litePdfConverterTrack.setUserPort("11");
			
			converterTrackService.saveTracker(litePdfConverterTrack);
			return directory.replace("input", "output")+ "/"+outFileName;
	    }
		
		
	 @RequestMapping(value = "/download-file", method = RequestMethod.GET)
	 public void convert( HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String url){
		 
		 String dataDirectory = servletContext.getRealPath(BASE_FILE_PATH + url);
	        Path file = Paths.get(dataDirectory);
	        if (Files.exists(file))
	        {
	            //response.setContentType("application/pdf");
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader("Content-Disposition", "attachment; filename="+url.split("/")[2]);
	            try
	            {
	                Files.copy(file, response.getOutputStream());
	                response.getOutputStream().flush();
	            }
	            catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	 }
	  
	 
	 
	@RequestMapping(value = "/doc-to-pdf", method = RequestMethod.GET)//make it POST on actual implementation
	//public String register(ModelAndView model, @Valid Employee employee, BindingResult result) {
	public ModelAndView docToPdf(ModelAndView model) {
		/*if(result.hasErrors()) {
			return new ModelAndView("registerEmployee");
		}*/
		//String flag = employeeService.registerEmployee(employee);
		/*if(flag.equals("fail")){
			return new ModelAndView("registerEmployee","regMsg","Employee Already Exists."  );
		}*/
		//return new ModelAndView("welcome", "name", employee.getEmployeeName());
		//model.addAttribute("regMsg", "Registration is successful.");
		//return "redirect:/login";
		//return new ModelAndView("redirect:/doctopdf", "welcomeMsg", "You will get doc to PDF on this page.");
		return new ModelAndView("doc-to-pdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}
	@RequestMapping(value = "/docx-to-pdf", method = RequestMethod.GET)//make it POST on actual implementation
	//public String register(ModelAndView model, @Valid Employee employee, BindingResult result) {
	public ModelAndView docxToPdf(ModelAndView model) {
		
		return new ModelAndView("doc-to-pdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}
	
	@RequestMapping(value = "/ppt-to-pdf", method = RequestMethod.GET)//make it POST on actual implementation
	//public String register(ModelAndView model, @Valid Employee employee, BindingResult result) { 
	public ModelAndView pptToPdf(ModelAndView model) {
		/*if(result.hasErrors()) {
			return new ModelAndView("registerEmployee");
		}*/
		//String flag = employeeService.registerEmployee(employee);
		/*if(flag.equals("fail")){
			return new ModelAndView("registerEmployee","regMsg","Employee Already Exists."  );
		}*/
		//return new ModelAndView("welcome", "name", employee.getEmployeeName());
		//model.addAttribute("regMsg", "Registration is successful.");
		//return "redirect:/login";
		//return new ModelAndView("redirect:/doctopdf", "welcomeMsg", "You will get doc to PDF on this page.");
		return new ModelAndView("ppt-to-pdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}
	@RequestMapping(value = "/pptx-to-pdf", method = RequestMethod.GET)//make it POST on actual implementation
	//public String register(ModelAndView model, @Valid Employee employee, BindingResult result) { 
	public ModelAndView pptxToPdf(ModelAndView model) {
		return new ModelAndView("ppt-to-pdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}
	
	 @RequestMapping(value = "/home", method = RequestMethod.GET)
	    public String index(ModelMap model) {
	        model.addAttribute("message", "Spring 4 Multiple File Upload Example.");
	        return "index";
	    }
	
	
	
	/*@RequestMapping(value = "/test", method = RequestMethod.GET)//make it POST on actual implementation
	public ModelAndView docToPdf(ModelAndView model) {
		return new ModelAndView("doctopdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}*/
}
