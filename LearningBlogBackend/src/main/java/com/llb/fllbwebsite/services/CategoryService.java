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


    public Category saveOrUpdateCategory(Category category){
        try {
        return categoryRepository.save(category);
        }catch (Exception e){
            throw new CategoryNameException("Category already exist");
        }
    }

    public Optional<Category> findCategoryById(Long categoryId){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new CategoryNameException("Category with Id '" + categoryId + "' don't exist");
        }
        return category;
    }

    public Category findCategoryByName(String categoryName){

        Category category = categoryRepository.findByCategoryName(categoryName);
        if(category == null){
            throw new CategoryNameException("Category with name '" + categoryName + "' don't exist");
        }
        return category;
    }

    public Iterable<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public void deleteCategoryById(Long categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new CategoryNameException("Cannot delete! Category with Id '" + categoryId + "' don't exist");
        }
        categoryRepository.deleteById(categoryId);
    }
}
