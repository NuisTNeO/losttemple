/**
 * 
 */
package cn.com.hotmaze.util;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author ibm
 * 
 */
public class XmlIoUtil {

	public static Properties readXML(String fileName) {

		Properties props = new Properties();
		try {
			SAXReader reader = new SAXReader();
			File file = new File(fileName);
			Document dom = reader.read(file);
			Element root = dom.getRootElement();
			Iterator itconfigs = root.elementIterator();
			while (itconfigs.hasNext()) {
				Element elDbserver = (Element) itconfigs.next();
				props.put(elDbserver.getName(), elDbserver.getTextTrim());
			}
		} catch (DocumentException e) {
			System.out.println("读取配置文件 " + fileName + " 出错！");
			e.printStackTrace();
		}
		return props;
	}
}
