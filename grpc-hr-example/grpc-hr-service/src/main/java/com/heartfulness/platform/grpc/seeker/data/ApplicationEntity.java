package com.heartfulness.platform.grpc.seeker.data;

import com.heartfulness.platform.grpc.seeker.service.Application;


public class ApplicationEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public ApplicationEntity() {
    }

    public ApplicationEntity(Long id, String firstName, String lastName, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Application toProto(){

        return  Application.newBuilder().setId(getId())
                .setEmail(getEmail())
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setPhone(getPhone()).build();
    }

    public static ApplicationEntity fromProto(Application applicationRequest){
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setId(applicationRequest.getId());
        applicationEntity.setEmail(applicationRequest.getEmail());
        applicationEntity.setFirstName(applicationRequest.getFirstName());
        applicationEntity.setLastName(applicationRequest.getLastName());
        applicationEntity.setPhone(applicationRequest.getPhone());
        return applicationEntity;
    }
}
