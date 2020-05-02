package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.service.businness.CategoryItemServiceImpl;
import com.upgrad.FoodOrderingApp.service.businness.CategoryServiceImpl;
import com.upgrad.FoodOrderingApp.service.businness.ItemServiceImpl;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private CategoryItemServiceImpl categoryItemServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    //getAllCategory endpoint definition
    @RequestMapping(value = "/category",method = RequestMethod.GET)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllCategories()
    {

        List<CategoryEntity>categoryList=categoryServiceImpl.getAllCategories();
        List <CategoryListResponse> list = new ArrayList<>();
        for(CategoryEntity categoryEntity:categoryList)
        {
            CategoryListResponse categoryListResponse=new CategoryListResponse().id(UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName());
            list.add(categoryListResponse);
        }
        CategoriesListResponse response = new CategoriesListResponse().categories(list);
        return new ResponseEntity<CategoriesListResponse> (response, HttpStatus.FOUND);
        //return new ResponseEntity<CategoriesListResponse> (response, HttpStatus.OK);
    }

    @RequestMapping(value = "/category/{category_id}", method = RequestMethod.GET)//,produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCategoryById(@PathVariable("category_id") final String categoryUuid) throws CategoryNotFoundException {

        try {
            CategoryEntity categoryEntity = categoryServiceImpl.getCategoryUsingUuid(categoryUuid);
            List<CategoryItemEntity>list=categoryItemServiceImpl.getItemsUsingCategoryId(categoryEntity.getId());
            List<ItemList> itemList=new ArrayList<>();
            for(CategoryItemEntity categoryItemEntity:list)
            {
                ItemEntity itemEntity=itemServiceImpl.getItemUsingId(categoryItemEntity.getItemId());
                ItemList itemList1=new ItemList().id(UUID.fromString(itemEntity.getUuid())).itemName(itemEntity.getItemName()).price(itemEntity.getPrice());
                if(itemEntity.getType().equals("0"))
                {
                    itemList1.setItemType(ItemList.ItemTypeEnum.VEG);
                }
                else if(itemEntity.getType().equals("1"))
                {
                    itemList1.setItemType(ItemList.ItemTypeEnum.NON_VEG);
                }
                itemList.add(itemList1);
            }
            CategoryDetailsResponse response = new CategoryDetailsResponse().id( UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName()).itemList(itemList );
            return new ResponseEntity<CategoryDetailsResponse> (response, HttpStatus.FOUND);
        }
        catch (CategoryNotFoundException e)
        {
            ErrorResponse response=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
        }
    }

}
