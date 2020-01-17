package gq.jackg.example;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
public class ExampleApploication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleApploication.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse("30MB"));
		factory.setMaxRequestSize(DataSize.parse("300MB"));
		return factory.createMultipartConfig();
	}
}
