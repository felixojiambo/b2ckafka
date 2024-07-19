package com.b2c.tandapay.service;

import com.b2c.tandapay.dto.B2CResponse;
import com.b2c.tandapay.model.B2CRequest;
import com.b2c.tandapay.repository.B2CRequestRepository;
import com.b2c.tandapay.repository.B2CResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumerService {

    @Autowired
    private MpesaService mpesaService;

    @Autowired
    private B2CRequestRepository b2cRequestRepository;

    @Autowired
    private B2CResponseRepository b2cResponseRepository;

    @KafkaListener(topics = "b2c_requests", groupId = "mpesa_group")
    public void consumeB2CRequest(B2CRequest b2cRequest) throws IOException {
        B2CResponse b2cResponse = mpesaService.performB2CTransaction(b2cRequest);
        if (b2cResponse != null) {
            b2cResponseRepository.save(b2cResponse);
        }
    }

    @KafkaListener(topics = "b2c_responses", groupId = "mpesa_group")
    public void consumeB2CResponse(B2CResponse b2cResponse) {
        b2cResponseRepository.save(b2cResponse);
    }
}
