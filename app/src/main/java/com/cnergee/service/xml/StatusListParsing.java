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
      <StatusId>-1</StatusId>
      <Status>UserUpdate</Status>
      <ForProcess>Complaint</ForProcess>
      <IsCompleted>false</IsCompleted>
      <IsInternal>true</IsInternal>
      <IsLost>false</IsLost>
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

import com.cnergee.service.obj.StatusListObj;

public class StatusListParsing extends DefaultHandler {

	private StatusListObj statusListObj;
	private static Map<String, StatusListObj> mapStatusList;
	
	private boolean _inTable,_inStatusId,_inStatus,_inForProcess,_inIsCompleted,_inIsInternal,_inIsLost;
	
	public StatusListParsing(String xmlData){
		
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
		mapStatusList = new HashMap<String, StatusListObj>();
		statusListObj = new StatusListObj();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		setMapStatusList(mapStatusList);
	}

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// Log.i("tag start",localName);

		if (localName.equals("Table")) {
			_inTable = true;
		} else if (localName.equals("StatusId")) {
			_inStatusId = true;
		} else if (localName.equals("Status")) {
			_inStatus = true;
		} else if (localName.equals("ForProcess")) {
			_inForProcess = true;
		} else if (localName.equals("IsCompleted")) {
			_inIsCompleted = true;
		} else if (localName.equals("IsInternal")) {
			_inIsInternal = true;
		} else if (localName.equals("IsLost")) {
			_inIsLost = true;
		}
		// super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		// Log.i("tag start",new String(ch,start,length));
		// super.characters(ch, start, length);
		String chars = new String(ch, start, length);
		chars = chars.trim();
		
		if (_inStatusId) {statusListObj.setStatusId(chars.toString());}
		else if(_inStatus){statusListObj.setStatus(chars.toString());}
		else if(_inForProcess){statusListObj.setForProcess(chars.toString());}
		else if(_inIsCompleted){statusListObj.setCompleted(Boolean.parseBoolean((chars.toString())));}
		else if(_inIsInternal){statusListObj.setInternal(Boolean.parseBoolean((chars.toString())));}
		else if(_inIsLost){statusListObj.setLost(Boolean.parseBoolean((chars.toString())));}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (localName.equals("Table")) {
			_inTable = false;
			mapStatusList.put(statusListObj.getStatusId(), statusListObj);
			statusListObj = new StatusListObj();
			
		} else if (localName.equals("StatusId")) {
			_inStatusId = false;
		} else if (localName.equals("Status")) {
			_inStatus = false;
		} else if (localName.equals("ForProcess")) {
			_inForProcess = false;
		} else if (localName.equals("IsCompleted")) {
			_inIsCompleted = false;
		} else if (localName.equals("IsInternal")) {
			_inIsInternal = false;
		} else if (localName.equals("IsLost")) {
			_inIsLost = false;
		}


		// super.endElement(uri, localName, qName);

	}

	/**
	 * @return the mapStatusList
	 */
	public static Map<String, StatusListObj> getMapStatusList() {
		return mapStatusList;
	}

	/**
	 * @param mapStatusList the mapStatusList to set
	 */
	public static void setMapStatusList(Map<String, StatusListObj> mapStatusList) {
		StatusListParsing.mapStatusList = mapStatusList;
	}

	
}
