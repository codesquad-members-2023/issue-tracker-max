package com.issuetrackermax.domain.filter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.issuetrackermax.service.filter.dto.FilterInformation;

@Mapper
public interface FilterMapper {

	List<FilterResultVO> getFilteredList(FilterInformation filterInformation);

}
