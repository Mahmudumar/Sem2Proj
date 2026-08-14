# University Academic Record System

## About

Semester 2 project about taking in the student ID of any student and using that to generate an XML file which contains information about that person.

That information was generated using simple rules such as stripping any non-alphabetical characters from the ID, then doing mathematical operations on the numbers to get information such as the student's department. For example, if the result is `1`, then the student is studying Software Engineering.


## Features

The program basically takes the ID of the student in any format. For example: `Student1244855`, `CSC1066`, `CS/22/64`, and strips anything that is not a number.

The result will be a list of numbers which will be used with the rules provided in the Project Question file sent by the instructor.

The program also implements multithreading by using 4 threads for four different tasks. However, these tasks need resources from the previous thread, therefore, one thread needs to wait for the previous thread to finish before it starts.

For example, XML Validation is a thread which validates the XML file which is generated in a separate thread of its own. If the XML Validation thread starts before the XML Generation finishes, then we will run into an error. The Validation cannot be done because the XML file hasn't been created yet.

Because of this, I have made each thread start and completely finish its task before starting another.


## Project Structure

`Student.java` is responsible for anything having to do with information about the student, including information generated from the student ID provided.

`XMLManager.java` is in charge of generating the XML file and validating the file.

`Main.java` is the main entry point for the project.

`Course.java` was created to make creating and accessing information about every course, which is used by `getMasterCourses()`, easier.

`StudentReportGenerator.java` is the endpoint. It is the class that takes all the information it gets from the other threads' tasks to generate and log into a new `StudentReport.txt` file.


## How It Works

When we run the program, we will be prompted from the command line to enter a student ID. When the user enters any student ID, the program takes that string value and strips out anything that is not a number. It then puts those numbers inside a list for calculations.

We create a `Vector` (a thread-safe collection) to store the status about whether each thread was successful or not.

Next, an instance of the `Student` class is created in the `Main` class so it can be accessible to all threads.

We then pass this instance into every thread: the XML Generation thread, the XML Validation and Parsing thread, and the Student Report Generation thread. Each thread will use this instance to extract the exact information they need when they need it.

The first thread is the GPA Calculation thread. It needs no resource from other threads to get started.

The next thread to run is the XML Generation thread. It first gets all the information that has been specifically outlined by the DTD provided to us. Information such as Student ID, Department, Category, Courses, and so on, was specified. This thread must create an XML file named `student.xml`.

The next thread to run immediately after the XML file `student.xml` has been created is the XML Validation thread. In this thread, two things happen: validation and parsing. When the validation is done, we **parse** and **display** the information from the generated XML file itself, not from the student instance created earlier. We print this to the console so that the user running our program will see meaningful output as opposed to a "done" output or no output at all.

Then we end with the last thread, which takes all the information stored in the `Vector` above and writes all that cleanly into a `StudentReport.txt` file. The format of this file is specified by the instructor as well.


## Purpose

It is a Java project, so durrh.

The project is about generating an XML file of a student in a school. This perhaps could be used in a school system where student information needs to be generated, stored, validated, and processed in a structured format such as XML.

The project also demonstrates how XML, DTD, DOM parsing, CSV files, and multithreading can be used together in one application.


## How to Run

Just run `Main.java` in IntelliJ.
