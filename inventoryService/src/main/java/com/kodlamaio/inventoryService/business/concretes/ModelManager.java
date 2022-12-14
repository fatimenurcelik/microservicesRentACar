package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.ModelDeletedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.MessagesForModel;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryService.dataAccess.ModelRepository;
import com.kodlamaio.inventoryService.entities.Model;
import com.kodlamaio.inventoryService.kafka.FilterProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService{
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private FilterProducer filterProducer;

	@Override
	public List<GetAllModelsResponse> getAll() {
		List<Model> models = modelRepository.findAll();
		return models.stream().map(model -> modelMapperService.forResponse()
				.map(model, GetAllModelsResponse.class))
					.collect(Collectors.toList());
	}
	
	@Override
	public GetModelResponse getModelById(String id) {
		checkIfModelExistsById(id);
		Model model = modelRepository.findById(id).get();
		return modelMapperService.forResponse().map(model, GetModelResponse.class);
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createRequest) {
		checkIfBrandExistById(createRequest.getBrandId());
		Model model = modelMapperService.forRequest().map(createRequest, Model.class);
		model.setId(UUID.randomUUID().toString());
		modelRepository.save(model);
		return modelMapperService.forResponse().map(model, CreateModelResponse.class);
	}

	@Override
	public UpdateModelResponse update(UpdateModelRequest updateRequest) {
		checkIfModelExistsById(updateRequest.getId());
		Model model = modelMapperService.forRequest().map(updateRequest, Model.class);
		modelRepository.save(model);
		return modelMapperService.forResponse().map(model, UpdateModelResponse.class);
	}

	@Override
	public void delete(String id) {
		checkIfModelExistsById(id);
		modelRepository.deleteById(id);
		
		ModelDeletedEvent deletedEvent = new ModelDeletedEvent();
		deletedEvent.setMessage("model deleted");
		deletedEvent.setModelId(id);
		
		filterProducer.sendMessage(deletedEvent);
	}
	
	/// Public Rules \\\
	
	
	
	/// Private Rules \\\
	
	private void checkIfBrandExistById(String id) {
		brandService.getBrandById(id);
	}
	
	private void checkIfModelExistsById(String id) {
		if(modelRepository.findById(id) == null) {
			throw new BusinessException(MessagesForModel.ModelNotExist);
		}
	}
	
}
