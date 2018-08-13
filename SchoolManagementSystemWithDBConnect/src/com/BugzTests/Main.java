package com.BugzTests;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;



public class Main extends Thread implements Serializable {

    @Override
    public void run()
    {

       DBConnection.closeConn();

    }

    //initialize buffereader and make it accesible through out the whole class
    private static BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        // this thread closes the db connection when the program fails
        Runtime.getRuntime().addShutdownHook(new Main());

        //calls the static method getDbconnection in DbConnection file to initialize a connection to the db that will
        //be used through out when the program will be alive
        DBConnection.getDBConnection();

        // welcome statement
        System.out.println("\n\t\t\t welcome to MU school management system");
        System.out.println("******************************************************************");

        try {
            // this method displays the main menu used in interacting with the program
            //and it takes a parameter of type BufferReader since it will ask for user input
            getUserInput(b);
        }
        //handle exceptions
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    private static void executeTheChoiceMade(String userInput) throws IOException {

        //an object of type DBconnection,Student and Staff instantiated
        //this object will be used to interact with the methods in the DBConnection class
        //that are used by this method

        DBConnection readDetails = new DBConnection();
        Student globalStudent= new Student(); //create a new instance of Student and call method register in student
        Staff   globalStaff = new Staff();   //create a new instance of Staff and call method register in staff




       //userInput=getUserInput(b);
        //switch to check what user has inputted and its worked on appropriately
        try {
            switch (userInput) {
                case "1":  //case 1 to register a student
                    globalStudent.register(); //call the register method in student class

                    //don't exit system, prompt to continue execution
                    //this method will be called after every case
                    promptToContinueExecution(b);
                    break;

                case "2": // case 2 to register a staff
                    globalStaff.register();  //call the register method in staff class
                    promptToContinueExecution(b);
                    break;

                case "3": // case 3 to read students from the db
                    try {
                        if(readDetails.readFromDB("student")<=0)
                        {
                            System.out.println("no records of students found in the db");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);
                    break;

                case "4":  // case view all staff staff file content

                    try {
                        //call the method readFromDB in class DBConnection
                        //this method executes the statement select * staff
                        if(readDetails.readFromDB("staff")<1)
                        {
                            System.out.println("no records of staff found in the db");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);
                    break;

                case "5":  //edit student
                    try {

                        //if the read db method returns anything in the result set
                        if(readDetails.readFromDB("student") >=1)
                        {
                            //display all the values stored in the students file
                            // and the user will select appropriately


                            //prompt user to select the id of the student they wish to edit
                            System.out.println("\nInput the admission of the student you wish to edit from the above list: ");

                            //take the input
                            String theSelectedStudent = b.readLine();

                            //display a selection menu with the student object properties and ask the user to
                            // select the number associated with the property they wish to edit

                            System.out.println("what property do you want to edit? (enter the number associated with the choice)\n");
                            System.out.println("1: name");
                            System.out.println("2: phone number");
                            System.out.println("3: ID number");
                            System.out.println("4: class level");
                            System.out.println("5: guardian");
                            System.out.println("press P to go the main menu");
                            System.out.println("::::::press \"q\":::::");


                            //read the input
                            String selectedProperty = b.readLine();

                            // call the function edit student and pass the selected student (id row) and the selected property(coloumn).
                            globalStudent.editStudent(selectedProperty,theSelectedStudent);
                        }

                        else
                        {
                            System.out.println("no registered student in the database");
                        }

                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);
                    break;



                case "6": //edit staff
                    try {
                        if(readDetails.readFromDB("staff") >=1)
                        {

                            //prompt user to select the id of the student they wish to edit
                            System.out.println("\nInput the id of the staff you wish to edit from the above list: ");

                            //take the input
                            String theSelectedStaff = b.readLine();

                            //display a selection menu with the student object properties and ask the user to
                            // select the number associated with the property they wish to edit
                            System.out.println("what property do you want to edit? (enter the number associated with the choice)\n");
                            System.out.println("1: id");
                            System.out.println("2: name");
                            System.out.println("3: phone number");
                            System.out.println("4: role");
                            //System.out.println("press P to go the main menu");
                            System.out.println("::::::press \"q\":::::");


                            //read the input
                            String selectedProperty = b.readLine();

                            // call the function edit student and pass the selected student (row) and the selected property.
                            globalStaff.editStaff(selectedProperty,theSelectedStaff);
                        }
                        else
                        {
                            System.out.println("no registered staff in the database");
                        }

                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);
                    break;

                case"7": // student delete student

                    try {
                        if( readDetails.readFromDB("student") >=1)
                        {
                            globalStudent.deleteStudent();
                        }
                        else
                        {
                            System.out.println("No registered student in database");
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);

                    break;

                case"8":  //delete staff


                    try {

                        if( readDetails.readFromDB("staff") >=1)
                        {
                            globalStaff.deleteStaff();
                        }
                        else
                        {
                            System.out.println("No registered staff in database");
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                    promptToContinueExecution(b);

                    break;



                //if a user press q, the BufferReader closes, file input closes and the system exits
                case"p":
                    getUserInput(b);
                case "q":
                    b.close();
                    System.exit(0);

                    // if a user don't select any of the given choices, prompt the again
                default:
                    System.out.println("\nplease input a number indicated on the menu");

                    //redisplay the menu
                    getUserInput(b);

            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }


    }




    // method that gets the user input when they are given the menu with the
    //various processes available in the system

    private static void getUserInput(BufferedReader b) throws IOException {

        //prompt the user to input the task they want to execute
        System.out.println("");
        System.out.println("what task do you want to execute? (please enter the number associated with your " +
                "choice in the below menu) \n");

        // the options 1-5
        System.out.println
                (
                        "1: register a student.\n" +
                                "2: register staff.\n" +
                                "3: view registered students.\n" +
                                "4: view registered staff.\n" + "5: edit student.\n"+"6: edit staff"+"\n7:delete student"+
                                "\n8: delete staff\n"+

                                ":::::::: press\"q\" to exit::::::::\n"
                );

        //read the user input
        executeTheChoiceMade(b.readLine());


    }


    // this method prompts the user if the wish to perform another function in the system
    // or they want to exit the system. it reads the user input and runs it against a
    //switch case.

    public static void promptToContinueExecution(BufferedReader b) throws IOException {
        System.out.println("\ndo you want to perform another task ?");
        System.out.println("please press { Y } for YES and { N } for NO");
        String promptAnswer = b.readLine();

        switch (promptAnswer) {
            case "y":
                getUserInput(b);   //if the user wishes to continue execution, call the method getUserInput
                // which displays the selection menu
                break;
            case "n":
                b.close();           //else if the user chooses to exit the system, close the BufferedReader and
                //exit the system
                System.exit(0);

            default:
                System.out.println("please input option YES { Y } or NO { N }"); //if user inputs invalid choice prompt them again
                promptToContinueExecution( b);


        }

    }



    // this method edits the student object properties held in the student ArrayList
    //it receives the selected student object and the selected property to be edited in the selected student Object



    // this method edits the staff Object properties held in the staff ArrayList
    //it receives the selected staff object and the selected property to be edited in the selected staff Object

    @Override
    protected void finalize() throws Throwable {

        super.finalize();
    }
}







