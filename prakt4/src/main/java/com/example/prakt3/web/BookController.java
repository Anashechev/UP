package com.example.prakt3.web;

import com.example.prakt3.domain.Author;
import com.example.prakt3.domain.Book;
import com.example.prakt3.domain.Category;
import com.example.prakt3.repo.AuthorRepository;
import com.example.prakt3.repo.BookRepository;
import com.example.prakt3.repo.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;

	public BookController(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
	}

	// USER: просмотр
	@GetMapping
	@PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
	public String list(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "books/list";
	}

	// MANAGER+: создание форма
	@GetMapping("/new")
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public String createForm(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		return "books/form";
	}

	// MANAGER+: создание
	@PostMapping
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public String create(@ModelAttribute Book book, BindingResult br, @RequestParam Long authorId, @RequestParam Long categoryId, Model model) {
		Author author = authorRepository.findById(authorId).orElse(null);
		Category category = categoryRepository.findById(categoryId).orElse(null);
		if (author == null || category == null) {
			model.addAttribute("error", "Выберите корректного автора и категорию");
			model.addAttribute("book", book);
			model.addAttribute("authors", authorRepository.findAll());
			model.addAttribute("categories", categoryRepository.findAll());
			return "books/form";
		}
		book.setAuthor(author);
		book.setCategory(category);
		bookRepository.save(book);
		return "redirect:/books";
	}

	// ADMIN: редактирование форма
	@GetMapping("/{id}/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public String editForm(@PathVariable Long id, Model model) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book == null) return "redirect:/books";
		model.addAttribute("book", book);
		model.addAttribute("authors", authorRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		return "books/form";
	}

	// ADMIN: обновление
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String update(@PathVariable Long id, @ModelAttribute Book book, @RequestParam Long authorId, @RequestParam Long categoryId, Model model) {
		Book existing = bookRepository.findById(id).orElse(null);
		if (existing == null) return "redirect:/books";
		Author author = authorRepository.findById(authorId).orElse(null);
		Category category = categoryRepository.findById(categoryId).orElse(null);
		if (author == null || category == null) {
			model.addAttribute("error", "Выберите корректного автора и категорию");
			model.addAttribute("book", book);
			model.addAttribute("authors", authorRepository.findAll());
			model.addAttribute("categories", categoryRepository.findAll());
			return "books/form";
		}
		existing.setTitle(book.getTitle());
		existing.setAuthor(author);
		existing.setCategory(category);
		bookRepository.save(existing);
		return "redirect:/books";
	}

	// ADMIN: удаление
	@PostMapping("/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String delete(@PathVariable Long id) {
		bookRepository.deleteById(id);
		return "redirect:/books";
	}
}
