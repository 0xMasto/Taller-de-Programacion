<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!-- Scripts adicionales específicos de la página -->
    <% if (request.getAttribute("extraJS") != null) { %>
        <%= request.getAttribute("extraJS") %>
    <% } %>
</body>
</html>
