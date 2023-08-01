package com.issuetrackermax.domain.filter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilterMapper {

	List<FilterResultVO> getFilteredList(Map<String, Object> param);

}
