package com.avides.xml.saxxomparser;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DocumentHelper
{
    private DocumentHelper()
    {

    }

    public static void parse(final InputStream inputStream, final String elementTagName, final DocumentCallbackHandler callbackHandler, final boolean withValidation) throws Exception
    {
        DocumentSaxParser handler = new DocumentSaxParser(elementTagName, callbackHandler);
        SAXParser parser = buildSAXParser(withValidation);
        parser.parse(inputStream, handler);
    }

    private static SAXParser buildSAXParser(final boolean withValidation) throws Exception
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        if (!withValidation)
        {
            saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        }

        return saxParserFactory.newSAXParser();
    }
}