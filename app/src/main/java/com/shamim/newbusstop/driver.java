package com.shamim.newbusstop;

public class driver {


//    public Map<String, Boolean> getStars() {
//        return stars;
//    }
//
//    public void setStars(Map<String, Boolean> stars) {
//        this.stars = stars;
//    }
    public driver(  String Fullname,  String Email, String Phone, String Password,String License,String NID,String Company) {
        this.Fullname = Fullname;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
        this.License = License;
        this.NID = NID;
        this.Company=Company;
    }

    public String getFullname() {

        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String License) {
        this.License = License;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getNID() {
        return NID;
    }

    public void setNID(String nid) {
        NID =nid;
    }


    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        NID =company;
    }





    private String Fullname, Phone, Email, License, Password,NID,Company;
    //public Map<String, Boolean> stars = new HashMap<>();

    public driver() {
    }







    /*@Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Fullname", Fullname);
        result.put("Email", Email);
        result.put("Phone", Phone);
        result.put("Password", Password);
        result.put("gender", gender);
        return result;
    }*/
}