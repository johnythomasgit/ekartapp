package com.underscore.ekartapp;

import com.fasterxml.jackson.core.JsonFactory;
import com.underscore.ekartapp.util.LanguageUtil;
import com.underscore.ekartapp.util.storage.AmazonS3Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EkartappApplication {

    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${aws.s3.url}")
    private String s3Url;
    @Value("${aws.s3.basepath}")
    private String basePath;

    public static void main(String[] args) {
        SpringApplication.run(EkartappApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new JsonFactory();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LanguageUtil LanguageUtil() {
        return new LanguageUtil();
    }

    @Bean
    public AmazonS3Storage amazonS3Storage() {
        AmazonS3Storage awsStorage = new AmazonS3Storage();
        awsStorage.setRegion(region.trim());
        awsStorage.setBucket(bucket.trim());
        awsStorage.setS3Url(s3Url.trim());
        awsStorage.setBasePath(basePath.trim());
        return awsStorage;
    }

}
