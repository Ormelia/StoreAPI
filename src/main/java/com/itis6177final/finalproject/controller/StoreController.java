package com.itis6177final.finalproject.controller;

import com.itis6177final.finalproject.model.BingSearch;
import com.itis6177final.finalproject.model.Store;
import com.itis6177final.finalproject.repos.StoreRepositiory;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class StoreController {
    @Autowired
    private StoreRepositiory storeRepositiory;
    //getting all items in the store
    @GetMapping("store/all")
    public List<Store> getAllItems(){
        return storeRepositiory.findAll();
    }
    //add from Bing API
    @PostMapping("store/add")
    public Map<Integer, String> mySearch(@RequestParam(value = "searchTerm", defaultValue = "Women's Clothing") String searchTerm){
        BingSearch bingSearch = new BingSearch();
        Map<Integer, String> testSearch = new HashMap<>();
        bingSearch.setSearchTerm(searchTerm);
        System.out.println(String.format("Search images for query %s", bingSearch.getSearchTerm()));
        ImagesModel imageResults = bingSearch.getClient().bingImages().search()
                .withQuery(searchTerm)
                .withMarket("en-us")
                .execute();
        if (imageResults != null && imageResults.value().size() > 0) {
            // Image results
            for(int i = 0; i < imageResults.value().size(); i++) {
                bingSearch.setStoreItems(imageResults.value().get(i));
            }
            System.out.println(String.format("***The total number is %d", bingSearch.getStoreItems().size()));
            //ImageObject firstImageResult = imageResults.value().get(2);
            //System.out.println(String.format("Total number of images found: %d", imageResults.value().size()));
//            System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
//            System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));
            System.out.println("-----------------------------------------------------------");
            System.out.println(bingSearch.getStoreItems());
            System.out.println(bingSearch.getStoreItems().get(0));
            ImageObject firstImageResult = bingSearch.getStoreItems().get(0);
            System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));
            for(int j = 0; j < bingSearch.getStoreItems().size(); j++ ){
                System.out.println(bingSearch.getStoreItems().get(j).contentUrl());
                System.out.println(bingSearch.getStoreItems().get(j).hostPageUrl());
                System.out.println(bingSearch.getStoreItems().get(j).name());
                System.out.println(bingSearch.getStoreItems().get(j).thumbnailUrl());
                System.out.println(bingSearch.getStoreItems().get(j).insightsMetadata().aggregateOffer().offers());

                storeRepositiory.save(new Store(bingSearch.getStoreItems().get(j).name(),
                                                bingSearch.getStoreItems().get(j).contentUrl()));
                //System.out.println(bingSearch.getStoreItems().get(j).insightsMetadata().aggregateOffer().offers().indexOf(j));
                System.out.println("--------------------------------------------------");
            }
        }
        else {
            System.out.println("Couldn't find image results!");
        }
        return testSearch;
    }
    //add a product(1) to the store
    @PostMapping("store/add_item")
    public Store create(@RequestBody Map<String, String> store){
        String name = store.get("name");
        return storeRepositiory.save(new Store(name));
    }
    //updates an item by the id
    @PutMapping("store/{id}")
    public boolean update(@PathVariable String id, @RequestBody Map<String, String> body){
        Integer itemId = Integer.parseInt(id);
        Optional<Store> item = storeRepositiory.findById(itemId);
        if(item.isPresent()){
            Store myItem = item.get();
            myItem.setName(body.get("name"));
            storeRepositiory.save(myItem);
            return true;
        } else {
            return false;
        }
    }
    //deletes the item by the id
    @DeleteMapping("store/{id}")
    public boolean delete(@PathVariable String id){
        Integer itemId = Integer.parseInt(id);
        Optional<Store> item = storeRepositiory.findById(itemId);
        if(item.isPresent()){
            storeRepositiory.deleteById(itemId);
            return true;
        } else {
            return false;
        }
    }
    //deletes all the items
    @DeleteMapping("store/deleteAll")
    public boolean deleteAll(){
        storeRepositiory.deleteAll();
        return true;
    }
    //finds the item based on an id, still needs to return appropriate error message
    @GetMapping("store/{id}")
    public Object getOneItem(@PathVariable String id){
        Integer itemId = Integer.parseInt(id);
        Optional<Store> item = storeRepositiory.findById(itemId);
            if (item.isPresent()) {
                storeRepositiory.findById(itemId);
                return ResponseEntity.ok(item.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND);
            }
    }

}
