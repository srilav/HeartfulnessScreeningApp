package com.heartfulness.platform.grpc.seeker.data;
import com.heartfulness.platform.grpc.seeker.service.Seeker;
import com.heartfulness.platform.grpc.seeker.service.Application;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class SeekerEntity {
    private Long id;
    private String seekerName;
    private String location;
    @JsonProperty("employees")
    private List<ApplicationEntity> applicationEntities;

    public SeekerEntity() {
    }

    public SeekerEntity(Long id, String seekerName, String location, List<ApplicationEntity> applications) {
        this.id = id;
        this.seekerName = seekerName;
        this.location = location;
        this.applicationEntities = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ApplicationEntity> getApplicationEntities() {
        return applicationEntities;
    }

    public void setApplicationEntities(List<ApplicationEntity> applicationEntities) {
        this.applicationEntities = applicationEntities;
    }

    public Seeker toProto(){
        List<Application> application = applicationEntities.stream().map(ApplicationEntity::toProto).collect(Collectors.toList());
        return  Seeker.newBuilder().setId(getId())
                .setSeekerName(getSeekerName())
                .setLocation(getLocation())
                .addAllApplications(application)
                .build();
    }

    public static SeekerEntity fromProto(Seeker seekerRequest){
        SeekerEntity seekerEntity = new SeekerEntity();
        seekerEntity.setSeekerName(seekerRequest.getSeekerName());
        seekerEntity.setId(seekerRequest.getId());
        seekerEntity.setLocation(seekerRequest.getLocation());
        List<ApplicationEntity> applicationEntities = seekerRequest.getApplicationsList().stream().map(ApplicationEntity::fromProto).collect(Collectors.toList());
        seekerEntity.setApplicationEntities( applicationEntities);
        return seekerEntity;
    }
}
