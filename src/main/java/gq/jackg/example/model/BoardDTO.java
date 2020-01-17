package gq.jackg.example.model;

import java.io.Serializable;

public class BoardDTO implements Serializable {
	private long boardId;
	private String author;
	private String subject;
	private String content;
	private String pass;
	private String monthDay;
	
	public static final String BOARD_ID = "BoardId", AUTHOR = "Author", SUBJECT = "Subject", 
							   CONTENT = "Content", PASS = "Pass", DATE = "Date";
	
	public BoardDTO() {}

	public BoardDTO(long boardId, String author, String subject, String content, String pass, String monthDay) {
		super();
		this.boardId = boardId;
		this.author = author;
		this.subject = subject;
		this.content = content;
		this.pass = pass;
		this.monthDay = monthDay;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}
}
