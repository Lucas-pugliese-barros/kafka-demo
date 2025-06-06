package com.example.kafka.consumer.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDTOTest {

    @Test
    public void validateIdField() {
        MessageDTO messageDTO = new MessageDTO("id", null, MessageType.ACCESS_GRANTED);

        assertThat(messageDTO.id()).isNotBlank();
        assertThat(messageDTO.content()).isNull();
    }

    @Test
    public void validateContentField() {
        MessageDTO messageDTO = new MessageDTO(null, "content", MessageType.ACCESS_GRANTED);

        assertThat(messageDTO.id()).isNull();
        assertThat(messageDTO.content()).isNotBlank();
    }

    @Test
    public void validateIdAndContentField() {
        MessageDTO messageDTO = new MessageDTO("id", "content", MessageType.ACCESS_GRANTED);

        assertThat(messageDTO.id()).isEqualTo("id");
        assertThat(messageDTO.id()).isNotBlank();

        assertThat(messageDTO.content()).isEqualTo("content");
        assertThat(messageDTO.content()).isNotBlank();
    }
}
