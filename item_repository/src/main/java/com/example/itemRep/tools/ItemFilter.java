package com.example.itemRep.tools;

import java.util.List;

public class ItemFilter {

    private List<Long> ids;
    private String code;
    private String description;
    private Integer isDeleted;
    private Long customerId;

    public ItemFilter(List<Long> ids, String code, String description, Integer isDeleted,Long customerId) {
        this.ids = ids;
        this.code = code;
        this.description = description;
        this.isDeleted = isDeleted;
        this.customerId = customerId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
