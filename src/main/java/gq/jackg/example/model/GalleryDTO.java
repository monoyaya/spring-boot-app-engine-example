package gq.jackg.example.model;

import java.io.Serializable;

public class GalleryDTO implements Serializable {
	private String imgUrl;
	private String filename;
	
	public GalleryDTO() {
		super();
	}
	
	public GalleryDTO(String imgUrl, String filename) {
		super();
		this.imgUrl = imgUrl;
		this.filename = filename;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
