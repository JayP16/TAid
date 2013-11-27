/**
 *
 * Opens StudentList and adds mark.
 */

package com.example.taid;

import java.io.*;

public class StudentList {
    
    File studentList;
    BufferedReader reader;
    BufferedWriter writer;
    
    public void OpenListFile() throws IOException {
        studentList = new File("C:/Users/AoR/Desktop/text.txt");
        reader = new BufferedReader(new FileReader(studentList));
    }
    
    public void SaveFile() throws IOException {
        String line;
        writer = new BufferedWriter(new FileWriter("C:/Users/AoR/Desktop/textmarks.txt"));
        while ((line = reader.readLine()) != null) {
            // code to read marks from the text box
            System.out.println(line.concat(","));
            writer.write(line.concat(", stuff")); // adding a comma for the new field
            writer.newLine();
        }
        writer.close();
    }
    
    public static void main(String[] args) throws IOException {
        StudentList test = new StudentList();
        test.OpenListFile();
        test.SaveFile();
        
    }
}
