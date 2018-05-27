package com.litepdf.converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		return new ModelAndView("doctopdf", "welcomeMsg", "You will get doc to PDF on this page.");
	}
}
