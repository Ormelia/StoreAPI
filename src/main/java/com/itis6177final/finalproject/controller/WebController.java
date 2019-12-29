package com.itis6177final.finalproject.controller;

import com.itis6177final.finalproject.model.BingSearch;
import com.itis6177final.finalproject.model.Store;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @RequestMapping("/sample")
    public Store sample(@RequestParam(value = "name", defaultValue = "Basketball") String name){
        Store item = new Store();
        item.setId(1);
        item.setName("Your item is "+name);
        return item;
    }
    @RequestMapping("/search")
    public BingSearch mySearch(@RequestParam(value = "searchTerm", defaultValue = "Men's Preppy Clothing Sale: Discount & Clearance Items") String searchTerm){
        BingSearch bingSearch = new BingSearch();
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
            }
        }
        else {
            System.out.println("Couldn't find image results!");
        }
        return bingSearch;
    }
}
