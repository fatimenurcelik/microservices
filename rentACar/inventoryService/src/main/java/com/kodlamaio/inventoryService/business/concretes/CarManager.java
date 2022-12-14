package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CarManager implements CarService{

	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	
	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = this.carRepository.findAll();

		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		checkIfCarExistsByPlate(createCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		
		this.carRepository.save(car);
		
		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfIdExists(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
		Car result = carRepository.save(car);
		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(result, UpdateCarResponse.class);
		return updateCarResponse;
	}
	
	@Override
	public void delete(String id) {
		checkIfIdExists(id);
		this.carRepository.deleteById(id);
	}
	
	private void checkIfCarExistsByPlate(String plate) {
		Car currentCar = this.carRepository.findByPlate(plate);
		if(currentCar!=null) {
		    throw new BusinessException("CAR.EXISTS");
		}
	}

	private void checkIfIdExists(String id) {
		if(!this.carRepository.existsById(id)) {
			throw new BusinessException("CAR.NOT.EXISTS");
		}
	}
}
