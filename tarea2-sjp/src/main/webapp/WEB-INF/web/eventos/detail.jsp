<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ws.WSTypeConverter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="${evento.nombre} - eventos.uy" />
    </jsp:include>
</head>
<body>

<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

    <!-- Main Content -->
    <div class="content-area">
        <div class="page-content">
            <h1 class="page-title">Detalle del Evento</h1>

            <c:if test="${not empty requestScope.success}">
                <div class="alert alert-success">
                    <c:out value="${requestScope.success}" />
                </div>
            </c:if>

            <div class="event-detail-container">
                <div class="event-main">
                    <div class="event-image">
                        <c:choose>
                            <c:when test="${not empty evento.imagen && evento.imagen != '-'}">
                                <img src="${pageContext.request.contextPath}/media/images/${evento.imagen}" 
                                     alt="${evento.nombre}" 
                                     style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
                            </c:when>
                            <c:otherwise>
                                <span style="color: white; font-size: 15px; font-weight: bold;">
                                    ${evento.sigla} 
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="event-info">
                        <h2><c:out value="${evento.nombre}" /></h2>
                        <p><strong>Descripción:</strong> <c:out value="${evento.descripcion}" /></p>
                        <p><strong>Sigla:</strong> <c:out value="${evento.sigla}" /></p>
                        <p>
                            <strong>Categorías:</strong>
                            <c:choose>
                                <c:when test="${not empty evento.categorias}">
                                    <c:forEach var="categoria" items="${evento.categorias}" varStatus="loop">
                                        <c:out value="${categoria.nombre}" />
                                        <c:if test="${!loop.last}">, </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    No especificadas
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <c:if test="${not empty evento.fechaAlta}">
                            <p><strong>Fecha Alta:</strong> <c:out value="${evento.fechaAlta}" /></p>
                        </c:if>
                        <p>
                            <strong>Estado:</strong> 
                            <c:choose>
                                <c:when test="${evento.finalizado}">
                                    <span style="color: #dc3545; font-weight: bold;">Finalizado</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #28a745; font-weight: bold;">Activo</span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p><strong>Visitas:</strong> <c:out value="${evento.visitas}" /></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Editions Sidebar -->
    <div class="editions-sidebar">
        <div class="editions-section">
            <div class="editions-header">Ediciones</div>
            <div class="editions-list">
                <c:if test="${empty ediciones}">
                    <div class="edition-item" style="justify-content: center; text-align: center;">
                        <span style="color: #666;">No hay ediciones disponibles para este evento.</span>
                    </div>
                </c:if>
                <c:forEach var="edicion" items="${ediciones}">
                    <c:url var="edicionUrl" value="/ediciones">
                        <c:param name="id" value="${edicion.nombre}" />
                        <c:param name="evento" value="${evento.nombre}" />
                    </c:url>
                    <a href="${edicionUrl}" class="edition-link">
                        <div class="edition-item">
                            <div class="edition-image">
                                <c:choose>
                                    <c:when test="${not empty edicion.imagen && edicion.imagen != '-'}">
                                        <img src="${pageContext.request.contextPath}/media/images/${edicion.imagen}" 
                                             alt="${edicion.nombre}" 
                                             style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: white; font-size: 15px; font-weight: bold;">
                                            ${edicion.sigla}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="edition-content">
                                <div class="edition-name"><c:out value="${edicion.nombre}" /></div>
                                <small style="color: #666;">
                                    <c:out value="${edicion.ciudad}" />, <c:out value="${edicion.pais}" /> |
                                    <c:out value="${edicion.fechaInicio}" /> - <c:out value="${edicion.fechaFin}" />
                                    <%
                                        publicar.ws.client.EdicionDTO edicionActual = (publicar.ws.client.EdicionDTO) pageContext.getAttribute("edicion");
                                        String estadoDisplay = "Sin definir";
                                        if (edicionActual != null) {
                                            if (WSTypeConverter.esActiva(edicionActual)) {
                                                estadoDisplay = "En curso";
                                            } else if (WSTypeConverter.esFutura(edicionActual)) {
                                                estadoDisplay = "Próxima";
                                            } else if (WSTypeConverter.esPasada(edicionActual)) {
                                                estadoDisplay = "Finalizada";
                                            }
                                        }
                                    %>
                                    | Estado: <%= estadoDisplay %>
                                </small>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
