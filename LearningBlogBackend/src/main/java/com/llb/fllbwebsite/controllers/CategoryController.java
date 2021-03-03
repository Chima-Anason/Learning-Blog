package com.llb.fllbwebsite.controllers;

import com.llb.fllbwebsite.domain.Category;
import com.llb.fllbwebsite.services.CategoryService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public CategoryController(CategoryService categoryService, ValidationErrorService validationErrorService) {
        this.categoryService = categoryService;
        this.validationErrorService = validationErrorService;
    }

    // Create Category  { @route: api/categories,  access: private }
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        Category newCategory = categoryService.saveOrUpdateCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // Get all Categories  { @route: api/categories/all,  access: public }
    @GetMapping("/all")
    public ResponseEntity<Iterable<Category>> getAllCategories(){
        Iterable<Category> allCategory = categoryService.findAllCategory();
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }


    // Get Category by ID { @route: api/categories/id/:categoryId,  access: private/public }
    @GetMapping("/id/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Long categoryId ){
        Optional<Category> category = categoryService.findCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Get Category by Name { @route: api/categories/name/:categoryName,  access: private/public }
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String categoryName){
        Category category = categoryService.findCategoryByName(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    // Delete Category by ID  { @route: api/categories/id/:categoryId,  access: private }
    @DeleteMapping("/id/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>("Category with ID '" + categoryId + "' was deleted successfully",HttpStatus.OK);
    }

}
