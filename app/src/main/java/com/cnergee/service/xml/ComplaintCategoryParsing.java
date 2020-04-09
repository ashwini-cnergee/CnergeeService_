/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  05 Mar. 2013 
 *
 * Version 1.1
 *
 */
package com.cnergee.service.xml;

/*
 *   <Table>
      <ComplaintCategoryId>1</ComplaintCategoryId>
      <ComplaintCategory>Connectivity</ComplaintCategory>
    </Table>
 * 
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.cnergee.service.obj.ComplaintCategoryObj;

public class ComplaintCategoryParsing extends DefaultHandler {

	private ComplaintCategoryObj complaintCategoryObj;
	private static Map<String, ComplaintCategoryObj> mapComplaintCategory;
	
	private boolean _inTable,_inComplaintCategoryId,_inComplaintCategory;
	
	public ComplaintCategoryParsing(String xmlData){
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = null;
		XMLReader xr = null;
		try {
			sp = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		try {
			xr = sp.getXMLReader();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		try {
			xr.setContentHandler(this);
			xr.parse(new InputSource(new ByteArrayInputStream(xmlData
					.getBytes("UTF8"))));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		mapComplaintCategory = new HashMap<String, ComplaintCategoryObj>();
		complaintCategoryObj = new ComplaintCategoryObj();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		setMapComplaintCategory(mapComplaintCategory);
	}

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// Log.i("tag start",localName);

		if (localName.equals("Table")) {
			_inTable = true;
		} else if (localName.equals("ComplaintCategoryId")) {
			_inComplaintCategoryId = true;
		} else if (localName.equals("ComplaintCategory")) {
			_inComplaintCategory = true;
		} 
		// super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String chars = new String(ch, start, length);
		chars = chars.trim();
		
		if (_inComplaintCategoryId) {complaintCategoryObj.setComplaintCategoryId(chars.toString());}
		else if(_inComplaintCategory){complaintCategoryObj.setComplaintCategory(chars.toString());}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (localName.equals("Table")) {
			_inTable = false;
			mapComplaintCategory.put(complaintCategoryObj.getComplaintCategoryId(), complaintCategoryObj);
			complaintCategoryObj = new ComplaintCategoryObj();
			
			
		} else if (localName.equals("ComplaintCategoryId")) {
			_inComplaintCategoryId = false;
		} else if (localName.equals("ComplaintCategory")) {
			_inComplaintCategory = false;
		} 
		
		// super.endElement(uri, localName, qName);

	}

	/**
	 * @return the mapComplaintCategory
	 */
	public static Map<String, ComplaintCategoryObj> getMapComplaintCategory() {
		return mapComplaintCategory;
	}

	/**
	 * @param mapComplaintCategory the mapComplaintCategory to set
	 */
	public static void setMapComplaintCategory(
			Map<String, ComplaintCategoryObj> mapComplaintCategory) {
		ComplaintCategoryParsing.mapComplaintCategory = mapComplaintCategory;
	}



	
}
