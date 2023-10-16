package com.thamri.banking.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends AbstractEntity{
	
	private String street;
	
	private Integer houseNumber;
	
	private Integer zipCode;
	
	private String city;
	
	private String country;
	
	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;
	
}
