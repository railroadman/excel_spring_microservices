package com.nayax.intern.microservices.receiver.dto;

public class ItemDto {
    private Long id;
    private String code;
    private String description;
   private int isDeleted ;
   private Long customerId;

    public ItemDto() {
    }

    public ItemDto(Long id, String code, String description, int isDeleted,Long customerId) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isDeleted = isDeleted;
        this.customerId = customerId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
