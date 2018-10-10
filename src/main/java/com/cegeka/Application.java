package com.cegeka;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.jms.Queue;

@SpringBootApplication
@EnableTransactionManagement
@EnableJms
public class Application extends SpringBootServletInitializer {
    @Bean(name = "JMSLoggerQueue")
    public Queue jmsLoggerQue() {
        return new ActiveMQQueue("LoggerQueue");
    }
    public static void main(String[] args) {
        // Run the application
        SpringApplication.run(Application.class, args);
    }
    @Bean public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(3145728);
        return multipart;
    } @Bean @Order(0) public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter(); multipartFilter.setMultipartResolverBeanName("multipartReso‌​lver");
        return multipartFilter;
    }

}
