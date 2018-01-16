package org.springframework.github.core.service.impl;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.github.core.model.IssueDto;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.repository.IssuesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Marcin Grzejszczak
 */
@Service
public class IssuesService {
	private static final Logger log = LoggerFactory.getLogger(IssuesService.class);

	public final IssuesRepository repository;

	IssuesService(IssuesRepository repository, MeterRegistry meterRegistry) {
		this.repository = repository;
		meterRegistry.gauge("issues", this, IssuesService::count);
	}

	public void save(String user, String repo) {
		log.info("Saving user [{}], and repo [{}]", user, repo);
		this.repository.save(new Issues(user, repo));
	}

	public List<IssueDto> allIssues() {
		List<IssueDto> dtos = new ArrayList<>();
		this.repository.findAll().forEach(i -> dtos.add(new IssueDto(i.getUsername(), i.getRepository(), null)));
		return dtos;
	}

	public long numberOfIssues() {
		return count();
	}

	private long count() {
		return this.repository.count();
	}

	public void deleteAll() {
		log.info("Deleting all issues");
		this.repository.deleteAll();
	}

}

