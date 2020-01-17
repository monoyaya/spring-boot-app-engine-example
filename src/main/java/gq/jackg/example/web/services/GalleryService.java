package gq.jackg.example.web.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gq.jackg.example.dao.GalleryDAO;
import gq.jackg.example.model.GalleryDTO;

@Service
public class GalleryService {
	@Autowired GalleryDAO dao;
	
	public ArrayList<GalleryDTO> getList() {
		return dao.getList();
	}
	
	public void upload(MultipartFile[] files) {
		for (MultipartFile file : files) {
			dao.uploadImg(file);
		}
	}
	
	public String geturl(String filename) {
		return dao.getImg(filename);
	}
	
	public boolean delete(String filename) {
		return dao.deleteImg(filename);
	}
}
