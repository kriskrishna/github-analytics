package org.springframework.github.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.repository.IssuesRepository;
import org.springframework.github.core.service.CrudService;
import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuesCrudService implements CrudService<Issues, Long> {

    @Autowired
    protected IssuesRepository repo;

    public static final String NotFoundMessage = "The Issue was Not Found.";
    public static final String ConflictMessage = "The Issue ID is in conflict with current data.";
    public static final String BadRequestMessage = "The Issue is not formed correctly.";

    public List<Issues> getAll() {
        return repo.findAll();
    }

    public Issues getOne(Long id) throws NotFoundException {
        Issues retval = repo.findOne(id);
        if (retval == null) {
            throw new NotFoundException(NotFoundMessage);
        }
        return retval;
    }

    public Issues create(Issues entity) throws ConflictException {
        if (entity.getId() != null) {
            throw new ConflictException(ConflictMessage);
        }
        return repo.saveAndFlush(entity);
    }

    public Issues update(Long id, Issues entity) throws NotFoundException, BadRequestException {
        if (!repo.exists(id)) {
            throw new NotFoundException(NotFoundMessage);
        }
        if (!id.equals(entity.getId())) {
            throw new BadRequestException(BadRequestMessage);
        }
        return repo.saveAndFlush(entity);
    }

    public Issues delete(Long id) throws NotFoundException {
        Issues entity = repo.findOne(id);
        if (entity == null) {
            throw new NotFoundException(NotFoundMessage);
        }
        repo.delete(id);
        return entity;
    }
}
