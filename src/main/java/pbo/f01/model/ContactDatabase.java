package pbo.f01.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class ContactDatabase extends AbstractDatabase {



    public ContactDatabase(String url) throws SQLException {
        super(url);
    }

    protected void createTables() throws SQLException {
        String Student = "CREATE TABLE IF NOT EXISTS student (" + 
            "id VARCHAR(255) NOT NULL PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "student_year INT NOT NULL," +
            "gender VARCHAR(255) NOT NULL," +
            "student_dorm VARCHAR(255) NOT NULL" +
            ")";
        

        String Dorm = "CREATE TABLE IF NOT EXISTS dorm (" + 
            "name VARCHAR(255) NOT NULL PRIMARY KEY," +
            "dorm_capacity INT NOT NULL," +
            "dorm_gender VARCHAR(255) NOT NULL" +
            ")";
        
        Statement statement = this.getConnection().createStatement();

        statement.execute("DROP TABLE IF EXISTS student");
        statement.execute("DROP TABLE IF EXISTS dorm");      

        statement.execute(Student);
        statement.execute(Dorm);

        statement.close();
    }


    //PRINT ALL STUDENTS
    public void printAll() throws SQLException {

        Statement statement = this.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM dorm ORDER BY name ASC");

        while (resultSet.next()){
            String dormy = resultSet.getString("name");
            Integer capacity = this.Countcapacity(dormy);

            System.out.println(
                resultSet.getString("name") + "|" +
                resultSet.getString("dorm_gender") + "|" +
                resultSet.getInt("dorm_capacity") + "|" +
                capacity
            );


            //System.out.println(dormy);
           
            String sql = "SELECT * FROM student WHERE student_dorm = ? ORDER BY name ASC";
            PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
            pStatement.setString(1, dormy);
            ResultSet dorm = pStatement.executeQuery();

            while (dorm.next()){


                System.out.println(

                    dorm.getString("id") + "|" +
                    dorm.getString("name") + "|" +
                    dorm.getInt("student_year")

                );
            }
            
        }

        resultSet.close();
        statement.close();

    }

    private int Countcapacity(String dorm) throws SQLException{
        int total = 0;
           
        String sql = "SELECT * FROM student WHERE student_dorm = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
        pStatement.setString(1, dorm);
        ResultSet dormed= pStatement.executeQuery();

        while (dormed.next()){
            total++;
        }

        dormed.close();
        pStatement.close();

        return total;
    }

    public String CekGender(String dorm) throws SQLException{

        
        System.out.println(dorm);
        String sql = "SELECT * FROM dorm WHERE name = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
        pStatement.setString(1, dorm);
        ResultSet dormed= pStatement.executeQuery();

            //String gender = dormed.getString("dorm_gender");

        //}


        return dormed.getString("dorm_gender");

    }

    


    //PRINT ALL ENROLLMENT
    public void AssignDorm(String id, String dorm) throws SQLException {
        String gender = "";

        String sqldorm = "SELECT * FROM dorm WHERE name = ?";
        PreparedStatement statementgdorm = this.getConnection().prepareStatement(sqldorm);
        statementgdorm.setString(1, dorm);
        ResultSet dormed= statementgdorm.executeQuery();

        while (dormed.next()){
            gender = dormed.getString("dorm_gender");
        }

        //String cekgender = CekGender(dorm);

        String sql = "UPDATE student SET student_dorm = ? WHERE id = ? AND gender = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);

        //ResultSet student = pStatement.executeQuery();

        // String doit = "SELECT * FROM dorm where name = ?";

        // PreparedStatement statement = this.getConnection().prepareStatement(doit);

        // statement.setString(1, dorm);

        // Integer capacity = ((ResultSet) statement).getInt("dorm_capacity");

        Integer maxcapa = this.Countcapacity(dorm);

        if ( maxcapa < 5){

        
            pStatement.setString(1, dorm);
            pStatement.setString(2, id);
            pStatement.setString(3, gender);
        //}
        } else{
            pStatement.setString(1, "None");
            pStatement.setString(2, id);
            pStatement.setString(3, gender);
        }
        pStatement.executeUpdate();

        pStatement.close();
    }




//     //PRINT STUDENT DETAILS tambahkan total ipk mahasiswa dan total sks mahasiswa
//     public void printStudentDetails(String nim) throws SQLException {
//         totalIPK = 0.0;
//         totalSKS = 0;
//         gradePoint = 0.00;
//         BGrade = 0.00;
//         BCourse = "";
        
//         // Query untuk mengambil semua data mahasiswa
//         String query = "SELECT * FROM student WHERE nim = ?";
//         PreparedStatement statement = this.getConnection().prepareStatement(query);
//         statement.setString(1, nim);
//         ResultSet studentResult = statement.executeQuery();
    
//         // Jika mahasiswa ditemukan
//         while (studentResult.next()) {
//             String studentName = studentResult.getString("name");
//             String studyProgram = studentResult.getString("studyProgram");
//             Integer year = studentResult.getInt("year");

//             //cek apakah ada data di dalam enrollment yang memiliki nim yang sama
//             String queryEnrollment = "SELECT * FROM enrollment WHERE nim = ?";
//             PreparedStatement statementEnrollment = this.getConnection().prepareStatement(queryEnrollment);
//             statementEnrollment.setString(1, nim);
//             ResultSet enrollmentResult = statementEnrollment.executeQuery();

//             // Jika ada data di dalam enrollment
//             while (enrollmentResult.next()) {
//                 String code = enrollmentResult.getString("code");
//                 String grade = enrollmentResult.getString("grade");

//                 String[] Realgrade = grade.split("\\(");

//                 gradePoint = this.calculateGradePoint(Realgrade[0]);

//                 this.AllTotal(code, gradePoint);
//             }
    
//             // Menampilkan detail mahasiswa beserta total IPK dan total SKS
//             System.out.println(nim + "|" + studentName + "|" + year + "|" + studyProgram + "|" + String.format("%.2f", totalIPK / totalSKS) + "|" + totalSKS);
//         } 
    
//         statement.close();
//     }

//     // ADD COURSE OPENING yang nilainya course iambil dari table course dan lakukan print
//     public void addCourseOpening(String code, String year, String semester, String initial ) throws SQLException {

//         String IE = "";
//         //ambil dari course
//         String query = "SELECT * FROM course WHERE code = ?";
//         PreparedStatement statement = this.getConnection().prepareStatement(query);
//         statement.setString(1, code);
//         ResultSet Courseresult = statement.executeQuery();
        

//         while (Courseresult.next()) {
//             String courseAll = Courseresult.getString("code") +"|"+ Courseresult.getString("name") +"|"+ Courseresult.getInt("credits") +"|"+ Courseresult.getString("grade");
//             String getLecturer = "SELECT * FROM lecturer WHERE initial = ?";
//             PreparedStatement statementLecturer = this.getConnection().prepareStatement(getLecturer);
//             statementLecturer.setString(1, initial);
//             ResultSet Lecturerresult = statementLecturer.executeQuery();

//             while (Lecturerresult.next()) {
//                 IE = Lecturerresult.getString("initial") + " (" + Lecturerresult.getString("email") + ")";

//                 String sql = "INSERT INTO course_opening (course, year, semester, IE) VALUES (?, ?, ?, ?)";

//                 PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
//                 pStatement.setString(1, courseAll);
//                 pStatement.setString(2, year);
//                 pStatement.setString(3, semester);
//                 pStatement.setString(4, IE);

//                 pStatement.executeUpdate();

//                 pStatement.close();

//                 break;
//             }

//         }

    
//     }

//     // SHOW COURSE HISTORY

//     public void showCourseHistory(String code) throws SQLException {
//         String query = "SELECT * FROM course_opening ORDER BY CASE WHEN semester = 'odd' THEN 1 ELSE 2 END";
//         PreparedStatement statement = this.getConnection().prepareStatement(query);
//         ResultSet result = statement.executeQuery();

//         while (result.next()) {
//             String course = result.getString("course");
//             String year = result.getString("year");
//             String semester = result.getString("semester");
//             String IE = result.getString("IE");

//             System.out.println(course + "|" + year + "|" + semester + "|" + IE);

//             //tokenize course
//             String[] tokens = course.split("\\|");
//             String courseCode = tokens[0];

//             String queryEnrollment = "SELECT * FROM enrollment WHERE code = ? AND year = ? AND semester = ?";
//             PreparedStatement statementEnrollment = this.getConnection().prepareStatement(queryEnrollment);
//             statementEnrollment.setString(1, courseCode);
//             statementEnrollment.setString(2, year);
//             statementEnrollment.setString(3, semester);
//             ResultSet enrollmentResult = statementEnrollment.executeQuery();

//             while (enrollmentResult.next()) {
//                 String Enrolcode = enrollmentResult.getString("code");
//                 String Enrollnim = enrollmentResult.getString("nim");
//                 String Enrolyear = enrollmentResult.getString("year");
//                 String Enrolsemester = enrollmentResult.getString("semester");
//                 String Enrollgrade = enrollmentResult.getString("grade");

//                 System.out.println(Enrolcode + "|" + Enrollnim + "|" + Enrolyear + "|" + Enrolsemester + "|" + Enrollgrade);
//             }

//         }

//         statement.close();
//     }

//     // PRINT STUDENT TRANSCRIPT
//     public void printStudentTranscript(String nim) throws SQLException {
//         totalIPK = 0.0;
//         totalSKS = 0;
//         gradePoint = 0.00;
//         BGrade = 0.00;
//         BCourse = "";
        
//         // Query untuk mengambil semua data mahasiswa
//         String query = "SELECT * FROM student WHERE nim = ?";
//         PreparedStatement statement = this.getConnection().prepareStatement(query);
//         statement.setString(1, nim);
//         ResultSet studentResult = statement.executeQuery();
    
//         // Jika mahasiswa ditemukan
//         while (studentResult.next()) {
//             String studentName = studentResult.getString("name");
//             String studyProgram = studentResult.getString("studyProgram");
//             Integer year = studentResult.getInt("year");


//             //cek apakah ada data di dalam enrollment yang memiliki nim yang sama
//             String RealEnroll = "SELECT * FROM enrollment m1 WHERE nim = ? AND CAST(SUBSTR(year, INSTR(year, '/') + 1) AS INTEGER) = (SELECT MAX(CAST(SUBSTR(year, INSTR(year, '/') + 1) AS INTEGER)) FROM enrollment m2 WHERE m1.code = m2.code)";
//             PreparedStatement enrollmentStat = this.getConnection().prepareStatement(RealEnroll);
//             enrollmentStat.setString(1, nim);
//             ResultSet result = enrollmentStat.executeQuery();

//             // Jika ada data di dalam enrollment
//             while (result.next()) {
//                 String code = result.getString("code");
//                 String grade = result.getString("grade");

//                 String[] Realgrade = grade.split("\\(");

//                 gradePoint = this.calculateGradePoint(Realgrade[0]);

//                 this.AllTotal(code, gradePoint);
//             }
    
//             // Menampilkan detail mahasiswa beserta total IPK dan total SKS
//             System.out.println(nim + "|" + studentName + "|" + year + "|" + studyProgram + "|" + String.format("%.2f", totalIPK / totalSKS) + "|" + totalSKS);
//         }

//         //while
//         String query2 = "SELECT * FROM student WHERE nim = ?";
//         PreparedStatement statement2 = this.getConnection().prepareStatement(query2);
//         statement2.setString(1, nim);
//         ResultSet studentResult2 = statement2.executeQuery();


//         while (studentResult2.next()){

            

//             String RealEnroll = "SELECT * FROM enrollment m1 WHERE nim = ? AND CAST(SUBSTR(year, INSTR(year, '/') + 1) AS INTEGER) = (SELECT MAX(CAST(SUBSTR(year, INSTR(year, '/') + 1) AS INTEGER)) FROM enrollment m2 WHERE m1.code = m2.code)";
//             PreparedStatement enrollmentStat = this.getConnection().prepareStatement(RealEnroll);
//             enrollmentStat.setString(1, nim);
//             ResultSet result = enrollmentStat.executeQuery();

//             // Jika ada data di dalam enrollment
//             while (result.next()) {
//                 String Enrollnim = result.getString("nim");
//                 String Enrollcode = result.getString("code");
//                 String Enrolyear = result.getString("year");
//                 String Enrolsemester = result.getString("semester");
//                 String Enrollgrade = result.getString("grade");

//                 System.out.println(Enrollcode + "|" + Enrollnim + "|" + Enrolyear + "|" + Enrolsemester + "|" + Enrollgrade);
//             }
        
//         }
        
    
//         statement2.close();
//         statement.close();
//     }



//     // Method untuk mengambil jumlah kredit dari sebuah mata kuliah
//     public void AllTotal(String code, Double gradePoint) throws SQLException {
//         String query = "SELECT * FROM course WHERE code = ?";
//         PreparedStatement statement = this.getConnection().prepareStatement(query);
//         statement.setString(1, code);
//         ResultSet Courseresult = statement.executeQuery();
        
//         while (Courseresult.next()) {

//             String coursecode = Courseresult.getString("code");

//             if (code.equals(coursecode) && gradePoint > 0.00){
//                 // jika course selanjutnya sama dengan BCourse
//                 if (BCourse.equals(coursecode)){
//                     Integer credits = Courseresult.getInt("credits");
                    
//                     // Menghitung total IPK dan total SKS
//                     totalSKS -= credits;
//                     totalIPK -= gradePoint * credits;
//                 }
//                 // Mengambil jumlah kredit dari mata kuliah
//                 Integer credits = Courseresult.getInt("credits");
                
//                 // Menghitung total IPK dan total SKS
//                 totalSKS += credits;
//                 totalIPK += gradePoint * credits;
                
//                 BCourse = code;
//                 BGrade = gradePoint;
//                 break;
//             }
//             //return result.getInt("credits");
//         }
//     }

    
//     // Method untuk menghitung nilai indeks berdasarkan grade
//     private Double calculateGradePoint(String grade) {
//         switch (grade) {
//             case "A":
//                 return 4.0;
//             case "AB":
//                 return 3.5;
//             case "B":
//                 return 3.0;
//             case "BC":
//                 return 2.5;
//             case "C":
//                 return 2.0;
//             case "D":
//                 return 1.5;
//             case "E":
//                 return 1.0;
//             default:
//                 return 0.0;
//         }
//     }
       
    @Override
    protected void prepareTables() throws SQLException {
        this.createTables();
        //this.seedTables();
    }

}