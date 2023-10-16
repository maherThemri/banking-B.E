package com.thamri.banking.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Contact extends AbstractEntity{
	
	private String firstname;

	private String lastname;

	private String email;
	
	private String iban;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
}
