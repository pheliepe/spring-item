package com.example.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import inginf.Item;
import jakarta.servlet.http.HttpSession;

@RestController
public class ItemRestController {

    /* Zur Verwendung mit dem REST-Client müssen dort
        die folgenden Header eingefügt werden:
      
       {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
     */


    @Autowired
	private ApplicationContext Context;

    private AppStore getItemStore() 
    {
        return Context.getBean(AppStore.class);
    }


    @PostMapping("/items")
    public Item createItem(
        HttpSession session,
        @RequestBody Item item) 
    {
        // Logic to create the item
        AppStore appStore = getItemStore();
        appStore.addNewItem(item);
        return item;
    }

    // Gibt per Konvertion alle Items zurück, sollte man in der
    // Praxis nicht verwenden, weil das im Zweifel sehr viele
    // sein könnten. Man verwendet Methodem die nur passende 
    // Teile der Daten zurückgeben. (s. folgende)
    @GetMapping("/items")
    public ArrayList<inginf.Item> getItems(HttpSession session) {
        return getItemStore().getItemStore();
    }

    // Funktion zur Rückgabe eines bestimmten Items
    // in die Route werden mit {} Platzhalter eingebaut, die dann in Parameter der Funktion
    // eingesetzt werden. HTTP überträgt dabei die Zahl als Zeichenkette das Framework
    // übernimmt die Unwandlung in einen Integer.
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable int id) {
        // Logic to retrieve the item by ID
        AppStore appStore = getItemStore();
        ArrayList<Item> itemStore = appStore.getItemStore();
        
        for (Item item : itemStore) {
            if (item.Id == id) {
                return item;
            }
        }         
        // If item not found, you can throw an exception or return null
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found "+id);
    }

    // Funktion zum Suchen eines Items mittels Suchstrings
    // Der Suchstring wird in der URL als Parameter übergeben.
    // z.B. meinhost.tld/items/search?token=Auto
    // Druch den Decoratpr @RequestParam wird das Framework 
    // angewiesen den Parameter aus der URL zu lesen.
    @GetMapping("/items/search")
    public List<Item> searchItems(@RequestParam("token") String searchString) {
        List<Item> matchingItems = new ArrayList<>();
        List<Item> itemStore = getItemStore().getItemStore();    
        for (Item item : itemStore) {
            if (item.getNomenclature().contains(searchString) || 
                item.getDescription().contains(searchString)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    /*
     * Funktion zum erstellen eines Beispiel-Items
     * dient nur dazu dessen Darstellung im Client zu 
     * demonstrieren.
     */
    @GetMapping("/items/sample")
    public Item createItemSample() {
        Item item = new Item(
            "Sample Item",
            "This is a sample item",
            "Titanium");
        item.Id = -1;
        item.setEstimatedWeight(6);
        item.setCalculatedWeight(7);
        item.setWeightedWeight(8);        
        return item;
    }


}
