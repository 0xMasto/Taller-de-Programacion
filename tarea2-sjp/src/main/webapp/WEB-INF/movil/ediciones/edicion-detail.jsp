<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "${edicion.nombre} - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/eventos">Eventos</a></li>
            <li class="breadcrumb-item active">${edicion.nombre}</li>
        </ol>
    </nav>
    
    <!-- Edition Header -->
    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">${edicion.nombre}</h4>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${not empty edicion.imagen && edicion.imagen != '-' && edicion.imagen != null && edicion.imagen != ''}">
                            <div id="edicion-imagen-container" style="position: relative;">
                                <img id="edicion-imagen" src="${pageContext.request.contextPath}/media/images/${edicion.imagen}" 
                                     class="img-fluid rounded" alt="${edicion.nombre}"
                                     onerror="document.getElementById('edicion-imagen').style.display='none'; document.getElementById('edicion-sigla-fallback').style.display='flex';">
                                <div id="edicion-sigla-fallback" style="display: none; width: 100%; height: 200px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 0.25rem; align-items: center; justify-content: center;">
                                    <span style="color: white; font-size: 48px; font-weight: bold; text-transform: uppercase;">
                                        ${edicion.sigla}
                                    </span>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div style="width: 100%; height: 200px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 0.25rem; display: flex; align-items: center; justify-content: center;">
                                <span style="color: white; font-size: 48px; font-weight: bold; text-transform: uppercase;">
                                    ${edicion.sigla}
                                </span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-8">
                    <h3>${edicion.nombre}</h3>
                    <p class="text-muted"><strong>Sigla:</strong> ${edicion.sigla}</p>
                    
                    <hr>
                    
                    <p><strong>Ubicación:</strong> ${edicion.ciudad}, ${edicion.pais}</p>
                    <p><strong>Fechas:</strong> ${edicion.fechaInicio} - ${edicion.fechaFin}</p>
                    <p><strong>Organizador:</strong> ${edicion.organizador}</p>
                    
                    <!-- Registration info -->
                    <div class="mt-4">
                        <c:choose>
                            <c:when test="${registrado}">
                                <div class="alert alert-success">
                                    <strong>¡Estás registrado en esta edición!</strong>
                                    <c:if test="${not empty miRegistro}">
                                        <br><small>Tipo: ${miRegistro.tipoRegistro.nombre} - Costo: $${miRegistro.costo}</small>
                                    </c:if>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info">
                                    <strong>No estás registrado en esta edición</strong>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Registration Types -->
    <c:if test="${not empty tiposRegistro}">
        <div class="card shadow mb-4">
            <div class="card-header bg-secondary text-white">
                <h5 class="mb-0">Tipos de Registro</h5>
            </div>
            <div class="card-body">
                <div class="row row-cols-1 row-cols-md-2 g-4">
                    <c:forEach var="tipo" items="${tiposRegistro}">
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h6 class="card-title">${tipo.nombre}</h6>
                                    <p class="card-text"><small>${tipo.descripcion}</small></p>
                                    <p class="mb-1"><strong>Costo:</strong> $${tipo.costo}</p>
                                    <p class="mb-0">
                                        <strong>Cupo:</strong> ${tipo.cupo}
                                        <c:if test="${tipo.cupoDisponible < tipo.cupo}">
                                            <span class="badge badge-warning text-dark">
                                                ${tipo.cupoDisponible} disponibles
                                            </span>
                                        </c:if>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
    
    <!-- Sponsorships -->
    <c:if test="${not empty patrocinios}">
        <div class="card shadow mb-4">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0">Patrocinios</h5>
            </div>
            <div class="card-body">
                <div class="list-group">
                    <c:forEach var="patrocinio" items="${patrocinios}">
                        <div class="list-group-item">
                            <div class="d-flex justify-content-between">
                                <h6 class="mb-1">${patrocinio.institucion.nombre}</h6>
                                <span class="badge badge-primary">${patrocinio.nivelPatrocinio}</span>
                            </div>
                            <p class="mb-1"><small>Aporte: $${patrocinio.aporteEconomico}</small></p>
                            <small class="text-muted">Registros incluidos: ${patrocinio.cantidadRegistros}</small>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../template/footer.jsp"/>
