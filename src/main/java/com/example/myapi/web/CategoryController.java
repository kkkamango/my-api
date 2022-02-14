package com.example.myapi.web;

import com.example.myapi.model.Category;
import com.example.myapi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public List<Category> getCategoryList(){
        return categoryService.getCategoryList();
    }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable("id") Integer id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/category")
    public Category saveCategory(@RequestBody Category category) {
        log.info("saveCategory requestBody = {}", category);
        return categoryService.save(category);
    }

    @PostMapping("/category/{id}")
    public Category updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {

        log.info("updateCategory id = {}", id);
        log.info("updateCategory requestBody = {}", category);

        return categoryService.update(id, category);
    }

    @DeleteMapping("/category/{id}")
    public Map deleteCategory(@PathVariable("id") Integer id) {

        log.info("deleteCategory id = {}", id);
        Map<String, Object> response = new HashMap<>();
        Integer result = categoryService.delete(id);

        if (result > 0) {
            response.put("result", "OK");
        } else {
            response.put("result", "FAIL");
            response.put("message", "일치하는 정보를 찾을 수 없습니다.");
        }

        return response;
    }
}
