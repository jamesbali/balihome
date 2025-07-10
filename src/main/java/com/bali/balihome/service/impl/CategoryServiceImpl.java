package com.bali.balihome.service.impl;
import com.bali.balihome.dto.requestdto.CategoryRequestDto;
import com.bali.balihome.dto.responsedto.CategoryResponseDto;
import com.bali.balihome.exception.DuplicateResourceException;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.CategoryMapper;
import com.bali.balihome.model.domain.Category;
import com.bali.balihome.repository.CategoryRepository;
import com.bali.balihome.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        if (categoryRepository.existsByName(dto.name())) {
            throw new DuplicateResourceException("Category with name already exists: " + dto.name());
        }
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getName().equals(dto.name()) && categoryRepository.existsByName(dto.name())) {
            throw new DuplicateResourceException("Category with name already exists: " + dto.name());
        }

        categoryMapper.updateEntityFromDto(dto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
}



