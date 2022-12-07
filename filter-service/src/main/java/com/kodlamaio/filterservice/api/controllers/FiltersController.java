package com.kodlamaio.filterservice.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.responses.GetFilterResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("api/filters")
@RestController
public class FiltersController {
	private FilterService filterService;
	
	@GetMapping()
	public List<GetAllFiltersResponse> getAll(){
		return filterService.getAll();
	}
	
	@GetMapping("/{brandName}")
	public List<GetAllFiltersResponse> getByBrandName(@PathVariable String brandName){
		return filterService.getByBrandName(brandName);
	}
	
	@GetMapping("/{modelName}")
	public List<GetAllFiltersResponse> getByModelName(@PathVariable String modelName){
		return filterService.getByModelName(modelName);
	}
	
	@GetMapping("/{plate}")
	public GetFilterResponse getByPlate(@PathVariable String plate) {
		return filterService.getByPlate(plate);
	}
	
	@GetMapping("/{searchPlate}")
	public List<GetAllFiltersResponse> searchByPlate(@PathVariable String searchPlate ){
		return filterService.searchByPlate(searchPlate);
	}
	
	@GetMapping("/{searchBrand}")
	public List<GetAllFiltersResponse> searchByBrandName(@PathVariable String searchBrand){
		return filterService.searchByBrandName(searchBrand);
	}
	
	@GetMapping("/{searchModel}")
	public List<GetAllFiltersResponse> searchByModelName(@PathVariable String searchModel) {
		return filterService.searchByModelName(searchModel);
	}
	
	@GetMapping("/{modelyear}")
	public List<GetAllFiltersResponse> getByModelYear(@PathVariable int modelYear){
		return filterService.getByModelYear(modelYear);
	}
	
	@GetMapping("/{state}")
	public List<GetAllFiltersResponse> getByState(@PathVariable int state){
		return filterService.getByState(state);
	}
}
