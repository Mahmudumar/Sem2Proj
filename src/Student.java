import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements ResultProcessor{
    private int numberOfCourses;
    private int maxScore;
    private int startingCourse;
    private double GPA;
    private String department;
    private String category;
    private int level;
    private int graduationYear;
    List<Course> courses = new ArrayList<>();
    public Student(
            String studentID,
            String name) {

        super(studentID, name);

        List<Integer> sID = stripLetters(studentID);

        this.department = calcDepartment(sID);
        this.category = calcCategory(sID);
        this.level = calcLevel(sID);
        this.graduationYear = calcGradYear(sID);
        this.numberOfCourses = calcNumberOfCourses(sID);
        this.maxScore= calcMaxScore(sID);
        this.startingCourse = calcStartingCourse(sID);
        this.GPA = 0.0;

    }
    public static int generateRandomScore(int maxScore) {
        return (int) (Math.random() * (maxScore + 1));
    }
//    Student ID rules methods: calculate just from studentID
    public List<Course> getMasterCourses() throws Exception {
        if (!courses.isEmpty()) {return courses;}

        String file = "master_courses.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            br.readLine();

            for (int i = 0; i < this.numberOfCourses; i++) {

                line = br.readLine();

                String[] data = line.split(csvSplitBy);

                Course course = new Course();
                course.setCode(data[0]);
                course.setTitle(data[1]);

                course.setScore(String.valueOf(generateRandomScore(this.maxScore)));

                courses.add(course);
            }

        } catch (FileNotFoundException e) {
            throw new Exception("Master courses file not found: " + file, e);

        } catch (IOException e) {
            throw new Exception("Error reading master courses file.", e);
        }

        return courses;
}
    public static int calcNumberOfCourses(List<Integer> studentID){
//    Number of Courses = (last digit % 5) + 4.
        return (studentID.getLast() % 5) + 4;
    }

    static int get_sum_of_digits(List<Integer> studentID){
        int sum_of_digits = 0;
        for (int i = 0; i < studentID.size(); i++) {
            sum_of_digits+=studentID.get(i);
        }
        return sum_of_digits;
    }

    public static String calcDepartment(List<Integer> studentID){
//    Department = (sum of digits % 4): 0=Computer Science, 1=Software Engineering, 2=Information Technology, 3= CyberSecurity.

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

    public static int calcStartingCourse(List<Integer> studentID){
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


//    variable Getters
    public String getDepartment() {
        return department;
    }

    public String getCategory() {
        return category;
    }

    public int getLevel() {
        return level;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    @Override
    public void calculateFinalGPA() {
        double total = 0;
        for (Course course : courses) {
            total += Double.parseDouble(course.getScore());
        }
    
        this.GPA = total / courses.size();
    }
    public double getAverageScore(){
        double total = 0;
        for (int i = 0; i < courses.size(); i++) {
            total += Double.parseDouble(courses.get(i).getScore());
        }
        return total / this.numberOfCourses;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
