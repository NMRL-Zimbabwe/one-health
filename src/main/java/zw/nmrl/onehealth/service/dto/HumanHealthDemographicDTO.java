package zw.nmrl.onehealth.service.dto;

import java.util.ArrayList;
import java.util.List;

public class HumanHealthDemographicDTO {

    private String sender;
    private String senderAddress;
    private String farmName;
    private String farmAddress;
    private List<String> senderPhone = new ArrayList<String>();
    private List<String> farmerPhone = new ArrayList<String>();
    private String senderEmail;
    private String labNumber;
    private String sector;
    private String species;
    private String breed;
    private String sex;
    private String age;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public List<String> getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(List<String> senderPhone) {
        this.senderPhone = senderPhone;
    }

    public List<String> getFarmerPhone() {
        return farmerPhone;
    }

    public void setFarmerPhone(List<String> farmerPhone) {
        this.farmerPhone = farmerPhone;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(String labNumber) {
        this.labNumber = labNumber;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
