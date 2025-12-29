package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to track event visits for statistics
 * Intercepts requests to event detail pages and registers the visit
 */
@WebFilter(filterName = "EventVisitFilter", urlPatterns = {"/eventos/detail*", "/EventosServlet*"})
public class EventVisitFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EventVisitFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            
            // Get event name from parameter
            String nombreEvento = httpRequest.getParameter("nombre");
            
            if (nombreEvento != null && !nombreEvento.isEmpty()) {
                // Register visit through Web Service
                try {
                    // This will be called through WS client in actual implementation
                    // For now, we just log it
                    System.out.println("Visit registered for event: " + nombreEvento);
                    
                    // TODO: Call WS method registrarVisita(nombreEvento)
                    // This will be implemented when WS client is set up
                    
                } catch (Exception e) {
                    System.err.println("Error registering event visit: " + e.getMessage());
                }
            }
        }
        
        // Continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("EventVisitFilter destroyed");
    }
}

