package com.b2c.tandapay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "b2c_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class B2CRequest {
    @Id
    private String id;
    private String commandID;
    private double amount;
    private String partyB;
    private String remarks;
    private String occasion;
    private String securityCredential;
    private String resultURL;
    private String queueTimeOutURL;
    private String initiatorName;
    private String partyA;
}
