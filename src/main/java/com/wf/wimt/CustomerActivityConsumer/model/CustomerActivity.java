package com.wf.wimt.CustomerActivityConsumer.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Book")
public class CustomerActivity {

    private String customerId;
    private String activitySource;
    private String payload;
    private String timestamp;
    private String activityType;
    private String orgName;

    public CustomerActivity() {
    }

    public CustomerActivity(String customerId, String activitySource, String payload, String timestamp, String activityType, String orgName) {
        this.customerId = customerId;
        this.activitySource = activitySource;
        this.payload = payload;
        this.timestamp = timestamp;
        this.activityType = activityType;
        this.orgName = orgName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "CustomerActivity{" +
                "customerId='" + customerId + '\'' +
                ", activitySource='" + activitySource + '\'' +
                ", payload='" + payload + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", activityType='" + activityType + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
