package com.example.godel.service;

import com.example.godel.entity.Category;
import com.example.godel.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void save() {
        Category category = new Category();

        categoryRepository.save(category);

        assertThat(categoryRepository.findAll().size()).isEqualTo(1);

    }

    @Test
    void deleteById() {
        Category category = new Category();

        categoryRepository.save(category);

        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
        categoryRepository.deleteById(1L);

        assertThat(categoryRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void findAll() {
        Category category1 = new Category();
        Category category2 = new Category();

        categoryRepository.saveAll(List.of(category1, category2));

        assertThat(categoryRepository.findAll().size()).isEqualTo(2);
    }
}