package com.shamim.newbusstop;

public class Admin {


    //    public Map<String, Boolean> getStars() {
//        return stars;
//    }
//
//    public void setStars(Map<String, Boolean> stars) {
//        this.stars = stars;
//    }
    public Admin(String Fullname, String Email, String Phone, String Password, String NID, String Company) {
        this.Fullname = Fullname;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
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
    public void setLicense(String license) {
        License = license;
    }

    public String getLicense() {
        return License;
    }




    private String Fullname;
    private String Phone;
    private String Email;
    private String License;
    private String Password;
    private String NID;
    private String Company;

    public Admin(String fullname, String phone, String email, String license, String password, String NID, String company) {
        this.Fullname = fullname;
        this.Email =email;
        this.Phone = phone;
        this.Password = password;
        this.NID = NID;
        this.License=license;
        this.Company=company;
    }


    //public Map<String, Boolean> stars = new HashMap<>();

    public Admin() {
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