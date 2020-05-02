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

@RestController// used tp create Restful web services using spring mvc.
@CrossOrigin//for the future use if we want t0 link the frontend to the backend and to avoid the CORS issue
public class RestaurantController {

    @Autowired //control over where and how autowiring should be done with the code. can be on constructors, variable class and its objects
    private RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Autowired
    private StateServiceImpl stateServiceImpl;

    @Autowired
    private RestaurantCategoryServiceImpl restaurantCategoryServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private CategoryItemServiceImpl categoryItemServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    //getRestaurantByName endpoint function definition and Request Method type is GET
    @RequestMapping(value = "/restaurant/name/{restaurant_name}" , method = RequestMethod.GET)
    public ResponseEntity getRestaurantByName(@PathVariable(value="restaurant_name") final String restaurantName)
    {
        try
        {
            List<RestaurantEntity> restaurantEntityList = restaurantServiceImpl.getRestaurantUsingName(restaurantName); //list type of Restaurant entity defined in serviceEntity
            List<RestaurantList> restaurantListList = new ArrayList<>(); // list which is type of Restaurant List defined in api.model

            String category = new String(); // category type of String
            List<String> categories = new ArrayList<>(); // categories list type of string
            for (RestaurantEntity restaurantEntity :restaurantEntityList)
            {
                long restaurantId = restaurantEntity.getId();//fetching the restaurantId from the RestaurantEntity
                List<RestaurantCategoryEntity> restaurantCategoryEntityList = restaurantCategoryServiceImpl.getCategoryByRestaurantId(restaurantId);
                for(RestaurantCategoryEntity restaurantCategoryEntity : restaurantCategoryEntityList)
                {

                    CategoryEntity categoryEntity = categoryServiceImpl.getCategoryUsingId(restaurantCategoryEntity.getCategoryId());
                    categories.add(categoryEntity.getCategoryName()); //add category in list of categories
                }

                Collections.sort(categories); // sort all the categories
                category = categories.toString().substring(1,categories.toString().length()-1);

                AddressEntity addressEntity = addressServiceImpl.getAddressById(restaurantEntity.getAddressId());// fetch address using getAddressById and Store in addressEntity
                StateEntity stateEntity =stateServiceImpl.getStateById(addressEntity.getStateId()); // fetch state using getStateById and store in stateEntity

                RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName()); //getting id(stateId) , stateName

                // getting id, city , flatBuildNUmber, locality, pincode, state
                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress().id(UUID.fromString(addressEntity.getUuid())).city(addressEntity.getCity()).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).pincode(addressEntity.getPincode()).state(restaurantDetailsResponseAddressState);

                //getting id, restaurantName,photoUrl,customerRating , averagePrice,numberCustomerRated , address , Categories
                RestaurantList restaurantList = new RestaurantList().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl()).customerRating(new BigDecimal(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAveragePriceForTwo()).numberCustomersRated(restaurantEntity.getNumberOfCustomersRated()).address(restaurantDetailsResponseAddress).categories(category);

                        restaurantListList.add(restaurantList); // add restaurantlist in List of Restaurant for final result
            }

            RestaurantListResponse restaurantListResponse = new RestaurantListResponse().restaurants(restaurantListList); // object of restaurantList Response and pass list of restaurant
            return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);// return the list of response to the customer
        }
        catch (RestaurantNotFoundException exc)//if the constraints are not taken care of or validations not followed then exception is thrown which is caught by the catch block
        {
        ErrorResponse response = new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage());
        return  new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
        }
    }
   // this is getRestaurantById endpoint of RequestMethod of type GET
    @RequestMapping(value = "/api/restaurant/{restaurant_id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurantById(@PathVariable(value ="restaurant_id") final String restaurantId)
    {
        try {

            RestaurantEntity restaurantEntity = restaurantServiceImpl.getRestaurantUsingUuid(restaurantId);// get RestaurantUsingId and store in object of RestaurantEntity
            List<RestaurantCategoryEntity> restaurantCategoryEntityList = restaurantCategoryServiceImpl.getCategoryByRestaurantId(restaurantEntity.getId());// list of RestaurantCategoryEntity and find category type by using getCategoryBYRestaurantId
            List<CategoryEntity> categories= new ArrayList<>();//list of CategoryEntity type
            for(RestaurantCategoryEntity restaurantCategoryEntity : restaurantCategoryEntityList)
            {
                CategoryEntity categoryEntity = categoryServiceImpl.getCategoryUsingId(restaurantCategoryEntity.getCategoryId());
                categories.add(categoryEntity);
            }
            List<CategoryList> categoryListList = new ArrayList<>();  // list type of categoryList defined in api.model
            for(CategoryEntity category : categories)
            {
                List<CategoryItemEntity> list = categoryItemServiceImpl.getItemsUsingCategoryId(category.getId()); // list type of categoryItem entity and add items in list using getItemsUsingCategoryId
                List<ItemList> itemListList = new ArrayList<>();

            for(CategoryItemEntity categoryItemEntity :list)
            {

                ItemEntity itemEntity = itemServiceImpl.getItemUsingId(categoryItemEntity.getItemId()); //create object of ItemEntity  and store items using getItemsById
                ItemList itemList =new ItemList().id(UUID.fromString(itemEntity.getUuid())).itemName(itemEntity.getItemName()).price(itemEntity.getPrice()); // getting the id, itemName, price

                    if(itemEntity.getType().equals("0")) // if itemType value is 0
                    {
                        itemList.setItemType(ItemList.ItemTypeEnum.VEG); // set Item Type is VEG
                    }
                    else if(itemEntity.getType().equals("1")) // if item value is 1
                    {
                        itemList.setItemType(ItemList.ItemTypeEnum.NON_VEG); //set item Type is NONVEG
                    }
                    itemListList.add(itemList);   // add itemList in list
            }
                CategoryList categoryList= new CategoryList();
                    categoryList.id(UUID.fromString(category.getUuid())).categoryName(category.getCategoryName()).itemList(itemListList);//getting categoryUuid,categoryName, itemList

                    categoryListList.add(categoryList);  // add in categoryList
            }
            //sort category using Collections.sort and comparator
            Collections.sort(categoryListList, new Comparator<CategoryList>()
            {
                @Override
                public int compare(CategoryList o1, CategoryList o2)
                {
                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                }
            });
            AddressEntity addressEntity = addressServiceImpl.getAddressById(restaurantEntity.getAddressId());// object of restaurantEntity and getAddressById function
            StateEntity stateEntity =stateServiceImpl.getStateById(addressEntity.getStateId());
            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName()); //getting id, stateId, stateName
            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress().id(UUID.fromString(addressEntity.getUuid())).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).city(addressEntity.getCity()).pincode(addressEntity.getPincode()).state(restaurantDetailsResponseAddressState);//getting id, flatBuildNumber, pincode, locality , city, state

            // getting id, restaurantName, photoUrl, customerRating , average price, average price for two, number of customerRated
            RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl()).customerRating(new BigDecimal(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAveragePriceForTwo()).numberCustomersRated(restaurantEntity.getNumberOfCustomersRated()).address(restaurantDetailsResponseAddress).categories(categoryListList);
            return new ResponseEntity<RestaurantDetailsResponse>(restaurantDetailsResponse,HttpStatus.FOUND);// return the Response of restaurant and HttpStatus.FOUND
        }
        catch(RestaurantNotFoundException e)//if the validations are not followed or the constraints are not properly handled then an exception is thrown which is caught by the catch block
        {
            ErrorResponse response =new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
        }
    }


}
