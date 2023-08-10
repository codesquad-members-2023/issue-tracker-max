package codesquad.kr.gyeonggidoidle.issuetracker.config;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> jwtAuthorizationFilter(ObjectMapper objectMapper) {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtAuthorizationFilter(objectMapper));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
