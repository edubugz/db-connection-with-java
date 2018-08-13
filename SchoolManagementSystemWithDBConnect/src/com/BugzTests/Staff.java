package com.BugzTests;

import java.io.*;
import java.sql.*;

public class Staff extends Person implements Register {
    protected String role; //custom datatype

    //initialize reader
    BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

    // this object of type DBConnection to be used by methods in this class
    DBConnection globalDBConObject = new DBConnection();

    @Override
    public String toString() {
        return  name+"-"+ IdNo +"-"+phoneNumber+"-" +role;
    }

    // setters and getters for the class variables
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    //this method carrries out the writing operation to the staff file.
    // It has a FileWriter and BufferedWriter objects
    public void register() throws IOException
    {

        try {

            // prompt for the staff details and set them as the instance variables values
            System.out.println("enter staff name:");
            this.setName(b.readLine());

            System.out.println("enter staff ID:");
            this.setIdNo(b.readLine());

            System.out.println("enter staff phone number:");
            this.setPhoneNumber(b.readLine());


            // start of role details
            System.out.println("enter designation name:");
            this.setRole(b.readLine());

            try
            {
                globalDBConObject.editStudentsAndStaff( this,"","");
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());

            }



        }
            // release held system resources
        catch (IOException e){
            System.out.println(e);
        }

        // on a successful registration


    }


    public void deleteStaff()
    {
        // the menu have already been displayed in the main class by the calling case
        System.out.println("input the id number of the staff you want to delete as displayed in the records above\n");


        try
        {


            //read the input
            String selectedId = b.readLine();

            //pass the id read above to method editStudentAndStaff for deleting
            //the method editStudentAndStaff takes 3 parameters... an object and two strings
            //it uses the 3 parameters to execute different 3 procedures separately and thus the method is called with
            //the appropriate parameter as to what procedure you want to execute.
            //here the object of type BufferReader "b" is passed just to satisfy the parameter requirements and
            //not for any processing.
            //the other two are the ones which will invoke the delete procedure
            //you might encounter a similar scenario in other classes
            globalDBConObject.editStudentsAndStaff(b,"deleteStaff",selectedId);

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


    //this method is called when you want to edit staff properties
    //the parameters are passed from the main class

    public  void editStaff (String selectedProperty, String theSelectedStaff) throws IOException
    {
        // the parameters are assigned to new variables
        //so that when executing the switch case, and a user enters a wrong choice
        // the same method editStduent can be recalled recursively with them as the parameters;

        String property = selectedProperty;
        String selectedStaff = theSelectedStaff;
        String valueEdited ="value edited successfully";

        //buffered reader to read user input
        BufferedReader input= new BufferedReader(new InputStreamReader(System.in));
        try {

            // this switch statement checks the property selected for editing
            switch (property) {
                case "1":
                    //case 1. edit ID
                    System.out.println("modified ID number");
                    String modifiedId = input.readLine();
                    //call method edit db in DBConnection class which updates the staff table
                    globalDBConObject.editTheDB("staff","id",modifiedId,selectedStaff);
                    // show the edited staff to the user by calling returnOneItem in the DBConnecvtion class
                    globalDBConObject.returnOneItem("staff",modifiedId);
                    break;

                case "2":
                    //edit name. case 2
                    System.out.println("enter the modified name");
                    String modifiedName = input.readLine();
                    globalDBConObject.editTheDB("staff","name",modifiedName,selectedStaff);
                    globalDBConObject.returnOneItem("staff",selectedStaff);
                    System.out.println(valueEdited);

                    break;
                case "3":
                    //edit phone number case 3
                    System.out.println("enter modified phone number");
                    String modifiedPhone = input.readLine();
                    globalDBConObject.editTheDB("staff","phone_number",modifiedPhone,selectedStaff);
                    globalDBConObject.returnOneItem("staff",selectedStaff);
                    break;

                case "4":
                    System.out.println("enter the modified role");
                    String modifiedRole = input.readLine();
                    globalDBConObject.editTheDB("staff","role",modifiedRole,selectedStaff);
                    globalDBConObject.returnOneItem("staff",selectedStaff);

                    break;

                case"p":
                    Main.main(null);

                case "q":
                    //quit the system
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

        finally {
          //  input.close();
        }
    }

}
