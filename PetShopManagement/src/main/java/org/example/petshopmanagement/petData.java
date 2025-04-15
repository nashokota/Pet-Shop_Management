package org.example.petshopmanagement;

public class petData {
    private Integer petId;
    private String breed;
    private String sex;
    private Integer age;
    private Double price;

    public petData(Integer petId ,String breed ,String sex , Integer age,Double price) {
        this.petId = petId;
        this.breed = breed;
        this.sex = sex;
        this.age = age;
        this.price = price;
    }
    public Integer getPetId() {
        return petId;
    }
    public String getBreed() {
        return breed;
    }
    public Integer getAge() {
        return age;
    }
    public String getSex(){
        return sex;
    }
    public Double getPrice() {
        return price;
    }

}
