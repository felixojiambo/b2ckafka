package com.b2c.tandapay.service;
import com.b2c.tandapay.dto.B2CResponse;
import com.b2c.tandapay.model.B2CRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendB2CRequest(B2CRequest b2cRequest) {
        kafkaTemplate.send("b2c_requests", b2cRequest);
    }

    public void sendB2CResponse(B2CResponse b2cResponse) {
        kafkaTemplate.send("b2c_responses", b2cResponse);
    }
}
