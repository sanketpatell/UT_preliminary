package com.citynote.sanky.ut_preliminary.Attandance_model;

import java.util.ArrayList;

public class Employee {
    private String id,title,thumbnailUrl,email,status,genre;

    private ArrayList<String> post;

    public Employee() {
    }
/*    public Employee(String id, String name, String thumbnailUrl, String email, String status,
                    ArrayList<String> post) {*/

    public Employee(String id, String name, String thumbnailUrl, String email, String status,String genre)
            {
        this.id = id;
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.email = email;
        this.status = status;
        this.genre = genre;
        //this.post = post;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public String getEmail() {    return email;   }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getStatus() {return status;   }
    public void setStatus(String status) {
        this.status = status;
    }


    public String getGenre() {return genre;   }
    public void setGenre(String genre) {
        this.genre = genre;
    }


/*

    public ArrayList<String> getPost() {
        return post;
    }
    public void setPost(ArrayList<String> post) {
        this.post = post;
    }
*/

}
