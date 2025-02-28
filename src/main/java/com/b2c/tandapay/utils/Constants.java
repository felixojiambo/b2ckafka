package com.b2c.tandapay.utils;
import okhttp3.MediaType;

public class Constants {
    public static final String BASIC_AUTH_STRING = "Basic";
    public static final String AUTHORIZATION_HEADER_STRING = "authorization";
    public static final String CACHE_CONTROL_HEADER = "cache-control";
    public static final String CACHE_CONTROL_HEADER_VALUE = "no-cache";
    public static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static final String TRANSACTION_STATUS_QUERY_COMMAND = "TransactionStatusQuery";
    public static final String TRANSACTION_STATUS_VALUE = "Transaction Status";

    public static final String MSISDN_IDENTIFIER = "1";
    public static final String TILL_NUMBER_IDENTIFIER = "2";
    public static final String SHORT_CODE_IDENTIFIER = "4";
}