import org.w3c.dom.Document;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

//    select consecutive courses from the provided CSV,
//    wrapping to the beginning if necessary.

    static List<String> getMasterCourses () throws Exception {
        List<String> courses = new ArrayList<>();
        String file = "src//master_courses.csv";
        String line;
        String csvSplitBy = ",";

        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader br = new BufferedReader(fr);

        try {
            br.readLine(); // remove first row, header row..
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String courseCode = "";
        String courseTitle = "";

        try{
            while ((line = br.readLine()) != null){
                String[] data = line.split(csvSplitBy);
                courseCode = data[0];
                courseTitle = data[1];

            courses.add(courseCode+" ("+courseTitle+") ");

            }
            return courses;

        }catch (Exception e){
            throw  new Exception(e);
        }
    }

    public static int calcNumberOfCourses(List<Integer> studentID){
//    Number of Courses = (last digit % 5) + 4.
        return (studentID.getLast() % 5) + 4;
    }

    static int get_sum_of_digits(List<Integer> studentID){
        int sum_of_digits = 0;
        for (int i = 0; i < studentID.toArray().length; i++) {
            sum_of_digits+=studentID.get(i);
        }
        return sum_of_digits;
    }

    public static String calcDepartment(List<Integer> studentID){
//    Department = (sum of digits % 4): 0=Computer Science, 1=Software Engineering, 2=Information Technology, 3=Cyber Security.

        int sum_of_digits = get_sum_of_digits(studentID);

//        System.out.println(sum_of_digits);

        int dept = (sum_of_digits % 4);
        if (dept == 0){
            return "Computer Science";
        } else if (dept == 1) {
            return "Software Engineering";
        } else if (dept == 2) {
            return "Information Technology";
        } else if (dept == 3) {
            return "Cyber Security";
        }
        else {
            return "";
        }
    }

    public static String calcCategory(List<Integer> studentID){
//    Student Category: first digit even = Regular Student; otherwise Direct Entry Student.
        if (studentID.get(0)%2 == 0){
            return "Regular Student";
        } else {
            return "Direct Entry Student";
        }
    }

    public static int calcGradYear(List<Integer> studentID){
//    Graduation Year = Current Year + (last two digits % 3).
        int current_year = LocalDateTime.now().getYear();

        // get the last 2 digits
//        System.out.println(studentID);

        int last_digit = studentID.getLast();
//        System.out.println(studentID);

        int last_digit2 = studentID.get(studentID.size() - 2); // get the second to the last
//        System.out.println(studentID);

        studentID.add(last_digit);
//        System.out.println(studentID);


        return current_year + ((last_digit+last_digit2) % 3);
    }

    public static int calcLevel(List<Integer> studentID){
//    Student Level = 100 + (last digit × 100).
        return 100 + (studentID.getLast() * 100);

    }

    public static int calcMaxScore(List<Integer> studentID){
//    Maximum Score = (sum of digits % 21) + 80;
        return (get_sum_of_digits(studentID) % 21) +80;
    }

    public static int calcStartingScore(List<Integer> studentID){
//    Starting course = (sum of digits % 20);
        return (get_sum_of_digits(studentID) % 20);
    }

    public static List<Integer> stripLetters(String studentID){
//    Remove all non-numeric characters from the Student ID.
        List<Integer> mainStudentID = new ArrayList<>();

//        remove the letters
        for (int letter_index = 0; letter_index < studentID.length(); letter_index++) {
            String chr = String.valueOf(studentID.charAt(letter_index));
            try{
                int no = Integer.parseInt(chr);
                mainStudentID.add(no);
//                System.out.println(no);
            }catch (Exception n){
//                System.out.println(""+n);
//                ignore every letter
                continue;
            }
        }
        return mainStudentID;
    }

    public void main (String[] args) throws Exception {

        // get the csv file
        List<String> courses = getMasterCourses();
//        System.out.println(courses);

//        XMLManager XMLManager = new XMLManager();


        String studentID = "Student1643702";
        List<Integer> sID = stripLetters(studentID);

        int numberOfCourses = calcNumberOfCourses(sID);
        System.out.println("Number of Courses: "+numberOfCourses);

        String department = calcDepartment(sID);
        System.out.println("Department: "+department);

        String category = calcCategory(sID);
        System.out.println("Category: "+category);

        int gradYear = calcGradYear(sID);
        System.out.println("Graduation year: "+gradYear);

        int level = calcLevel(sID);
        System.out.println("Student Level: "+level);

        int maxScore = calcMaxScore(sID);
        System.out.println("Maximum Score: "+maxScore);

        int startScore = calcStartingScore(sID);
        System.out.println("Starting Score: "+startScore);

        Document xmlFile= XMLManager.createXML(studentID, "Umar",department, category, level, gradYear);
        XMLManager.saveXML(xmlFile, "student.xml");
    }
}
