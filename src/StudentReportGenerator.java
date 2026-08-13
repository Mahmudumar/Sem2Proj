import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.time.LocalDateTime;
public class StudentReportGenerator {

    public static void write(
            Student studentObj,
            Vector<String> finalReportInfo) throws IOException {

//        Timestarmp stuff
        String date = LocalDateTime.now().toLocalDate().toString();
        String time = LocalDateTime.now().toLocalTime().toString();

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("StudentReport.txt"))) {

            writer.write("========================================");
            writer.newLine();
            writer.write("UNIVERSITY ACADEMIC RECORD SYSTEM");
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();
            writer.write("Date of Report: %s".formatted(date));
            writer.newLine();
            writer.write("Time of Report: %s".formatted(time));
            writer.newLine();
            writer.newLine();

//            status like ....Success or failed gotten from the finalReportInfo vector
            for (String status : finalReportInfo) {
                writer.write(status);
                writer.newLine();
                writer.newLine();
            }

            // Student information
//            writer.newLine();
            writer.write("Student ID: " + studentObj.getStudentID());
            writer.newLine();

            writer.write("Department: " + studentObj.getDepartment());
            writer.newLine();

            writer.write("Category: " + studentObj.getCategory());
            writer.newLine();

            writer.write("Level : " + studentObj.getLevel());
            writer.newLine();

            writer.newLine();
            writer.write("Courses Taken: " + studentObj.getNumberOfCourses());
            writer.newLine();
            writer.newLine();
            writer.write("Average Score: " + studentObj.getAverageScore());
            writer.newLine();
            writer.newLine();
            writer.write("GPA: " + studentObj.getGPA());
            writer.newLine();

            writer.newLine();

            writer.newLine();
            writer.write("Threads Executed Successfully");
            writer.newLine();
            writer.newLine();

            writer.write("Student Report Generated");
            writer.newLine();
            writer.newLine();

            writer.write("Program Completed Successfully");
            writer.newLine();
            writer.newLine();
        }
    }
}