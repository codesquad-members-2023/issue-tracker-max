package org.presents.issuetracker.global.config;

import org.presents.issuetracker.global.filter.CorsFilter;
import org.presents.issuetracker.global.filter.JwtAuthorizationFilter;
import org.presents.issuetracker.jwt.JwtProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CorsFilter());
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter(JwtProvider jwtProvider,
		ObjectMapper objectMapper) {
		FilterRegistrationBean<JwtAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthorizationFilter(jwtProvider, objectMapper));
		registrationBean.setOrder(2);
		return registrationBean;
	}
}
