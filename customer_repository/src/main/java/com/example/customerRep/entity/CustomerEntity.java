package com.example.customerRep.entity;

import java.util.Objects;

public class CustomerEntity {
    private Long id;
    private String code;
    private int isDeleted;
    private String firstName;
    private String lastName;
    private Long customerId;

    public CustomerEntity(){};

    public CustomerEntity(Long id, String code, int isDeleted, String firstName, String lastName,Long customerId) {
        this.id = id;
        this.code = code;
        this.isDeleted = isDeleted;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerEntity)) return false;
        CustomerEntity entity = (CustomerEntity) o;
        return getIsDeleted() == entity.getIsDeleted() && Objects.equals(getId(), entity.getId()) && Objects.equals(getCode(), entity.getCode()) && Objects.equals(getFirstName(), entity.getFirstName()) && Objects.equals(getLastName(), entity.getLastName());
    }



    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getIsDeleted(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", isDeleted=" + isDeleted +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
