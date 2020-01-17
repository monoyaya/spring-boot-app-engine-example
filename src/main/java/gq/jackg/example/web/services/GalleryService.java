package gq.jackg.example.web.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gq.jackg.example.dao.GalleryDAO;

@Service
public class GalleryService {
	@Autowired GalleryDAO dao;
	
	public ArrayList<String> getList() {
		return dao.getList();
	}
	
	public void upload(MultipartFile[] files) {
		for (MultipartFile file : files) {
			dao.uploadImg(file);
		}
	}
	
	public String geturl(String gsKey) {
		return dao.getImg(gsKey);
	}
	
	public boolean delete(String gsKey) {
		return dao.deleteImg(gsKey);
	}
}
