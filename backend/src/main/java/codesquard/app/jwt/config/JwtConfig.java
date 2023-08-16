package codesquard.app.jwt.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.JwtAuthorizationFilter;
import codesquard.app.jwt.filter.JwtBasicUserFilter;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.service.UserQueryService;

@Configuration
public class JwtConfig {

	@Bean
	public FilterRegistrationBean verifyUserFilter(ObjectMapper objectMapper, UserQueryService userQueryService) {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new VerifyUserFilter(objectMapper, userQueryService));
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/api/login");
		return filterFilterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean jwtBasicUserFilter(AuthenticateUserService authenticateUserService,
		ObjectMapper objectMapper) {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new JwtBasicUserFilter(authenticateUserService, objectMapper));
		filterFilterRegistrationBean.setOrder(2);
		filterFilterRegistrationBean.addUrlPatterns("/api/login");
		return filterFilterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean jwtAuthorizationFilter(JwtProvider provider, ObjectMapper objectMapper,
		RedisTemplate<String, Object> redisTemplate) {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new JwtAuthorizationFilter(provider, objectMapper, redisTemplate));
		filterFilterRegistrationBean.addUrlPatterns("/api/*");
		return filterFilterRegistrationBean;
	}

}
