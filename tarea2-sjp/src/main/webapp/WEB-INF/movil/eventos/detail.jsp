<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "${evento.nombre} - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/eventos">Eventos</a></li>
            <li class="breadcrumb-item active">${evento.nombre}</li>
        </ol>
    </nav>
    
    <!-- Event Header -->
    <div class="card shadow mb-4">
        <div class="row">
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${not empty evento.imagen}">
                        <img src="${pageContext.request.contextPath}/media/images/${evento.imagen}" 
                             class="img-fluid rounded-start" alt="${evento.nombre}"
                             style="height: 100%; object-fit: cover;"
                             onerror="this.src='${pageContext.request.contextPath}/media/images/IMG-EV02.jpeg'">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/media/images/IMG-EV02.jpeg" 
                             class="img-fluid rounded-start" alt="Evento"
                             style="height: 100%; object-fit: cover;">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h3 class="card-title">${evento.nombre}</h3>
                    <p class="text-muted"><strong>Sigla:</strong> ${evento.sigla}</p>
                    
                    <c:if test="${not empty evento.descripcion}">
                        <p class="card-text">${evento.descripcion}</p>
                    </c:if>
                    
                    <c:if test="${not empty evento.categorias}">
                        <p>
                            <strong>Categorías:</strong>
                            <c:forEach var="categoria" items="${evento.categorias}" varStatus="status">
                                <span class="badge badge-secondary">${categoria.nombre}</span>
                            </c:forEach>
                        </p>
                    </c:if>
                    
                    <p>
                        <strong>Estado:</strong>
                        <c:choose>
                            <c:when test="${evento.finalizado}">
                                <span class="badge badge-danger">Finalizado</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-success">Activo</span>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    
                    <p><strong>Visitas:</strong> ${evento.visitas}</p>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Editions List -->
    <h4 class="mb-3">Ediciones Disponibles</h4>
    
    <c:if test="${empty ediciones}">
        <div class="alert alert-info" role="alert">
            No hay ediciones aprobadas disponibles para este evento.
        </div>
    </c:if>
    
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <c:forEach var="edicion" items="${ediciones}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <c:choose>
                        <c:when test="${not empty edicion.imagen && edicion.imagen != '-' && edicion.imagen != null && edicion.imagen != ''}">
                            <img src="${pageContext.request.contextPath}/media/images/${edicion.imagen}" 
                                 class="card-img-top" alt="${edicion.nombre}"
                                 style="height: 150px; object-fit: cover;"
                                 onerror="this.src='${pageContext.request.contextPath}/media/images/IMG-EDEV01.jpeg'">
                        </c:when>
                        <c:otherwise>
                            <span style="color: white; font-size: 15px; font-weight: bold;">
                                ${edicion.sigla} 
                            </span>
                        </c:otherwise>
                    </c:choose>
                    <div class="card-body">
                        <h5 class="card-title">${edicion.nombre}</h5>
                        <p class="card-text">
                            <small class="text-muted">
                                Sigla: ${edicion.sigla}<br>
                                Ubicación: ${edicion.ciudad}, ${edicion.pais}<br>
                                Fechas: ${edicion.fechaInicio} - ${edicion.fechaFin}
                            </small>
                        </p>
                        <span class="badge badge-success mb-2">
                            ${edicion.estado}
                        </span>
                        <a href="${pageContext.request.contextPath}/ediciones?nombre=${edicion.nombre}" 
                           class="btn btn-primary btn-sm w-100">
                            Ver Detalles →
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../template/footer.jsp"/>
