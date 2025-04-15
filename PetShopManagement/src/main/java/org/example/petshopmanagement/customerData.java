package org.example.petshopmanagement;

import java.util.Date;

public class customerData {
    private Integer customerId;
    private Integer petId;
    private String breed;
    private String sex;
    private Integer age;
    private Integer quantity;
    private Double price;
    private Date date;

    public customerData(Integer customerId,Integer petId,String breed,String sex,Integer age,Integer quantity,Double price,Date date) {
        this.customerId=customerId;
        this.petId=petId;
        this.breed=breed;
        this.sex=sex;
        this.age=age;
        this.quantity=quantity;
        this.price=price;
        this.date=date;
    }

    public Integer getCustomerId() {
        return customerId;
    }
    public Integer getPetId() {
        return petId;
    }
    public String getBreed() {
        return breed;
    }
    public String getSex(){
        return sex;
    }
    public Integer getAge() {
        return age;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public Date getDate() {
        return date;
    }
}
