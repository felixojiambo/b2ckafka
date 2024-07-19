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
    private String OriginatorConversationID;
    private String InitiatorName;
    private String SecurityCredential;
    private String CommandID;
    private String Amount;
    private String PartyA;
    private String PartyB;
    private String Remarks;
    private String QueueTimeOutURL;
    private String ResultURL;
    private String Occassion;
}
