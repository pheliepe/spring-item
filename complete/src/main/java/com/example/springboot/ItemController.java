package com.example.springboot;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import inginf.Item;

import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

	@Autowired
	private ApplicationContext context;
    AppStore _AppStore;
    AppStore getAppStore() {
        if (_AppStore == null)
            _AppStore = context.getBean(AppStore.class);
        return _AppStore;
    }

    @PostMapping("/items-gui")
    public String createItem(
        Model model,
        HttpSession session,
        @RequestParam Map<String, String> body ) 
    {        
        inginf.Item item = new inginf.Item(
            body.get("Nomenclature"),
            body.get("Description"),
            body.get("Material"));
        if (body.get("WeightedWeight") != null && body.get("WeightedWeight").length() > 0)
            item.setWeightedWeight(Integer.parseInt(body.get("WeightedWeight")));
        if (body.get("CalculatedWeight") != null && body.get("CalculatedWeight").length() > 0)
            item.setCalculatedWeight(Integer.parseInt(body.get("CalculatedWeight")));
        if (body.get("EstimatedWeight") != null && body.get("EstimatedWeight").length() > 0)
            item.setEstimatedWeight(Integer.parseInt(body.get("EstimatedWeight")));
        getAppStore().addNewItem(item);
        model.addAttribute(
                "id", item.Id);
        return "itemCreated";
    }

    @GetMapping("/items-gui")
    public String createItemDialog() {
        return "itemTemplate";
    } 
    
    @GetMapping("/items-gui/list")
    public String listItems(Model model) {
        model.addAttribute(
            "items", 
            getAppStore().getItemStore());
        return "listItems";
    }

    @GetMapping("/items-gui/{id}/delete")
    public String deleteItem(@PathVariable int id, Model model) {        
        model.addAttribute(
            "id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) {
                getAppStore().getItemStore().remove(item);
                break;
            }
        return "itemDeleted";
    }

    @GetMapping("/items-gui/{id}/show")
    public String showItem(@PathVariable int id, Model model) {        
        model.addAttribute(
            "id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) {
                model.addAttribute(
                    "item", item);
                break;
            }
        return "showItem";
    }

    @GetMapping("/items-gui/createUsage")
    public String createUsage(Model model, HttpSession session) {
        model.addAttribute("item", getAppStore().getItemStore());
        return "usage";
    }

    @PostMapping("/items-gui/usage")
    public String createUsage(Model model, HttpSession session, @RequestParam Map<String, String> body) {
        
        String itemId = body.get("itemUses");

        int ItemId = Integer.parseInt(itemId);

        int id = Integer.parseInt(body.get("itemUsed"));

        Item usedItem = getAppStore().getItemById(id);
        ArrayList<inginf.ItemInstance> uses = usedItem.getUses();

        inginf.ItemInstance instance = new inginf.ItemInstance(body.get("itemUsage"), 
                                                  getAppStore().getItemById(id), uses.size());

        for (int i = 0; i < getAppStore().getItemStore().size()-1; i++) {
            if (getAppStore().getItemStore().get(i).Id == ItemId) 
            {
                getAppStore().getItemStore().get(i).getUses().add(instance);
                System.out.println(getAppStore().getItemStore().get(i).getUses());
                model.addAttribute("usageId", instance.getInstanceId());
                break;
            }
        }
        

        return "usageCreated";
    }
}