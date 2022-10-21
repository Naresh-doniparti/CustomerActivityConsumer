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
    public void handle(HashMap message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName){
        Context templateContext = templateContext(topicName, message);
        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        var consumerActivityMessage =  templateEngine.process("customerActivityTemplate", templateContext);
        System.out.println("consumerActivityMessage -->"+ consumerActivityMessage);
//        customerActivityRepository.save(new CustomerActivity("123", "service1", "{}", "01-01-2020", "created", "WIMT"));
//        var all = customerActivityRepository.findAll();
//        all.forEach(System.out::println);
//        System.out.println("saved in repo");
    }
    private Context templateContext(String topicName, HashMap map) {
        var topicTransformationProperties = topicTransformationProperties(topicName);

        String message = new Gson().toJson(map);
        var documentContext = JsonPath.parse(message);
        var context = new Context();
        context.setVariable("LOBName", valueFromMessage(topicTransformationProperties, "LOBName", documentContext));
        context.setVariable("SRType", valueFromMessage(topicTransformationProperties, "SRType", documentContext));
        context.setVariable("SRSubType", valueFromMessage(topicTransformationProperties, "SRSubType", documentContext));
        context.setVariable("SRStatus", valueFromMessage(topicTransformationProperties, "SRStatus", documentContext));
        context.setVariable("CreatedDate", valueFromMessage(topicTransformationProperties, "CreatedDate", documentContext));
        context.setVariable("EndDate", valueFromMessage(topicTransformationProperties, "CreatedDate", documentContext));
        context.setVariable("RequestedBy", valueFromMessage(topicTransformationProperties, "RequestedBy", documentContext));
        context.setVariable("RequestDescription", valueFromMessage(topicTransformationProperties, "RequestDescription", documentContext));
        context.setVariable("ResolutionDescription", valueFromMessage(topicTransformationProperties, "ResolutionDescription", documentContext));
        context.setVariable("ActivitySource", topicName);
        context.setVariable("Payload", message);
        return context;
    }

    private String valueFromMessage(Properties transformationProperties, String key, DocumentContext documentContext){
        Optional mappingOptional = Optional.ofNullable(transformationProperties.get(key));
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
    private Properties topicTransformationProperties(String topicName) {
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
