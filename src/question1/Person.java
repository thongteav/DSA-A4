/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question1;

import java.util.Objects;

/**
 *
 * @author Thong Teav
 */
public class Person {
    //variables-----------------------------------------------------------------
    private String name;
    private String phoneNum;
    private int age;
    //--------------------------------------------------------------------------
    
    //constructors--------------------------------------------------------------
    public Person(String name){
        this.name = name;
    }
    
    public Person(String name, String phoneNum, int age){
        this.name = name;
        this.phoneNum = phoneNum;
        this.age = age;
    }
    //--------------------------------------------------------------------------
    
    //accessors and mutators----------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    //--------------------------------------------------------------------------
    
    /**
     * Returns the hashcode by adding the value of the each character in the name
     * @return 
     */
    @Override
    public int hashCode() {
        int hashcode = 0;
        for(char c : this.name.toCharArray()){
            hashcode += c;
        }
        return hashcode;
    }

    /**
     * Returns a string containing the person's name, age and phone number if they have
     * @return 
     */
    @Override
    public String toString(){
        String output = "(" + this.name;
        output += this.age == 0 ? "" : " " + this.age;
        output += this.phoneNum == null ? "" : " " + this.phoneNum;
        output += ")";
        return output;
    }
    
    public static void main(String[] args){
        System.out.println("John has a hashcode of: " + new Person("John").hashCode());
        System.out.println("Kim has a hashcode of: " + new Person("Kim").hashCode());
        System.out.println("Katie has a hashcode of: " + new Person("Katie").hashCode());
    }
}
