package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.CategoryRequestDto;
import com.bali.balihome.dto.responsedto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto dto);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    void deleteCategory(Long id);
}
