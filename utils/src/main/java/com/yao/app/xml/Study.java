package com.yao.app.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * https://www.ibm.com/developerworks/cn/xml/x-javaxpathapi.html
 *
 * https://www.ibm.com/developerworks/cn/xml/x-jaxp13a.html
 *
 * Created by yaolei02 on 2017/4/9.
 */
public class Study {

    public static void main(String[] args) {
        System.out.println("==================");
        study1();
        System.out.println("==================");
        study2();
        System.out.println("==================");
        study3();
    }

    public static void study1() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/xml/inventory1.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("//book/author[@test]/@test");
            iterateNodeSet(document, expression);

            XPathExpression expression2 = xPath.compile("//book[author='Neal Stephenson']/title/text()");
            iterateNodeSet(document, expression2);
            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xml文档包含xmlns时，study1是无法解析的
     */
    public static void study2() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/xml/inventory2.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            SimpleNamespaceContext context = new SimpleNamespaceContext();
            context.bindDefaultNamespaceUri("http://www.example.com/books");
            xPath.setNamespaceContext(context);

            XPathExpression expression = xPath.compile("//book/author[@test]/@test");
            iterateNodeSet(document, expression);

            XPathExpression expression2 = xPath.compile("//book[author='Neal Stephenson']/title/text()");
            iterateNodeSet(document, expression2);

            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void study3() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/database/application.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("//context:property-placeholder/@location");
            iterateNodeSet(document, expression);

            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void iterateNodeSet(Document document, XPathExpression expression) throws XPathExpressionException {
        System.out.println("----");
        NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println(node.toString() + "\t" + node.getNodeValue());
        }
        System.out.println("----");
    }
}
