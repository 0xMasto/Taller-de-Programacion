<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Iniciar Sesión - eventos.uy" />
    </jsp:include>
</head>
<body>

    <!-- Include Header Component -->
    <jsp:include page="/WEB-INF/web/template/header.jsp" />
    
    <div class="main-layout">
        <!-- Include Sidebar Component -->
        <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

        <!-- Main Content -->
        <div class="content-area">
            <c:if test="${not empty error}">
            <div class="alert-danger">
                ${error}
            </div>
        </c:if>
        
        
            <div class="login-form">
                <h1 class="login-title">Iniciar Sesión</h1>
                
                <!-- Mensaje de éxito después de registro -->
                <% if ("registered".equals(request.getParameter("success"))) { %>
                    <div class="alert alert-success">
                        <strong>¡Registro exitoso!</strong> Tu cuenta ha sido creada. Ahora puedes iniciar sesión.
                    </div>
                <% } %>
                
                <form id="loginForm" method="post" action="${pageContext.request.contextPath}/iniciar-sesion">
                    <div class="form-group">
                        <label class="form-label">Nickname o Email:</label>
                        <input type="text" 
                               class="form-control" 
                               id="nickname" 
                               name="nickname"
                               placeholder="Ingresa tu nickname o email" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Contraseña:</label>
                        <input type="password" 
                               class="form-control" 
                               id="password" 
                               name="password"
                               placeholder="Ingresa tu contrasena" 
                               required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="width: 100%; margin-bottom: 15px;">Iniciar Sesión</button>
                    
                    <div style="text-align: center;">
                        <p>¿No tienes cuenta? <a href="${pageContext.request.contextPath}/usuarios?action=new">Regístrate aquí</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>

</body>
</html>
