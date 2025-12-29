<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!-- Sidebar -->
<div class="sidebar">
    <!-- Profile Section (only for logged in users) -->
    <c:if test="${not empty sessionScope.usuario_logueado}">
        <div class="profile-section" id="profileSection">
            <div class="profile-header">Mi Perfil</div>
            <div class="profile-options">
                <c:if test="${sessionScope.tipo_usuario == 'Organizador'}">
                    <a href="${pageContext.request.contextPath}/perfil" id="profileLink">Modificar Usuario</a>
                    <a href="${pageContext.request.contextPath}/eventos?action=new" id="organizerLink1">Alta Evento</a>
                    <a href="${pageContext.request.contextPath}/ediciones?action=new" id="organizerLink2">Alta Edición</a>
                    <a href="${pageContext.request.contextPath}/instituciones?action=new" id="organizerLink3">Alta Institución</a>
                    <a href="${pageContext.request.contextPath}/eventos/finalizar" id="organizerLink4">Finalizar Evento</a>
                </c:if>
                <c:if test="${sessionScope.tipo_usuario == 'Asistente'}">
                    <a href="${pageContext.request.contextPath}/eventos" id="profileLink">Registro a Edicion</a>
                </c:if>
            </div>
        </div>
    </c:if>

    <!-- Categories -->
    <div class="sidebar-section">
        <div class="sidebar-title">Categorías</div>
        <ul class="category-list">
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=tecnologia" data-category="tecnología" class="${param.categoria == 'tecnologia' ? 'active' : ''}">Tecnología</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=innovacion" data-category="innovación" class="${param.categoria == 'innovacion' ? 'active' : ''}">Innovación</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=literatura" data-category="literatura" class="${param.categoria == 'literatura' ? 'active' : ''}">Literatura</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=cultura" data-category="cultura" class="${param.categoria == 'cultura' ? 'active' : ''}">Cultura</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=musica" data-category="música" class="${param.categoria == 'musica' ? 'active' : ''}">Música</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=deporte" data-category="deporte" class="${param.categoria == 'deporte' ? 'active' : ''}">Deporte</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=salud" data-category="salud" class="${param.categoria == 'salud' ? 'active' : ''}">Salud</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=entretenimiento" data-category="entretenimiento" class="${param.categoria == 'entretenimiento' ? 'active' : ''}">Entretenimiento</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=agro" data-category="agro" class="${param.categoria == 'agro' ? 'active' : ''}">Agro</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=negocios" data-category="negocios" class="${param.categoria == 'negocios' ? 'active' : ''}">Negocios</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=moda" data-category="moda" class="${param.categoria == 'moda' ? 'active' : ''}">Moda</a></li>
            <li><a href="${pageContext.request.contextPath}/eventos?categoria=investigacion" data-category="investigación" class="${param.categoria == 'investigacion' ? 'active' : ''}">Investigación</a></li>
        </ul>
    </div>

    <!-- Users -->
    <div class="users-section">
        <div class="sidebar-title">
            <a href="${pageContext.request.contextPath}/usuarios" style="color: #333; text-decoration: underline;">Usuarios</a>
        </div>
    </div>            
</div>

