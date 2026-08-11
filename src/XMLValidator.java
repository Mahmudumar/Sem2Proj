import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLValidator{

    public static boolean validate(String xmlFile) {

        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            factory.setValidating(true);

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            builder.parse(new File(xmlFile));

            return true;

        } catch (Exception e) {
            System.out.println("XML Validation Failed: "
                    + e.getMessage());

            return false;
        }
    }
}