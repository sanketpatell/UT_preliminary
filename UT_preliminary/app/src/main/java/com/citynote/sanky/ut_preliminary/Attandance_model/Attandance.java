package com.citynote.sanky.ut_preliminary.Attandance_model;


/**
 * Created by Jigar on 19/08/2015.
 */
public class Attandance {
    // private variables
    Integer _emp_id;
    String _date ;
    String _status;


    // Empty constructor
    public Attandance() {

    }

    // constructor
    public Attandance(Integer emp_id, String date, String status) {

        this._emp_id = emp_id;
        this._date = date;
        this._status = status;
    }

    // getting name
    public Integer getEmp_id() {
        return this._emp_id;
    }

    // setting name
    public void setEmp_id(Integer emp_id) {
        this._emp_id = emp_id;
    }

    // getting fileName
    public String getDate() {
        return this._date;
    }

    // setting fileName
    public void setDate(String date) {
        this._date = date;
    }

    // getting counter
    public String getStatus() {
        return this._status;
    }

    // setting counter
    public String setStatus (String status) {
        return this._status = status;
    }

}
