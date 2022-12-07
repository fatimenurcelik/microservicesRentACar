package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.CarCreatedEvent;
import com.kodlamaio.common.events.CarDeletedEvent;
import com.kodlamaio.common.events.CarUpdatedEvent;
import com.kodlamaio.common.events.ConsumeRentalCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.MessagesForCar;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarStateResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.kafka.FilterProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService{
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;
	private FilterProducer filterProducer;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = carRepository.findAll();
		return cars.stream().map(car -> modelMapperService.forResponse()
				.map(car, GetAllCarsResponse.class))
					.collect(Collectors.toList());
	}
	
	@Override
	public GetCarResponse getCarById(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		return modelMapperService.forResponse().map(car, GetCarResponse.class);
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createRequest) {
		checkIfPlateExists(createRequest.getPlate());
		checkIfModelExistById(createRequest.getModelId());
		Car car = modelMapperService.forRequest().map(createRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		Car temp = carRepository.save(car);
		CarCreatedEvent carCreatedEvent = new CarCreatedEvent();
		carCreatedEvent.setCarId(temp.getId());
		carCreatedEvent.setBrandId(temp.getModel().getBrand().getId());
		carCreatedEvent.setBrandName(temp.getModel().getBrand().getName());
		carCreatedEvent.setDailyPrice(temp.getDailyPrice());
		carCreatedEvent.setMessage("");
		carCreatedEvent.setModelId(temp.getModel().getId());
		carCreatedEvent.setModelName(temp.getModel().getName());
		carCreatedEvent.setModelYear(temp.getModelYear());
		carCreatedEvent.setPlate(temp.getPlate());
		carCreatedEvent.setState(temp.getState());
		
		this.filterProducer.sendMessage(carCreatedEvent);
		
		return modelMapperService.forResponse().map(car, CreateCarResponse.class);
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateRequest) {
		checkIfCarExistsById(updateRequest.getId());
		Car car = modelMapperService.forRequest().map(updateRequest, Car.class);
		Car tempCar =carRepository.save(car);
		CarUpdatedEvent carUpdatedEvent = modelMapperService.forResponse().map(tempCar, CarUpdatedEvent.class);
		carUpdatedEvent.setBrandId(tempCar.getModel().getBrand().getId());
		carUpdatedEvent.setBrandName(tempCar.getModel().getBrand().getName());
		carUpdatedEvent.setModelName(tempCar.getModel().getName());
		carUpdatedEvent.setModelId(tempCar.getModel().getId());
		carUpdatedEvent.setCarId(tempCar.getId());
		carUpdatedEvent.setMessage("car updated");
		
		this.filterProducer.sendMessage(carUpdatedEvent);
		
		return modelMapperService.forResponse().map(car, UpdateCarResponse.class);
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
		
		CarDeletedEvent carDeletedEvent = new CarDeletedEvent();
		carDeletedEvent.setCarId(id);
		carDeletedEvent.setMessage("car deleted");
		
		this.filterProducer.sendMessage(carDeletedEvent);
	}
	

	@Override
	public UpdateCarStateResponse updateCarState(String carId, int state) {
		Car car =this.carRepository.findById(carId).get();
		
		//car = modelMapperService.forRequest().map(car, Car.class);
		
		car.setState(state);
		
		this.carRepository.save(car);
		
		ConsumeRentalCreatedEvent consumeRentalCreatedEvent = new ConsumeRentalCreatedEvent();
		consumeRentalCreatedEvent.setCarId(car.getId());
		consumeRentalCreatedEvent.setMessage("Car state updated");
		
		UpdateCarStateResponse updateCarState= this.modelMapperService.forResponse().map(car, UpdateCarStateResponse.class);
		return updateCarState;
		
	}
	
	@Override
	public void checkCarAvailabel(String id) {
		Car tempCar = carRepository.findById(id).orElse(null);
		if(tempCar == null || tempCar.getState() == 1 ) {
			throw new BusinessException("CarNoExist or state is not availabel");
		}
	}
	/// Public Rules \\\

	
	
	/// Private Rules \\\
	
	private void checkIfCarExistsById(String id) {
		if(carRepository.findById(id) == null) {
			throw new BusinessException(MessagesForCar.CarNotExist);
		}
	}
	
	private void checkIfPlateExists(String plate) {
		if(carRepository.findByPlate(plate) != null) {
			throw new BusinessException(MessagesForCar.PlateExist);
		}
	}
	
	private void checkIfModelExistById(String id) {
		modelService.getModelById(id);
	}

	
	
}
