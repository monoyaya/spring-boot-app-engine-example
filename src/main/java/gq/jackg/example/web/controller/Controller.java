package gq.jackg.example.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index.html");
	}
}
