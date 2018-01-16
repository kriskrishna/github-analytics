/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.github.core.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "issues")
public class Issues {

	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String repository;

	public Issues(String username, String repository) {
		this.username = username;
		this.repository = repository;
	}

	public Issues() {
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRepository() {
		return this.repository;
	}

	public void setRepository(String lastname) {
		this.repository = lastname;
	}

	@Override
	public String toString() {
		return "IssueCreation [username=" + this.username + ", repository=" + this.repository
				+ "]";
	}
}
