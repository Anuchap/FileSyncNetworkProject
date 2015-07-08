/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldemo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author IzedTea
 */
public class XMLDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XMLDemo xml = new XMLDemo();
        
        System.out.println(xml.getServer());
        System.out.println(xml.getSrc());
        System.out.println(xml.getTar());
    }

    private String server;
    private String src;
    private String tar;
    
    public String getServer() {
        return server;
    }
    
    public String getSrc() {
        return src;
    }
    
    public String getTar() {
        return tar;
    }
    
    public XMLDemo() {
        try {
            File f = new File("c:\\config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("config");
            Node nNode = nList.item(0);
            Element eElement = (Element)nNode;
            server = getTagValue("server", eElement);
            src = getTagValue("src", eElement);
            tar = getTagValue("tar", eElement);
        } catch (SAXException | IOException | ParserConfigurationException ex) { }
    }
    
    private String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node)nlList.item(0);
	return nValue.getNodeValue();
    }
}
