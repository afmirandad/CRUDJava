package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private final List<String> items = new ArrayList<>();
	
	public hellocontroller() {
		items.add("Alemania");
		items.add("Argentina");
		items.add("Australia");
		items.add("Bélgica");
		items.add("Brasil");
		items.add("Colombia");
		items.add("Costa Rica");
		items.add("Croacia");
		items.add("Dinamarca");
		items.add("Egipto");
		items.add("España");
		items.add("Francia");
		items.add("Islandia");
		items.add("Irán");
		items.add("Japón");
		items.add("Marruecos");
		items.add("México");
		items.add("Panamá");
		items.add("Perú");
		items.add("Polonia");
		items.add("Portugal");
		items.add("Rusia");
		items.add("Senegal");
		items.add("Serbia");
		items.add("Suecia");
		items.add("Suiza");
		items.add("Túnez");
		items.add("Costa");
	}
	

    //Creación de elementos
	@PostMapping
	public ResponseEntity<Map<String, Object>> addItem(@RequestBody String newItem) {
		Map<String, Object> response = new HashMap<>();
		if(items.contains(newItem)) {
			items.remove(newItem);
			response.put("status", "Item already exists");
		}else{
			items.add(newItem);
			response.put("status","Item inserted succesfully");
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//Consulta de elementos
    //1. Cuando un usuario consulta mediante GET la url /api/v1, retornará todos los items de la lista "items"
    @GetMapping
	public ResponseEntity<Map<String, Object>> getAllItems() {
		Map<String, Object> response = new HashMap<>();
		response.put("items", items);
		response.put("count", items.size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @GetMapping("/{index}")
    public ResponseEntity<Map<String, String>> getItem(@PathVariable int index) {
		Map<String, String> response = new HashMap<>();
    	if(index >= 0 && index < items.size()) {
			response.put("item", items.get(index));
			return new ResponseEntity<>(response, HttpStatus.OK);
    	}else {
			response.put("error", "Invalid index");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    	}
    }
    
    //Actualización de elementos
    @PutMapping("/{index}")
    public ResponseEntity<Map<String, String>> modifyItem(@PathVariable int index, @RequestBody String newItem) {
		Map<String, String> response = new HashMap<>();
    	if(index >= 0 && index < items.size()) {
    		items.set(index, newItem);
			response.put("Info", "Item updated successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
    	}else {
			response.put("error", "Invalid index");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    	}
    }
    
    //Eliminación de elementos
    @DeleteMapping("/{index}")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable int index) {
		Map<String, String> response = new HashMap<>();
    	if(index >= 0 && index < items.size()) {
    		items.remove(index);
			response.put("Info", "Item deleted successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
    	}else {
			response.put("error", "Invalid index");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    	}
    }
    
}