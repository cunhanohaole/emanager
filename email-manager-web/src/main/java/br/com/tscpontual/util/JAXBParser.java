package br.com.tscpontual.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBParser <T> {

	public String objectToXml(T object, Class<T> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(object, os);
		return new String(os.toByteArray());
	}
	
	@SuppressWarnings("unchecked")
	public T xmlToObject(String xml, Class<T> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return (T) jaxbUnmarshaller.unmarshal(reader);
	}
	
}