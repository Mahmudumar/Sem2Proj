import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Vector;

class Main{
    static void main() throws Exception {
//        thread safe way of logging to StudentReport.txt
        Vector finalReportInfo = new Vector();

        Student studentObj = new Student("Student1643702","umar");

        Thread gpaCalc = new Thread(()->{
            try {
                studentObj.calculateFinalGPA();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread XMLGen = new Thread(()-> {
            Document xmlFile= null;
            try {
                xmlFile = XMLManager.createXML(studentObj);
                XMLManager.saveXML(xmlFile, "student.xml");
                finalReportInfo.add("Generating XML.............SUCCESS");
            } catch (Exception e) {
                finalReportInfo.add("Generating XML.............FAILED");
                throw new RuntimeException(e);
            }
        });

        Thread XMLValidateAndParsing = new Thread(()->{
//            VALIDATING
            try{
                XMLManager.validateXML("student.xml");
                finalReportInfo.add("Validating XML............SUCCESS");

            }catch (Exception e){
                finalReportInfo.add("Validating XML............FAILED");
                System.out.println(e);
                throw new RuntimeException();
            }

//            PARSING
            try{
                XMLManager.parseAndDisplayXML("student.xml");
                finalReportInfo.add("Parsing XML............SUCCESS");

            }catch (Exception e){
                finalReportInfo.add("Parsing XML............FAILED");
                throw new RuntimeException();
            }
        });

        Thread ReportGen = new Thread(()->{
            try {
                StudentReportGenerator.write(studentObj, finalReportInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        XMLGen.start();
        XMLGen.join();

        XMLValidateAndParsing.start();
        XMLValidateAndParsing.join();

        gpaCalc.start();
        gpaCalc.join();

        ReportGen.start();
        ReportGen.join();
    }
}
