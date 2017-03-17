package com.citynote.sanky.ut_preliminary.Attandance_model;

import java.util.ArrayList;

public class Employee {
    private String emloyee_employee_id,emloyee_first_name,emloyee_last_name,emloyee_email,emloyee_mobile,emloyee_emer_contact,emloyee_dob,emloyee_blood_groop,emloyee_joining_date,emloyee_designation,emloyee_work_exp,emloyee_technical_skill,emloyee_Image,thumbnailUrl,email,status,genre;

    private ArrayList<String> post;

    public Employee() {
    }
/*    public Employee(String id, String name, String thumbnailUrl, String email, String status,
                    ArrayList<String> post) {*/

    public Employee(String emloyee_employee_id, String emloyee_first_name, String emloyee_last_name, String emloyee_email, String emloyee_mobile, String emloyee_emer_contact, String emloyee_dob, String emloyee_blood_groop, String emloyee_joining_date, String emloyee_designation, String emloyee_work_exp, String emloyee_technical_skill,String emloyee_Image)
            {
                this.emloyee_employee_id = emloyee_employee_id;
                this.emloyee_first_name = emloyee_first_name;
                this.emloyee_last_name = emloyee_last_name;
                this.emloyee_email = emloyee_email;
                this.emloyee_mobile = emloyee_mobile;
                this.emloyee_emer_contact = emloyee_emer_contact;
                this.emloyee_dob = emloyee_dob;
                this.emloyee_blood_groop = emloyee_blood_groop;
                this.emloyee_joining_date = emloyee_joining_date;
                this.emloyee_designation = emloyee_designation;
                this.emloyee_work_exp = emloyee_work_exp;
                this.emloyee_technical_skill = emloyee_technical_skill;
                this.emloyee_Image = emloyee_Image;





        this.thumbnailUrl = thumbnailUrl;
        this.email = email;
        this.status = status;
        this.genre = genre;
        //this.post = post;
    }

    public String getemloyee_employee_id() {
        return emloyee_employee_id;
    }
    public void setemloyee_employee_id(String emloyee_employee_id) {
        this.emloyee_employee_id = emloyee_employee_id;
    }

    public String getemloyee_first_name() {
        return emloyee_first_name;
    }
    public void setemloyee_first_name(String emloyee_first_name) {
        this.emloyee_first_name = emloyee_first_name;
    }

    public String getemloyee_last_name() {
        return emloyee_last_name;
    }
    public void setemloyee_last_name(String emloyee_last_name) {
        this.emloyee_last_name = emloyee_last_name;
    }

    public String getemloyee_email() {
        return emloyee_email;
    }
    public void setemloyee_email(String emloyee_email) {
        this.emloyee_email = emloyee_email;
    }

    public String getemloyee_mobile() {
        return emloyee_mobile;
    }
    public void setemloyee_mobile(String emloyee_mobile) {
        this.emloyee_mobile = emloyee_mobile;
    }

    public String getemloyee_emer_contact() {
        return emloyee_emer_contact;
    }
    public void setemloyee_emer_contact(String emloyee_emer_contact) {
        this.emloyee_emer_contact = emloyee_emer_contact;
    }

    public String getemloyee_dob() {
        return emloyee_dob;
    }
    public void setemloyee_dob(String emloyee_dob) {
        this.emloyee_dob = emloyee_dob;
    }

    public String getemloyee_blood_groop() {
        return emloyee_blood_groop;
    }
    public void setemloyee_blood_groop(String emloyee_blood_groop) {
        this.emloyee_blood_groop = emloyee_blood_groop;
    }

    public String getemloyee_joining_date() {
        return emloyee_joining_date;
    }
    public void setemloyee_joining_date(String emloyee_joining_date) {
        this.emloyee_joining_date = emloyee_joining_date;
    }

    public String getemloyee_designation() {
        return emloyee_designation;
    }
    public void setemloyee_designation(String emloyee_designation) {
        this.emloyee_designation = emloyee_designation;
    }

    public String getemloyee_work_exp() {
        return emloyee_work_exp;
    }
    public void setemloyee_work_exp(String emloyee_work_exp) {
        this.emloyee_work_exp = emloyee_work_exp;
    }

    public String getemloyee_technical_skill() {
        return emloyee_technical_skill;
    }
    public void setemloyee_technical_skill(String emloyee_technical_skill) {
        this.emloyee_technical_skill = emloyee_technical_skill;
    }

    public String getemloyee_Image() {
        return emloyee_Image;
    }
    public void setemloyee_Image(String emloyee_Image) {
        this.emloyee_Image = emloyee_Image;
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
