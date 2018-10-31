package net.readify.olingo.demo.web;

import net.readify.olingo.demo.data.InMemoryStorage;
import net.readify.olingo.demo.service.DemoEdmProvider;
import net.readify.olingo.demo.service.DemoEntityCollectionProcessor;
import net.readify.olingo.demo.service.DemoEntityProcessor;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class ODataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ODataServlet.class);

    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException {
        try {
            HttpSession session = req.getSession(true);
            InMemoryStorage storage = (InMemoryStorage) session.getAttribute(InMemoryStorage.class.getName());
            if (storage == null) {
                storage = new InMemoryStorage();
                session.setAttribute(InMemoryStorage.class.getName(), storage);
            }

            OData odata = OData.newInstance();
            ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<>());
            ODataHttpHandler handler = odata.createHandler(edm);
            handler.register(new DemoEntityCollectionProcessor(storage));
            handler.register(new DemoEntityProcessor(storage));

            handler.process(req, resp);
        } catch (RuntimeException e) {
            LOG.error("Server Error occurred in ODataServlet", e);
            throw new ServletException(e);
        }
    }
}
