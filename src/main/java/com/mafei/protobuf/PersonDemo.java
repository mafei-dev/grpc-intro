package com.mafei.protobuf;

import com.mafei.model.Person;

/**
 * @Author mafei
 * @Created 6/24/2021 12:29 PM
 */
public class PersonDemo {
    public static void main(String[] args) {
        Person mafei = Person.newBuilder().setName("mafei").setAge(25).build();
        System.out.println("mafei = " + mafei);
    }
}