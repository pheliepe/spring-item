package com.example.springboot;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;


/* Diese Klasse gibt es im Kontext der Applikation nur einmal
 * und wird von Spring verwaltet. Sie ist ist Platzhalter für einen
 * Datenbankzugriff, der hier nicht implementiert ist.
 */

@ApplicationScope 
@Component
public class AppStore {

    inginf.SampleStore sampleStore = new inginf.SampleStore();

    private java.util.ArrayList<inginf.Item> itemStore 
        = new java.util.ArrayList<inginf.Item>();
    
    public AppStore() {
        itemStore = sampleStore.getItems();
    }

    public ArrayList<inginf.Item> getItemStore() {
        return itemStore;
    }        
    public int getItemCount() {
        return itemStore.size();
    }
    public inginf.Item addNewItem(inginf.Item item) {
        if (itemStore.size() == 0)
            item.Id = 1;
        else
            item.Id = itemStore.get(itemStore.size()-1).Id + 1;
        itemStore.add(item);
        return item;
    }    
}
