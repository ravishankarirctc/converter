package com.litepdf.converter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.litepdf.converter.util.FileUpload;
import com.litepdf.converter.util.Response;

/**
 * 
 * @author RAVI
 *
 */
@Controller
public class ConverterController {

	@RequestMapping(value = "/doctopdf", method = RequestMethod.GET)//make it POST on actual implementation
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
		return new ModelAndView("converter", "welcomeMsg", "You will get doc to PDF on this page.");
	}
	
	 @RequestMapping(value = "/home", method = RequestMethod.GET)
	    public String index(ModelMap model) {
	        model.addAttribute("message", "Spring 4 Multiple File Upload Example.");
	        return "index";
	    }
	
	 @RequestMapping(value="/upload", method=RequestMethod.POST, produces = "application/json")
	    @ResponseBody
	    public Response handleFileUpload(@RequestParam("file") MultipartFile[] file , @RequestParam("type") String type) {

	        List<String> result = new ArrayList<String>();
	        FileUpload fileUpload = new FileUpload();
	        System.out.println(type);
	        for (int i = 0; i < file.length; i++) {
	            result.add(fileUpload.process(file[i]));
	            System.out.println("bbbbbbbbb");
	  	      
	        }
	        System.out.println("cccccccccccc");
		      

	        return new Response(result);
	    }
}
