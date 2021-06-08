package com.example.skoolworkshop2.dao.localData;

import android.content.Context;

import com.example.skoolworkshop2.domain.Product;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class LocalAppStorage {
    private Context context;

    public LocalAppStorage(Context context) {
        this.context = context;
        initDatabase();
    }

    private void initDatabase() {
        Paper.init(context);
    }

    public void createList(String key, Object object) {
        Paper.book().write(key, object);
    }

    public Product getObject(String key){
        return Paper.book().read(key);
    }

    public void addToList(String key, Object value) {
        List<Object> list = Paper.book().read(key, new ArrayList<>());
        list.add(value);
        Paper.book().write(key, list);
    }

    public void removeFromList(String key, int index) {
        List<Object> list = Paper.book().read(key);
        list.remove(index);
        Paper.book().write(key, list);
    }

    public <T> List<T> getList(String key) {
        return Paper.book().read(key, new ArrayList<>());
    }

    public void deleteKey(String key) {
        Paper.book().delete(key);
    }
}
