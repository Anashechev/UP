package com.example.prakt3.config;

import com.example.prakt3.domain.*;
import com.example.prakt3.repo.AuthorRepository;
import com.example.prakt3.repo.BookRepository;
import com.example.prakt3.repo.CategoryRepository;
import com.example.prakt3.service.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner initUsers(RegistrationService registrationService, AuthorRepository authorRepo, CategoryRepository categoryRepo, BookRepository bookRepo) {
		return args -> {
			try { registrationService.registerUser("admin", "Admin@123", Set.of(Role.ADMIN)); } catch (IllegalArgumentException ignored) {}
			try { registrationService.registerUser("manager", "Manager@123", Set.of(Role.MANAGER)); } catch (IllegalArgumentException ignored) {}

			if (authorRepo.findAll().isEmpty()) {
				Author a1 = new Author(); a1.setName("Достоевский"); authorRepo.save(a1);
				Author a2 = new Author(); a2.setName("Пушкин"); authorRepo.save(a2);
				Category c1 = new Category(); c1.setTitle("Роман"); categoryRepo.save(c1);
				Category c2 = new Category(); c2.setTitle("Поэзия"); categoryRepo.save(c2);
				Book b = new Book(); b.setTitle("Преступление и наказание"); b.setAuthor(a1); b.setCategory(c1); bookRepo.save(b);
			}
		};
	}
}
