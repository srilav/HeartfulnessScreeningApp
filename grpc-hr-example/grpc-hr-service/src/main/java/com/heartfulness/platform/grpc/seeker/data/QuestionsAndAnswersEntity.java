package com.heartfulness.platform.grpc.seeker.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heartfulness.platform.grpc.seeker.service.QuestionsAndAnswers;

@JsonIgnoreProperties
public class QuestionsAndAnswersEntity {
    private String categoryId;
    private String questionId;
    private String answerOptionId;
    private String additionalComment;

    public QuestionsAndAnswersEntity() {
    }

    public QuestionsAndAnswersEntity(String categoryId, String questionId, String answerOptionId, String additionalComment) {
        this.categoryId = categoryId;
        this.questionId = questionId;
        this.answerOptionId = answerOptionId;
        this.additionalComment = additionalComment;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(String answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public QuestionsAndAnswers toProto(){
        return  QuestionsAndAnswers.newBuilder().setCategoryId(getCategoryId())
                .setQuestionId(getCategoryId())
                .setAnswerOptionId(getAnswerOptionId())
                .setAdditionalComment(getAdditionalComment()).build();
    }

    public static QuestionsAndAnswersEntity fromProto(QuestionsAndAnswers questionsAndAnswersRequest){
        QuestionsAndAnswersEntity questionsAndAnswersEntity = new QuestionsAndAnswersEntity();
        questionsAndAnswersEntity.setCategoryId(questionsAndAnswersRequest.getCategoryId());
        questionsAndAnswersEntity.setCategoryId(questionsAndAnswersRequest.getQuestionId());
        questionsAndAnswersEntity.setQuestionId(questionsAndAnswersRequest.getAnswerOptionId());
        questionsAndAnswersEntity.setAnswerOptionId(questionsAndAnswersRequest.getAdditionalComment());
        return questionsAndAnswersEntity;
    }
}
