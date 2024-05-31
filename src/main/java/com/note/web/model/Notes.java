package com.note.web.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes_tbl")
public class Notes {

	@Id
	private int id;
	private String username;
	private String title;
	private String description;
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt; 
	
	public Notes() {
	}

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

	public Notes(int id, String username, String title, String description, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.username = username;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
	}
	
	 @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }
}
