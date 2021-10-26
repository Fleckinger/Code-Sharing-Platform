package com.fleckinger.codesharingplatform.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CodeSnippet {

    @Id
    @JsonIgnore
    private final UUID id = UUID.randomUUID();

    @Lob
    @Column(length = 8192)
    private String code;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy/dd/MM HH:mm:ss")
    private LocalDateTime uploadDate;

    @JsonIgnore
    private LocalDateTime accessExpireDate;

    private int time;

    private int views;

    @JsonIgnore
    private boolean timeRestrictions;

    @JsonIgnore
    private boolean viewsRestrictions;


    public CodeSnippet() {
        uploadDate = LocalDateTime.now();
    }

    public UUID getId() {
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

    public LocalDateTime getAccessExpireDate() {
        return accessExpireDate;
    }

    public void setAccessExpireDate(LocalDateTime accessExpireDate) {
        this.accessExpireDate = accessExpireDate;
    }

    public int getTime() {
        return time;
    }

    public int getViews() {
        return views;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean hasTimeRestrictions() {
        return timeRestrictions;
    }

    public void setTimeRestrictions(boolean timeRestrictions) {
        this.timeRestrictions = timeRestrictions;
    }

    public boolean hasViewsRestrictions() {
        return viewsRestrictions;
    }

    public void setViewsRestrictions(boolean viewsRestrictions) {
        this.viewsRestrictions = viewsRestrictions;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", uploadDate=" + uploadDate +
                ", time=" + time +
                ", views=" + views +
                ", timeRestrictions=" + timeRestrictions +
                ", viewsRestrictions=" + viewsRestrictions +
                '}';
    }
}
