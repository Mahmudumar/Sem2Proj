import org.w3c.dom.Document;
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
import org.xml.sax.helpers.DefaultHandler;

public class XMLManager {
    public static Document createXML(
            String studentID,
            String name,
            String department,
            String category,
            int level,
            int graduationYear
    ) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        Document document = builder.newDocument();

        Element student = document.createElement("student");
        student.setAttribute("id", studentID);

        document.appendChild(student);

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(name);
        student.appendChild(nameElement);

        Element departmentElement = document.createElement("department");
        departmentElement.setTextContent(department);
        student.appendChild(departmentElement);

        Element categoryElement = document.createElement("category");
        categoryElement.setTextContent(category);
        student.appendChild(categoryElement);

        Element levelElement = document.createElement("level");
        levelElement.setTextContent(String.valueOf(level));
        student.appendChild(levelElement);

        Element graduationElement =
                document.createElement("graduationYear");

        graduationElement.setTextContent(
                String.valueOf(graduationYear)
        );

        student.appendChild(graduationElement);

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
                OutputKeys.INDENT,
                "yes"
        );

        DOMSource source =
                new DOMSource(document);

        StreamResult result =
                new StreamResult(fileName);

        transformer.transform(source, result);
    }


    public static void validateXML(String fileName)
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setValidating(true);

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        builder.setErrorHandler(new ErrorHandler() {

            @Override
            public void warning(SAXParseException e)
                    throws SAXException {
                System.out.println("Warning: " + e.getMessage());
            }

            @Override
            public void error(SAXParseException e)
                    throws SAXException {
                throw e;
            }

            @Override
            public void fatalError(SAXParseException e)
                    throws SAXException {
                throw e;
            }
        });

        builder.parse(fileName);

        System.out.println("XML is valid!");
    }
    public static Document readAndValidateXML(
            String xmlFile,
            String dtdFile
    ) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setValidating(true);

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        builder.setErrorHandler(
                new DefaultHandler()
        );

        return builder.parse(xmlFile);
    }
}

