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
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/registros">Mis Registros</a></li>
            <li class="breadcrumb-item active">Detalle</li>
        </ol>
    </nav>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </c:if>
    
    <c:if test="${not empty registro}">
        <div class="card shadow mb-4">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Detalle de Registro</h4>
            </div>
            <div class="card-body">
                <h5 class="card-title">${registro.edicion.nombre}</h5>
                
                <div class="mb-3">
                    <p class="mb-2"><strong>Evento:</strong> ${registro.edicion.evento}</p>
                    <p class="mb-2"><strong>Tipo de Registro:</strong> ${registro.tipoRegistro.nombre}</p>
                    <p class="mb-2"><strong>Descripción:</strong> ${registro.tipoRegistro.descripcion}</p>
                    <p class="mb-2"><strong>Costo:</strong> $${registro.costo}</p>
                    <p class="mb-2"><strong>Fecha de Registro:</strong> ${registro.fechaRegistro}</p>
                </div>
                
                <hr>
                
                <div class="mb-3">
                    <h6>Información de la Edición</h6>
                    <p class="mb-2"><strong>Lugar:</strong> ${registro.edicion.ciudad}, ${registro.edicion.pais}</p>
                    <p class="mb-2"><strong>Fecha Inicio:</strong> ${registro.edicion.fechaInicio}</p>
                    <p class="mb-2"><strong>Fecha Fin:</strong> ${registro.edicion.fechaFin}</p>
                    <p class="mb-2"><strong>Estado:</strong> 
                        <c:choose>
                            <c:when test="${registro.asistio}">
                                <span class="badge bg-success">Asistió</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-secondary">Pendiente</span>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
                
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/ediciones?nombre=${registro.edicion.nombre}" class="btn btn-primary">
                        Ver Edición Completa
                    </a>
                    <a href="${pageContext.request.contextPath}/registros" class="btn btn-secondary">
                        Volver a Mis Registros
                    </a>
                </div>
            </div>
        </div>
    </c:if>
    
    <c:if test="${empty registro && empty error}">
        <div class="alert alert-warning" role="alert">
            No se encontró el registro solicitado.
        </div>
        <a href="${pageContext.request.contextPath}/registros" class="btn btn-secondary">
            Volver a Mis Registros
        </a>
    </c:if>
</div>

<style>
.breadcrumb {
    background-color: #f8f9fa;
    padding: 10px 15px;
    border-radius: 4px;
    margin-bottom: 20px;
}

.breadcrumb-item + .breadcrumb-item::before {
    content: ">";
    padding: 0 8px;
    color: #6c757d;
}

.breadcrumb-item.active {
    color: #6c757d;
}

.breadcrumb-item a {
    color: #007bff;
    text-decoration: none;
}

.card {
    border: 1px solid rgba(0,0,0,.125);
    border-radius: 0.25rem;
}

.card-header {
    padding: 0.75rem 1.25rem;
    margin-bottom: 0;
    border-bottom: 1px solid rgba(0,0,0,.125);
}

.card-body {
    padding: 1.25rem;
}

.badge {
    display: inline-block;
    padding: 0.25em 0.6em;
    font-size: 85%;
    font-weight: 700;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 0.25rem;
}

.bg-success {
    background-color: #28a745 !important;
    color: white;
}

.bg-secondary {
    background-color: #6c757d !important;
    color: white;
}

.btn {
    display: inline-block;
    padding: 0.375rem 0.75rem;
    margin-right: 0.5rem;
    font-size: 1rem;
    line-height: 1.5;
    border-radius: 0.25rem;
    text-decoration: none;
    text-align: center;
    cursor: pointer;
    border: 1px solid transparent;
}

.btn-primary {
    color: #fff;
    background-color: #007bff;
    border-color: #007bff;
}

.btn-secondary {
    color: #fff;
    background-color: #6c757d;
    border-color: #6c757d;
}

.alert {
    padding: 0.75rem 1.25rem;
    margin-bottom: 1rem;
    border: 1px solid transparent;
    border-radius: 0.25rem;
}

.alert-danger {
    color: #721c24;
    background-color: #f8d7da;
    border-color: #f5c6cb;
}

.alert-warning {
    color: #856404;
    background-color: #fff3cd;
    border-color: #ffeaa7;
}
</style>

</body>
</html>

