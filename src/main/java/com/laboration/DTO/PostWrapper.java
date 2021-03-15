package com.laboration.DTO;

public class PostWrapper {
    private String content;
    private String username;

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    private String postDate;

    public PostWrapper() {

    }

    public PostWrapper(String content, String username, String postDate) {
        this.content = content;
        this.username = username;
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
