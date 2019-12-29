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

    public BingSearch(){
        this.subscriptionKey = "91d2afd983e64b98931d56441f6a4dd4";
        this.client = BingImageSearchManager.authenticate(subscriptionKey);
        this.storeItems = new ArrayList<>();
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
