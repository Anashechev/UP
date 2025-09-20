package com.example.prakt3.repo;

import com.example.prakt3.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByTitle(String title);
}
