package com.nayax.intern.microservices.receiver.utils;

import com.nayax.intern.microservices.receiver.enums.ContentTypeEnum;
import com.nayax.intern.microservices.receiver.enums.DataTypeEnum;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;

import java.util.List;


public class EventFilter {
    private Integer isDeleted;
    private StateTypeEnum state;
    private List<Long> ids;

    public EventFilter() {
    }

    public EventFilter(Integer isDeleted, StateTypeEnum state) {
        this.isDeleted = isDeleted;
        this.state = state;

    }

    public EventFilter(Integer isDeleted, StateTypeEnum state,List<Long> ids) {
        this.isDeleted = isDeleted;
        this.state = state;
        this.ids = ids;

    }

    public StateTypeEnum getState() {
        return state;
    }

    public void setState(StateTypeEnum state) {
        this.state = state;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
