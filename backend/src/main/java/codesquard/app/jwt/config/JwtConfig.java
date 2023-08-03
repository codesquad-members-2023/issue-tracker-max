package codesquard.app.jwt.config;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.service.UserService;

@Configuration
public class JwtConfig {
	private static final Logger logger = LoggerFactory.getLogger(JwtConfig.class);

	@Bean
	public FilterRegistrationBean verifyUserFilter(ObjectMapper objectMapper, UserService userService) {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new VerifyUserFilter(objectMapper, userService));
		filterFilterRegistrationBean.addUrlPatterns("/api/login");
		return filterFilterRegistrationBean;
	}
}
