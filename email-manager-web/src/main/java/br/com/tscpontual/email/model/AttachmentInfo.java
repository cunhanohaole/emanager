package br.com.tscpontual.email.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class AttachmentInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String originalFileName;
	
	private String size;
	
	private String getContentType;

	public AttachmentInfo(MultipartFile file) {
		this.name = file.getName();
		this.originalFileName = file.getOriginalFilename();
		this.size = String.valueOf(file.getSize());
		this.getContentType = file.getContentType();
	}
	
	public AttachmentInfo(String name, String size, String contentType) {
		this.name = name;
		this.originalFileName = name;
		this.size = size;
		this.getContentType = contentType;
	}

	public String getName() {
		return name;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public String getSize() {
		return size;
	}

	public String getGetContentType() {
		return getContentType;
	}
	
}
