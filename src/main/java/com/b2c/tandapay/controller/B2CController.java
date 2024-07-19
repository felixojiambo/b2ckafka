package com.b2c.tandapay.controller;
import com.b2c.tandapay.dto.AccessTokenResponse;
import com.b2c.tandapay.dto.B2CResponse;
import com.b2c.tandapay.model.B2CRequest;
import com.b2c.tandapay.repository.B2CRequestRepository;
import com.b2c.tandapay.repository.B2CResponseRepository;
import com.b2c.tandapay.service.KafkaProducerService;
import com.b2c.tandapay.service.MpesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/b2c")
public class B2CController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private B2CRequestRepository b2cRequestRepository;

    @Autowired
    private B2CResponseRepository b2cResponseRepository;

    @Autowired
    private MpesaService mpesaService;

    // Handle incoming B2C request
    @PostMapping("/request")
    public void receiveB2CRequest(@RequestBody B2CRequest b2cRequest) {
        // Save B2C request to MongoDB
        b2cRequestRepository.save(b2cRequest);

        // Send B2C request to Kafka
        kafkaProducerService.sendB2CRequest(b2cRequest);
    }

    // Fetch payment status by request ID
    @GetMapping("/status/{requestId}")
    public Optional<B2CResponse> fetchPaymentStatus(@PathVariable String requestId) {
        return b2cResponseRepository.findById(requestId);
    }

    // Update payment status by request ID
    @PutMapping("/status/{requestId}")
    public void updatePaymentStatus(@PathVariable String requestId, @RequestBody B2CResponse b2cResponse) {
        b2cResponse.setConversationID(requestId);
        b2cResponseRepository.save(b2cResponse);
    }

    // Perform B2C transaction
    @PostMapping("/transaction")
    public B2CResponse performB2CTransaction(@RequestBody B2CRequest b2CRequest) {
        try {
            return mpesaService.performB2CTransaction(b2CRequest);
        } catch (IOException e) {
            throw new RuntimeException("Error performing B2C transaction", e);
        }
    }

    // Get access token
    @GetMapping("/token")
    public AccessTokenResponse getAccessToken() {
        return mpesaService.getAccessToken();
    }
}
