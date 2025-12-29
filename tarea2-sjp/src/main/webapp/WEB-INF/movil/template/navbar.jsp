<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Barra de Navegación Superior -->
<nav class="movil-navbar">
    <!-- Botón Hamburguesa (Izquierda) -->
    <button class="hamburger-btn" id="menuToggle" aria-label="Abrir menú de navegación" aria-expanded="false">
        ☰
    </button>
    
    <!-- Título Centrado -->
    <div class="navbar-title">EventosUy</div>
    
    <!-- Ícono de Usuario (Derecha) -->
    <a class="user-icon" aria-label="Perfil de usuario" title="<%= session.getAttribute("nickname") != null ? session.getAttribute("nickname") : "Usuario" %>">
        👤
    </a>
</nav>

<!-- Menú Overlay Expandible -->
<div class="menu-overlay" id="menuOverlay">
    <ul class="menu-list">
        <li>
            <a href="${pageContext.request.contextPath}/eventos">Consulta Edición</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/registros">Consulta Registro</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/asistencia">Asistencia</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/logout">Cierre de Sesión</a>
        </li>
    </ul>
</div>

<!-- JavaScript para Toggle del Menú -->
<script>
(function() {
    // Obtener elementos
    const menuToggle = document.getElementById('menuToggle');
    const menuOverlay = document.getElementById('menuOverlay');
    
    if (!menuToggle || !menuOverlay) return;
    
    // Toggle del menú al hacer clic en hamburguesa
    menuToggle.addEventListener('click', function(e) {
        e.stopPropagation();
        
        // Alternar clase active
        menuOverlay.classList.toggle('active');
        
        // Actualizar ARIA expanded
        const isExpanded = menuOverlay.classList.contains('active');
        menuToggle.setAttribute('aria-expanded', isExpanded);
    });
    
    // Cerrar menú al hacer clic fuera
    document.addEventListener('click', function(event) {
        const isClickInside = menuToggle.contains(event.target) || 
                              menuOverlay.contains(event.target);
        
        if (!isClickInside && menuOverlay.classList.contains('active')) {
            menuOverlay.classList.remove('active');
            menuToggle.setAttribute('aria-expanded', 'false');
        }
    });
    
    // Cerrar menú al presionar Escape
    document.addEventListener('keydown', function(event) {
        if (event.key === 'Escape' && menuOverlay.classList.contains('active')) {
            menuOverlay.classList.remove('active');
            menuToggle.setAttribute('aria-expanded', 'false');
            menuToggle.focus();
        }
    });
    
    // Cerrar menú al hacer clic en un enlace
    const menuLinks = menuOverlay.querySelectorAll('.menu-list a');
    menuLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            menuOverlay.classList.remove('active');
            menuToggle.setAttribute('aria-expanded', 'false');
        });
    });
})();
</script>
