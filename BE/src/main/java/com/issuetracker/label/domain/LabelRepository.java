package com.issuetracker.label.domain;

import java.util.List;

public interface LabelRepository {

	boolean existByIds(List<Long> labelIds);
}
