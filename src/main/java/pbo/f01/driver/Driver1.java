package pbo.f01.driver;

import java.sql.SQLException;
import java.util.*;
import pbo.f01.model.*;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class Driver1 {

    static final String US = "root";
    static final String PS = "Mulyad1yam1n.";
    
    public static void main(String[] args) {
        Student student = new Student();
        Dorm dorm = new Dorm();
        
        //javac example\*.java -d bin
        //java -cp "bin;./libs/*" example.Driver
        try {
            //Contact ke database
            ContactDatabase database = new ContactDatabase("jdbc:h2:./db/dormy");
            //Masukkan inputan
            Scanner Input = new Scanner(System.in);

            //Percabangan
            while (Input.hasNextLine()){
                String input = Input.nextLine();
                if (input.equals("---")){
                    Input.close();
                    break;
                }

                String[] tokens = input.split("#");

                if (tokens[0].equals("student-add")){
                    try {
                        String dormy = "None";
                        student.studentAdd(tokens[1], tokens[2], Integer.parseInt(tokens[3]), tokens[4], dormy, database);
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        
                    }
                } else if (tokens[0].equals("dorm-add")){
                    try {
                        dorm.dormAdd(tokens[1], Integer.parseInt(tokens[2]), tokens[3], database);
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        
                    }

                } else if (tokens[0].equals("assign")){
                    database.AssignDorm(tokens[1], tokens[2]);

                } else if (tokens[0].equals("display-all")){

                    database.printAll();
                }

            }
            //paling bawah
            database.shutdown();
        } catch (SQLException sqle) {
            //System.out.println(sqle.getMessage());
        }

        
    }

}