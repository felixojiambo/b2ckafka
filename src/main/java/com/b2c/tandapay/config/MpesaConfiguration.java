package com.b2c.tandapay.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MpesaConfiguration {

    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String oauthEndpoint;
    private String registerUrlEndpoint;
    private String shortCode;
    private String confirmationURL;
    private String validationURL;
    private String responseType;
    private String b2cTransactionEndpoint;
    private String b2cResultUrl;
    private String b2cQueueTimeoutUrl;
    private String b2cInitiatorName;
    private String b2cInitiatorPassword;
    private String transactionResultUrl;

    @Override
    public String toString() {
        return String.format("{consumerKey='%s', consumerSecret='%s', grantType='%s', oauthEndpoint='%s'}",
                consumerKey, consumerSecret, grantType, oauthEndpoint);
    }

}
