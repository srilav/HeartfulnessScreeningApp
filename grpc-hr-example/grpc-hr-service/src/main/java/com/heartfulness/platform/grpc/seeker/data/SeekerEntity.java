package com.heartfulness.platform.grpc.seeker.data;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heartfulness.platform.grpc.seeker.service.Seeker;
import com.heartfulness.platform.grpc.seeker.service.Application;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;
@JsonIgnoreProperties
public class SeekerEntity {
    private Long id;
    private String seekerName;
    private String location;
    private String firstName;
    private String lastName;
    private String fullName;
    private String fatherName;
    private String motherName;
    private String dateOfBirth;
    private String phoneNo;
    private String email;
    private String gender;
    private String highestEducationQualification;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String pincode;
    private String heartfulnessCenter;
    private String dateOfFirstSitting;
    private String nameOfPreceptor;
    private String whomDoYouLiveWith;
    @JsonProperty("applications")
    private List<ApplicationEntity> applicationEntities;

    public SeekerEntity(Long id, String seekerName, String location, String firstName, String lastName, 
                        String fullName, String fatherName, String motherName, String dateOfBirth, String phoneNo, 
                        String email, String gender, String highestEducationQualification, String addressLine1, 
                        String addressLine2, String city, String district, String state, String pincode, 
                        String heartfulnessCenter, String dateOfFirstSitting, String nameOfPreceptor, 
                        String whomDoYouLiveWith, List<ApplicationEntity> applicationEntities) {
        this.id = id;
        this.seekerName = seekerName;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
        this.highestEducationQualification = highestEducationQualification;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.heartfulnessCenter = heartfulnessCenter;
        this.dateOfFirstSitting = dateOfFirstSitting;
        this.nameOfPreceptor = nameOfPreceptor;
        this.whomDoYouLiveWith = whomDoYouLiveWith;
        this.applicationEntities = applicationEntities;
    }

    public SeekerEntity() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHighestEducationQualification() {
        return highestEducationQualification;
    }

    public void setHighestEducationQualification(String highestEducationQualification) {
        this.highestEducationQualification = highestEducationQualification;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHeartfulnessCenter() {
        return heartfulnessCenter;
    }

    public void setHeartfulnessCenter(String heartfulnessCenter) {
        this.heartfulnessCenter = heartfulnessCenter;
    }

    public String getDateOfFirstSitting() {
        return dateOfFirstSitting;
    }

    public void setDateOfFirstSitting(String dateOfFirstSitting) {
        this.dateOfFirstSitting = dateOfFirstSitting;
    }

    public String getNameOfPreceptor() {
        return nameOfPreceptor;
    }

    public void setNameOfPreceptor(String nameOfPreceptor) {
        this.nameOfPreceptor = nameOfPreceptor;
    }

    public String getWhomDoYouLiveWith() {
        return whomDoYouLiveWith;
    }

    public void setWhomDoYouLiveWith(String whomDoYouLiveWith) {
        this.whomDoYouLiveWith = whomDoYouLiveWith;
    }

    public Seeker toProto(){
        List<Application> application = applicationEntities.stream().map(ApplicationEntity::toProto).collect(Collectors.toList());
        return  Seeker.newBuilder().setId(getId())
                .setSeekerName(getSeekerName())
                .setLocation(getLocation())
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setFullName(getFullName())
                .setFatherName(getFatherName())
                .setMotherName(getMotherName())
                .setDateOfBirth(getDateOfBirth())
        .setPhoneNo(getPhoneNo())
        .setEmail(getEmail())
        .setGender(getGender())
        .setHighestEducationQualification(getHighestEducationQualification())
        .setAddressLine1(getAddressLine1())
        .setAddressLine2(getAddressLine2())
        .setCity(getCity())
        .setDistrict(getDistrict())
        .setState(getState())
        .setPincode(getPincode())
        .setHeartfulnessCenter(getHeartfulnessCenter())
        .setDateOfFirstSitting(getDateOfFirstSitting())
        .setNameOfPreceptor(getNameOfPreceptor())
        .setWhomDoYouLiveWith(getWhomDoYouLiveWith())
        .addAllApplications(application).build();
    }

    public static SeekerEntity fromProto(Seeker seekerRequest){
        SeekerEntity seekerEntity = new SeekerEntity();
        seekerEntity.setSeekerName(seekerRequest.getSeekerName());
        seekerEntity.setId(seekerRequest.getId());
        seekerEntity.setLocation(seekerRequest.getLocation());
seekerEntity.setSeekerName(seekerRequest.getSeekerName());
seekerEntity.setLocation(seekerRequest.getLocation());
seekerEntity.setFirstName(seekerRequest.getFirstName());
seekerEntity.setLastName(seekerRequest.getLastName());
seekerEntity.setFullName(seekerRequest.getFullName());
seekerEntity.setFatherName(seekerRequest.getFatherName());
seekerEntity.setMotherName(seekerRequest.getMotherName());
seekerEntity.setDateOfBirth(seekerRequest.getDateOfBirth());
seekerEntity.setPhoneNo(seekerRequest.getPhoneNo());
seekerEntity.setEmail(seekerRequest.getEmail());
seekerEntity.setGender(seekerRequest.getGender());
seekerEntity.setHighestEducationQualification(seekerRequest.getHighestEducationQualification());
seekerEntity.setAddressLine1(seekerRequest.getAddressLine1());
seekerEntity.setAddressLine2(seekerRequest.getAddressLine2());
seekerEntity.setCity(seekerRequest.getCity());
seekerEntity.setDistrict(seekerRequest.getDistrict());
seekerEntity.setState(seekerRequest.getState());
seekerEntity.setPincode(seekerRequest.getPincode());
seekerEntity.setHeartfulnessCenter(seekerRequest.getHeartfulnessCenter());
seekerEntity.setDateOfFirstSitting(seekerRequest.getDateOfFirstSitting());
seekerEntity.setNameOfPreceptor(seekerRequest.getNameOfPreceptor());
seekerEntity.setWhomDoYouLiveWith(seekerRequest.getWhomDoYouLiveWith());
        List<ApplicationEntity> applicationEntities = seekerRequest.getApplicationsList().stream().map(ApplicationEntity::fromProto).collect(Collectors.toList());
        seekerEntity.setApplicationEntities( applicationEntities);
        return seekerEntity;
    }
}
