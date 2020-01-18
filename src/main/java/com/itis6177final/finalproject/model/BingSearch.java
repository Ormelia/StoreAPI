package com.itis6177final.finalproject.model;

import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchAPI;
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageObject;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;

import java.util.ArrayList;

public class BingSearch {
    private String searchTerm;
//    private ImageObject contentUrl;
    final String subscriptionKey;
    private BingImageSearchAPI client;
    private ArrayList<ImageObject> storeItems;
    private String endPoint;

    public BingSearch(){
        this.subscriptionKey = "34b85ba581794d5ab13fe6c111d73303";
        this.client = BingImageSearchManager.authenticate(subscriptionKey);
        this.storeItems = new ArrayList<>();
        this.endPoint = "https://ormeliafinalproject.cognitiveservices.azure.com/bing/v7.0";
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public ArrayList<ImageObject> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(ImageObject storeItems) {
        this.storeItems.add(storeItems);
    }

    public BingImageSearchAPI getClient() {
        return client;
    }
}
