<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Alta Tipo de Registro - eventos.uy" />
    </jsp:include>
</head>
<body>
<c:set var="ediciones" value="${requestScope.ediciones}" />
<c:set var="edicionSeleccionada" value="${requestScope.edicionSeleccionada}" />
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
            <h1 class="page-title">Alta de Tipo de Registro</h1>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    <strong>Error:</strong> <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <form method="POST" action="${pageContext.request.contextPath}/tipos-registro?action=new">
                <div class="form-group">
                    <label class="form-label">Seleccionar Edición:</label>
                    <select class="form-control" name="edicion" required>
                        <option value="">Selecciona una de tus ediciones...</option>
                        <c:if test="${not empty ediciones}">
                            <c:forEach var="ed" items="${ediciones}">
                                <option value="${ed.nombre}" 
                                    <c:if test="${edicionSeleccionada eq ed.nombre}">selected</c:if>>
                                    <c:out value="${ed.nombre}" />
                                    <c:if test="${not empty ed.sigla}">
                                        (<c:out value="${ed.sigla}" />)
                                    </c:if>
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <small class="form-text">Solo puedes crear tipos de registro para tus ediciones</small>
                </div>
                
                <div class="grid-2">
                    <div class="form-group">
                        <label class="form-label">Nombre del Tipo:</label>
                        <input type="text" 
                               class="form-control" 
                               name="nombre"
                               value="${not empty requestScope.nombre ? requestScope.nombre : ''}"
                               placeholder="Ej: Estudiante, Profesional, VIP..." 
                               required>
                        <small class="form-text">Debe ser único dentro de esta edición</small>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Costo (UYU):</label>
                        <input type="number" 
                               class="form-control" 
                               name="costo"
                               value="${not empty requestScope.costo ? requestScope.costo : ''}"
                               placeholder="500" 
                               min="0" 
                               step="0.01" 
                               required>
                        <small class="form-text">Ingresa 0 para registro gratuito</small>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Descripción:</label>
                    <textarea class="form-control" 
                              name="descripcion" 
                              rows="3" 
                              placeholder="Describe qué incluye este tipo de registro..." 
                              required><c:out value="${not empty requestScope.descripcion ? requestScope.descripcion : ''}" /></textarea>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Cupo Disponible:</label>
                    <input type="number" 
                           class="form-control" 
                           name="cupo"
                           value="${not empty requestScope.cupo ? requestScope.cupo : ''}"
                           placeholder="100" 
                           min="1" 
                           required>
                    <small class="form-text">Número máximo de registros de este tipo</small>
                </div>
                
                <div style="display: flex; gap: 15px; margin-top: 30px;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">Crear Tipo de Registro</button>
                    <button type="reset" class="btn btn-secondary">Limpiar</button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

