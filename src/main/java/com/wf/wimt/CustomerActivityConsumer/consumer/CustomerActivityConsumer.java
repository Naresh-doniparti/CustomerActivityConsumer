package com.wf.wimt.CustomerActivityConsumer.consumer;

import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.wf.wimt.CustomerActivityConsumer.repository.CustomerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
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
        String json = new Gson().toJson(jsonMessage);

        var context = new Context();
        context.setVariable("ActivitySource", topicName);
        context.setVariable("Payload", json);

        var documentContext = JsonPath.parse(json);
        context.setVariable("CreatedDate", valueFromJsonMessage(transformationProperties, "CreatedDate", documentContext));
        context.setVariable("RequestedBy", valueFromJsonMessage(transformationProperties, "RequestedBy", documentContext));
        context.setVariable("SRType", valueFromJsonMessage(transformationProperties, "SRType", documentContext));
        context.setVariable("LOBName", valueFromJsonMessage(transformationProperties, "LOBName", documentContext));

        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        var transformedMessage =  templateEngine.process("customerActivityTemplate", context);
        System.out.println("transformedMessage -->"+ transformedMessage);

//        customerActivityRepository.save(new CustomerActivity("123", "service1", "{}", "01-01-2020", "created", "WIMT"));
//        var all = customerActivityRepository.findAll();
//        all.forEach(System.out::println);
//        System.out.println("saved in repo");
    }
    private String valueFromJsonMessage(Properties transformationProperties, String name, DocumentContext documentContext){
        Optional mappingOptional = Optional.ofNullable(transformationProperties.get(name));
        if(mappingOptional.isEmpty()){
            return "";
        }
        var mapping = String.valueOf(mappingOptional.get());
        System.out.println("mapping found"+ mapping);
        if(mapping.contains(".")){
            return documentContext.read(mapping);
        }else{
            return mapping;
        }
    }
    private Properties fetchServiceTransformationProperties(String topicName) {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        InputStream stream = loader.getResourceAsStream("transformer/topics/" +topicName+"/translation.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
