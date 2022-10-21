package com.wf.wimt.CustomerActivityConsumer.consumer;

import com.google.gson.Gson;
import com.wf.wimt.CustomerActivityConsumer.model.CustomerActivity;
import com.wf.wimt.CustomerActivityConsumer.repository.CustomerActivityRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


@Component
@KafkaListener(id="someId", topics = "#{'${kafka.topics}'.split(',')}")
public class CustomerActivityConsumer {

    @Autowired
    ClassLoaderTemplateResolver classLoaderTemplateResolver;

    @Autowired
    CustomerActivityRepository customerActivityRepository;

    @KafkaHandler(isDefault = true)
    public void handle(HashMap jsonMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName){
        var transformationProperties = fetchServiceTransformationProperties(topicName);

        var context = new Context();
        context.setVariable("activitySource", topicName);
        context.setVariable("payload", new Gson().toJson(jsonMessage));

        context.setVariable("timestamp", valueFromJsonMessage(transformationProperties, "timestamp", jsonMessage));
        context.setVariable("customerId", valueFromJsonMessage(transformationProperties, "customerId", jsonMessage));
        context.setVariable("activityType", valueFromJsonMessage(transformationProperties, "activityType", jsonMessage));
        context.setVariable("orgName", valueFromJsonMessage(transformationProperties, "orgName", jsonMessage));

        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        var transformedMessage =  templateEngine.process("customerActivityTemplate", context);
        System.out.println("transformedMessage -->"+ transformedMessage);

        customerActivityRepository.save(new CustomerActivity("123", "service1", "{}", "01-01-2020", "created", "WIMT"));
        var all = customerActivityRepository.findAll();
        all.forEach(System.out::println);
        System.out.println("saved in repo");
    }

    private String valueFromJsonMessage(Properties transformationProperties, String name, HashMap jsonMessage){
        String mapping = (String)transformationProperties.get(name);
        if(mapping.contains(".")){
            var value = jsonMessage.get(mapping.substring(1));
            return ObjectUtils.nullSafe((String)value, "");
        }else{
            return mapping;
        }
    }

    private Properties fetchServiceTransformationProperties(String topicName) {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("transformer/services/"+topicName+"/translation.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
