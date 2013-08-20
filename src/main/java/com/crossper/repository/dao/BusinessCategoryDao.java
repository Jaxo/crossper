/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao;

import com.crossper.representations.BusinessCategoryRepresentation;
import java.util.List;
import java.util.Map;


public interface BusinessCategoryDao {
     public List<String> getSubcategories(String category);
     public List<String> getAffinityCategories(String category, String subcategory);
     public List<String> getCategories();
     public List<BusinessCategoryRepresentation> getCategoriesForDisplay();
}
