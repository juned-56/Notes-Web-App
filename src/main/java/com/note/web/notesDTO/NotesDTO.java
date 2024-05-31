package com.note.web.notesDTO;

import java.time.LocalDateTime;

public class NotesDTO {

	private int id;
	private String username;
	private String title;
	private String description;
	private LocalDateTime createdAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public NotesDTO(int id, String username, String title, String description, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.username = username;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
	}
}
