
package org.example;

import java.sql.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/employee_db";
        String username = "root";
        String password = "siddhesh17";

        String sql = "SELECT * FROM employee";
        String fileName = "C:\\Users\\Dell\\IdeaProjects\\jdbc\\employees.csv";

        try (
                Connection con = DriverManager.getConnection(
                        url, username, password);

                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(fileName))
        ) {
            System.out.println("Database connected successfully.");

            // Write CSV header
            writer.write("id,name,department,salary");
            writer.newLine();

            // Read employee records
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");

                // Write employee details to CSV
                writer.write(id + "," + name + ","
                        + department + "," + salary);
                writer.newLine();

                System.out.println(id + " " + name + " "
                        + department + " " + salary);
            }

            System.out.println("Employee records exported successfully.");
            System.out.println("File location: "
                    + new File(fileName).getAbsolutePath());

        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Error writing CSV file.");
            e.printStackTrace();
        }
    }
}
