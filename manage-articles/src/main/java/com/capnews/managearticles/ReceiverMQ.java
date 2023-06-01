package com.capnews.managearticles;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.capnews.managearticles.service.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReceiverMQ {

    private final ArticleService articleService;
    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @RabbitListener(queues = ManageArticlesApplication.queueName)
    public void receiveMessage(byte[] message) {
        try {

            String messageString = new String(message);
            System.out.println(messageString);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> fields = objectMapper.readValue(messageString,
                    new TypeReference<Map<String, Object>>() {
                    });

            String operationType = (String) fields.get("type");
            String operationTypeSecond = (String) fields.get("operationType");
            System.out.println(operationType);

            if (operationTypeSecond != null && operationTypeSecond.equals("DELETE")) {

                String userPath = (String) fields.get("resourcePath");
                String[] parts = userPath.split("/", 2);
                String userId = parts[1];
                articleService.deleteArticlesByUserId(userId);
            }

            if (operationType != null && operationType.equals("UPDATE_PROFILE")) {

                ObjectMapper newObjectMapper = new ObjectMapper();
                JsonNode jsonNode = newObjectMapper.readTree(messageString);
                JsonNode details = jsonNode.get("details");
                String userId = (String) fields.get("userId");

                if (details.get("updated_first_name") != null) {

                    String firstName = details.get("updated_first_name").asText();
                    articleService.updateFirstNameForArticlesByUserId(userId, firstName);

                }

                if (details.get("updated_last_name") != null) {

                    String lastName = details.get("updated_last_name").asText();
                    articleService.updateLastNameForArticlesByUserId(userId, lastName);

                }

            }

        } catch (Exception e) {
            // Handle any parsing errors
            e.printStackTrace();
        }
        latch.countDown();
    }

    private static void printJsonNodeValues(JsonNode jsonNode) {
        jsonNode.fieldNames().forEachRemaining(fieldName -> {
            JsonNode fieldValue = jsonNode.get(fieldName);
            System.out.println(fieldName + ": " + fieldValue);
        });
    }
}
