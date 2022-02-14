package com.example.myapi.service;

import com.example.myapi.model.Category;
import com.example.myapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findByDelYnNot("Y");
    }

    public Category getCategory(Integer id){
        return categoryRepository.findByIdAndDelYn(id, "N").orElseGet(Category::new);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public Category update(Integer id, Category category){
        category.setId(id);
        category.setDelYn("N");
        return this.save(category);
    }

    @Transactional
    public Integer delete(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDelYn(id, "N");

        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setDelYn("Y");
            categoryRepository.save(category);
            return category.getId();
        }
        return 0;
    }
}
