package com.fleckinger.codesharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class CodeEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    @JsonProperty("date")
    private LocalDateTime uploadDate;


    public CodeEntity() {
        code = "Default code";
        uploadDate = LocalDateTime.now();
    }

    public CodeEntity(String code, LocalDateTime uploadDate) {
        this.code = code;
        this.uploadDate = uploadDate;
    }

    public CodeEntity(String code) {
        this.code = code;
        uploadDate = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

}
