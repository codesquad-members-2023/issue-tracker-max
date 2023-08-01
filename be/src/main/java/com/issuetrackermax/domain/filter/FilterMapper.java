package com.issuetrackermax.domain.filter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FilterMapper {

	List<FilterResultVO> getFilteredList(Map<String, Object> param);

}
