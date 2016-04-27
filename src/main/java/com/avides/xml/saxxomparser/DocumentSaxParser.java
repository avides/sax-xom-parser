package com.avides.xml.saxxomparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

public class DocumentSaxParser extends DefaultHandler2
{
    private String elementTagName;
    private DocumentCallbackHandler callbackHandler;
    private Document document;
    private Element currentElement;

    public DocumentSaxParser(String elementTagName, DocumentCallbackHandler callback)
    {
        this.elementTagName = elementTagName;
        this.callbackHandler = callback;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (document == null && elementTagName.equals(qName))
        {
            Element rootElement = createElement(qName, attributes);
            document = new Document(rootElement);
        }
        else if (document != null && currentElement == null)
        {
            currentElement = createElement(qName, attributes);
            document.getRootElement().appendChild(currentElement);
        }
        else if (currentElement != null)
        {
            Element element = createElement(qName, attributes);
            currentElement.appendChild(element);
            currentElement = element;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (currentElement != null && currentElement.getParent() instanceof Element)
        {
            currentElement = (Element) currentElement.getParent();
        }

        if (elementTagName.equals(qName))
        {
            callbackHandler.process(document);
            document = null;
            currentElement = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (currentElement != null)
        {
            currentElement.appendChild(new String(ch, start, length));
        }
    }

    private Element createElement(String name, Attributes attributes)
    {
        Element element = new Element(name);

        for (int i = 0; i < attributes.getLength(); i++)
        {
            String key = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            element.addAttribute(new Attribute(key, value));
        }

        return element;
    }
}