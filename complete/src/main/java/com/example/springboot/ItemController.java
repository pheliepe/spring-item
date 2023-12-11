package com.example.springboot;

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

import inginf.ItemInstance;

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
    @GetMapping("/")
    public String welcome() {
        return "welcome";
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
        model.addAttribute("id", item.Id);

        return "itemCreated";
    }

    @GetMapping("/items-gui")
    public String createItemDialog() {
        return "itemTemplate";
    } 
    
    @GetMapping("/items-gui/list")
    public String listItems(Model model) {
        model.addAttribute("items", getAppStore().getItemStore());
        return "listItems2";
    }

    @GetMapping("/items-gui/{id}/delete")
    public String deleteItem(@PathVariable int id, Model model) {        
        model.addAttribute(
            "id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) 
            {
                getAppStore().getItemStore().remove(item);
                break;
            }
        return "itemDeleted";
    }

    @GetMapping("/items-gui/{id}/{usage_id}/deleteInstance")
    public String deleteUsage(@PathVariable int id, @PathVariable String usage_id , Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("usage_id", usage_id);
        int tempUsageId = Integer.parseInt(usage_id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) 
            {
                for (ItemInstance instance: item.getUses())
                {
                    if (instance.getInstanceId() == tempUsageId)
                    {
                        item.getUses().remove(instance);
                        ArrayList<ItemInstance> temp = item.getUses();
                        System.out.println(temp);
                        break;
                    }
                }
                break;
            }
            // return "showInstances";
            return "instanceDeleted";
    }

    @GetMapping("/items-gui/{id}/showInstances_delete")
    public String editItem(@PathVariable int id, Model model) {        
        model.addAttribute("id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) 
            {
                model.addAttribute("item", item);
                break;
            }
        return "showInstances_delete";
    }
    @GetMapping("/items-gui/{id}/show")
    public String showItem(@PathVariable String id, Model model) {        
        model.addAttribute("id", id);
        int tempId= Integer.parseInt(id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == tempId) 
            {
                model.addAttribute("item", item);
                break;
            }
        return "showTemplate3";
    }

    @GetMapping("/items-gui/instanceTemplate")
    public String createUsage(Model model, HttpSession session) {
        model.addAttribute("item", getAppStore().getItemStore());
        return "instanceTemplate";
    }

    @PostMapping("/items-gui/instanceTemplate")
    public String createUsage(Model model, HttpSession session, @RequestParam Map<String, String> body) {

        int uses_id = Integer.parseInt(body.get("itemUses"));
        int used_id = Integer.parseInt(body.get("itemUsed"));
        
        Item usesItem = getAppStore().getItemById(uses_id);
        ArrayList<inginf.ItemInstance> uses = usesItem.getUses();

        inginf.ItemInstance instance = new inginf.ItemInstance(body.get("itemUsage"), getAppStore().getItemById(used_id), uses.size());

        uses.add(instance);
        model.addAttribute("usesId", uses_id);
        
        return "instanceCreated";
    }

    @GetMapping("/items-gui/{id}/show_instances")
    public String showInstances(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        for (Item item : getAppStore().getItemStore())
            if (item.Id == id) 
            {
                model.addAttribute(
                    "item", item);
                break;
            }
        return "showInstances";
    }
}