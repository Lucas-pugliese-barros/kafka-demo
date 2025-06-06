package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.MessageDTO;
import com.example.kafka.producer.dto.MessageType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = {"topic-messages"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.group-id=test-group",
        "spring.kafka.consumer.auto-offset-reset=earliest",
        "spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
        "spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
        "spring.kafka.consumer.properties.spring.json.trusted.packages=com.example.kafka.producer.dto"
})
class MessageControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private MessageDTO receivedMessage;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Test
    void testSendMessage_PublishesToKafka() throws InterruptedException {
        MessageDTO message = new MessageDTO("id", "content",  MessageType.ACCOUNT_OPENED);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<MessageDTO> request = new HttpEntity<>(message, headers);

        ResponseEntity<MessageDTO> response = restTemplate.exchange(
                "/api/messages",
                HttpMethod.POST,
                request,
                MessageDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue();

        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.id()).isEqualTo(message.id());
        assertThat(receivedMessage.content()).isEqualTo(message.content());
    }

    @KafkaListener(topics = "topic-messages", groupId = "test-group")
    public void listen(MessageDTO message) {
        this.receivedMessage = message;
        latch.countDown();
    }
}
