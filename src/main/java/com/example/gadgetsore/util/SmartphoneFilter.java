package com.example.gadgetsore.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SmartphoneFilter {
    private SortedSet<String> listBrand = new TreeSet<>();
    private  SortedSet<String> listModel = new TreeSet<>();
    private SortedSet<Integer> listMemory = new TreeSet<>();
    private SortedSet<Integer> listStorage = new TreeSet<>();
    private SortedSet<String> listColor = new TreeSet<>();

    public SortedSet<String> getListBrand() {
        return listBrand;
    }

    public void setListBrand(SortedSet<String> listBrand) {
        this.listBrand = listBrand;
    }

    public SortedSet<String> getListModel() {
        return listModel;
    }

    public void setListModel(SortedSet<String> listModel) {
        this.listModel = listModel;
    }

    public SortedSet<Integer> getListMemory() {
        return listMemory;
    }

    public void setListMemory(SortedSet<Integer> listMemory) {
        this.listMemory = listMemory;
    }

    public SortedSet<Integer> getListStorage() {
        return listStorage;
    }

    public void setListStorage(SortedSet<Integer> listStorage) {
        this.listStorage = listStorage;
    }

    public SortedSet<String> getListColor() {
        return listColor;
    }

    public void setListColor(SortedSet<String> listColor) {
        this.listColor = listColor;
    }


}
