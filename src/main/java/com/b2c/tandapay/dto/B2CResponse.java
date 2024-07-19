package com.b2c.tandapay.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "b2c_responses")
@Data
public class B2CResponse {
    @Id
    private String id;
    private String requestId;
    private String status;
    private String description;

}
