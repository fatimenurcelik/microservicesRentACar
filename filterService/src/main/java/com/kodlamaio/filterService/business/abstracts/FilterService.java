package com.kodlamaio.filterService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdatedEvent;
import com.kodlamaio.common.events.ModelUpdatedEvent;
import com.kodlamaio.filterService.business.responses.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.GetFilterResponse;

public interface FilterService {
	List<GetAllFiltersResponse> getAll();
	List<GetAllFiltersResponse> getByBrandName(String brandName);
	List<GetAllFiltersResponse> getByModelName(String modelName);
	GetFilterResponse getByPlate(String plate);
	List<GetAllFiltersResponse> searchByPlate(String plate);
	List<GetAllFiltersResponse> searchByBrandName(String brandName);
    List<GetAllFiltersResponse> searchByModelName(String modelName);
    List<GetAllFiltersResponse> getByModelYear(int modelYear);
    List<GetAllFiltersResponse> getByState(int state);
    
    void addCar(CarCreatedEvent carCreatedEvent);
    void deleteCar(CarDeletedEvent carDeletedEvent);
    void updateCar(CarUpdatedEvent carUpdatedEvent);
    
    void updateBrand(BrandUpdatedEvent brandUpdatedEvent);
    void updateModel(ModelUpdatedEvent modelUpdatedEvent);
    
    void deleteAllByBrandId(String brandId);
    void deleteAllByModelId(String modelId);
}
