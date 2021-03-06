package br.udesc.exemplo.nutriapp_.model;

public class User {

    private String id;
    private String username;
    private String height;
    private String weight;
    private String goalWeight;
    private String imageUrl;
    private String bio;

    public User(String id, String username, String height, String weight, String goalWeight, String imageUrl, String bio) {
        this.id = id;
        this.username = username;
        this.height = height;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public User(String id, String username, String imageUrl, String bio) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
