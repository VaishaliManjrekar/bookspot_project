package com.bookspot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Recommend")
public class Recommend {
	
	@Id
	private int id;
	private String bookName;
	private String authorName;
	private String isbn;
	private int userid;
	private String username;
	private String bookImg;
	private String bookDesc;
	
	
	public Recommend() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public int getUserid() {
		return userid;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getBookImg() {
		return bookImg;
	}


	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}


	public String getBookDesc() {
		return bookDesc;
	}


	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}


	public Recommend(int id, String bookName, String authorName, String isbn, int userid,
			String username, String bookImg, String bookDesc) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
		this.isbn = isbn;
		this.userid = userid;
		this.username = username;
		this.bookImg = bookImg;
		this.bookDesc = bookDesc;
	}
	
	
}