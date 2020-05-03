package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController//returns the object and object data is directly written into HTTP response as JSON
@CrossOrigin//for resolving the CORS issue when integrating the frontend and the backend
public class RestaurantController {

    @Autowired//control over where and how autowiring should be done in the code .Can be on constructors, variables class and its objects
    private RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    private RestaurantCategoryServiceImpl restaurantCategoryServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    private CategoryItemServiceImpl categoryItemServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Autowired
    private StateServiceImpl stateServiceImpl;

    //RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.
    //indicates that a method parameter should be bound to a URI template variable

    //getRestaurantsByName endpoint of type GET
    @RequestMapping(value = "/restaurant/name/{restaurant_name}",method = RequestMethod.GET)
    public ResponseEntity getRestaurantsByName(@PathVariable(value = "restaurant_name") final String restaurantName)throws RestaurantNotFoundException
    {
        try {
            List<RestaurantEntity>list=restaurantServiceImpl.getRestaurantsByName(restaurantName);//returns retaurantsByName
            List<RestaurantList>restaurantLists=new ArrayList<>();//creating a list of RestaurantList
            for (RestaurantEntity restaurantEntity : list)
            {
                long restaurantId=restaurantEntity.getId();
                List <RestaurantCategoryEntity> restaurantCategoryList=restaurantCategoryServiceImpl.getCategoriesUsingRestaurantId(restaurantId);
                String category=new String();
                List<String>categories=new ArrayList<>();
                for (RestaurantCategoryEntity restaurantCategoryEntity:restaurantCategoryList)
                {
                    CategoryEntity categoryEntity=categoryServiceImpl.getCategoryUsingId(restaurantCategoryEntity.getCategoryId());//returning the restaurant by categoryId.
                    categories.add(categoryEntity.getCategoryName());//adding into the list

                }
                Collections.sort(categories);//sorting the list
                category=categories.toString().substring(1,categories.toString().length()-1);
                AddressEntity addressEntity=addressServiceImpl.getAddressById(restaurantEntity.getAddressId());//returning the AddressById
                StateEntity stateEntity=stateServiceImpl.getStateById(addressEntity.getStateId());

                //getting the id,stateName
                RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState=new  RestaurantDetailsResponseAddressState().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName());

                //getting the id,flatNumber,locality,city,pincode,state,
                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress=new RestaurantDetailsResponseAddress().id(UUID.fromString(addressEntity.getUuid())).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).city(addressEntity.getCity()).pincode(addressEntity.getPincode()).state(restaurantDetailsResponseAddressState);

                //getting the id,restaurantName,photo_url,customerRating,averagePrice,number of customers rated,address, categories
                RestaurantList restaurantList=new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl()).customerRating(new BigDecimal(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAveragePriceForTwo()).numberCustomersRated(restaurantEntity.getNumberOfCustomersRated()).address(restaurantDetailsResponseAddress).categories(category);
                restaurantLists.add(restaurantList);//adding the list
            }
            RestaurantListResponse restaurantListResponse=new RestaurantListResponse().restaurants(restaurantLists);
            return new ResponseEntity<RestaurantListResponse>(restaurantListResponse,HttpStatus.OK);
        }
        catch (RestaurantNotFoundException e)//if the constraints and validations are not followed then exception is thrown which is caught by catch block
        {
            ErrorResponse errorResponse=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
        }
    }

    //getRestaurantsByCategoryID which is of GET type.
    @RequestMapping(method = RequestMethod.GET,value = "/restaurant/category/{category_id}")
    public ResponseEntity getRestaurantsByCategory(@PathVariable(value = "category_id") final String categoryUuid){

        try {
            List<RestaurantList>restaurantLists=new ArrayList<>();
            CategoryEntity categoryEntity=categoryServiceImpl.getCategoryUsingUuid(categoryUuid);
            List<RestaurantCategoryEntity>restaurantCategoryEntityList=restaurantCategoryServiceImpl.getRestaurantsUsingCategoryId(categoryEntity.getId());
            for(RestaurantCategoryEntity restaurantCategoryEntity:restaurantCategoryEntityList)
            {
                RestaurantEntity restaurantEntity=restaurantServiceImpl.getRestaurantById(restaurantCategoryEntity.getRestaurantId());
                long restaurantId=restaurantEntity.getId();//returns restaurantId
                List<RestaurantCategoryEntity>restaurantCategoryEntityList1=restaurantCategoryServiceImpl.getCategoriesUsingRestaurantId(restaurantId);
                String category=new String();
                List<String>categories=new ArrayList<>();
                for (RestaurantCategoryEntity restaurantCategoryEntity1:restaurantCategoryEntityList)
                {
                    CategoryEntity categoryEntity1=categoryServiceImpl.getCategoryUsingId(restaurantCategoryEntity1.getCategoryId());
                    categories.add(categoryEntity1.getCategoryName());
                }
                Collections.sort(categories);//sort the list
                category=categories.toString().substring(1,categories.toString().length()-1);
                AddressEntity addressEntity=addressServiceImpl.getAddressById(restaurantEntity.getAddressId());
                StateEntity stateEntity=stateServiceImpl.getStateById(addressEntity.getStateId());

                //getting the id,stateName
                RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState=new RestaurantDetailsResponseAddressState().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName());

                //getting the id,flatNumber,locality,city,pincode,state
                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress=new RestaurantDetailsResponseAddress().id(UUID.fromString(addressEntity.getUuid())).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).city(addressEntity.getCity()).pincode(addressEntity.getPincode()).state(restaurantDetailsResponseAddressState);

                //getting id, restaurantName, photoUrl, customerRating, averagePrice, address, categories
                RestaurantList restaurantList=new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl()).customerRating(new BigDecimal(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAveragePriceForTwo()).numberCustomersRated(restaurantEntity.getNumberOfCustomersRated()).address(restaurantDetailsResponseAddress).categories(category);
                restaurantLists.add(restaurantList);//adding into the list
            }
            RestaurantListResponse restaurantListResponse=new RestaurantListResponse().restaurants(restaurantLists);
            return new ResponseEntity<RestaurantListResponse>(restaurantListResponse,HttpStatus.OK);

        }
        catch (CategoryNotFoundException e)//if the constraints and the validations are not followed then exception is thrown which is caught by th catch block
        {
            ErrorResponse response=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
        }
    }

    //getRestaurantsByID endpoint which is of type GET
    @RequestMapping(value = "/api/restaurant/{restaurant_id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurantsByID(@PathVariable(value = "restaurant_id") final String restaurantUuid)
    {
        try {
            RestaurantEntity restaurantEntity=restaurantServiceImpl.getRestaurantByUuid(restaurantUuid);//return the restaurantUuid
            List<RestaurantCategoryEntity>restaurantCategoryEntityList=restaurantCategoryServiceImpl.getCategoriesUsingRestaurantId(restaurantEntity.getId());
            List<CategoryEntity>categories=new ArrayList<>();
            for(RestaurantCategoryEntity restaurantCategoryEntity:restaurantCategoryEntityList)
            {
               CategoryEntity categoryEntity=categoryServiceImpl.getCategoryUsingId(restaurantCategoryEntity.getCategoryId());//returning the categoryId
               categories.add(categoryEntity);
            }
            List<CategoryList>categoryLists=new ArrayList<>();
            for (CategoryEntity categoryEntity1:categories)
            {
                List<CategoryItemEntity>list=categoryItemServiceImpl.getItemsUsingCategoryId(categoryEntity1.getId());
                List<ItemList>itemList=new ArrayList<>();
                for(CategoryItemEntity categoryItemEntity:list)
                {
                    ItemEntity itemEntity=itemServiceImpl.getItemUsingId(categoryItemEntity.getItemId());

                    //getting the id,itemName,price,
                    ItemList itemList1=new ItemList().id(UUID.fromString(itemEntity.getUuid())).itemName(itemEntity.getItemName()).price(itemEntity.getPrice());
                    if(itemEntity.getType().equals("0"))//if the getType is 0
                        itemList1.setItemType(ItemList.ItemTypeEnum.VEG);//set the enum to VEG
                    else if(itemEntity.getType().equals("1"))//if getType is 1
                        itemList1.setItemType(ItemList.ItemTypeEnum.NON_VEG);//set the enum to NON_VEG
                    itemList.add(itemList1);//adding to the itemList
                }

                //getting the id,categoryName,itemList
                CategoryList categoryList=new CategoryList().id(UUID.fromString(categoryEntity1.getUuid())).categoryName(categoryEntity1.getCategoryName()).itemList(itemList);
                categoryLists.add(categoryList);
            }
            //sorting the categoryList using the comparator function
            Collections.sort(categoryLists, new Comparator<CategoryList>()
            {
                @Override
                public int compare(CategoryList o1, CategoryList o2)
                {
                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                }
            });
            AddressEntity addressEntity=addressServiceImpl.getAddressById(restaurantEntity.getAddressId());//returning the restaurant address by its ID
            StateEntity stateEntity=stateServiceImpl.getStateById(addressEntity.getStateId());//returning the state by its stateId

            //returning the id and stateName
            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState=new RestaurantDetailsResponseAddressState().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName());

            //returning the id,flatBuilding number, locality, city, pincode, state
            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress=new RestaurantDetailsResponseAddress().id(UUID.fromString(addressEntity.getUuid())).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).city(addressEntity.getCity()).pincode(addressEntity.getPincode()).state(restaurantDetailsResponseAddressState);

            //returning the id,restarantName, photoUrl, customerRating, averagePrice, numberOf the custoerms who rated, address
            RestaurantDetailsResponse restaurantDetailsResponse=new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl()).customerRating(new BigDecimal( restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAveragePriceForTwo()).numberCustomersRated(restaurantEntity.getNumberOfCustomersRated()).address(restaurantDetailsResponseAddress).categories(categoryLists);
            return new ResponseEntity<RestaurantDetailsResponse>(restaurantDetailsResponse,HttpStatus.FOUND);//if no issues then return the response with HttpStatus
        }
        catch (RestaurantNotFoundException e)//if the constraints are not followed or the validtions have issues then exception is thrown which is caught by catch block
        {
            ErrorResponse errorResponse=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
        }
    }
}
