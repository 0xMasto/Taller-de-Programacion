<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="publicar.ws.client.EventoDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Alta Edición - eventos.uy" />
    </jsp:include>
</head>
<body>
<c:set var="eventos" value="${requestScope.eventos}" />
<c:set var="eventoSeleccionado" value="${requestScope.eventoSeleccionado}" />
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
            <h1 class="page-title">Alta de Edición</h1>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    <strong>Error:</strong> <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <form method="POST" action="${pageContext.request.contextPath}/ediciones?action=new" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="form-label">Seleccionar Evento:</label>
                    <select class="form-control" name="evento" required>
                        <option value="">Selecciona un evento...</option>
                        <c:if test="${not empty eventos}">
                            <c:forEach var="ev" items="${eventos}">
                                <option value="${ev.nombre}" 
                                    <c:if test="${eventoSeleccionado eq ev.nombre}">selected</c:if>>
                                    <c:out value="${ev.nombre}" /> (<c:out value="${ev.sigla}" />)
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <small class="form-text">Puedes crear ediciones para cualquier evento del sistema</small>
                </div>
                
                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Nombre de la Edición:</label>
                        <input type="text" 
                               class="form-control" 
                               name="nombre"
                               value="${not empty requestScope.nombre ? requestScope.nombre : ''}"
                               placeholder="Ej: Conferencia de Tecnología 2025" 
                               required>
                        <small class="form-text">El nombre debe ser único en el sistema</small>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Sigla:</label>
                        <input type="text" 
                               class="form-control" 
                               name="sigla"
                               value="${not empty requestScope.sigla ? requestScope.sigla : ''}"
                               placeholder="Ej: TECHCONF25" 
                               maxlength="15" 
                               required>
                        <small class="form-text">Máximo 15 caracteres</small>
                    </div>
                </div>
                
                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Ciudad:</label>
                        <input type="text" 
                               class="form-control" 
                               name="ciudad"
                               value="${not empty requestScope.ciudad ? requestScope.ciudad : ''}"
                               placeholder="Ej: Montevideo" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">País:</label>
                        <input type="text" 
                               class="form-control" 
                               name="pais"
                               value="${not empty requestScope.pais ? requestScope.pais : ''}"
                               placeholder="Ej: Uruguay" 
                               required>
                    </div>
                </div>
                
                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Fecha de Inicio:</label>
                        <input type="date" 
                               class="form-control" 
                               name="fechaInicio"
                               value="${not empty requestScope.fechaInicio ? requestScope.fechaInicio : ''}"
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Fecha de Fin:</label>
                        <input type="date" 
                               class="form-control" 
                               name="fechaFin"
                               value="${not empty requestScope.fechaFin ? requestScope.fechaFin : ''}"
                               required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Imagen de la Edición (opcional):</label>
                    <input type="file" 
                           class="form-control" 
                           name="imagen"
                           id="imagen"
                           accept="image/jpeg,image/jpg,image/png,image/gif">
                    <small class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 5MB</small>
                </div>
                
                <div class="form-group">
                    <label class="form-label">URL del Video (opcional):</label>
                    <input type="url" 
                           class="form-control" 
                           name="videoUrl"
                           id="videoUrl"
                           value="${not empty requestScope.videoUrl ? requestScope.videoUrl : ''}"
                           placeholder="https://www.youtube.com/embed/VIDEO_ID">
                    <small class="form-text">Ingrese la URL de YouTube en formato embed. Ejemplo: https://www.youtube.com/embed/dQw4w9WgXcQ</small>
                </div>
                
                
                <div style="display: flex; gap: 15px; margin-top: 30px;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">Crear Edición</button>
                    <button type="reset" class="btn btn-secondary">Limpiar</button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

