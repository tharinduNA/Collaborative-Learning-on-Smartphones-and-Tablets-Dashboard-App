package com.android.utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Environment;

public class XmlFileUtil {

	public static int getTotalFeedbacksForTodayLesson()
	{
		//accessing xml and get total feedbacks for that lesson
		int totalFeedbacks = -1;
		String todayLessonName = "";
		ArrayList<String[]> tagsData;
		try{
			todayLessonName = MyGlobal.getTodayLesson();
			String fileName = todayLessonName + ".xml";
			
			File fXmlFile = new File(Environment.getExternalStorageDirectory()+ "/lessons/" + todayLessonName,fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			doc.getDocumentElement().normalize(); 
			
			String expression = "/lesson_plan/lessonID";
			Node node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
			tagsData = new ArrayList<String[]>();
			if(null != node) {
			    NodeList nodeList = node.getChildNodes();
			    
			    for (int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
			    	
			        Node nod = nodeList.item(i);
			        
			        if(nod.getNodeType() == Node.ELEMENT_NODE){
			        	Element eElement = (Element) nod;
			        	String attribute = eElement.getAttribute("type");
			        	if(attribute.equalsIgnoreCase("feedback")){
			        		totalFeedbacks = Integer.parseInt(nod.getFirstChild().getNodeValue());
			        	}
				        	
			        }
			    }
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return totalFeedbacks;
		
	}
}
