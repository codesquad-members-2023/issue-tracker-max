package codesquard.app.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import codesquard.app.authenticate_user.resolver.LoginUserArgumentResolver;
import codesquard.app.user.interceptor.UserLoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("http://3.36.120.124:5173", "http://localhost:5173")
			.exposedHeaders("Authorization") // 노출될 헤더 설정
			.allowCredentials(true) // 쿠키 인증 요청 허용
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.maxAge(3600);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginUserArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserLoginInterceptor())
			.order(Ordered.HIGHEST_PRECEDENCE)
			.addPathPatterns("/api/login");
	}
}
