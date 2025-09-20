package com.example.prakt3.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String title;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Author author;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Category category;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public Author getAuthor() { return author; }
	public void setAuthor(Author author) { this.author = author; }
	public Category getCategory() { return category; }
	public void setCategory(Category category) { this.category = category; }
}
