<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "Detalle de Registro - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/asistencia">Mis Registros</a></li>
            <li class="breadcrumb-item active">Detalle</li>
        </ol>
    </nav>
    
    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>¡Éxito!</strong> Asistencia registrada correctamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error:</strong> ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <h2 class="mb-4">Detalle del Registro</h2>
    
    <!-- Registration Information Card -->
    <div class="card shadow mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Información del Registro</h5>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${not empty registro.edicion.imagen && registro.edicion.imagen != '-' && registro.edicion.imagen != null && registro.edicion.imagen != ''}">
                            <img src="${pageContext.request.contextPath}/media/images/${registro.edicion.imagen}" 
                                 class="img-fluid rounded" alt="${registro.edicion.nombre}"
                                 onerror="this.src='${pageContext.request.contextPath}/media/images/IMG-EDEV01.jpeg'">
                        </c:when>
                        <c:otherwise>
                            <span style="color: white; font-size: 15px; font-weight: bold;">
                                ${registro.edicion.sigla} 
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-8">
                    <h4>${registro.edicion.nombre}</h4>
                    <p class="text-muted"><strong>Evento:</strong> ${registro.edicion.evento}</p>
                    
                    <hr>
                    
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Ubicación:</strong><br>${registro.edicion.ciudad}, ${registro.edicion.pais}</p>
                            <p><strong>Fechas:</strong><br>${registro.edicion.fechaInicio} - ${registro.edicion.fechaFin}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Tipo de Registro:</strong><br>${registro.tipoRegistro.nombre}</p>
                            <p><strong>Costo:</strong><br>$${registro.costo}</p>
                            <p><strong>Fecha de Registro:</strong><br>${registro.fechaRegistro}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Attendance Status Card -->
    <div class="card shadow mb-4">
        <div class="card-header bg-info text-white">
            <h5 class="mb-0">Estado de Asistencia</h5>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${registro.asistio}">
                    <div class="alert alert-success">
                        <h5><i class="bi bi-check-circle-fill"></i> Asistencia Confirmada</h5>
                        <p class="mb-0">Ya has registrado tu asistencia a esta edición del evento.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning">
                        <h5><i class="bi bi-exclamation-triangle-fill"></i> Asistencia No Registrada</h5>
                        <p class="mb-0">Aún no has marcado tu asistencia a esta edición.</p>
                    </div>
                    
                    <!-- Form to mark attendance -->
                    <c:choose>
                        <c:when test="${puedeConfirmarAsistencia}">
                            <div class="mt-4">
                                <h6>¿Asististe a esta edición?</h6>
                                
                                <form method="post" action="${pageContext.request.contextPath}/asistencia" 
                                      onsubmit="return confirm('¿Confirmar que asististe a ${registro.edicion.nombre}?');">
                                    <input type="hidden" name="nombreEdicion" value="${registro.nombreEdicion}">
                                    <button type="submit" class="btn btn-success">
                                        <i class="bi bi-check-circle"></i> Confirmar Asistencia
                                    </button>
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="mt-4">
                                <div class="alert alert-info">
                                    <i class="bi bi-info-circle"></i> Podrás confirmar tu asistencia una vez que comience la edición.
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <!-- Action Buttons -->
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/asistencia" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Volver a Mis Registros
        </a>
        <a href="${pageContext.request.contextPath}/ediciones?nombre=${registro.nombreEdicion}" class="btn btn-primary">
            <i class="bi bi-info-circle"></i> Ver Detalles de la Edición
        </a>
    </div>
</div>

<jsp:include page="../template/footer.jsp"/>

