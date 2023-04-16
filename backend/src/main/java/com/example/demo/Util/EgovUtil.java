package com.example.demo.Util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;


public class EgovUtil {
    public static String getAccessToken() {
        String url = "http://hakaton-idp.gov4c.kz/auth/realms/con-web/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "username=test-operator&password=DjrsmA9RMXRl&client_id=cw-queue-service&grant_type=password";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String responseBody = response.getBody();
        String accessToken = JsonPath.read(responseBody, "$.access_token");
        return accessToken;
    }


    public static String getBMG(String iin, String accessToken) {

        String url = "http://hakaton.gov4c.kz/api/bmg/check/" + iin;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        return responseBody;
    }

    public static String getFL(String iin, String accessToken) {

        String url = "http://hakaton-fl.gov4c.kz/api/persons/" + iin;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String originalJson = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        String newJson = "";
        try {
            // read the original JSON into a JsonNode object
            JsonNode originalNode = mapper.readTree(originalJson);
            // create a new ObjectNode with only the desired fields
            ObjectNode newNode = mapper.createObjectNode();
            newNode.set("iin", originalNode.get("iin"));
            newNode.set("lastName", originalNode.get("lastName"));
            newNode.set("firstName", originalNode.get("firstName"));
            newNode.set("middleName", originalNode.get("middleName"));
            newNode.set("engFirstName", originalNode.get("engFirstName"));
            newNode.set("engSurname", originalNode.get("engSurname"));
            // convert the new ObjectNode to a JSON string
            newJson = newNode.toPrettyString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newJson;
    }


    public static String getDocumentStatus(String requestId, String iin) {
        String url = "http://89.218.80.61/vshep-api/con-sync-service?requestId=" + requestId + "&requestIIN=" + iin +"&token=eyJG6943LMReKj_kqdAVrAiPbpRloAfE1fqp0eVAJ-IChQcV-kv3gW-gBAzWztBEdFY";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
        return responseBody;

    }

    public static String getInfo(String requestID, String iin) {
        String token = getAccessToken();
        String BMGresult = getBMG(iin, token);
        String FLresult = getFL(iin, token);
        String DocumentStatusResult = getDocumentStatus(requestID, iin);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode finalResult = mapper.createObjectNode();
        finalResult.set("BMG", parseJson(BMGresult, mapper));
        finalResult.set("FL", parseJson(FLresult, mapper));
        finalResult.set("DocumentStatus", parseJson(DocumentStatusResult, mapper));

        try {
            String resultString = mapper.writeValueAsString(finalResult);
            System.out.println(resultString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return finalResult.toPrettyString();
    }

    public static String sendSMS(String iin, String smsText, String accessToken) {
        if (accessToken.equals("")){
            accessToken = getAccessToken();
        }
        String url = "http://hak-sms123.gov4c.kz/api/smsgateway/send";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        ObjectMapper mapper = new ObjectMapper();
        String phone = "";
        try{
            JsonNode bmgTree = mapper.readTree(getBMG(iin, accessToken));
            phone = bmgTree.get("phone").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("phone", phone);
        requestBody.put("smsText", smsText);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toPrettyString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();

    }


    public static JsonNode parseJson(String jsonString, ObjectMapper mapper) {
        try {
            return mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getServiceType(String requestId, String customerIIN) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode docs = parseJson(getDocumentStatus(requestId, customerIIN), mapper);
        return docs.get("data").get("serviceType").get("nameRu").asText();

    }
}
