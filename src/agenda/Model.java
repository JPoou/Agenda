/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jpoou
 */
public class Model {
    
    protected String name;
    protected String surname;
    protected int sex;
    protected String address;
    protected String date;
    protected int age;
    protected String nationality;
    protected String email;
    protected String image;
    protected String phone;
    
    String[] split;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.split = date.split("/");        
        this.date = date;
        this.ageCalculation();
    }

    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    public void ageCalculation() {
  
	LocalDate date = LocalDate.now();
        
        int day = Integer.parseInt(split[0]);
        int moth = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        
        int edad = date.getYear() - year;
        if (date.getMonthValue() < moth) {
            edad = edad-1;
        }
        
        this.age = edad;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
