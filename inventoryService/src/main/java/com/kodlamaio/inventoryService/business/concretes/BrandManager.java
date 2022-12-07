package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.BrandDeletedEvent;
import com.kodlamaio.common.events.BrandUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.constants.MessagesForBrand;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryService.dataAccess.BrandRepository;
import com.kodlamaio.inventoryService.entities.Brand;
import com.kodlamaio.inventoryService.kafka.FilterProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService{
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	private FilterProducer filterProducer;

	@Override
	public List<GetAllBrandsResponse> getAll() {
		List<Brand> brands = brandRepository.findAll();
		return brands.stream().map(brand -> modelMapperService.forResponse()
				.map(brand, GetAllBrandsResponse.class)).collect(Collectors.toList());
	}
	
	@Override
	public GetBrandResponse getBrandById(String id) {
		checkIfBrandExistsById(id);
		Brand brand = brandRepository.findById(id).get();
		return modelMapperService.forResponse().map(brand, GetBrandResponse.class);
	}
	
	@Override
	public GetBrandResponse getBrandByName(String name) {
		checkIfBrandExistsByName(name);
		Brand brand = brandRepository.findByName(name);
		return modelMapperService.forResponse().map(brand, GetBrandResponse.class);
	}

	@Override
	public CreateBrandResponse add(CreateBrandRequest createRequest) {
		Brand brand = modelMapperService.forRequest().map(createRequest, Brand.class);
		brand.setId(UUID.randomUUID().toString());
		brandRepository.save(brand);
		return modelMapperService.forResponse().map(brand, CreateBrandResponse.class);
	}

	@Override
	public UpdateBrandResponse update(UpdateBrandRequest updateRequest) {
		checkIfBrandExistsById(updateRequest.getId());
		Brand brand = modelMapperService.forRequest().map(updateRequest, Brand.class);
		Brand temp = brandRepository.save(brand);
		
		BrandUpdatedEvent brandUpdatedEvent = modelMapperService.forResponse().map(temp, BrandUpdatedEvent.class);
		brandUpdatedEvent.setMessage("update brand ");
		brandUpdatedEvent.setBrandId(temp.getId());
		
		this.filterProducer.sendMessage(brandUpdatedEvent);
		
		return modelMapperService.forResponse().map(brand, UpdateBrandResponse.class);
	}

	@Override
	public void delete(String id) {
		checkIfBrandExistsById(id);
		brandRepository.deleteById(id);
		
		BrandDeletedEvent brandEvent = new BrandDeletedEvent();
		brandEvent.setMessage("deleted Brand");
		brandEvent.setBrandId(id);
		
		this.filterProducer.sendMessage(brandEvent);
	}
	
	/// Public Rules \\\
	

	
	/// Private Rules \\\
	
	private void checkIfBrandExistsById(String id) {
		if(brandRepository.findById(id) == null) {
			throw new BusinessException(MessagesForBrand.BrandNotExist);
		}
	}
	
	private void checkIfBrandExistsByName(String name) {
		if(brandRepository.findByName(name) == null) {
			throw new BusinessException(MessagesForBrand.BrandNotExist);
		}
	}
	
}
