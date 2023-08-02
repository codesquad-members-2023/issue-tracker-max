package com.issuetrackermax.domain.filter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.issuetrackermax.controller.filter.dto.FilterRequest;

@Mapper
public interface FilterMapper {

	List<FilterResultVO> getFilteredList(FilterRequest param);

}
