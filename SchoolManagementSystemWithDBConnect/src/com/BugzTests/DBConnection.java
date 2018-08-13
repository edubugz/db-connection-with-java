package com.BugzTests;

import java.sql.*;

public class DBConnection {

    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/school_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //establish connection
    public static Connection dbConnection = getDBConnection();


    //pass the id read above to method editStudentAndStaff for deleting
    //the method editStudentAndStaff takes 3 parameters... an object and two strings
    //it uses the 3 parameters to execute different 3 procedures separately and thus the method is called with
    //the appropriate parameter as to what procedure you want to execute.

    public void editStudentsAndStaff(Object j, String determinantString, String toBeDeleted) throws SQLException {
        dbConnection.setAutoCommit(false);

        // check the passed object if its of type student
        if (j instanceof Student)
        {

            // prepared statement for student and guardian
            String insertStudentTableSQL = "insert into students values(?,?,?,?,?)";

            String insertGuardianTableSQL = "insert into guardian values(?,?,?,?)";


            PreparedStatement updateStudents = dbConnection.prepareStatement(insertStudentTableSQL);
            PreparedStatement updateGuardian = dbConnection.prepareStatement(insertGuardianTableSQL);

            //set the values in the prepared statements to the values extracted from the object passed
            updateStudents.setString(1, ((Student) j).getIdNo());
            updateStudents.setString(2, ((Student) j).getName());
            updateStudents.setString(3, ((Student) j).getPhoneNumber());
            updateStudents.setString(4, ((Student) j).getClassForm());
            updateStudents.setString(5, ((Student) j).guardian.toString());


            //set guardian values
            updateGuardian.setString(1, ((Student) j).guardian.getIdNo());
            updateGuardian.setString(2, ((Student) j).guardian.getName());
            updateGuardian.setString(3, ((Student) j).getIdNo());
            updateGuardian.setString(4, ((Student) j).guardian.getPhoneNumber());


            //commit the changes if both execute update statements goes through
               if( updateStudents.executeUpdate()>0 && updateGuardian.executeUpdate() > 0)
               {
                   dbConnection.commit();
                   System.out.println("successful in registering the student");
               }
               else
               {
                   //if one fails, roll back
                   dbConnection.rollback();
                   System.out.println("failed to register the student, check the db connection");
               }


        }


        // // check the passed object if its of type staff
        if (j instanceof Staff)
        {

            // prepared statement for student and guardian
            String updateString = "insert into staff values(?,?,?,?)";
            PreparedStatement updateStaff = dbConnection.prepareStatement(updateString);

            //set the values in the prepared statements to the values extracted from the object passed
            updateStaff.setString(1, ((Staff) j).getIdNo());
            updateStaff.setString(2, ((Staff) j).getName());
            updateStaff.setString(3, ((Staff) j).getPhoneNumber());
            updateStaff.setString(4, ((Staff) j).getRole());
           // updateStaff.executeUpdate();

            if( updateStaff.executeUpdate()>0 )
            {
                dbConnection.commit();
                System.out.println("successful in registering the staff member detils::");
                returnOneItem("staff",((Staff) j).getIdNo());
            }
            else
            {
                //if it fails, roll back
                dbConnection.rollback();
                System.out.println("failed to delete the selected  staff, check the db connection");
            }


        }

            //edit staffAndStudent method can receive a string "determinant"
            // if the string equals "deleteStudent" the delete student procedure is triggered
        if (determinantString.equals("deleteStudent"))
        {

            //prepared statement to delete
            String studentToBeDeleted = "DELETE FROM STUDENTS WHERE ID =?";
            PreparedStatement deleteAStudent = dbConnection.prepareStatement(studentToBeDeleted);

            //set the ID in the prepared statement to the passed value toBeDeleted
            // this value is also received by the EditStaffAbdStudent from the deleteStaff() method in class Staff
            deleteAStudent.setString(1, toBeDeleted);

            if( deleteAStudent.executeUpdate()>0 )
            {
                dbConnection.commit();
                System.out.println("successful in deleting the selected student");
                readFromDB("student");
            }
            else
            {
                //if it fails, roll back
                dbConnection.rollback();
                System.out.println("failed to delete. check the db connection or the id entered" );

            }


        }


        if (determinantString.equals("deleteStaff"))
        {
            String staffToBeDeleted = "DELETE FROM STAFF WHERE ID =?";
            PreparedStatement deleteAStaff = dbConnection.prepareStatement(staffToBeDeleted);
            deleteAStaff.setString(1, toBeDeleted);

            if( deleteAStaff.executeUpdate()>0 )
            {
                dbConnection.commit();
                System.out.println("successful in deleting the selected staff");
                readFromDB("staff");
            }
            else
            {
                //if one fails, roll back
                dbConnection.rollback();
                System.out.println("failed to delete. check the db connection or the id entered" );

            }


        }

    }

    // this method is triggered when a user wants to view all the students or staff in the db
    //the passed string parameter decides which procedure to execute

