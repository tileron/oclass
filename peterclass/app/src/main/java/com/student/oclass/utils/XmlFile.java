package com.student.oclass.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.content.Context;
public class XmlFile {
	private String fileName;
	private Context context;
	private Element root;
	private Node currentnode;
	private Document doc;

	public XmlFile(String myfile,Context maincontext){
		DocumentBuilderFactory docBuilderFactory = null;
		DocumentBuilder docBuilder = null;
		context=maincontext;
		fileName=myfile;
		try {
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
			//xml file 放到 assets目录中的
			InputStream is;

			//root element
			is = new FileInputStream(fileName);
			doc = docBuilder.parse(is);
			root = doc.getDocumentElement();
			currentnode=root.getParentNode();
			//Do something here


		} catch (IOException e) {
		} catch (SAXException e) {
		} catch (ParserConfigurationException e) {
		} finally {
			doc = null;
			docBuilder = null;
			docBuilderFactory = null;
		}

	}

	public void nodeGotoFirst(String path,String nodename,int index) throws TransformerException, IOException{
		//将某个节点连同其子节点放到最前面
		//String expression = "/users/item[1]";
		//Element node = (Element) selectSingleNode(expression, root);
		//node.getAttributeNode("id").setNodeValue("check");
		index++;
		String expression=path+"/"+nodename+"["+index+"]";
		Element child = (Element) selectSingleNode(expression, root);

		Element parent = (Element) selectSingleNode(path, root);
		parent.removeChild(child);
		parent.insertBefore(child, parent.getFirstChild());

		saveXml(fileName,root.getOwnerDocument());
	}
	public Node findNode(String nodeName,int index){
		NodeList nodeList = root.getElementsByTagName(nodeName);
		currentnode=nodeList.item(index);
		return currentnode;
	}

	public void addNode(String path,int type,String args) throws TransformerException, IOException{
		Element parent = (Element) selectSingleNode(path, root);
		//Element child = (Element) selectSingleNode(path+"/item[2]", root);
		//Element newnode = (Element)child.cloneNode(true);
		if(type==1){
			Element child=root.getOwnerDocument().createElement("item");
			//<item format="string" type="string" id="00000003" name="隆美尔" image="head2.png" grade="3.1"></item>
			child.setAttribute("format", "string");
			child.setAttribute("type", "string");
			child.setAttribute("id", "00000004");
			child.setAttribute("name", args);
			child.setAttribute("image", "head4.png");
			child.setAttribute("grade", "0");
			parent.appendChild(child);
		}
		if(type==2){
			Element child=root.getOwnerDocument().createElement("item");
			//<friend id="" name="" nickname="" image="" jyd="" time=""></item>
			child.setAttribute("id", "00000001");
			child.setAttribute("name", args);
			child.setAttribute("nickname", "null");
			child.setAttribute("image", "head4.png");
			child.setAttribute("jyd", "0");
			child.setAttribute("time", "0");
			parent.appendChild(child);
		}
		saveXml(fileName,root.getOwnerDocument());
	}
	public Node getNode(String path){
		Element node = (Element) selectSingleNode(path, root);
		return node;
	}
	public void insertNode(Node node){
		currentnode.insertBefore(node, currentnode.getFirstChild());
	}

	public void deleteNode(String path,String nodename,int index) throws TransformerException, IOException{
		index++;
		String expression=path+"/"+nodename+"["+index+"]";
		Element child = (Element) selectSingleNode(expression, root);

		Element parent = (Element) selectSingleNode(path, root);
		parent.removeChild(child);

		saveXml(fileName,root.getOwnerDocument());
	}

	public void updateNode(String nodeValue){
		currentnode.setNodeValue(nodeValue);
	}

	public String readAttr(String attrName,String path){
		Element node = (Element) selectSingleNode(path, root);
		return node.getAttribute(attrName);
	}

	public void addAttr(String attrName,String attrValue){
		Attr attr=doc.createAttribute(attrName);
		attr.setValue(attrValue);
		currentnode.getAttributes().setNamedItem(attr);
	}

	public void updateAttr(String path,String attrName,String attrValue) throws TransformerException, IOException{
		Element node = (Element) selectSingleNode(path, root);
		node.setAttribute(attrName, attrValue);
		saveXml(fileName,root.getOwnerDocument());
	}

	public void deleteAttr(String attrName){
		currentnode.getAttributes().removeNamedItem(attrName);

	}

	public String[] getAttrList(String attrName,String path){


		NodeList nodeList=selectNodes(path, root);
		int n=nodeList.getLength();
		String[] list=new String[n];
		for(int i=0;i<n;i++){
			list[i]=nodeList.item(i).getAttributes().getNamedItem(attrName).getNodeValue();
		}
		return list;

	}
	public float[] getAttrList1(String attrName,String path){

		NodeList nodeList=selectNodes(path, root);
		int n=nodeList.getLength();
		float[] list=new float[n];
		for(int i=0;i<n;i++){
			list[i]=Float.parseFloat(nodeList.item(i).getAttributes().getNamedItem(attrName).getNodeValue());
		}
		return list;

	}
	public int getcount(){
		return currentnode.getChildNodes().getLength();
	}
	public Node getnode(){
		return currentnode;
	}
	public static Node selectSingleNode(String express, Object source) {//查找节点，并返回第一个符合条件节点
		Node result=null;
		XPathFactory xpathFactory=XPathFactory.newInstance();
		XPath xpath=xpathFactory.newXPath();
		try {
			result=(Node) xpath.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static NodeList selectNodes(String express, Object source) {//查找节点，返回符合条件的节点集。
		NodeList result=null;
		XPathFactory xpathFactory=XPathFactory.newInstance();
		XPath xpath=xpathFactory.newXPath();
		try {
			result=(NodeList) xpath.evaluate(express, source, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void saveXml(String fileName, Document doc) throws TransformerException, IOException {//将Document输出到文件
		javax.xml.transform.Transformer tf;
		try {
			tf = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			OutputStream os=context.openFileOutput(fileName,0);
			tf.transform(new javax.xml.transform.dom.DOMSource(doc),new javax.xml.transform.stream.StreamResult(os));
			os.close();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
