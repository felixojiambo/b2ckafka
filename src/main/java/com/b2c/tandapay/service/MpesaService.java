package com.b2c.tandapay.service;

import com.b2c.tandapay.config.MpesaConfiguration;
import com.b2c.tandapay.dto.AccessTokenResponse;
import com.b2c.tandapay.dto.B2CResponse;
import com.b2c.tandapay.model.B2CRequest;
import com.b2c.tandapay.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Service
public class MpesaService {

    private static final Logger logger = LoggerFactory.getLogger(MpesaService.class);

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final MpesaConfiguration mpesaConfiguration;

    @Autowired
    public MpesaService(OkHttpClient okHttpClient,
                        ObjectMapper objectMapper,
                        MpesaConfiguration mpesaConfiguration) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
        this.mpesaConfiguration = mpesaConfiguration;
    }

    public AccessTokenResponse getAccessToken() {
        String url = mpesaConfiguration.getOauthEndpoint();
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("OAuth endpoint URL is not configured.");
        }

        String credentials = String.format("%s:%s", mpesaConfiguration.getConsumerKey(), mpesaConfiguration.getConsumerSecret());
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", url, mpesaConfiguration.getGrantType()))
                .get()
                .addHeader("Authorization", String.format("Basic %s", encodedCredentials))
                .addHeader("Cache-Control", "no-cache")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
                return objectMapper.readValue(responseBody, AccessTokenResponse.class);
            } else {
                logger.error("Failed to get access token. Response code: {}, message: {}", response.code(), response.message());
                return AccessTokenResponse.builder()
                        .errorCode(String.valueOf(response.code()))
                        .errorMessage(response.message())
                        .build();
            }
        } catch (IOException e) {
            logger.error("Failed to get access token", e);
            return AccessTokenResponse.builder()
                    .errorMessage("Failed to get access token.")
                    .build();
        }
    }

    public B2CResponse performB2CTransaction(B2CRequest b2CRequest) throws IOException {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        if (accessTokenResponse.getErrorCode() != null) {
            throw new RuntimeException("Failed to obtain access token: " + accessTokenResponse.getErrorMessage());
        }

        b2CRequest.setSecurityCredential(HelperUtility.getSecurityCredentials(mpesaConfiguration.getB2cInitiatorPassword()));
        b2CRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        b2CRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeoutUrl());
        b2CRequest.setInitiatorName(mpesaConfiguration.getB2cInitiatorName());
        b2CRequest.setPartyA(mpesaConfiguration.getShortCode());

        String jsonRequest = HelperUtility.toJson(b2CRequest);
        logger.debug("B2C Request JSON: {}", jsonRequest);

        RequestBody body = RequestBody.create(
                Objects.requireNonNull(jsonRequest), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getB2cTransactionEndpoint())
                .post(body)
                .addHeader("Authorization", String.format("Bearer %s", accessTokenResponse.getAccessToken()))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
                return objectMapper.readValue(responseBody, B2CResponse.class);
            } else {
                logger.error("Failed to perform B2C transaction. Response code: {}, message: {}, response body: {}", response.code(), response.message(), responseBody);
                throw new RuntimeException("Failed to perform B2C transaction. Response code: " + response.code());
            }
        } catch (IOException e) {
            logger.error("Failed to perform B2C transaction", e);
            throw new RuntimeException("Failed to perform B2C transaction", e);
        }
    }
}
