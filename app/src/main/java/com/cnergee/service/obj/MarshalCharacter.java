/*
 *
 * 
 * Date of code generation:  22 Dec. 2012
 *
 * Version 1.1
 *
 */
package com.cnergee.service.obj;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/*
 * Used to marshal Doubles - crucial to serialization for SOAP
 */
public class MarshalCharacter implements Marshal {

	@Override
	public Object readInstance(XmlPullParser parser, String namespace,
			String name, PropertyInfo expected) throws IOException,
			XmlPullParserException {
		return Character.valueOf(parser.nextText().charAt(0));
	}

	@Override
	public void register(SoapSerializationEnvelope cm) {
		cm.addMapping(cm.xsd, "char", Character.class, this);
	}

	@Override
	public void writeInstance(XmlSerializer writer, Object obj)
			throws IOException {
		writer.text(obj.toString());
	}
}
