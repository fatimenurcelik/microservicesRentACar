package com.kodlamaio.filterservice.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdatedEvent;
import com.kodlamaio.common.events.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.responses.GetFilterResponse;
import com.kodlamaio.filterservice.dataAccess.FilterRepository;
import com.kodlamaio.filterservice.entities.Filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FilterManager implements FilterService {
	private FilterRepository filterRepository;
	private ModelMapperService mapperService;

	@Override
	public List<GetAllFiltersResponse> getAll() {
		List<Filter> filters = filterRepository.findAll();
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}

	@Override
	public List<GetAllFiltersResponse> getByBrandName(String brandName) {
		List<Filter> filters = filterRepository.findByBrandNameIgnoreCase(brandName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}
	
	@Override
	public List<GetAllFiltersResponse> searchByBrandName(String brandName) {
		List<Filter> filters = filterRepository.findByBrandNameContainingIgnoreCase(brandName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}

	@Override
	public List<GetAllFiltersResponse> getByModelName(String modelName) {
		List<Filter> filters = filterRepository.findByModelNameIgnoreCase(modelName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}
	
	@Override
	public List<GetAllFiltersResponse> searchByModelName(String modelName) {
		List<Filter> filters = filterRepository.findByModelNameContainingIgnoreCase(modelName);
		List<GetAllFiltersResponse> responses = filters.stream()
				.map(filter -> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}
	
	@Override
	public GetFilterResponse getByPlate(String plate) {		
		Filter filter = filterRepository.findByPlate(plate);
		GetFilterResponse response = mapperService.forResponse().map(filter, GetFilterResponse.class);
		return response;
	}

	@Override
	public List<GetAllFiltersResponse> searchByPlate(String plate) {
		List<Filter> filters = filterRepository.findByPlateIgnoreCase(plate);
		List<GetAllFiltersResponse> responses= filters.stream()
				.map(filter-> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}

	@Override
	public List<GetAllFiltersResponse> getByModelYear(int modelYear) {
		List<Filter> filters = filterRepository.findByModelYear(modelYear);
		List<GetAllFiltersResponse> responses= filters.stream()
				.map(filter-> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}

	@Override
	public List<GetAllFiltersResponse> getByState(int state) {
		List<Filter> filters = filterRepository.findByState(state);
		List<GetAllFiltersResponse> responses= filters.stream()
				.map(filter-> mapperService.forResponse().map(filter, GetAllFiltersResponse.class))
				.collect(Collectors.toList());
		return responses;
	}

	
	
	

	@Override
	public void addCar(CarCreatedEvent carCreatedEvent) {
		Filter filter = mapperService.forRequest().map(carCreatedEvent, Filter.class);
		filter.setId(UUID.randomUUID().toString());
		filterRepository.save(filter);
	}

	@Override
	public void deleteCar(CarDeletedEvent carDeletedEvent) {
		filterRepository.deleteByCarId(carDeletedEvent.getCarId());
	}
	
	@Override
	public void updateCar(CarUpdatedEvent carUpdatedEvent) {
		Filter filter = mapperService.forRequest().map(carUpdatedEvent, Filter.class);
		filterRepository.save(filter);
	}

	@Override
	public void updateBrand(BrandUpdatedEvent brandUpdatedEvent) {
		List<Filter> filters =filterRepository.findByBrandId(brandUpdatedEvent.getBrandId());
		for (Filter filter : filters) {
			filter=mapperService.forRequest().map(brandUpdatedEvent, Filter.class);
			filterRepository.save(filter);
		}
	}

	@Override
	public void updateModel(ModelUpdatedEvent modelUpdatedEvent) {
		List<Filter> filters =filterRepository.findByBrandId(modelUpdatedEvent.getModelId());
		for (Filter filter : filters) {
			filter=mapperService.forRequest().map(modelUpdatedEvent, Filter.class);
			filterRepository.save(filter);
		}
	}		

	@Override
	public void deleteAllByBrandId(String brandId) {
		filterRepository.deleteAllByBrandId(brandId);
	}

	@Override
	public void deleteAllByModelId(String modelId) {
		filterRepository.deleteAllByModelId(modelId);
	}
}
