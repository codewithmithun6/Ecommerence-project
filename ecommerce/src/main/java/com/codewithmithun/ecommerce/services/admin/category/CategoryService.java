package com.codewithmithun.ecommerce.services.admin.category;

import com.codewithmithun.ecommerce.dto.CategoryDto;
import com.codewithmithun.ecommerce.entity.Category;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
}
