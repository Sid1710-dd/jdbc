package org.example;

import java.sql.*;
import java.io.*;

public class Main {

    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "siddhesh17";

    public static void main(String[] args) {

        String sql = "SELECT * FROM employee";
        String fileName = "employees.csv";

        try (
                Connection conn = DriverManager.getConnection(
                        URL, USERNAME, PASSWORD);

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery()
        ) {

            System.out.println("Database is connected");

            // Create CSV file and write employee records
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(fileName))) {

                // Write CSV header
                writer.write("id,name,department,salary");
                writer.newLine();

                boolean found = false;

                // Read records using ResultSet
                while (rs.next()) {

                    found = true;

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String department = rs.getString("department");
                    double salary = rs.getDouble("salary");

                    // Write records into CSV
                    writer.write(
                            id + "," +
                                    escapeCSV(name) + "," +
                                    escapeCSV(department) + "," +
                                    salary
                    );

                    writer.newLine();

                    System.out.println(
                            id + " | " + name + " | " +
                                    department + " | " + salary
                    );
                }

                if (!found) {
                    System.out.println("No employee records found.");
                }

                System.out.println(
                        "Employee records exported successfully!");

                System.out.println(
                        "CSV file location: " +
                                new File(fileName).getAbsolutePath()
                );

            } catch (IOException e) {
                System.out.println("Error writing CSV file!");
                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println("Database error occurred!");
            e.printStackTrace();
        }
    }

    // Handle commas and quotation marks in CSV fields
    private static String escapeCSV(String value) {

        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}