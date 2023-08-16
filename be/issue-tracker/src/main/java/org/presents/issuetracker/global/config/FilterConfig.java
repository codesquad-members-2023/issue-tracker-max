package org.presents.issuetracker.global.config;

import org.presents.issuetracker.global.filter.JwtAuthorizationFilter;
import org.presents.issuetracker.jwt.JwtProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter(JwtProvider jwtProvider) {
		FilterRegistrationBean<JwtAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthorizationFilter(jwtProvider));
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
