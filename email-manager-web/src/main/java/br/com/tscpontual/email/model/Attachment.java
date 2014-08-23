package br.com.tscpontual.email.model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


/**
 * The persistent class for the attachment database table.
 * 
 */
@Entity
@Table(name="attachment")
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name="file_name")
	private String fileName;
	
	@Column(name="created_timestamp")
	private Timestamp createdTimeStamp;

	@Column(name="user_stamp")
	private String userStamp;

	//bi-directional many-to-one association to Email
	@ManyToOne
	private Email email;
	
	@Transient
	private MultipartFile multipartFile;
	
	@Transient
	private byte [] bytes;
	
	@Transient
	private String contentType;

	public Attachment() {
	}
	
	public Attachment(MultipartFile multipartFile) throws IOException {
		this.multipartFile = multipartFile;
		this.fileName = formatFileName(multipartFile.getOriginalFilename());
		this.bytes = multipartFile.getBytes();
	}
	
	public String formatFileName(String fileName){
		fileName = fileName.trim();
		fileName = fileName.toLowerCase();
		fileName = Normalizer.normalize(fileName, Normalizer.Form.NFD);
		fileName = fileName.replaceAll(" ", "_");
		return fileName;
	}
	
	public AttachmentInfo getAttachmentInfo(){
		if(multipartFile != null) {
			return new AttachmentInfo(multipartFile);
		}
		else {
			return new AttachmentInfo(fileName, String.valueOf(bytes.length), contentType);
		}
	}	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Email getEmail() {
		return this.email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public byte[] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte [] bytes) {
		this.bytes = bytes;
	}

	public String getUserStamp() {
		return userStamp;
	}

	public void setUserStamp(String userStamp) {
		this.userStamp = userStamp;
	}

	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}