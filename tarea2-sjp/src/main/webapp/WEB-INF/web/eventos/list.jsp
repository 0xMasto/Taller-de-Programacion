<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Eventos - Eventos.uy" />
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
           
            
            <!-- Category Filter Indicator -->
            <c:if test="${not empty categoriaFiltro}">
                <div style="margin-bottom: 20px; padding: 15px; background: #e7f3ff; border-left: 4px solid #2196F3; border-radius: 4px; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <strong>🔍 Filtrando por categoría:</strong> 
                        <span style="text-transform: capitalize; color: #2196F3; font-weight: bold;">${categoriaFiltro}</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/eventos" 
                       style="padding: 8px 16px; background: #2196F3; color: white; text-decoration: none; border-radius: 4px; font-size: 14px;">
                        ✕ Limpiar filtro
                    </a>
                </div>
            </c:if>
            
            <c:if test="${not empty success}">
                <div class="alert alert-success" style="padding: 15px; background: #d4edda; color: #155724; border: 1px solid #c3e6cb; border-radius: 4px; margin-bottom: 15px;">
                    ${success}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-info" style="padding: 15px; background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; border-radius: 4px; margin-bottom: 15px;">
                    ${error}
                </div>
            </c:if>
            
            <c:choose>
                <c:when test="${empty eventos}">
                    <div class="no-eventos" style="text-align: center; padding: 60px 20px; background: white; border-radius: 8px;">
                        <c:choose>
                            <c:when test="${not empty categoriaFiltro}">
                                <h3 style="color: #666; margin-bottom: 20px;">📭 No hay eventos en esta categoría</h3>
                                <p>No se encontraron eventos para la categoría "<span style="text-transform: capitalize; font-weight: bold;">${categoriaFiltro}</span>".</p>
                                <p style="margin-top: 20px;">
                                    <a href="${pageContext.request.contextPath}/eventos" 
                                       style="padding: 10px 20px; background: #2196F3; color: white; text-decoration: none; border-radius: 4px; display: inline-block;">
                                        Ver todos los eventos
                                    </a>
                                </p>
                            </c:when>
                            <c:otherwise>
                                <h3 style="color: #666; margin-bottom: 20px;">📭 No hay eventos disponibles</h3>
                                <p>Aún no se han creado eventos en la plataforma.</p>
                                <c:if test="${not empty sessionScope.usuario_logueado && sessionScope.tipo_usuario == 'Organizador'}">
                                    <p>
                                        <a href="${pageContext.request.contextPath}/eventos?action=create" class="btn btn-primary">
                                            Crear Primer Evento
                                        </a>
                                    </p>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Events List -->
                    <ul class="events-list">
                        <c:forEach items="${eventos}" var="evento">
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
                                        <p>${evento.descripcion}</p>
                                        <small style="color: #999;">
                                            <c:if test="${not empty evento.categorias}">
                                                Categorías: 
                                                <c:forEach items="${evento.categorias}" var="cat" varStatus="status">
                                                    ${cat.nombre}<c:if test="${!status.last}">, </c:if>
                                                </c:forEach>
                                                <c:if test="${not empty evento.ediciones}"> | </c:if>
                                            </c:if>
                                            <c:if test="${not empty evento.ediciones}">
                                                <c:choose>
                                                    <c:when test="${evento.ediciones.size() == 1}">
                                                        1 edición
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${evento.ediciones.size()} edición(es)
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </small>
                                    </div>
                                </li>
                            </a>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>

