package org.springframework.github.core.service;

import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;

import java.util.List;

public interface CrudService<E,ID> {

    List<E> getAll();

    E getOne(ID id) throws NotFoundException;

    E create(E entity) throws ConflictException;

    E update(ID id, E entity) throws NotFoundException, BadRequestException;

    E delete(ID id) throws NotFoundException;
}
