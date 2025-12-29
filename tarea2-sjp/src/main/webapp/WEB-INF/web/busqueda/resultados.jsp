<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Resultados de Búsqueda - eventos.uy" />
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
            <h1 class="page-title">Resultados de Búsqueda</h1>
            
            <c:if test="${not empty requestScope.query}">
                <p style="color: #666; margin-bottom: 20px;">
                    Mostrando resultados para: <strong>"<c:out value="${requestScope.query}" />"</strong>
                </p>
            </c:if>
            
            <div class="search-results-info">
                <div>
                    <strong>${requestScope.totalResultados}</strong> resultado(s) encontrado(s)
                </div>
                <div>
                    <form method="GET" action="${pageContext.request.contextPath}/busqueda" style="display: inline-flex; gap: 10px; align-items: center;">
                        <input type="hidden" name="q" value="${requestScope.query}">
                        <label for="ordenar" style="margin: 0; color: #666; font-weight: 500;">Ordenar:</label>
                        <select name="ordenar" id="ordenar" class="form-control" style="width: auto; padding: 8px 12px;" onchange="this.form.submit()">
                            <option value="fecha-desc" ${requestScope.ordenar eq 'fecha-desc' ? 'selected' : ''}>
                                Por Fecha (Más reciente primero)
                            </option>
                            <option value="alfa-asc" ${requestScope.ordenar eq 'alfa-asc' ? 'selected' : ''}>
                                Alfabéticamente (A-Z)
                            </option>
                            <option value="alfa-desc" ${requestScope.ordenar eq 'alfa-desc' ? 'selected' : ''}>
                                Alfabéticamente (Z-A)
                            </option>
                        </select>
                    </form>
                </div>
            </div>
            
            <!-- Eventos Section -->
            <c:if test="${not empty requestScope.eventos}">
                <h2 style="margin-top: 30px; margin-bottom: 15px;">
                    <i class="bi bi-calendar-event"></i> Eventos (${requestScope.eventos.size()})
                </h2>
                <ul class="events-list">
                    <c:forEach var="evento" items="${requestScope.eventos}">
                        <a href="${pageContext.request.contextPath}/eventos?id=${evento.nombre}" style="color: #333; text-decoration: none;">
                            <li class="event-card">
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
                                <div class="event-content">
                                    <h3>${evento.nombre}</h3>
                                    <p><c:out value="${evento.descripcion}" /></p>
                                    <small style="color: #999;">
                                        <c:if test="${not empty evento.categorias}">
                                            Categorías: 
                                            <c:forEach items="${evento.categorias}" var="cat" varStatus="status">
                                                ${cat.nombre}<c:if test="${!status.last}">, </c:if>
                                            </c:forEach>
                                            <c:if test="${not empty evento.fechaAlta}"> | </c:if>
                                        </c:if>
                                        <c:if test="${not empty evento.fechaAlta}">
                                            Agregado: ${evento.fechaAlta}
                                        </c:if>
                                    </small>
                                </div>
                            </li>
                        </a>
                    </c:forEach>
                </ul>
            </c:if>
            
            <!-- Ediciones Section -->
            <c:if test="${not empty requestScope.ediciones}">
                <h2 style="margin-top: 40px; margin-bottom: 15px;">
                    <i class="bi bi-calendar-check"></i> Ediciones (${requestScope.ediciones.size()})
                </h2>
                <ul class="events-list">
                    <c:forEach var="edicion" items="${requestScope.ediciones}">
                        <a href="${pageContext.request.contextPath}/ediciones?id=${edicion.nombre}" style="color: #333; text-decoration: none;">
                            <li class="event-card">
                                <div class="event-image">
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
                                <div class="event-content">
                                    <h3>${edicion.nombre}</h3>
                                    <p>
                                        <i class="bi bi-geo-alt"></i> ${edicion.ciudad}, ${edicion.pais}
                                    </p>
                                    <small style="color: #999;">
                                        <i class="bi bi-calendar-range"></i> ${edicion.fechaInicio} - ${edicion.fechaFin}
                                        <c:if test="${not empty edicion.fechaAlta}">
                                            | Agregado: ${edicion.fechaAlta}
                                        </c:if>
                                    </small>
                                </div>
                            </li>
                        </a>
                    </c:forEach>
                </ul>
            </c:if>
            
            <!-- No results message -->
            <c:if test="${requestScope.totalResultados == 0}">
                <div style="text-align: center; padding: 60px 20px; background: #fff; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); border: 1px solid #e0e0e0;">
                    <i class="bi bi-search" style="font-size: 64px; color: #ddd;"></i>
                    <h3 style="margin-top: 20px; color: #666; font-weight: 600;">No se encontraron resultados</h3>
                    <p style="color: #999; font-size: 15px;">Intenta con otros términos de búsqueda</p>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>

