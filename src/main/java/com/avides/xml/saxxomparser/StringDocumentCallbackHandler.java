package com.avides.xml.saxxomparser;

import nu.xom.Document;

public abstract class StringDocumentCallbackHandler implements DocumentCallbackHandler
{
    @Override
    public void process(Document document)
    {
        process(document.toXML());
    }
    
    public abstract void process(String xml);
}