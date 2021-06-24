package com.mafei.protobuf;

import com.mafei.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author mafei
 * @Created 6/24/2021 12:29 PM
 */
public class PersonDemo {
    public static void main(String[] args) throws IOException {
        Person mafei = Person.newBuilder().setName("mafei").setAge(25).build();
        System.out.println("mafei = " + mafei);

        //serialize the object
        Path path = Paths.get("mafei.person.ser");
        Files.write(path, mafei.toByteArray());

        //deserialize the object
        byte[] mafieBytes = Files.readAllBytes(path);
        Person mafeiFormFile = Person.parseFrom(mafieBytes);
        System.out.println("mafeiFormFile = " + mafeiFormFile);

        System.out.println("equality = " + (mafei == mafeiFormFile));
        System.out.println("equality by data = " + (mafei.equals(mafeiFormFile)));
    }
}
