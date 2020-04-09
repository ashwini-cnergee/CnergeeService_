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
 *  <Table>
      <ComplaintId>29</ComplaintId>
      <ComplaintName>BB 101 - Unable to Reach Gateway </ComplaintName>
      <ComplaintCategoryId>1</ComplaintCategoryId>
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

import com.cnergee.service.obj.ComplaintTypeObj;

public class ComplaintTypeParsing extends DefaultHandler {

	private ComplaintTypeObj complaintTypeObj;
	private static Map<String, ComplaintTypeObj> mapComplaintType;
	
	private boolean _inTable,_inComplaintId,_inComplaintName,_inComplaintCategoryId;
	
	public ComplaintTypeParsing(String xmlData){
		
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
		mapComplaintType = new HashMap<String, ComplaintTypeObj>();
		complaintTypeObj = new ComplaintTypeObj();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		setMapComplaintType(mapComplaintType);
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
		} else if (localName.equals("ComplaintId")) {
			_inComplaintId = true;
		}  else if (localName.equals("ComplaintName")) {
			_inComplaintName = true;
		} 
		// super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String chars = new String(ch, start, length);
		chars = chars.trim();
		
		if (_inComplaintCategoryId) {complaintTypeObj.setComplaintCategoryId(chars.toString());}
		else if(_inComplaintId){complaintTypeObj.setComplaintId(chars.toString());}
		else if(_inComplaintName){complaintTypeObj.setComplaintName(chars.toString());}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (localName.equals("Table")) {
			_inTable = false;
			mapComplaintType.put(complaintTypeObj.getComplaintCategoryId()+"~"+complaintTypeObj.getComplaintId(), complaintTypeObj);
			complaintTypeObj = new ComplaintTypeObj();
			
			
		} else if (localName.equals("ComplaintCategoryId")) {
			_inComplaintCategoryId = false;
		} else if (localName.equals("ComplaintId")) {
			_inComplaintId = false;
		}  else if (localName.equals("ComplaintName")) {
			_inComplaintName = false;
		} 
		
		// super.endElement(uri, localName, qName);

	}

	/**
	 * @return the mapComplaintType
	 */
	public static Map<String, ComplaintTypeObj> getMapComplaintType() {
		return mapComplaintType;
	}

	/**
	 * @param mapComplaintCategory the mapComplaintCategory to set
	 */
	public static void setMapComplaintType(
			Map<String, ComplaintTypeObj> mapComplaintCategory) {
		ComplaintTypeParsing.mapComplaintType = mapComplaintCategory;
	}



	
}
