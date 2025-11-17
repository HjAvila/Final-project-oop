package com.example.finaoop.models;

import java.io.Serializable;

public class DisposalRequest implements Serializable {

    private static final long serialVersionUID = 2L;

    private String id;
    private String requesterEmail;
    private String location;
    private String type;
    private String description;
    private String date;
    private String status;

    private String imagePath;

    public DisposalRequest(String id, String requesterEmail, String location, String type, String description, String date, String status, String imagePath) {
        this.id = id;
        this.requesterEmail = requesterEmail;
        this.location = location;
        this.type = type;
        this.description = description;
        this.date = date;
        this.status = status;
        this.imagePath = imagePath;
    }

    public String getId() { return id; }
    public String getRequesterEmail() { return requesterEmail; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public String getImagePath() { return imagePath; }

    public void setStatus(String status) { this.status = status; }
}