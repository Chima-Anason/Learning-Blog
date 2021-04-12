package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Category;
import com.llb.fllbwebsite.exceptions.CategoryNameException;
import com.llb.fllbwebsite.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void categoryDontExistMessage(Category category, String message){
        if (category == null) {
            throw new CategoryNameException(message);
        }
    }


    public Category saveOrUpdateCategory(Category category){
        try {
        return categoryRepository.save(category);
        }catch (Exception e){
            throw new CategoryNameException("Category already exist");
        }
    }

    public Category findCategoryById(Long categoryId){
        Category category = categoryRepository.getById(categoryId);
        categoryDontExistMessage(category, "Category with Id '" + categoryId + "' don't exist");
        return category;
    }

    public Category findCategoryByName(String categoryName){

        Category category = categoryRepository.findByCategoryName(categoryName);
        categoryDontExistMessage(category, "Category with name '" + categoryName + "' don't exist");
        return category;
    }

    public Iterable<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public void deleteCategoryById(Long categoryId){
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}
