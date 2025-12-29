<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("pageTitle", "Inicio - EventosUY Móvil");
    request.setAttribute("extraCSS", 
        "<style>" +
        "body { background-color: #f8f9fa; }" +
        ".hero-section { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 3rem 0; margin-bottom: 2rem; }" +
        ".feature-card { transition: transform 0.3s; height: 100%; }" +
        ".feature-card:hover { transform: translateY(-5px); }" +
        "</style>"
    );
%>
<jsp:include page="template/head.jsp"/>
<jsp:include page="template/navbar.jsp"/>

<!-- Hero Section -->
<br>
<div class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 fw-bold">Bienvenido a EventosUY</h1>
        <p class="lead">Gestiona tus eventos y registros desde tu dispositivo móvil</p>
    </div>
</div>

<!-- Features Grid -->


<jsp:include page="template/footer.jsp"/>
