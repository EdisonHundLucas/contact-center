/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.meetime.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {

    @JsonProperty("eventId")
    private Long eventId;

    @JsonProperty("subscriptionId")
    private Long subscriptionId;

    @JsonProperty("portalId")
    private Long portalId;

    @JsonProperty("appId")
    private Long appId;

    @JsonProperty("occurredAt")
    private Long occurredAt;

    @JsonProperty("subscriptionType")
    private String subscriptionType;

    @JsonProperty("attemptNumber")
    private Integer attemptNumber;

    @JsonProperty("objectId")
    private Long objectId;

    @JsonProperty("changeFlag")
    private String changeFlag;

    @JsonProperty("changeSource")
    private String changeSource;

    @JsonProperty("sourceId")
    private String sourceId;

    public Event() {}

    public Event(Long eventId, Long subscriptionId, Long portalId, Long appId, Long occurredAt, 
                 String subscriptionType, Integer attemptNumber, Long objectId, String changeFlag, 
                 String changeSource, String sourceId) {
        this.eventId = eventId;
        this.subscriptionId = subscriptionId;
        this.portalId = portalId;
        this.appId = appId;
        this.occurredAt = occurredAt;
        this.subscriptionType = subscriptionType;
        this.attemptNumber = attemptNumber;
        this.objectId = objectId;
        this.changeFlag = changeFlag;
        this.changeSource = changeSource;
        this.sourceId = sourceId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getPortalId() {
        return portalId;
    }

    public void setPortalId(Long portalId) {
        this.portalId = portalId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Long occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getChangeSource() {
        return changeSource;
    }

    public void setChangeSource(String changeSource) {
        this.changeSource = changeSource;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", subscriptionId=" + subscriptionId +
                ", portalId=" + portalId +
                ", appId=" + appId +
                ", occurredAt=" + occurredAt +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", attemptNumber=" + attemptNumber +
                ", objectId=" + objectId +
                ", changeFlag='" + changeFlag + '\'' +
                ", changeSource='" + changeSource + '\'' +
                ", sourceId='" + sourceId + '\'' +
                '}';
    }
}
