package com.wf.wimt.CustomerActivityConsumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Book")
@JsonIgnoreProperties
public class CustomerActivity {
    public CustomerActivity(UUID id, String LOBName, String SRType, String SRSubType, String createdBy, String createdDate, String endDate, String requestedBy, String requestDescription, String resolutionDescription, String activitySource, String payload) {
        this.id = id;
        this.LOBName = LOBName;
        this.SRType = SRType;
        this.SRSubType = SRSubType;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        EndDate = endDate;
        RequestedBy = requestedBy;
        RequestDescription = requestDescription;
        ResolutionDescription = resolutionDescription;
        ActivitySource = activitySource;
        Payload = payload;
    }

    @Id
    @JsonProperty("Id")
    private UUID id;
    @JsonProperty("LOBName")
    private String LOBName;
    @JsonProperty("SRType")
    private String SRType;
    @JsonProperty("SRSubType")
    private String SRSubType;
    @JsonProperty("CreatedBy")
    private String CreatedBy;
    @JsonProperty("CreatedDate")
    private String CreatedDate;
    @JsonProperty("EndDate")
    private String EndDate;
    @JsonProperty("RequestedBy")
    private String RequestedBy;
    @JsonProperty("RequestDescription")
    private String RequestDescription;
    @JsonProperty("ResolutionDescription")
    private String ResolutionDescription;
    @JsonProperty("ActivitySource")
    private String ActivitySource;
    @JsonProperty("Payload")
    private String Payload;

    public CustomerActivity() {
    }

    public String getLOBName() {
        return LOBName;
    }

    public void setLOBName(String LOBName) {
        this.LOBName = LOBName;
    }

    public String getSRType() {
        return SRType;
    }

    public void setSRType(String SRType) {
        this.SRType = SRType;
    }

    public String getSRSubType() {
        return SRSubType;
    }

    public void setSRSubType(String SRSubType) {
        this.SRSubType = SRSubType;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getRequestDescription() {
        return RequestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        RequestDescription = requestDescription;
    }

    public String getResolutionDescription() {
        return ResolutionDescription;
    }

    public void setResolutionDescription(String resolutionDescription) {
        ResolutionDescription = resolutionDescription;
    }

    public String getActivitySource() {
        return ActivitySource;
    }

    public void setActivitySource(String activitySource) {
        ActivitySource = activitySource;
    }

    public String getPayload() {
        return Payload;
    }

    public void setPayload(String payload) {
        Payload = payload;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerActivity{" +
                "LOBName='" + LOBName + '\'' +
                ", SRType='" + SRType + '\'' +
                ", SRSubType='" + SRSubType + '\'' +
                ", CreatedBy='" + CreatedBy + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", RequestedBy='" + RequestedBy + '\'' +
                ", RequestDescription='" + RequestDescription + '\'' +
                ", ResolutionDescription='" + ResolutionDescription + '\'' +
                ", ActivitySource='" + ActivitySource + '\'' +
                ", Payload='" + Payload + '\'' +
                '}';
    }
}
