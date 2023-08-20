package codesquad.kr.gyeonggidoidle.issuetracker.config;

import codesquad.kr.gyeonggidoidle.issuetracker.config.filter.CorsFilter;
import codesquad.kr.gyeonggidoidle.issuetracker.config.filter.JwtAuthorizationFilter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> corsFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> jwtAuthorizationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper) {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtAuthorizationFilter(jwtProvider, objectMapper));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
