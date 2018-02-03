package ink.laoliang.easyciplatform.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class _InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        InterceptorRegistration authorizationInterceptor = registry.addInterceptor(new UserLoginInterceptor());
//        authorizationInterceptor.excludePathPatterns("/user/*");

        super.addInterceptors(registry);
    }
}
