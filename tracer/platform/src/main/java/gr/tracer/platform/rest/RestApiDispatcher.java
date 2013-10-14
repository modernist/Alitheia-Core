package gr.tracer.platform.rest;

import eu.sqooss.service.db.DBService;
import gr.tracer.platform.TracerPlatform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

/**
 * Ensures that there is an active db session before serving
 * TRACER REST API requests and that any active sessions are
 * committed upon servicing any request
 */
public class RestApiDispatcher extends HttpServletDispatcher {
	
	private static final long serialVersionUID = 5860816995265545332L;

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		
		DBService db = TracerPlatform.getInstance().getAlitheiaCoreService(DBService.class);
		if(db != null && !db.isDBSessionActive())
			db.startDBSession();
		
		/* Additional request filtering could be performed here, e.g. auth checks */
		super.service(req, res);
		
		if(db != null && db.isDBSessionActive())
			db.commitDBSession();
	}

}
