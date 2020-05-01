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

@RestController//returns the object and object data is directly written into HTTP response as JSON
@CrossOrigin////for resolving the CORS issue when integrating the frontend and the backend
@RequestMapping("/")//to tell where the mapping in the db has to go
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private CategoryItemServiceImpl categoryItemServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    //getAllCategory endpoint definition which is of GET type
    @RequestMapping(value = "/category",method = RequestMethod.GET)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllCategories()
    {

        List<CategoryEntity>categoryList=categoryServiceImpl.getAllCategories();//getting a list of all the categories.
        List <CategoryListResponse> list = new ArrayList<>();//creating a new list which is to be added in response.
        for(CategoryEntity categoryEntity:categoryList)
        {
            //creating the object and adding values
            CategoryListResponse categoryListResponse=new CategoryListResponse().id(UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName());
            list.add(categoryListResponse);//adding the object into the list
        }
        CategoriesListResponse response = new CategoriesListResponse().categories(list);
        return new ResponseEntity<CategoriesListResponse> (response, HttpStatus.FOUND);
        //return new ResponseEntity<CategoriesListResponse> (response, HttpStatus.OK);
    }

    //getCategoryById endpoint definition having method as GET type

    //@PathVariable is a Spring annotation which indicates that a method parameter should be bound to a URI template variable.
    @RequestMapping(value = "/category/{category_id}", method = RequestMethod.GET)//,produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCategoryById(@PathVariable("category_id") final String categoryUuid) throws CategoryNotFoundException {

        try {
            CategoryEntity categoryEntity = categoryServiceImpl.getCategoryUsingUuid(categoryUuid);//getting the CategoryEntity object with the categoryUuid.
            List<CategoryItemEntity>list=categoryItemServiceImpl.getItemsUsingCategoryId(categoryEntity.getId());//getting all the items for the particular category
            List<ItemList> itemList=new ArrayList<>();
            for(CategoryItemEntity categoryItemEntity:list)
            {
                ItemEntity itemEntity=itemServiceImpl.getItemUsingId(categoryItemEntity.getItemId());//getting the ItemEntity object using itemId.
                ItemList itemList1=new ItemList().id(UUID.fromString(itemEntity.getUuid())).itemName(itemEntity.getItemName()).price(itemEntity.getPrice());
                if(itemEntity.getType().equals("0"))//Setting the type of item.
                {
                    itemList1.setItemType(ItemList.ItemTypeEnum.VEG);
                }
                else if(itemEntity.getType().equals("1"))
                {
                    itemList1.setItemType(ItemList.ItemTypeEnum.NON_VEG);
                }
                itemList.add(itemList1);//adding the items to the list
            }
            CategoryDetailsResponse response = new CategoryDetailsResponse().id( UUID.fromString(categoryEntity.getUuid())).categoryName(categoryEntity.getCategoryName()).itemList(itemList );
            return new ResponseEntity<CategoryDetailsResponse> (response, HttpStatus.FOUND);
            //return new ResponseEntity<CategoryDetailsResponse>(response, HttpStatus.OK);
        }
        catch (CategoryNotFoundException e)//if any of the validations dont hold true or constraints are not followed correctly exception is thrown
        {
            ErrorResponse response=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
        }
    }

}
