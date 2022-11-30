package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	@NotNull
	@NotBlank
	private String id;
	@Min(0)
	private double dailyPrice;
	@Min(2015)
	private int modelYear;
	@NotBlank
	@NotNull
	private String plate;
	@Min(1)
	@Max(3)
	private int state;
	@NotBlank
	@NotNull
	private String modelId;
}
