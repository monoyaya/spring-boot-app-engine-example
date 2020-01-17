package gq.jackg.example.web.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gq.jackg.example.web.services.GalleryService;

@RestController("/gallery")
public class GalleryController {
	@Autowired GalleryService service;
	
	@GetMapping("/gallery/list")
	public ArrayList<String> getList() {
		return service.getList();
	}
	
	@GetMapping("/gallery")
	public String getItem(@RequestParam(name = "gsKey", required = true) String gsKey) {
		return service.geturl(gsKey);
	}
	
	@PostMapping("/gallery")
	public void upload(@RequestParam(name = "files", required = true) MultipartFile[] files) {
		service.upload(files);
	}
	
	@DeleteMapping("/gallery")
	public void delete(@RequestParam(name = "gsKey", required = true) String gsKey
					   , @RequestParam(name = "pass", required = true) String pass
							, HttpServletResponse res) {
		
		if (pass != null && pass.equals("12345")) {
			if (!service.delete(gsKey)) {
				try {
					res.sendError(HttpStatus.SC_BAD_REQUEST, "삭제 실패");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				res.sendError(HttpStatus.SC_BAD_REQUEST, "비밀번호를 확인해주세요.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
