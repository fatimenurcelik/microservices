package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
	@NotBlank
	@NotNull
	private String id;
	@NotBlank
	@NotNull
	@Size(min=2, max=20)
	private String name;
}
