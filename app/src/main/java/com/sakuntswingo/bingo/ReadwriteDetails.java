package com.sakuntswingo.bingo;

public class ReadwriteDetails {
    private String username;
    private String email;
    private String profilePictureUrl;

    // Constructor
    public ReadwriteDetails(String username, String email, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
