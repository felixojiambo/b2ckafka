package com.b2c.tandapay.dto;

import lombok.Data;

@Data
public class B2CResponse {
    private String ConversationID;
    private String OriginatorConversationID;
    private String ResponseCode;
    private String ResponseDescription;
}
