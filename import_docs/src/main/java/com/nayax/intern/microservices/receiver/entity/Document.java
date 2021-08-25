package com.nayax.intern.microservices.receiver.entity;

import com.nayax.intern.microservices.receiver.enums.ContentTypeEnum;
import com.nayax.intern.microservices.receiver.enums.DataTypeEnum;

import java.util.Arrays;
import java.util.Objects;

//import javax.persistence.Entity;
public class Document {
public Long id;
public byte[] docValue;
public DataTypeEnum dataTypeEnum;
public ContentTypeEnum contentTypeEnum;
private Long customerId;

public Document(){}

 public Document(byte[] docValue, String dataType, String contentType,Long customerId) {
  this.docValue = docValue;
  this.dataTypeEnum = DataTypeEnum.valueOf(dataType);
  this.contentTypeEnum = ContentTypeEnum.valueOf(contentType);
  this.customerId = customerId;
 }

 public Document(Long id, byte[] docValue, String dataTypeEnum, String contentTypeEnum,Long customerId) {
  this.id = id;
  this.docValue = docValue;
  this.dataTypeEnum = DataTypeEnum.valueOf(dataTypeEnum);
  this.contentTypeEnum = ContentTypeEnum.valueOf(contentTypeEnum);
  this.customerId = customerId;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public byte[] getDocValue() {
  return docValue;
 }

 public void setDocValue(byte[] docValue) {
  this.docValue = docValue;
 }

 public int getDataType() {
  return dataTypeEnum.getNumVal();
 }

 public void setDataType(String dataType) {
  this.dataTypeEnum = DataTypeEnum.valueOf(dataType);
 }

 public int getContentType() {
  return contentTypeEnum.getNumVal();
 }

 public void setContentType(String contentType) {
  this.contentTypeEnum = ContentTypeEnum.valueOf(contentType);
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (!(o instanceof Document)) return false;
  Document document = (Document) o;
  return getId() == document.getId() && Arrays.equals(getDocValue(), document.getDocValue()) && dataTypeEnum == document.dataTypeEnum && contentTypeEnum == document.contentTypeEnum;
 }

 @Override
 public int hashCode() {
  int result = Objects.hash(getId(), dataTypeEnum, contentTypeEnum);
  result = 31 * result + Arrays.hashCode(getDocValue());
  return result;
 }

 public Long getCustomerId() {
  return customerId;
 }

 public void setCustomerId(Long customerId) {
  this.customerId = customerId;
 }

 @Override
 public String toString() {
  return "Document{" +
          "id=" + id +
          ", docValue=" + Arrays.toString(docValue) +
          ", dataType=" + dataTypeEnum.getNumVal() +
          ", contentType=" + contentTypeEnum.getNumVal() +
          '}';
 }
}
