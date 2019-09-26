package com.changgou.file.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @author zl
 * @version V1.0
 * @Description: TODO
 * @date 2019/9/24 16:32
 **/
public class FeignMutipartFormEncoder {
            @Bean
            @Primary
            @Scope("prototype")
            public Encoder multipartFormEncoder() {
                return new SpringFormEncoder();
            }

            @Bean
            public feign.Logger.Level multipartLoggerLevel() {
                return feign.Logger.Level.FULL;
            }
    }

