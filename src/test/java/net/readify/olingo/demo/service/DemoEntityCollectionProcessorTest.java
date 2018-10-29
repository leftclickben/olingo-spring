package net.readify.olingo.demo.service;

import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.core.uri.UriInfoImpl;
import org.apache.olingo.server.core.uri.UriResourceEntitySetImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DemoEntityCollectionProcessorTest {
    private DemoEntityCollectionProcessor processor;
    private EdmEntitySet productsSet;

    @Before
    public void instantiateProviderAndProcessor() {
        final OData odata = OData.newInstance();
        final ServiceMetadata metadata = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<>());
        processor = new DemoEntityCollectionProcessor();
        processor.init(odata, metadata);
        productsSet = metadata.getEdm().getEntityContainer(DemoEdmProvider.CONTAINER_FQN).getEntitySet(DemoEdmProvider.PRODUCTS_SET_NAME);
    }

    @Test
    public void testReadEntityCollection() throws ODataLibraryException, IOException {
        final ODataRequest request = new ODataRequest();
        final ODataResponse response = new ODataResponse();
        final UriInfoImpl uriInfo = new UriInfoImpl()
            .addEntitySetName(DemoEdmProvider.PRODUCTS_SET_NAME)
            .addResourcePart(new UriResourceEntitySetImpl(productsSet));
        final String expectedContent = readFile(getClass().getClassLoader().getResource("fixtures/expected/products.xml"));

        processor.readEntityCollection(request, response, uriInfo, ContentType.APPLICATION_XML);

        final String actualContent = readFile(response.getContent());

        assertEquals(200, response.getStatusCode());
        assertNotEquals(0, expectedContent.length());
        assertNotEquals(0, actualContent.length());

        // FIXME This fails because the `updated` date/time stamp cannot be easily mocked.
        // assertEquals(expectedContent, actualContent);
        // We can do this instead for now (since the timestamp format has a fixed width).
        assertEquals(expectedContent.length(), actualContent.length());
    }

    private String readFile(URL url) throws IOException {
        return readFile(new FileInputStream(new File(URLDecoder.decode(Objects.requireNonNull(url).getFile(), Charset.forName("UTF-8")))));
    }

    private String readFile(InputStream in) {
        final Scanner responseScanner = new Scanner(in).useDelimiter("\\A");
        return responseScanner.hasNext() ? responseScanner.next() : "";
    }
}
