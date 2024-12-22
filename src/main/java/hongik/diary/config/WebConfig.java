package hongik.diary.config;

import hongik.diary.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * addPathPatterns 안의 패턴을 가지는 url에 대하여 LoginInterceptor를 작동.
 * excludePathPattern 안의 url들에 대해서는 동작하지 않음.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/diary/view/**", "/diary/write", "/diary/modify/**", "/diary/delete/**", "/diary/update/**")
                .excludePathPatterns("/diary/list", "/login", "/signup", "/logout");
    }
}
