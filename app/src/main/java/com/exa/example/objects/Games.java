package com.example.legato.objects;

public class Games {
    private String name;
    private String sourceSite;
    private String platform;
    private String releaseDate;
    private String developer;

    // Default constructor
    public Games() {
        // Default constructor with no parameters
    }

    // Parameterized constructor
    public Games(String name, String sourceSite, String platform, String releaseDate, String developer) {
        this.name = name;
        this.sourceSite = sourceSite;
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.developer = developer;
    }

    // Getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for sourceSite
    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    // Getter and setter methods for platform
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    // Getter and setter methods for releaseDate
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Getter and setter methods for developer
    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
