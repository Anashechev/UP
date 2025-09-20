package com.example.prakt3.web;

import com.example.prakt3.domain.Author;
import com.example.prakt3.domain.Category;
import com.example.prakt3.repo.AuthorRepository;
import com.example.prakt3.repo.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/meta")
public class MetaController {
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;

	public MetaController(AuthorRepository authorRepository, CategoryRepository categoryRepository) {
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public String list(Model model) {
		model.addAttribute("authors", authorRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("author", new Author());
		model.addAttribute("category", new Category());
		return "meta/list";
	}

	@PostMapping("/authors")
	@PreAuthorize("hasRole('ADMIN')")
	public String createAuthor(@ModelAttribute Author author) {
		authorRepository.save(author);
		return "redirect:/meta";
	}

	@PostMapping("/categories")
	@PreAuthorize("hasRole('ADMIN')")
	public String createCategory(@ModelAttribute Category category) {
		categoryRepository.save(category);
		return "redirect:/meta";
	}

	@PostMapping("/authors/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteAuthor(@PathVariable Long id) {
		authorRepository.deleteById(id);
		return "redirect:/meta";
	}

	@PostMapping("/categories/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteCategory(@PathVariable Long id) {
		categoryRepository.deleteById(id);
		return "redirect:/meta";
	}
}
