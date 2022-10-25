package com.heartfulness.platform.grpc.seeker.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heartfulness.platform.grpc.seeker.service.Application;
import com.heartfulness.platform.grpc.seeker.service.ApplicationStatus;
import com.heartfulness.platform.grpc.seeker.service.Answers;
import java.util.List;
import java.util.stream.Collectors;


public class ApplicationEntity {
    String applicationId;
    ApplicationStatus status;
    @JsonProperty("abyasiAnswers")
    AnswersEntity abyasiAnswers;
    @JsonProperty("functionaryAnswers")
    AnswersEntity functionaryAnswers;

    public ApplicationEntity() {
    }
    public ApplicationEntity(String applicationId, AnswersEntity abyasiAnswers, AnswersEntity functionaryAnswers) {
        this.applicationId = applicationId;
        this.abyasiAnswers = abyasiAnswers;
        this.functionaryAnswers = functionaryAnswers;
    }
    public String getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public AnswersEntity getAbyasiAnswers() {
        return abyasiAnswers;
    }
    public void setAbyasiAnswers(AnswersEntity abyasiAnswers) {
        this.abyasiAnswers = abyasiAnswers;
    }
    public AnswersEntity getFunctionaryAnswers() {
        return functionaryAnswers;
    }
    public void setFunctionaryAnswers(AnswersEntity functionaryAnswers) {
        this.functionaryAnswers = functionaryAnswers;
    }
    public Application toProto(){
        return  Application.newBuilder().setApplicationId(getApplicationId())
                .setAbyasiAnswers(getAbyasiAnswers().toProto())
                .setFunctionaryAnswers(getFunctionaryAnswers().toProto())
                .build();
    }

    public static ApplicationEntity fromProto(Application applicationRequest){
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAbyasiAnswers( AnswersEntity.fromProto(applicationRequest.getAbyasiAnswers()));
        applicationEntity.setFunctionaryAnswers( AnswersEntity.fromProto(applicationRequest.getFunctionaryAnswers()));
        applicationEntity.setApplicationId(applicationRequest.getApplicationId());
        return applicationEntity;
    }
}
