package codesquad.issueTracker.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.issueTracker.global.filter.AuthFilter;
import codesquad.issueTracker.jwt.util.JwtProvider;

@Configuration
public class WebConfig {

	@Bean
	public FilterRegistrationBean authFilter(JwtProvider provider, ObjectMapper mapper) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new AuthFilter(mapper, provider));
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
