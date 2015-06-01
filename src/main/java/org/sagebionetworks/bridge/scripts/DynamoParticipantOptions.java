package org.sagebionetworks.bridge.scripts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

@DynamoDBTable(tableName = "ParticipantOptions")
public class DynamoParticipantOptions {
    
    private String healthCode; // hash
    private String studyKey; // range
    private Map<String,String> options = Maps.newHashMap();
    
    @DynamoDBAttribute
    public String getStudyKey() {
        return studyKey;
    }
    public void setStudyKey(String studyKey) {
        this.studyKey = studyKey;
    }
    @DynamoDBHashKey(attributeName="healthDataCode")
    public String getHealthCode() {
        return healthCode;
    }
    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }
    @DynamoDBIgnore
    public Map<String,String> getOptions() {
        return options;
    }
    public void setOptions(Map<String,String> options) {
        this.options = options;
    }
    @DynamoDBAttribute
    public String getData() {
        try {
            return new ObjectMapper().writeValueAsString(options);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void setData(String data) {
        try {
            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
            options = new ObjectMapper().readValue(data, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
