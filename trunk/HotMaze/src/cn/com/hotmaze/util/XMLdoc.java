package cn.com.hotmaze.util;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//操纵XML所需的重要的API
import org.dom4j.*;
import org.dom4j.io.*;

/**
 * 
 * @author liuyang
 * @date 2008-10-14 0:50
 * @version 1.0
 * 
 */
public class XMLdoc {
	private String config_document_path;
	Document document = null;

	// 初始化配置文件路径
	public XMLdoc(String path) {
		this.config_document_path = path;
		System.out.println(config_document_path);
	}

	// 打开文件
	public boolean openXML() {
		boolean returnValue = false;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File(config_document_path));
			returnValue = true;
		} catch (DocumentException e) {
			System.out.println("打开XML文件失败,请检查文件路径是否正确!");
		}

		return returnValue;
	}

	// 关闭文件
	public void closeXML() {
		document = null;
	}

	// 获取XML的根结点
	public Element getRootElement() {
		return document.getRootElement();
	}

	/**
	 * 获得某个节点的值
	 * 
	 * @param nodeName
	 *            节点名称
	 */
	public String getElementValue(String nodeName) {
		try {
			Node node = document.selectSingleNode("//" + nodeName);
			return node.getText();
		} catch (Exception e1) {
			System.out
					.println("getElementValue() Exception：" + e1.getMessage());
			return null;
		}
	}

	/**
	 * 获得某个节点的子节点的值
	 * 
	 * @param nodeName
	 * @param childNodeName
	 * @return
	 */
	public String getElementValue(String nodeName, String childNodeName) {
		try {
			Node node = this.document.selectSingleNode("//" + nodeName + "/"
					+ childNodeName);
			return node.getText();
		} catch (Exception e1) {
			System.out
					.println("getElementValue() Exception：" + e1.getMessage());
			return null;
		}
	}

	/**
	 * 获取某个节点的某个属性的值 这里nodeName可以是 父结点/子结点 构成范围越小性能越高
	 */
	@SuppressWarnings("unchecked")
	public List getElementOfAttributeValue(String nodeName, String attributeName) {
		List eleList = document.selectNodes("//" + nodeName + "/@"
				+ attributeName);
		Iterator iter = eleList.iterator();
		List returnll = new LinkedList();

		while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			if (attribute.getValue() != null) {
				returnll.add(attribute.getValue());

			}
		}

		return returnll;
	}

	/**
	 * 获取 属性值为特定字符串的节点 注意这里必须保证每个结点都设置了该属性
	 * 
	 * @param args
	 */
	public List<Element> getElementByValue(String nodeName,
			String attributeName, String value) {
		List eleList = document
				.selectNodes("/struts-config/action-mappings/action/forward");
		List returnll = new LinkedList();

		for (int i = 0; i < eleList.size(); i++) {
			Element element = (Element) eleList.get(i);

			if (element.attribute(attributeName).getValue() != null
					&& value
							.equals(element.attribute(attributeName).getValue())) {
				returnll.add(element);
			}
		}

		return returnll;
	}

	/**
	 * 获取 属性值为特定字符串的节点 且隶属于 属性值为特定字符串的父结点 注意这里必须保证每个结点都设置了该属性
	 * parentName父类的完整路径或相对路径 childName子结点的名字
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getChildElementByValue(String parentName,
			String parentAttributeName, String parentValue, String childName,
			String childAttributeName, String childValue) {
		List eleList = document.selectNodes(parentName);
		List returnll = new LinkedList();

		for (int i = 0; i < eleList.size(); i++) {
			Element element = (Element) eleList.get(i);
			if (element.attribute(parentAttributeName).getValue() != null
					&& parentValue.equals(element
							.attribute(parentAttributeName).getValue())) {
				List childElements = element.elements();
				for (int j = 0; j < childElements.size(); j++) {
					if (((Element) childElements.get(j)).attribute(
							childAttributeName).getValue() != null
							&& childValue.equals(((Element) childElements
									.get(j)).attribute(childAttributeName)
									.getValue())) {
						returnll.add(childElements.get(j));
					}
				}
			}
		}

		return returnll;
	}
}