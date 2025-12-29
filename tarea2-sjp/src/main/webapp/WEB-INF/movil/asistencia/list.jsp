<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "Mis Registros - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    
    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Asistencia registrada exitosamente
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <c:choose>
        <c:when test="${empty registros}">
            <div class="alert alert-info">
                <h5>No tienes registros</h5>
                <p>Aún no te has registrado en ninguna edición de evento.</p>
                <a href="${pageContext.request.contextPath}/eventos" class="btn btn-primary">
                    Ver Eventos Disponibles
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-muted mb-4">Selecciona un registro para ver detalles y marcar asistencia</p>
            
            <div class="row row-cols-1 g-4">
                <c:forEach var="registro" items="${registros}">
                    <div class="col">
                        <div class="card shadow-sm h-100">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-9">
                                        <h5 class="card-title mb-2">${registro.edicion.nombre}</h5>
                                        <p class="text-muted mb-2"><small>${registro.edicion.evento}</small></p>
                                        <p class="mb-1">
                                            <strong>Tipo:</strong> ${registro.tipoRegistro.nombre}
                                        </p>
                                        <p class="mb-1">
                                            <strong>Costo:</strong> $${registro.costo}
                                        </p>
                                        <p class="mb-1">
                                            <strong>Fecha Registro:</strong> ${registro.fechaRegistro}
                                        </p>
                                        <c:choose>
                                            <c:when test="${registro.asistio}">
                                                <span class="badge bg-success mt-2">
                                                    <i class="bi bi-check-circle"></i> Asistencia Registrada
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark mt-2">
                                                    <i class="bi bi-clock"></i> Asistencia Pendiente
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-3 d-flex align-items-center justify-content-end">
                                        <a href="${pageContext.request.contextPath}/asistencia?action=detail&nombreEdicion=${registro.nombreEdicion}" 
                                           class="btn btn-primary">
                                            Ver Detalle
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../template/footer.jsp"/>

