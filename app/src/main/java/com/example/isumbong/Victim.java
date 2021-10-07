package com.example.isumbong;

public class Victim {
    private String name;
    private String age;
    private String gender;

    public Victim(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public Victim(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Name:    "+name+"\n" +"Age:         "+age+"\n"+ "Gender:  "+gender+"\n\n";
    }
}
