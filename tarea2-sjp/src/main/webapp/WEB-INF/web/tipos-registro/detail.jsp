<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="${tipoRegistro.nombre} - eventos.uy" />
    </jsp:include>
</head>
<body>

<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<c:set var="tipoRegistro" value="${requestScope.tipoRegistro}" />
<c:set var="edicion" value="${requestScope.edicion}" />
<c:set var="evento" value="${requestScope.evento}" />

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

    <!-- Main Content -->
    <div class="content-area">
        <div class="content-with-sidebar">
            <!-- Main content -->
            <div class="main-content">
                <div class="page-content">
                    <h1 class="page-title">Detalle del Tipo de Registro</h1>

                    <c:if test="${not empty requestScope.success}">
                        <div class="alert alert-success">
                            <c:out value="${requestScope.success}" />
                        </div>
                    </c:if>

                    <!-- Registration Type Header -->
                    <div class="edition-detail-container">
                        <div class="edition-main">
                            <div class="event-image" style="background-color: #28a745; color: white; display: flex; align-items: center; justify-content: center; font-size: 24px; font-weight: bold; min-width: 120px; min-height: 120px;">
                                <c:choose>
                                    <c:when test="${not empty tipoRegistro.nombre && tipoRegistro.nombre.length() >= 3}">
                                        ${tipoRegistro.nombre.substring(0, 3).toUpperCase()}
                                    </c:when>
                                    <c:otherwise>
                                        ${tipoRegistro.nombre.toUpperCase()}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="edition-info">
                                <h2><c:out value="${tipoRegistro.nombre}" /></h2>
                                <p><strong>Descripción:</strong> <c:out value="${tipoRegistro.descripcion}" /></p>
                                <p><strong>Costo:</strong> 
                                    <c:choose>
                                        <c:when test="${tipoRegistro.costo == 0}">
                                            Gratis
                                        </c:when>
                                        <c:otherwise>
                                            $<fmt:formatNumber value="${tipoRegistro.costo}" pattern="#,##0.00" />
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p><strong>Cupo Total:</strong> <c:out value="${tipoRegistro.cupo}" /> personas</p>
                                <p><strong>Cupo Disponible:</strong> 
                                    <span style="color: ${tipoRegistro.cupoDisponible > 0 ? '#28a745' : '#dc3545'}; font-weight: bold;">
                                        <c:out value="${tipoRegistro.cupoDisponible}" /> personas
                                    </span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Right Sidebar -->
            <div class="right-sidebar">
                <!-- Event Section -->
                <c:if test="${not empty evento}">
                    <div class="sidebar-card">
                        <h3 class="sidebar-card-title">Evento</h3>
                        <div class="sidebar-card-content">
                            <div class="event-logo">
                                <c:choose>
                                    <c:when test="${not empty evento.sigla}">
                                        <span><c:out value="${evento.sigla}" /></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span><c:out value="${evento.nombre.substring(0, Math.min(10, evento.nombre.length()))}" /></span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:url var="eventoUrl" value="/eventos">
                                <c:param name="id" value="${evento.nombre}" />
                            </c:url>
                            <a href="${eventoUrl}" class="event-link">
                                <c:out value="${evento.nombre}" />
                            </a>
                        </div>
                    </div>
                </c:if>

                <!-- Edition Section -->
                <c:if test="${not empty edicion}">
                    <div class="sidebar-card">
                        <h3 class="sidebar-card-title">Edición</h3>
                        <div class="sidebar-card-content">
                            <div class="edition-logo">
                                <c:choose>
                                    <c:when test="${not empty edicion.sigla}">
                                        <span><c:out value="${edicion.sigla}" /></span>
                                    </c:when>
                                    <c:when test="${not empty edicion.fechaInicio}">
                                        <span>${edicion.fechaInicio.year}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>ED</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:url var="edicionUrl" value="/ediciones">
                                <c:param name="id" value="${edicion.nombre}" />
                                <c:param name="evento" value="${evento.nombre}" />
                            </c:url>
                            <a href="${edicionUrl}" class="edition-link">
                                <c:out value="${edicion.nombre}" />
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>

