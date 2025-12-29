<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Alta Institución - eventos.uy" />
    </jsp:include>
</head>
<body>
<c:if test="${sessionScope.tipo_usuario ne 'Organizador'}">
    <c:url var="loginUrl" value="/iniciar-sesion" />
    <c:redirect url="${loginUrl}" />
</c:if>

<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

    <!-- Main Content -->
    <div class="content-area">
        <div class="page-content">
            <h1 class="page-title">Alta de Institución</h1>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    <strong>Error:</strong> <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <form method="POST" action="${pageContext.request.contextPath}/instituciones?action=new">
                <div class="form-group">
                    <label class="form-label">Nombre de la Institución:</label>
                    <input type="text" 
                           class="form-control" 
                           name="nombre"
                           value="${not empty requestScope.nombre ? requestScope.nombre : ''}"
                           placeholder="Ej: Facultad de Ingeniería - UdelaR" 
                           required>
                    <small class="form-text">El nombre debe ser único en el sistema</small>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Descripción:</label>
                    <textarea class="form-control" 
                              name="descripcion"
                              rows="4" 
                              placeholder="Describe la institución, su misión y actividades principales..." 
                              required><c:out value="${not empty requestScope.descripcion ? requestScope.descripcion : ''}" /></textarea>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Sitio Web:</label>
                    <input type="url" 
                           class="form-control" 
                           name="url"
                           value="${not empty requestScope.url ? requestScope.url : ''}"
                           placeholder="https://www.ejemplo.edu.uy" 
                           required>
                    <small class="form-text">URL completa del sitio web oficial</small>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Logo/Imagen de la Institución (opcional):</label>
                    <input type="file" class="form-control" accept="image/*">
                    <small class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 2MB</small>
                </div>
                
                <div class="alert alert-info">
                    <strong>ℹ️ Sobre las instituciones:</strong><br>
                    Las instituciones pueden ser asociadas a asistentes durante su registro y utilizadas en patrocinios para eventos.
                </div>
                
                <div style="display: flex; gap: 15px; margin-top: 30px;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">Crear Institución</button>
                    <button type="reset" class="btn btn-secondary">Limpiar</button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

