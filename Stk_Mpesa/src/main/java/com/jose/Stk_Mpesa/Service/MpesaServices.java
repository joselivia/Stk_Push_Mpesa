package com.jose.Stk_Mpesa.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class MpesaServices {
        private final String consumerKey = "cCAG5NFbFZmX49yqJfyajZU0XcwZ8hHAOC6JKmWpSbdqZdPH";
        private final String consumerSecret = "HuE3pPqSFLfVpB814aBnzrjo7rOFcAvP5p2FuMFQmewM261k4sFTxFgyK6BTuSg8";
        private final String shortCode = "174379";
        private final String passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
        private final String callbackUrl = "https://mydomain.com/path";

        public String generateToken() {
            String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(consumerKey, consumerSecret);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
            String token = (String) response.getBody().get("access_token");
            return token;
        }

        public String stkPush(String phoneNumber, String amount, String accountReference, String transactionDesc) {
           try{
            String token = generateToken();
            String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.set("Content-Type", "application/json");

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String password = Base64.getEncoder().encodeToString((shortCode + passKey + timestamp).getBytes());

            String requestBody = "{"
                    + "\"BusinessShortCode\":\"" + shortCode + "\","
                    + "\"Password\":\"" + password + "\","
                    + "\"Timestamp\":\"" + timestamp + "\","
                    + "\"TransactionType\":\"CustomerPayBillOnline\","
                    + "\"Amount\":\"" + amount + "\","
                    + "\"PartyA\":\"" + phoneNumber + "\","
                    + "\"PartyB\":\"" + shortCode + "\","
                    + "\"PhoneNumber\":\"" + phoneNumber + "\","
                    + "\"CallBackURL\":\"" + callbackUrl + "\","
                    + "\"AccountReference\":\"" + accountReference + "\","
                    + "\"TransactionDesc\":\"" + transactionDesc + "\""
                    + "}";

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response.getBody();
        }catch (Exception e){
               throw new RuntimeException("Error processing STK push:" + e.getMessage());
           }
    }}