    public int readFromDB(String passedString) throws SQLException
    {
        dbConnection.setAutoCommit(false);

        // if the passed string value is "student" select * from students procedure is triggered
        if (passedString.equals("student"))
        {
            String readStudentFromDBSQL = "SELECT * FROM STUDENTS";
            Statement queryToExecute = dbConnection.createStatement();
            ResultSet studentsData = queryToExecute.executeQuery(readStudentFromDBSQL);

            // if the result set has any data assign the data to strings and print it
            if (studentsData.next())
            {

                //the statement used as the condition in the if statement --> studentsData.next()
                // moves the cursor to the next result set.
                //students.beforeFirst returns the cursor to the start of the result set.

                studentsData.beforeFirst();
                while (studentsData.next())
                {

                    String id = studentsData.getString("id");
                    String name = studentsData.getString("name");
                    String phone = studentsData.getString("phone_number");
                    String classLevel = studentsData.getString("class_level");
                    String guardian = studentsData.getString("guardian");

                    System.out.println("\n" + "student admission: " + id + "\nstudent name: " + name + "\nstudent phone: " +
                            phone + "\nclass: " + classLevel + "\nGuardian details: " + guardian + "\n##############");

                }

                // this return value is used in the main class by method executeTheChoiceMade()
                // case 5,7
                return 1;
            }

            else
                {
               // System.out.println("no registered student in database");

                // this return value is used in the main class by method executeTheChoiceMade()
                // case 5,7
                return 0;
            }

        }

        // if the passed string value is "student" select * from students procedure is triggered
        if (passedString.equals("staff"))
        {
            String readStaffFromDBSQL = "SELECT * FROM STAFF";
            Statement queryToExecute = dbConnection.createStatement();
            ResultSet staffData = queryToExecute.executeQuery(readStaffFromDBSQL);

            // if the result set has any data assign the data to strings and print it
            if (staffData.next())
            {
                staffData.beforeFirst();
                while (staffData.next())
                {
                    String id = staffData.getString("id");
                    String name = staffData.getString("name");
                    String phone = staffData.getString("phone_number");
                    String role = staffData.getString("role");

                    System.out.println("\nStaff ID: " + id + "\nStaff name: " + name + "\nStaff phone number: " +
                            phone + "\ndesignation: " + role + "\n###############");
                }

                // this return value is used in the main class by method executeTheChoiceMade()
                // case 8,6
                return 1;

            }
            else
                {
              //  System.out.println("no registered staff in database");

                // this return value is used in the main class by method executeTheChoiceMade()
                // case 8,6
                return 0;
            }

        }

        return 0;
    }


    // this method edits the student or staff table according to the values passed to the prepared statements
    public void editTheDB(String tableToUpdate, String column, String valueToUpdate, String checker) throws SQLException
    {
        dbConnection.setAutoCommit(false);
        String studentToBeEdited = String.format("UPDATE %s SET %s = \"%s\" where id = \"%s\"", tableToUpdate, column, valueToUpdate, checker);

        PreparedStatement EditAStudent = dbConnection.prepareStatement(studentToBeEdited);

        EditAStudent.executeUpdate();
        dbConnection.commit();

    }


    // this method tries to get a connection to the database and returns a connection object which used by methods
    // in this class
    public static Connection getDBConnection() {

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }


    // this method is utilized in edit methods for student and staff
    // it returns the updated student or staff
    //the procedure depends on the string passed

    public void returnOneItem(String table, String checker) throws SQLException {

        //if the  passed string equals students
        // select the student row where id = checker <--- passed also to the method

        if (table.equals("students")) {
            String viewOneStudent = String.format("SELECT * from %s WHERE id = \"%s\" ", table, checker);
            Statement singleItemReturned = dbConnection.createStatement();
            ResultSet singleStudent = singleItemReturned.executeQuery(viewOneStudent);

            // prints the row for the user
            while (singleStudent.next()) {
                String id = singleStudent.getString("id");
                String name = singleStudent.getString("name");
                String phone = singleStudent.getString("phone_number");
                String classLevel = singleStudent.getString("class_level");
                String guardian = singleStudent.getString("guardian");

                System.out.println("\n##### new student record #####\n" + "student admission: " + id + "\nstudent name: " + name + "\nstudent phone: " +
                        phone + "\nclass: " + classLevel + "\nGuardian details: " + guardian + "\n##############");
            }


        }

        //if the  passed string equals staff
        // select the staff row where id = checker <--- passed also to the method

        if (table.equals("staff")) {
            String viewOneStaff = String.format("SELECT * from %s WHERE id = \"%s\" ", table, checker);
            Statement singleItemReturned = dbConnection.createStatement();
            ResultSet singleStaff = singleItemReturned.executeQuery(viewOneStaff);

            // prints the row for the user
            while (singleStaff.next()) {
                String id = singleStaff.getString("id");
                String name = singleStaff.getString("name");
                String phone = singleStaff.getString("phone_number");
                String role = singleStaff.getString("role");

                System.out.println("\n#### new staff record ####\n Staff ID: " + id + "\nStaff name: " + name + "\nStaff phone number: " +
                        phone + "\ndesignation: " + role + "\n###############");
            }


        }
    }

    // this method closes the connection to the db.
    //it is called by a shutdown hook thread from the main method
   static protected void closeConn()
    {
        try {
            dbConnection.close();
            } catch (SQLException e) {
            System.out.println("closing unsuccessful");
        }


    }


}



















