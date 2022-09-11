package mx.admino;

import mx.admino.interceptors.CondominosHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMvc implements WebMvcConfigurer {

	@Autowired
	CondominosHandlerInterceptor customHandlerInterceptor;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("home");
	    registry.addViewController("/login").setViewName("auth/login");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customHandlerInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
