package com.BugzTests;

import java.io.*;
import java.io.IOException;
import java.sql.SQLException;


public class Student extends Person implements Register,Serializable {

    //the two class variables unique to class student
    protected String classLevel;
    protected Guardian guardian;

    //initialize buffered reader to read user input in the next phase of prompting for student details
    BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

    //object of DBConnection to be used through out the class
    DBConnection globalDBConObject = new DBConnection();

    // getters and setters for the class student instance variables
    public String getClassForm() {
        return classLevel;
    }

    public void setClassLevel(String classForm) {

        this.classLevel = classForm;
    }


    public Guardian getGuardian() {  //custom datatype Guardian

        return guardian;
    }

    public void setGuardian(Guardian guardian) {

        this.guardian = guardian;
    }

    //this method provided by the Register interface
    // carries out the writing operation to the student file.
    // It has a FileWriter and BufferedWriter objects

    @Override
    public String toString() {
        return name +"-"+phoneNumber+"-"+IdNo +"-"+classLevel+"-"+guardian ;
    }

    //this method is overriden from the Register interface
    //it is called when the user wants to register a new student
    @Override
    public void register() throws IOException {

        //Instantiate a Guardian object to hold the guardian details of the object student
        Guardian studentGuardian = new Guardian();

        //prompt the user for the details of the student and set the instance variables to the values

        System.out.println("enter student name");
        this.setName(b.readLine());

        System.out.println("enter student ID");
        this.setIdNo(b.readLine());

        System.out.println("enter student phone number");
        this.setPhoneNumber(b.readLine());

        System.out.println("enter student classLevel");
        this.setClassLevel(b.readLine());

        // start of guardian details prompting and setting the instance variables

        System.out.println("enter guardian name");
        studentGuardian.setName(b.readLine());


        System.out.println("enter guardian id");
        studentGuardian.setIdNo(b.readLine());

        System.out.println("enter guardian Phone number");
        studentGuardian.setPhoneNumber(b.readLine());
        //relate the guardian details to student guardian details
        this.setGuardian(studentGuardian);

       try
        {
            //pass the Student object loaded with the data above to the editStudentAndStaff method in DBConnection
            globalDBConObject.editStudentsAndStaff(this,"","");
            System.out.println("successful in registering");

            //this is a method call to the returnOneItem method in the DBConnection class
            // it returns one row of the registered student
            globalDBConObject.returnOneItem("students",this.getIdNo());
        }
        catch (SQLException e)
        {
            e.getMessage();
            System.exit(0);
        }

   }


   // this method is called when a user want to delete a student record from the database

    //don't touch this method,it's working
   public void deleteStudent()
   {
       //a student is deleted using the entry id
       //this step asks the user to input the admission number of the student they wish to delete
       //from the list that was before the method was called in the Main method

       System.out.println("input the admission number of the student you want to delete as displayed above");

       try
       {
           //read the user input
           String selectedAdmission = b.readLine();

           //pass the id read above to method editStudentAndStaff for deleting
           //the method editStudentAndStaff takes 3 parameters... an object and two strings
           //it uses the 3 parameters to execute different 3 procedures separately and thus the method is called with
           //the appropriate parameter as to what procedure you want to execute.
           //here the object of type BufferReader "b" is passed just to satisfy the parameter requirements and
           //not for any processing.
           //the other two are the ones which will invoke the delete procedure
           //you might encounter a similar scenario in other classes

           globalDBConObject.editStudentsAndStaff(b,"deleteStudent",selectedAdmission);

       }

       catch (IOException e)
       {
           e.printStackTrace();
       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }




   }


   //this method is called when you want to edit student properties
    //the parameters are passed from the main class
    public  void editStudent (String selectedProperty, String theSelectedStudent) throws IOException
    {
        // the parameters are assigned to new variables
        //so that when executing the switch case, and a user enters a wrong choice
        // the same method editStduent can be recalled recursively with them as the parameters;
        String property = selectedProperty;
        String SelectedStudent = theSelectedStudent;

        String valueEdited ="value edited successfully";

        //buffered reader to read user input
        BufferedReader input= new BufferedReader(new InputStreamReader(System.in));
        try {

            // this switch statement checks the property selected for editing
            switch (property) {

                case "1":
                    //edit name. case 1
                    System.out.println("enter the modified name");
                    String modifiedName = input.readLine();
                    globalDBConObject.editTheDB("students","name",modifiedName,SelectedStudent);
                    globalDBConObject.returnOneItem("students",theSelectedStudent);

                    break;
                case "2":
                    //edit phone number case 2
                    System.out.println("enter modified phone number");
                    String modifiedPhone = input.readLine();

                    //call method edit db in DBConnection class which updates the student table

                    globalDBConObject.editTheDB("students","phone_number",modifiedPhone,SelectedStudent);

                    // show the edited student to the user by calling returnOneItem in the DBConnecvtion class
                    globalDBConObject.returnOneItem("students",theSelectedStudent);
                    break;
                case "3":
                    System.out.println("modified ID number");
                    String modifiedId = input.readLine();
                    globalDBConObject.editTheDB("students","id",modifiedId,SelectedStudent);
                    globalDBConObject.returnOneItem("students",modifiedId);
                    break;
                case "4":
                    System.out.println("enter the modified class level");
                    String modifiedClass = input.readLine();
                    globalDBConObject.editTheDB("students","class_level",modifiedClass,SelectedStudent);
                    globalDBConObject.returnOneItem("students",theSelectedStudent);

                    break;
                case "5":
                    // case to edit the guardian details

                    System.out.println("enter new guardian name");
                    String guardianName = input.readLine();
                    globalDBConObject.editTheDB("guardian","name",guardianName,SelectedStudent);

                    System.out.println("enter new guardian ID number");
                    String guardianID = input.readLine();
                    globalDBConObject.editTheDB("guardian","guardian_id",guardianID,SelectedStudent);

                    System.out.println("enter new phone number");
                    String guardianPhoneNumber = input.readLine();
                    globalDBConObject.editTheDB("guardian","phone_number",guardianPhoneNumber,SelectedStudent);

                case "p":
                    Main.main(null);
                    break;


                    //quits the system
                case "q":
                    System.out.println("press \"q\" to quit");
                    System.exit(0);

                default:
                    System.out.println("enter a valid choice from the menu");
                    Main.main(null);
            }
        }

        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}







