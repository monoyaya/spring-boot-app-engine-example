package gq.jackg.example.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
	final String temp = "Hello, %s";
	
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index.html");
	}
}
