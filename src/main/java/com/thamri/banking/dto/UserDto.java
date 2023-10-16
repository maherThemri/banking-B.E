package com.thamri.banking.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.thamri.banking.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

	private Integer id;
	
	@NotNull(message="le prenom est null")
	@NotEmpty(message="le prenom est null")
	@NotBlank(message="le prenom est null")
	private String firstname;

	@NotNull(message="le prenom est null")
	@NotEmpty(message="le prenom est null")
	@NotBlank(message="le prenom est null")
	private String lastname;

	@NotNull(message="l'email est null")
	@NotEmpty(message="l'email est null")
	@NotBlank(message="l'email est null")
	@Email(message="l'email n'est pas conforme")
	private String email;
	
	@NotEmpty(message="Le mot de passe est null")
	@NotNull(message="Le mot de passe est null")
	@NotBlank(message="Le mot de passe est null")
	@Size(min=8, max=16 , message="Le mot de passe doit d'etre entre 8 et 16 caractere")
	private String password;
	
	private String iban;
	
	private Boolean active;
	
	public static UserDto fromEntity(User user) {
		//check null
		return UserDto.builder()
				.id(user.getId())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.email(user.getEmail())
				.password(user.getPassword())
				.iban(user.getAccount()== null ? " " : user.getAccount().getIban())
				.active(user.isActive())
				.build();
	}
	
	public static User toEntity(UserDto user) {
		//check null
		return User.builder()
				.id(user.getId())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();
	}
}
