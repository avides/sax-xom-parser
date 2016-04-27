package com.avides.xml.saxxomparser;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DocumentHelper
{
    public static void parse(InputStream inputStream, String elementTagName, DocumentCallbackHandler callbackHandler) throws Exception
    {
        DocumentSaxParser handler = new DocumentSaxParser(elementTagName, callbackHandler);
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(inputStream, handler);
    }
}