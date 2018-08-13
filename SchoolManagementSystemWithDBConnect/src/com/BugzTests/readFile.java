package com.BugzTests;

import java.io.*;
import java.util.ArrayList;

public class readFile {
    ArrayList<Student> returnedStudent = new ArrayList<>();
    ArrayList<Staff>    returnedStaff = new ArrayList<>();

    //this method reads the two files student.txt or Staff.txt according to the fileName passed and
    // recreates the necessary objects after which it pushes the objects to any of the two arrayList
    // returnedStudent or ReturnedStaff
    public void readFileContent(String fileName) throws IOException {

        //if the fileName passed is "students.txt"
        //use the file content to recreate Student objects
        if (fileName.equals("students.txt")) {

            FileReader fileToRead = new FileReader("students.txt");
            BufferedReader buffReader = new BufferedReader(fileToRead);

            String stringReadFromFile = buffReader.readLine();

            while (stringReadFromFile != null && !stringReadFromFile.isEmpty()) {

                String[] str = stringReadFromFile.split("-");


                Student j = new Student();
                Guardian recreatedGuardian = new Guardian();
                j.setName(str[0]);
                j.setPhoneNumber(str[1]);
                j.setIdNo(str[2]);
                j.setClassLevel(str[3]);
                recreatedGuardian.setName(str[4]);
                recreatedGuardian.setIdNo(str[5]);
                recreatedGuardian.setPhoneNumber(str[6]);
                j.setGuardian(recreatedGuardian);

                returnedStudent.add(j);
                stringReadFromFile = buffReader.readLine();

            }
            buffReader.close();

        }


        //if the fileName passed is "staff.txt"
        //use the file content to recreate Staff objects
        if (fileName.equals("staff.txt")) {

            FileReader fileToRead = new FileReader("staff.txt");
            BufferedReader buffReader = new BufferedReader(fileToRead);

            String stringReadFromFile = buffReader.readLine();

            while (stringReadFromFile != null && !stringReadFromFile.isEmpty()) {

                String[] str = stringReadFromFile.split("-");


                Staff j = new Staff();

                j.setName(str[0]);
                j.setIdNo(str[1]);
                j.setPhoneNumber(str[2]);
                j.setRole(str[3]);

                returnedStaff.add(j);
                stringReadFromFile = buffReader.readLine();

            }
            buffReader.close();

        }


    }

    //these two methods return the arraylist populated back to the main class
    //its determined by the calling object from main class
    public ArrayList<Staff> getReturnedStaff() {
        return returnedStaff;
    }

    public ArrayList<Student> getReturnedStudent() {
        return returnedStudent;
    }
}








