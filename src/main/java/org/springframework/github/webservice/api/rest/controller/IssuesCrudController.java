package org.springframework.github.webservice.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.github.core.service.impl.IssuesCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(IssuesCrudController.PATH)
@RestController
public class IssuesCrudController extends AbstractController implements CrudController<Issues,Long>  {

    public static final String PATH = "/api/issues";

    @Autowired
    protected IssuesCrudService svc;

    @RequestMapping(
            method = RequestMethod.GET)
    public ResponseEntity<List<Issues>> getAll() {
        return ok(svc.getAll());
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Issues> getOne(@PathVariable("id") Long id) throws NotFoundException {
        Issues entity = svc.getOne(id);
        return ok(entity);
    }

    @RequestMapping(
            method = RequestMethod.POST)
    public ResponseEntity<Issues> post(@RequestBody Issues entity) throws ConflictException {
        entity = svc.create(entity);
        return created(entity);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Issues> put(@PathVariable("id") Long id, @RequestBody Issues entity) throws BadRequestException, NotFoundException {
        entity = svc.update(id, entity);
        return ok(entity);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Issues> delete(@PathVariable("id") Long id) throws NotFoundException {
        Issues entity = svc.delete(id);
        return ok(entity);
    }
}
