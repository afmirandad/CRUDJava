package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class hellocontroller {
	
	//Creación de elementos
	private List<String> items = new ArrayList<String>();
	
	public hellocontroller() {
		items.add("Colombia");
		items.add("Brasil");
		items.add("Argentina");
		items.add("Peru");
		items.add("Chile");
		items.add("Venezuela");
		items.add("Bolivia");
	}
	

    //Creación de elementos
	@PostMapping
	public String addItem(@RequestBody String newItem) {
		items.add(newItem);
		return "Item insertado con éxito!!";
	}
	
	//Consulta de elementos
    //1. Cuando un usuario consulta mediante GET la url /api/v1, retornará todos los items de la lista "items"
    @GetMapping
    public Map<String, Object> getAllItems() {
    	Map<String, Object> response = new HashMap<>();
    	response.put("items", items);
    	response.put("count", items.size());
        return response;
    }
    
    @GetMapping("/{index}")
    public String getItem(@PathVariable int index) {
    	if(index >= 0 && index < items.size()) {
    		return items.get(index);
    	}else {
    		return "Item no encontrado!";
    	}
    }
    
    //Actualización de elementos
    @PutMapping("/{index}")
    public String modifyItem(@PathVariable int index, @RequestBody String newItem) {
    	if(index >= 0 && index < items.size()) {
    		items.set(index, newItem);
    		return "Item actualizado con éxito";
    	}else {
    		return "Item no encontrado!";
    	}
    }
    
    //Eliminación de elementos
    @DeleteMapping("/{index}")
    public String deleteItem(@PathVariable int index) {
    	if(index >= 0 && index < items.size()) {
    		items.remove(index);
    		return "Item eliminado con éxito";
    	}else {
    		return "Item no encontrado!";
    	}
    }
    
}
