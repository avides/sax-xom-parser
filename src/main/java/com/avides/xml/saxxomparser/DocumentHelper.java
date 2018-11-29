package com.avides.xml.saxxomparser;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DocumentHelper
{
    private DocumentHelper()
    {

    }

    public static void parse(final InputStream inputStream, final String elementTagName, final DocumentCallbackHandler callbackHandler) throws Exception
    {
        buildAndParse(inputStream, elementTagName, callbackHandler, true);
    }

    public static void parseWithoutValidation(final InputStream inputStream, final String elementTagName, final DocumentCallbackHandler callbackHandler)
            throws Exception
    {
        buildAndParse(inputStream, elementTagName, callbackHandler, false);
    }

    private static void buildAndParse(final InputStream inputStream, final String elementTagName, final DocumentCallbackHandler callbackHandler,
            final boolean withValidation) throws Exception
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        if (!withValidation)
        {
            saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        }

        DocumentSaxParser handler = new DocumentSaxParser(elementTagName, callbackHandler);
        SAXParser parser = saxParserFactory.newSAXParser();
        parser.parse(inputStream, handler);
    }
}
