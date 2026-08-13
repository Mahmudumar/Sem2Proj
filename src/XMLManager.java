import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.time.LocalDateTime;
import java.util.List;

public class XMLManager {

    public static Document createXML(Student studentObj)
            throws Exception {

        // Get student information
        String studentID = studentObj.getStudentID();
        String name = studentObj.getName();
        String department = studentObj.getDepartment();
        String category = studentObj.getCategory();

        int level = studentObj.getLevel();
        int graduationYear = studentObj.getGraduationYear();

        List<Course> coursesList =
                studentObj.getMasterCourses();


        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder =
                factory.newDocumentBuilder();


        DocumentType doctype =
                builder.getDOMImplementation()
                        .createDocumentType(
                                "student",
                                null,
                                "student.dtd"
                        );

        Document document =
                builder.getDOMImplementation()
                        .createDocument(
                                null,
                                "student",
                                doctype
                        );

        Element student =
                document.getDocumentElement();



        student.setAttribute("id", studentID);


        Element nameElement =
                document.createElement("name");

        nameElement.setTextContent(name);

        student.appendChild(nameElement);

        Element departmentElement =
                document.createElement("department");

        departmentElement.setTextContent(department);

        student.appendChild(departmentElement);


        Element categoryElement =
                document.createElement("category");

        categoryElement.setTextContent(category);

        student.appendChild(categoryElement);

        Element levelElement =
                document.createElement("level");

        levelElement.setTextContent(
                String.valueOf(level)
        );

        student.appendChild(levelElement);

        Element graduationElement =
                document.createElement("graduationYear");

        graduationElement.setTextContent(
                String.valueOf(graduationYear)
        );

        student.appendChild(graduationElement);

        Element generatedDateTime =
                document.createElement("generatedDateTime");

        generatedDateTime.setTextContent(
                LocalDateTime.now().toString()
        );

        student.appendChild(generatedDateTime);


        Element courses =
                document.createElement("courses");

        student.appendChild(courses);

        for (Course courseObj : coursesList) {

            Element course =
                    document.createElement("course");

            course.setAttribute(
                    "code",
                    courseObj.getCode()
            );


            Element title =
                    document.createElement("title");

            title.setTextContent(
                    courseObj.getTitle()
            );

            course.appendChild(title);

            Element score =
                    document.createElement("score");

            score.setTextContent(
                    String.valueOf(courseObj.getScore())
            );

            course.appendChild(score);

            courses.appendChild(course);
        }


        return document;
    }

    public static void saveXML(
            Document document,
            String fileName
    ) throws Exception {

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();

        Transformer transformer =
                transformerFactory.newTransformer();

        transformer.setOutputProperty(
                OutputKeys.DOCTYPE_SYSTEM,
                "student.dtd"
        );

        // XML declaration
        transformer.setOutputProperty(
                OutputKeys.OMIT_XML_DECLARATION,
                "no"
        );


        // Indentation
        transformer.setOutputProperty(
                OutputKeys.INDENT,
                "yes"
        );



        DOMSource source =
                new DOMSource(document);

        StreamResult result =
                new StreamResult(fileName);

        transformer.transform(source, result);
    }


    public static void validateXML(
            String fileName
    ) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        // Tell the parser to use DTD validation
        factory.setValidating(true);


        DocumentBuilder builder =
                factory.newDocumentBuilder();


        // handle validation errors
        builder.setErrorHandler(
                new ErrorHandler() {

                    @Override
                    public void warning(
                            SAXParseException e)
                            throws SAXException {

                        System.out.println(
                                "Warning: " +
                                        e.getMessage()
                        );
                    }


                    @Override
                    public void error(
                            SAXParseException e)
                            throws SAXException {

                        throw e;
                    }


                    @Override
                    public void fatalError(
                            SAXParseException e)
                            throws SAXException {

                        throw e;
                    }
                }
        );

        builder.parse(fileName);


        System.out.println(
                "XML is valid according to the DTD!"
        );
    }

    public static Document validateAndParseXML(
            String xmlFile
    ) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setValidating(true);


        DocumentBuilder builder =
                factory.newDocumentBuilder();


        builder.setErrorHandler(
                new ErrorHandler() {

                    @Override
                    public void warning(
                            SAXParseException e)
                            throws SAXException {

                        System.out.println(
                                "Warning: " +
                                        e.getMessage()
                        );
                    }


                    @Override
                    public void error(
                            SAXParseException e)
                            throws SAXException {

                        throw e;
                    }


                    @Override
                    public void fatalError(
                            SAXParseException e)
                            throws SAXException {

                        throw e;
                    }
                }
        );


        // This both validates and parses the XML
        return builder.parse(xmlFile);
    }

    public static void parseAndDisplayXML(String xmlFile)
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        Document document =
                builder.parse(xmlFile);

        Element student =
                document.getDocumentElement();

        System.out.println("STUDENT INFORMATIONS");

        System.out.println(
                "Student ID: " +
                        student.getAttribute("id")
        );

        System.out.println(
                "Name: " +
                        student.getElementsByTagName("name")
                                .item(0)
                                .getTextContent()
        );

        System.out.println(
                "Department: " +
                        student.getElementsByTagName("department")
                                .item(0)
                                .getTextContent()
        );

        System.out.println(
                "Category: " +
                        student.getElementsByTagName("category")
                                .item(0)
                                .getTextContent()
        );

        System.out.println(
                "Level: " +
                        student.getElementsByTagName("level")
                                .item(0)
                                .getTextContent()
        );

        System.out.println(
                "Graduation Year: " +
                        student.getElementsByTagName("graduationYear")
                                .item(0)
                                .getTextContent()
        );

        System.out.println(
                "Generated Date/Time: " +
                        student.getElementsByTagName("generatedDateTime")
                                .item(0)
                                .getTextContent()
        );

        System.out.println();
        System.out.println("COURSES");

        var courses =
                student.getElementsByTagName("course");

        for (int i = 0; i < courses.getLength(); i++) {

            Element course =
                    (Element) courses.item(i);

            System.out.println(
                    "\nCourse " + (i + 1)
            );

            System.out.println(
                    "Code: " +
                            course.getAttribute("code")
            );

            System.out.println(
                    "Title: " +
                            course.getElementsByTagName("title")
                                    .item(0)
                                    .getTextContent()
            );

            System.out.println(
                    "Score: " +
                            course.getElementsByTagName("score")
                                    .item(0)
                                    .getTextContent()
            );
        }

    }
}