package be.kuleuven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private String db = """
        DROP TABLE IF EXISTS student;
        CREATE TABLE student(
            studnr INT NOT NULL PRIMARY KEY,
            naam VARCHAR(200) NOT NULL,
            voornaam VARCHAR(200),
            goedbezig BOOL
        );
        DROP TABLE IF EXISTS log;
        CREATE TABLE log(
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            date DATETIME DEFAULT CURRENT_TIMESTAMP,
            foreign_id INT NOT NULL,
            msg TEXT
        );
        INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (123, 'Trekhaak', 'Jaak', 0);
        INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (456, 'Peeters', 'Jos', 0);
        INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (890, 'Dongmans', 'Ding', 1);
        """;

    public static void main(String[] args) {
        Connection connection = null;
        StudentRepository sr = new StudentRepository(connection);
    }
    public StudentRepository(Connection connection) throws SQLException {
        Statement s = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
            s = connection.createStatement();
            s.executeUpdate(db);
        } catch (SQLException e){
            throw new RuntimeException();
        } finally {
            if(s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
    }
    public List<Student> getStudentsByName(String name) throws SQLException{
        Statement statement = null;
        ArrayList<Student> list = new ArrayList<>();
        var result = statement.executeQuery("SELECT * FROM student");
        while (result.next()){
            var studnr = result.getInt("studnr");
            var naam = result.getString("naam");
            var goedbezig = result.getBoolean("goedbezig");
            var voornaam = result.getString("voornaam");
            Student stu = new Student(studnr, naam, voornaam, goedbezig);
            list.add(stu);
        }
        return list;
    }
}
