package org.springframework.github.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
	@NotNull
	private String userName;
	@NotNull
	private String repository;
	@Email
	private String email;
}
