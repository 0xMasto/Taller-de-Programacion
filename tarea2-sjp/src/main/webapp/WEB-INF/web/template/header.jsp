<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!-- Header / Navbar -->
<header class="main-header">
    <!-- Izquierda: Logo -->
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/" class="logo">Eventos.uy</a>
    </div>

    <!-- Centro: Search -->
    <div class="header-center">
        <form action="${pageContext.request.contextPath}/busqueda" method="GET" class="search-container">
            <input type="text" 
                   name="q" 
                   class="search-input" 
                   placeholder="Buscar Eventos, Ediciones..."
                   value="${param.q}">
            <button type="submit" class="search-btn">
                <i class="bi bi-search"></i> Buscar
            </button>
        </form>
    </div>

    <!-- Derecha: Auth Links -->
    <div class="header-right auth-links">
        <c:choose>
            <c:when test="${not empty sessionScope.usuario_logueado}">
                <div id="userAuth">
                    <div class="user-info">
                        <div class="user-profile-section">
                            <div class="user-avatar" id="userAvatar">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.usuario_logueado.imagen and sessionScope.usuario_logueado.imagen != '-'}">
                                        <img id="userImage"
                                             src="${pageContext.request.contextPath}/media/images/${sessionScope.usuario_logueado.imagen}"
                                             alt="${sessionScope.usuario_logueado.nombre}"
                                             style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
                                    </c:when>
                                    <c:otherwise>
                                        <span id="userInitial">
                                            ${sessionScope.usuario_logueado.nombre.substring(0,1).toUpperCase()}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="user-details">
                                <span class="user-name" id="userName">${sessionScope.usuario_logueado.nombre}</span>
                                <div class="user-actions">
                                    <a href="${pageContext.request.contextPath}/perfil" id="userProfileLink" style="color: #007bff; text-decoration: none; font-size: 12px;">Ver Perfil</a>
                                    <span style="color: #ccc; margin: 0 5px;">|</span>
                                    <a href="${pageContext.request.contextPath}/logout" style="color: #dc3545; text-decoration: none; font-size: 12px;">Cerrar Sesión</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div id="guestAuth">
                    <a href="${pageContext.request.contextPath}/iniciar-sesion">Iniciar Sesión</a> / 
                    <a href="${pageContext.request.contextPath}/usuarios?action=new">Registrarse</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>