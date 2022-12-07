package com.kodlamaio.filterService.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.GetFilterResponse;

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
	
	@GetMapping("/brand")
	public List<GetAllFiltersResponse> getByBrandName(String brandName){
		return filterService.getByBrandName(brandName);
	}
	
	@GetMapping("/model")
	public List<GetAllFiltersResponse> getByModelName(String modelName){
		return filterService.getByModelName(modelName);
	}
	
	@GetMapping("/plate")
	public GetFilterResponse getByPlate(String plate) {
		return filterService.getByPlate(plate);
	}
	
	@GetMapping("/search-plate")
	public List<GetAllFiltersResponse> searchByPlate(String plate){
		return filterService.searchByPlate(plate);
	}
	
	@GetMapping("/search-brand")
	public List<GetAllFiltersResponse> searchByBrandName(String brandName){
		return filterService.searchByBrandName(brandName);
	}
	
	@GetMapping("/search-model")
	public List<GetAllFiltersResponse> searchByModelName(String modelName) {
		return filterService.searchByModelName(modelName);
	}
	
	@GetMapping("/modelyear")
	public List<GetAllFiltersResponse> getByModelYear(int modelYear){
		return filterService.getByModelYear(modelYear);
	}
	
	@GetMapping("/state")
	public List<GetAllFiltersResponse> getByState(int state){
		return filterService.getByState(state);
	}
}
