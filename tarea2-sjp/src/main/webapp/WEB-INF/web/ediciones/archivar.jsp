<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Archivar Edición - eventos.uy" />
    </jsp:include>
</head>
<body>

<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<c:if test="${sessionScope.tipo_usuario ne 'Organizador'}">
    <c:url var="loginUrl" value="/iniciar-sesion" />
    <c:redirect url="${loginUrl}" />
</c:if>

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

    <!-- Main Content -->
    <div class="content-area">
        <div class="page-content">
            <h1 class="page-title">Archivar Edición</h1>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    <strong>Error:</strong> <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <div style="background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                <p style="margin-bottom: 20px; color: #666;">
                    <i class="bi bi-info-circle"></i>
                    Seleccione una edición para archivar. Solo se pueden archivar ediciones aprobadas que ya finalizaron.
                </p>
                
                <form method="POST" action="${pageContext.request.contextPath}/archivar-edicion">
                    <div class="form-group">
                        <label class="form-label">Edición a Archivar:</label>
                        <select class="form-control" name="edicion" required>
                            <option value="">Selecciona una edición...</option>
                            <c:forEach var="edicion" items="${requestScope.ediciones}">
                                <option value="${edicion.nombre}">
                                    <c:out value="${edicion.nombre}" /> - 
                                    <c:out value="${edicion.ciudad}" />, <c:out value="${edicion.pais}" /> 
                                    (<c:out value="${edicion.fechaFin}" />)
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${empty requestScope.ediciones}">
                            <small class="text-muted">No hay ediciones disponibles para archivar</small>
                        </c:if>
                    </div>
                    
                    <div class="alert alert-info" style="margin-top: 20px;">
                        <strong>ℹ️ Información:</strong> Los datos de la edición serán persistidos en la base de datos y la edición no aparecerá más en las búsquedas (excepto en tu perfil).
                    </div>
                    
                    <div style="display: flex; gap: 15px; margin-top: 30px;">
                        <button type="submit" class="btn btn-warning" style="flex: 1;" 
                                <c:if test="${empty requestScope.ediciones}">disabled</c:if>>
                            <i class="bi bi-archive"></i> Archivar Edición
                        </button>
                        <a href="${pageContext.request.contextPath}/perfil" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

