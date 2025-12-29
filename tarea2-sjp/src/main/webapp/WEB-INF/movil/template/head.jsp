<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Obtener título de la página (parametrizable)
    String pageTitle = (String) request.getAttribute("pageTitle");
    if (pageTitle == null) pageTitle = "EventosUY Móvil";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= pageTitle %></title>
    
    <!-- CSS Custom - Barra de Navegación -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movil-navbar.css">
    <!-- CSS Custom - Estilos de la Aplicación -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movil-app.css">
    
    <!-- CSS adicional específico de la página -->
    <% if (request.getAttribute("extraCSS") != null) { %>
        <%= request.getAttribute("extraCSS") %>
    <% } %>
</head>
<body>
