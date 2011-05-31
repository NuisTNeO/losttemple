package cn.com.hotmaze.util;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//����XML�������Ҫ��API
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

	// ��ʼ�������ļ�·��
	public XMLdoc(String path) {
		this.config_document_path = path;
		System.out.println(config_document_path);
	}

	// ���ļ�
	public boolean openXML() {
		boolean returnValue = false;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File(config_document_path));
			returnValue = true;
		} catch (DocumentException e) {
			System.out.println("��XML�ļ�ʧ��,�����ļ�·���Ƿ���ȷ!");
		}

		return returnValue;
	}

	// �ر��ļ�
	public void closeXML() {
		document = null;
	}

	// ��ȡXML�ĸ����
	public Element getRootElement() {
		return document.getRootElement();
	}

	/**
	 * ���ĳ���ڵ��ֵ
	 * 
	 * @param nodeName
	 *            �ڵ�����
	 */
	public String getElementValue(String nodeName) {
		try {
			Node node = document.selectSingleNode("//" + nodeName);
			return node.getText();
		} catch (Exception e1) {
			System.out
					.println("getElementValue() Exception��" + e1.getMessage());
			return null;
		}
	}

	/**
	 * ���ĳ���ڵ���ӽڵ��ֵ
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
					.println("getElementValue() Exception��" + e1.getMessage());
			return null;
		}
	}

	/**
	 * ��ȡĳ���ڵ��ĳ�����Ե�ֵ ����nodeName������ �����/�ӽ�� ���ɷ�ΧԽС����Խ��
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
	 * ��ȡ ����ֵΪ�ض��ַ����Ľڵ� ע��������뱣֤ÿ����㶼�����˸�����
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
	 * ��ȡ ����ֵΪ�ض��ַ����Ľڵ� �������� ����ֵΪ�ض��ַ����ĸ���� ע��������뱣֤ÿ����㶼�����˸�����
	 * parentName���������·�������·�� childName�ӽ�������
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