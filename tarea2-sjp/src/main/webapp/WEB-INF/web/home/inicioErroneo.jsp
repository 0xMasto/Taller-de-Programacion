<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/template/head.jsp">
        <jsp:param name="title" value="Error de Inicio de Sesión - Eventos.uy" />
    </jsp:include>
    <style>
        .login-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .login-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }
        .login-header p {
            color: #666;
            font-size: 14px;
        }
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: 500;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-group input:focus {
            outline: none;
            border-color: #007bff;
        }
        .form-group input.error {
            border-color: #dc3545;
        }
        .btn-login {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn-login:hover {
            background-color: #0056b3;
        }
        .register-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
        .register-link a {
            color: #007bff;
            text-decoration: none;
        }
        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>Eventos.uy</h1>
            <p>Inicia sesión para continuar</p>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert-danger">
                ${error}
            </div>
        </c:if>
        
        <c:if test="${empty error}">
            <div class="alert-danger">
                Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.
            </div>
        </c:if>
        
        <form method="post" action="${pageContext.request.contextPath}/iniciar-sesion">
            <div class="form-group">
                <label for="nickname">Nickname</label>
                <input type="text" id="nickname" name="nickname" required 
                       placeholder="Ingresa tu nickname" autofocus class="error">
            </div>
            
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" required 
                       placeholder="Ingresa tu contraseña" class="error">
            </div>
            
            <button type="submit" class="btn-login">Iniciar Sesión</button>
        </form>
        
        <div class="register-link">
            ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/usuarios?action=new">Regístrate aquí</a>
        </div>
    </div>
</body>
</html>
