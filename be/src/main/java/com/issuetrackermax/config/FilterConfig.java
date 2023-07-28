package com.issuetrackermax.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetrackermax.common.filter.JwtAuthorizationFilter;
import com.issuetrackermax.domain.jwt.service.JwtProvider;

@Configuration
public class FilterConfig {
	private final JwtProvider jwtProvider;

	public FilterConfig(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Bean
	public FilterRegistrationBean<Filter> jwtAuthorizationFilter(ObjectMapper mapper) {

		FilterRegistrationBean<Filter> filterRegistrationBean = new
			FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtAuthorizationFilter(mapper, jwtProvider));
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
