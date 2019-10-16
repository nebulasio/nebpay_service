package io.nebulas.dapp.pay.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.google.common.collect.Lists;
import io.nebulas.dapp.pay.common.web.JsonpFastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * User: nathan
 * Date: 2018-04-25
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {


    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(86400)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");
    }

    @Configuration
    public static class CustomConversionService extends DelegatingWebMvcConfiguration {

        @Override
        public FormattingConversionService mvcConversionService() {
            // Use the DefaultFormattingConversionService but do not register defaults
            DefaultFormattingConversionService conversionService =
                    new DefaultFormattingConversionService(false);

            // Ensure @NumberFormat is still supported
            conversionService.addFormatterForFieldAnnotation(
                    new NumberFormatAnnotationFormatterFactory());

            DateFormatterRegistrar registrar = new DateFormatterRegistrar();
            registrar.setFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
            registrar.registerFormatters(conversionService);

            return conversionService;
        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //custom json convert
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty
        );

        JsonpFastJsonHttpMessageConverter jsonConverter = new JsonpFastJsonHttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
        jsonConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(jsonConverter);

        // custom string convert
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
        stringConverter.setWriteAcceptCharset(false);
        converters.add(stringConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
