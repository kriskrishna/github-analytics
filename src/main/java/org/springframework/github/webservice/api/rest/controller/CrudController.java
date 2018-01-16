package org.springframework.github.webservice.api.rest.controller;

import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CrudController<E,ID> {

    ResponseEntity<List<E>> getAll();

    ResponseEntity<E> getOne(@PathVariable("id") ID id) throws NotFoundException;

    ResponseEntity<E> post(@RequestBody E entity) throws ConflictException;

    ResponseEntity<E> put(@PathVariable("id") ID id, @RequestBody E entity) throws NotFoundException, BadRequestException;

    ResponseEntity<E> delete(@PathVariable("id") ID id) throws NotFoundException;
}
