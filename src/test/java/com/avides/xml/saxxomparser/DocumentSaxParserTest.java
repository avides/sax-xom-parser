package com.avides.xml.saxxomparser;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.itelg.xpath.helper.XPathHelper;

import nu.xom.Document;
import nu.xom.Element;

public class DocumentSaxParserTest
{
    private TestDocumentCallbackHandler callbackHandler = new TestDocumentCallbackHandler();

    @Test
    public void testParse()
    {
        try (InputStream inputStream = new FileInputStream("src/test/resources/test.xml"))
        {
            DocumentHelper.parse(inputStream, "epic", callbackHandler);
            Assert.assertEquals(2, callbackHandler.documents);
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    private class TestDocumentCallbackHandler implements DocumentCallbackHandler
    {
        int documents = 0;

        @Override
        public void process(Document document)
        {
            documents++;
            Element root = document.getRootElement();

            if (documents == 1)
            {
                Assert.assertEquals("E123", XPathHelper.getString("@id", root));
                Assert.assertEquals("Write sax parser", XPathHelper.getString("name", root));
                Assert.assertEquals(1, XPathHelper.getNodeList("tickets/ticket", root).size());
                Assert.assertEquals(2, XPathHelper.getNodeList("tickets/ticket/subtask", root).size());
                Assert.assertEquals("S347", XPathHelper.getString("tickets/ticket/subtask[@id='S347']/@id", root));
                Assert.assertEquals("Create travis build too!", XPathHelper.getString("tickets/ticket/subtask[@id='S348']/description", root));
                Assert.assertEquals("S348", XPathHelper.getString("tickets/ticket/subtask[@id='S348']/@id", root));
            }
            else if (documents == 2)
            {
                Assert.assertEquals("E231", XPathHelper.getString("@id", root));
                Assert.assertEquals("Ticket planning", XPathHelper.getString("name", root));
            }
        }
    }
}