<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Alta Evento - eventos.uy" />
    </jsp:include>
</head>
<body>
<c:set var="categorias" value="${requestScope.categorias}" />
<c:set var="categoriasSeleccionadas" value="${requestScope.categoriasSeleccionadas}" />
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
            <h1 class="page-title">Alta de Evento</h1>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    <strong>Error:</strong> <c:out value="${requestScope.error}" />
                </div>
            </c:if>

            <form method="POST" action="${pageContext.request.contextPath}/eventos?action=new" enctype="multipart/form-data">
                <div class="form-group">
                    <label class="form-label">Nombre del Evento:</label>
                    <input type="text"
                           class="form-control"
                           name="nombre"
                           value="${not empty requestScope.nombre ? requestScope.nombre : ''}"
                           placeholder="Ej: Conferencia de Tecnología"
                           required>
                    <small class="form-text">El nombre debe ser único en el sistema</small>
                </div>

                <div class="form-group">
                    <label class="form-label">Descripción:</label>
                    <textarea class="form-control"
                              name="descripcion"
                              rows="4"
                              placeholder="Describe el propósito y características del evento..."
                              required><c:out value="${not empty requestScope.descripcion ? requestScope.descripcion : ''}" /></textarea>
                </div>

                <div class="form-group">
                    <label class="form-label">Sigla:</label>
                    <input type="text"
                           class="form-control"
                           name="sigla"
                           value="${not empty requestScope.sigla ? requestScope.sigla : ''}"
                           placeholder="Ej: TECHCONF"
                           maxlength="10"
                           required>
                    <small class="form-text">Máximo 10 caracteres</small>
                </div>

                <div class="form-group">
                    <label class="form-label">Categorías (selecciona al menos una):</label>
                    <div class="checkbox-group">
                        <c:if test="${not empty categorias}">
                            <c:forEach var="categoria" items="${categorias}">
                                <c:set var="catNombre" value="${categoria.nombre}" />
                                <c:set var="catId" value="${fn:replace(fn:toLowerCase(catNombre), ' ', '_')}" />
                                <c:set var="isSelected" value="false" />
                                <c:if test="${not empty categoriasSeleccionadas}">
                                    <c:forEach var="selected" items="${categoriasSeleccionadas}">
                                        <c:if test="${selected eq catNombre}">
                                            <c:set var="isSelected" value="true" />
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <div class="checkbox-item">
                                    <input type="checkbox"
                                           id="${catId}"
                                           name="categorias"
                                           value="${catNombre}"
                                           <c:if test="${isSelected}">checked</c:if>>
                                    <label for="${catId}"><c:out value="${catNombre}" /></label>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Imagen del Evento (opcional):</label>
                    <input type="file" 
                           class="form-control" 
                           name="imagen"
                           id="imagen"
                           accept="image/jpeg,image/jpg,image/png,image/gif">
                    <small class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 5MB</small>
                </div>

                <div style="display: flex; gap: 15px; margin-top: 30px;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">Crear Evento</button>
                    <button type="reset" class="btn btn-secondary">Limpiar</button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
