package com.emotionbank.business.api.category.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emotionbank.business.api.category.dto.CreateCategoryDto;
import com.emotionbank.business.domain.category.dto.CategoryDto;
import com.emotionbank.business.domain.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDto.Request request) {
		categoryService.createCategory(CategoryDto.from(request));
		return ResponseEntity.ok().build();
	}
}
