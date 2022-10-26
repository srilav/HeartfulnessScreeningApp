package com.heartfulness.platform.grpc.seeker.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heartfulness.platform.grpc.seeker.service.Answers;
import com.heartfulness.platform.grpc.seeker.service.QuestionsAndAnswers;

import java.util.List;
import java.util.stream.Collectors;
@JsonIgnoreProperties
public class AnswersEntity {
    String language;
    @JsonProperty("questionAndAnswer")
    List<QuestionsAndAnswersEntity> questionsAndAnswers;
    String finalComments;

    public AnswersEntity() {
    }

    public AnswersEntity(String language, List<QuestionsAndAnswersEntity> questionsAndAnswers, String finalComments) {
        this.language = language;
        this.questionsAndAnswers = questionsAndAnswers;
        this.finalComments = finalComments;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<QuestionsAndAnswersEntity> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public void setQuestionsAndAnswers(List<QuestionsAndAnswersEntity> questionsAndAnswers) {
        this.questionsAndAnswers = questionsAndAnswers;
    }

    public String getFinalComments() {
        return finalComments;
    }

    public void setFinalComments(String finalComments) {
        this.finalComments = finalComments;
    }


    public Answers toProto(){
        List<QuestionsAndAnswers> questionsAndAnswers = this.questionsAndAnswers.stream().map(QuestionsAndAnswersEntity::toProto).collect(Collectors.toList());
        return  Answers.newBuilder()
                .setLanguage(getLanguage())
                .addAllQuestionsAndAnswers(questionsAndAnswers)
                .setFinalComments(getFinalComments()).build();
    }

    public static AnswersEntity fromProto(Answers answersRequest){
        List<QuestionsAndAnswersEntity> questionsAndAnswers = answersRequest.getQuestionsAndAnswersList().stream().map(QuestionsAndAnswersEntity::fromProto).collect(Collectors.toList());
        AnswersEntity answersEntity = new AnswersEntity();
        answersEntity.setQuestionsAndAnswers( questionsAndAnswers);
        answersEntity.setLanguage(answersRequest.getLanguage());
        answersEntity.setFinalComments(answersRequest.getFinalComments());
        return answersEntity;
    }
}
