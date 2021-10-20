package com.fleckinger.codesharingplatform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CodeEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Lob
    @Column(length = 8192)
    private String code;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy/dd/MM HH:mm:ss")
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

    public long getId() {
        return id;
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
