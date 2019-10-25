package gq.jackg.example.web;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/{name}")
	public String hello(@PathVariable(name = "name", required = false) String name) {
		return String.format(temp, StringUtils.isEmpty(name) ? "World" : name);
	}
}
