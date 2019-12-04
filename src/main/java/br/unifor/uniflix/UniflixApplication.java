package br.unifor.uniflix;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class UniflixApplication {

	public static void main(String[] args) {
		Server server = new Server(8081);
		ServletContextHandler ctx =
				new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		ctx.setContextPath("/");
		server.setHandler(ctx);
		ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/api/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", "br.unifor.uniflix");
		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			Logger.getLogger(UniflixApplication.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			server.destroy();
		}
	}
}
