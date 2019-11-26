package com.etoc.ioc;

import java.lang.reflect.Field;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClassPathXml {
	private String xmlPath;

	public ClassPathXml(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public Object getBean(String beanId) throws Exception {
		Object newInstance = null;
		SAXReader reader = new SAXReader();
		Document document = reader.read(this.getClass().getClassLoader().getResourceAsStream(xmlPath));
		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		for (Element e : elements) {
			String beanId1 = e.attributeValue("id");
			if (!beanId.equals(beanId1)) {
				continue;
			}
			String attribute = e.attributeValue("class");
			Class<?> forName = Class.forName(attribute);
			newInstance = forName.newInstance();
			List<Element> elements2 = e.elements();
			for (Element ele : elements2) {
				String name = ele.attributeValue("name");
				String value = ele.attributeValue("value");
				Field declaredFields = forName.getDeclaredField(name);
				declaredFields.setAccessible(true);
				declaredFields.set(newInstance, value);
			}
		}
		return newInstance;
	}
}
