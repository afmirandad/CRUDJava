package com.example.demo;

import java.util.List;

public class CountryResponse {
    private int count;
    private List<String> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}