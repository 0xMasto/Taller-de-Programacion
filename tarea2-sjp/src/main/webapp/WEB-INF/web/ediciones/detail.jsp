<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="${edicion.nombre} - eventos.uy" />
    </jsp:include>
</head>
<body>

<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<c:set var="usuario" value="${sessionScope.usuario_logueado}" />
<c:set var="tipoUsuarioSesion" value="${sessionScope.tipo_usuario}" />
<c:set var="nickname" value="${sessionScope.nickname}" />
<c:set var="evento" value="${requestScope.evento}" />
<c:set var="edicion" value="${requestScope.edicion}" />
<c:set var="organizadorDTO" value="${requestScope.organizadorDTO}" />
<c:set var="tiposRegistro" value="${requestScope.tiposRegistro}" />
<c:set var="patrocinios" value="${requestScope.patrocinios}" />
<c:set var="edicionEstado" value="${requestScope.edicionEstado}" />
<c:set var="miRegistro" value="${requestScope.miRegistro}" />
<c:set var="miTipoRegistro" value="${requestScope.miTipoRegistro}" />
<c:set var="edicionFechaInicio" value="${requestScope.edicionFechaInicio}" />
<c:set var="edicionFechaFin" value="${requestScope.edicionFechaFin}" />
<c:set var="edicionFechaAlta" value="${requestScope.edicionFechaAlta}" />

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

    <!-- Main Content -->
    <div class="content-area">
        <div class="page-content">
            <h1 class="page-title">Detalle de la Edición</h1>

            <c:if test="${not empty requestScope.success}">
                <div class="alert alert-success">
                    <c:out value="${requestScope.success}" />
                </div>
            </c:if>

            <div class="edition-detail-container">
                <div class="edition-main">
                    <div class="event-image">
                        <c:choose>
                            <c:when test="${not empty edicion.imagen && edicion.imagen != '-'}">
                                <img src="${pageContext.request.contextPath}/media/images/${edicion.imagen}" 
                                     alt="${edicion.nombre}" 
                                     style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
                            </c:when>
                            <c:otherwise>
                                <span style="color: white; font-size: 15px; font-weight: bold;">
                                    ${edicion.sigla}
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="edition-info">
                        <h2><c:out value="${edicion.nombre}" /></h2>
                        <p><strong>Sigla:</strong> <c:out value="${edicion.sigla}" /></p>
                        <p><strong>Ciudad:</strong> <c:out value="${edicion.ciudad}" /></p>
                        <p><strong>País:</strong> <c:out value="${edicion.pais}" /></p>
                        <p><strong>Fecha Inicio:</strong> <c:out value="${edicionFechaInicio}" /></p>
                        <p><strong>Fecha Fin:</strong> <c:out value="${edicionFechaFin}" /></p>
               
                        <c:if test="${not empty edicionFechaAlta}">
                            <p><strong>Fecha Alta:</strong> <c:out value="${edicionFechaAlta}" /></p>
                        </c:if>
                        <c:if test="${not empty miRegistro}">
                            <p><strong>Tu registro:</strong>
                                <span style="color:#007bff;">
                                    <c:out value="${miTipoRegistro.nombre}" />
                                </span>
                                <c:if test="${not empty miRegistro.fechaRegistro}">
                                    <span>(Registrado el <c:out value="${miRegistro.fechaRegistro}" />)</span>
                                </c:if>
                                <c:if test="${miRegistro.asistio}">
                                    <span style="color:#28a745;">
                                        <i class="bi bi-check-circle-fill"></i> Asistió
                                    </span>
                                </c:if>
                            </p>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- Registration Button for Assistants -->
            <c:if test="${tipoUsuarioSesion eq 'Asistente' && empty miRegistro && not empty tiposRegistro && registroAbierto}">
                <div style="margin-top: 20px; text-align: center;">
                    <c:url var="registroUrl" value="/registros">
                        <c:param name="action" value="new" />
                        <c:param name="edicion" value="${edicion.nombre}" />
                    </c:url>
                    <a href="${registroUrl}" class="btn btn-success" style="padding: 12px 30px; font-size: 16px;">
                        Registrarse a esta Edición
                    </a>
                </div>
            </c:if>
            
            <!-- Registros List for Organizador -->
            <c:if test="${tipoUsuarioSesion eq 'Organizador' && not empty registrosEdicion}">
                <div style="margin-top: 40px;">
                    <h3>Registros de la Edición</h3>
                    <table style="width: 100%; border-collapse: collapse; margin-top: 15px;">
                        <thead>
                            <tr style="background-color: #f5f5f5; border-bottom: 2px solid #ddd;">
                                <th style="padding: 12px; text-align: left;">Asistente</th>
                                <th style="padding: 12px; text-align: left;">Tipo de Registro</th>
                                <th style="padding: 12px; text-align: left;">Fecha Registro</th>
                                <th style="padding: 12px; text-align: center;">Asistió</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="registro" items="${registrosEdicion}">
                                <tr style="border-bottom: 1px solid #ddd;">
                                    <td style="padding: 12px;">
                                        <c:url var="asistenteUrl" value="/usuarios">
                                            <c:param name="action" value="detail" />
                                            <c:param name="nickname" value="${registro.asistente.nickname}" />
                                        </c:url>
                                        <a href="${asistenteUrl}">
                                            <c:out value="${registro.asistente.nickname}" />
                                        </a>
                                    </td>
                                    <td style="padding: 12px;">
                                        <c:out value="${registro.tipoRegistro.nombre}" />
                                    </td>
                                    <td style="padding: 12px;">
                                        <c:out value="${registro.fechaRegistro}" />
                                    </td>
                                    <td style="padding: 12px;">
                                        <c:out value="${registro.asistio ? 'Sí ✓' : 'No ✗'}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>

    <!-- Right Sidebar -->
    <div class="editions-sidebar">
        <!-- Event Section -->
        <div class="editions-section">
            <div class="editions-header">Evento</div>
            <div class="editions-list">
                <c:if test="${empty evento}">
                    <div class="edition-item" style="justify-content:center; text-align:center;">
                        <span style="color:#666;">Evento no disponible</span>
                    </div>
                </c:if>
                <c:if test="${not empty evento}">
                    <c:url var="eventoUrl" value="/eventos">
                        <c:param name="id" value="${evento.nombre}" />
                    </c:url>
                    <div class="edition-item">
                        <div class="edition-image">
                            <c:choose>
                                <c:when test="${not empty evento.imagen && evento.imagen != '-'}">
                                    <img src="${pageContext.request.contextPath}/media/images/${evento.imagen}" 
                                         alt="${evento.nombre}" 
                                         style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
                                </c:when>
                                <c:otherwise>
                                    <span style="color: white; font-size: 15px; font-weight: bold;">
                                        ${evento.sigla}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="edition-content">
                            <div class="edition-name">
                                <a href="${eventoUrl}"><c:out value="${evento.nombre}" /></a>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Organizer Section -->
        <div class="editions-section">
            <div class="editions-header">Organizador</div>
            <div class="editions-list">
                <c:if test="${empty organizadorDTO}">
                    <div class="edition-item" style="justify-content:center; text-align:center;">
                        <span style="color:#666;">Organizador no disponible</span>
                    </div>
                </c:if>
                <c:if test="${not empty organizadorDTO}">
                    <c:set var="organizadorInitials" value="" />
                    <c:set var="organizadorNombreLength" value="${fn:length(organizadorDTO.nombre)}" />
                    <c:choose>
                        <c:when test="${organizadorNombreLength ge 2}">
                            <c:set var="organizadorInitials" value="${fn:toUpperCase(fn:substring(organizadorDTO.nombre, 0, 2))}" />
                        </c:when>
                        <c:when test="${organizadorNombreLength eq 1}">
                            <c:set var="organizadorInitials" value="${fn:toUpperCase(organizadorDTO.nombre)}" />
                        </c:when>
                    </c:choose>
                    <c:url var="organizadorUrl" value="/usuarios">
                        <c:param name="usuario" value="${organizadorDTO.nickname}" />
                    </c:url>
                    <div class="edition-item">
                        <div class="edition-image">
                            <c:choose>
                                <c:when test="${not empty organizadorDTO.imagen && organizadorDTO.imagen != '-'}">
                                    <img src="${pageContext.request.contextPath}/media/images/${organizadorDTO.imagen}"
                                         alt="${organizadorDTO.nombre}"
                                         style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
                                </c:when>
                                <c:otherwise>
                                    <div style="width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; border-radius: 4px; font-weight: bold;">
                                        <c:out value="${organizadorInitials}" />
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="edition-content">
                            <div class="edition-name">
                                <a href="${organizadorUrl}"><c:out value="${organizadorDTO.nombre}" /></a>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Registration Types Section -->
        <div class="editions-section">
            <div class="editions-header">Tipos Registros</div>
            <div class="editions-list">
                <c:if test="${empty tiposRegistro}">
                    <div style="padding: 10px; text-align: center; color: #666;">
                        <em>No hay tipos de registro</em>
                    </div>
                </c:if>
                <c:forEach var="tipo" items="${tiposRegistro}">
                    <c:url var="tipoUrl" value="/tipos-registro">
                        <c:param name="evento" value="${evento.nombre}" />
                        <c:param name="edicion" value="${edicion.nombre}" />
                        <c:param name="nombre" value="${tipo.nombre}" />
                    </c:url>
                    <div class="edition-item">
                        <div class="edition-content">
                            <div class="edition-name">
                                <a href="${tipoUrl}"><c:out value="${tipo.nombre}" /></a>
                                <c:if test="${not empty miTipoRegistro && miTipoRegistro.nombre == tipo.nombre}">
                                    <small style="color: #007bff;">Tu registro</small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${tipoUsuarioSesion eq 'Organizador' && organizadorDTO != null && organizadorDTO.nickname == nickname}">
                    <c:url var="nuevoTipoUrl" value="/tipos-registro">
                        <c:param name="action" value="new" />
                        <c:param name="edicion" value="${edicion.nombre}" />
                    </c:url>
                    <a href="${nuevoTipoUrl}" class="btn btn-success">Crear Nuevo Tipo</a>
                </c:if>
            </div>
        </div>

        <!-- Sponsorships Section -->
        <div class="editions-section">
            <div class="editions-header">Patrocinios</div>
            <div class="editions-list">
                <c:if test="${empty patrocinios}">
                    <div style="padding: 10px; text-align: center; color: #666;">
                        <em>No hay patrocinios</em>
                    </div>
                </c:if>
                <c:forEach var="patrocinio" items="${patrocinios}">
                    <c:url var="patrocinioUrl" value="/patrocinios">
                        <c:param name="id" value="${patrocinio.codigoPatrocinio}" />
                    </c:url>
                    <div class="edition-item">
                        <div class="edition-content">
                            <div class="edition-name">
                                <a href="${patrocinioUrl}">
                                    <c:out value="${patrocinio.institucion.nombre}" />
                                </a>
                                <c:if test="${not empty patrocinio.nivelPatrocinio}">
                                    <small style="color:#666;">Nivel: <c:out value="${patrocinio.nivelPatrocinio}" /></small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
