package com.yao.app.xml;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

/**
 *
 * https://www.ibm.com/developerworks/cn/xml/x-javaxpathapi.html
 *
 * https://www.ibm.com/developerworks/cn/xml/x-jaxp13a.html
 *
 * Created by yaolei02 on 2017/4/9.
 */
public class Study {
    public static void main(String[] args){
        System.out.println("==================");
        study1();
        System.out.println("==================");
        study2();
        System.out.println("==================");
    }

    public static void study1(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/xml/inventory1.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("//book/author[@test]/@test");
            Object result = expression.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println(node.getNodeValue());
            }

            XPathExpression expression2 = xPath.compile("//book[author='Neal Stephenson']/title/text()");
            Object result2 = expression2.evaluate(document, XPathConstants.NODESET);
            NodeList nodes2 = (NodeList) result;
            for (int i = 0; i < nodes2.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println(node.getNodeValue());
            }
            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * xml文档包含xmlns时，study1是无法解析的
     */
    public static void study2(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/xml/inventory2.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            xPath.setNamespaceContext(new MyNamespaceContext());

            XPathExpression expression = xPath.compile("//mypre:book/mypre:author[@test]/@test");
            Object result = expression.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println(node.getNodeValue());
            }

            XPathExpression expression2 = xPath.compile("//mypre:book[author='Neal Stephenson']/mypre:title/text()");
            Object result2 = expression2.evaluate(document, XPathConstants.NODESET);
            NodeList nodes2 = (NodeList) result;
            for (int i = 0; i < nodes2.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println(node.getNodeValue());
            }
            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public static void study3(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Resource resource = new ClassPathResource("/database/application.xml");
            Document document = documentBuilder.parse(resource.getInputStream());

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("//context:property-placeholder/@location");
            Object result = expression.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println(node.getNodeValue());
            }


            // nodeset,number,boolean,string
            // org.w3c.dom.NodeList,Double,Boolean,String
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
