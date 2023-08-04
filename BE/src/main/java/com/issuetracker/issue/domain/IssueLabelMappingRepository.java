package com.issuetracker.issue.domain;

import java.util.List;

import com.issuetracker.label.domain.Label;

public interface IssueLabelMappingRepository {

	int[] saveAll(List<IssueLabelMapping> issueLabelMappings);

	List<Label> findAll();
}
