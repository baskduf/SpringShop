package my.shop;

import lombok.RequiredArgsConstructor;
import my.shop.interceptor.AuthAdminInterceptor;
import my.shop.auth.AuthArgumentResolver;
import my.shop.interceptor.AuthInterceptor;
import my.shop.interceptor.MemberInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final MemberInterceptor memberInterceptor;
    private final AuthInterceptor authInterceptor;
    private final AuthAdminInterceptor authAdminInterceptor;

    @Value("${audio.file.path}")
    private String audioPath;

    @Value("${image.file.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 리소스 경로 설정
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + audioPath);


    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor).order(1);
        registry.addInterceptor(authInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/member/signup", "/auth/login", "/auth/logout",
                        "/css/**", "/*.ico", "/error", "/assets/**", "/images/**", "/upload/audio/**", "/upload/images/**", "/file/**"
                );
        registry.addInterceptor(authAdminInterceptor)
                .order(3)
                .addPathPatterns("/admin/**", "/item/add", "/item/edit");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }

}
