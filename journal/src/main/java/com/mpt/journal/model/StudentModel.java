package com.mpt.journal.model;

import java.time.LocalDateTime;

public class StudentModel {
    private int Id;
    private String Name;
    private String LastName;
    private String FirstName;
    private String MiddleName;
    private String email;
    private String phone;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted = false;

    public StudentModel(int id, String name, String lastName, String firstName, String middleName) {
        Id = id;
        Name = name;
        LastName = lastName;
        FirstName = firstName;
        MiddleName = middleName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public StudentModel(int id, String name, String lastName, String firstName, String middleName, 
                       String email, String phone, int age) {
        Id = id;
        Name = name;
        LastName = lastName;
        FirstName = firstName;
        MiddleName = middleName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public StudentModel(int id, String name, String lastName, String firstName, String middleName, 
                       LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        Id = id;
        Name = name;
        LastName = lastName;
        FirstName = firstName;
        MiddleName = middleName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getFullName() {
        return Name + " " + LastName + " " + FirstName + " " + MiddleName;
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
