package com.nayax.intern.microservices.receiver.entity;

//import javax.persistence.*;

import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;

import java.util.Objects;


public class EventEntity {


    private long id;

    private StateTypeEnum state;

    {
        state = StateTypeEnum.NEW;
    }

    private long customerId;
    private boolean isDelete;
    private  long documentId;

    public EventEntity() {
    }

    public EventEntity(long customerId, boolean isDelete,long documentId ) {
        this.customerId = customerId;
        this.isDelete = isDelete;
        this.documentId =documentId;

    }

    public EventEntity(String state, long customerId, boolean isDelete,long documentId ) {


        this.state = StateTypeEnum.valueOf(state);
        this.customerId = customerId;
        this.isDelete = isDelete;
        this.documentId =documentId;

    }

    public EventEntity(long id) {
        this.id = id;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        isDelete = isDelete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getState() {
        return state.getNumVal();
    }

    public void setState(String state) {
        this.state = StateTypeEnum.valueOf(state);
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEntity)) return false;
        EventEntity that = (EventEntity) o;
        return getId() == that.getId() && getCustomerId() == that.getCustomerId() && isDelete() == that.isDelete() && getDocumentId() == that.getDocumentId() && getState() == that.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getState(), getCustomerId(), isDelete(), getDocumentId());
    }

    @Override
    public String toString() {
        return "EventEntity{" +
                "id=" + id +
                ", state=" + state +
                ", customerId=" + customerId +
                ", isDelete=" + isDelete +
                ", documentId=" + documentId +
                '}';
    }
}
