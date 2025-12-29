<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - EventosUY Móvil</title>
    
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #E8E5F2;
            height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        .header-bar {
            background-color: white;
            padding: 12px 16px;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .header-bar .app-name {
            font-size: 14px;
            color: #000;
            font-weight: 500;
        }
        
        .login-container {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 40px 24px;
        }
        
        .app-title {
            font-size: 32px;
            font-weight: bold;
            color: #000;
            margin-bottom: 60px;
            text-align: center;
        }
        
        .login-form {
            width: 100%;
            max-width: 400px;
        }
        
        .input-field {
            width: 100%;
            padding: 14px 16px;
            margin-bottom: 16px;
            border: 1px solid #d0d0d0;
            border-radius: 8px;
            font-size: 16px;
            background-color: white;
            transition: border-color 0.2s;
        }
        
        .input-field:focus {
            outline: none;
            border-color: #3B99FC;
        }
        
        .input-field::placeholder {
            color: #999;
        }
        
        .login-button {
            width: 100%;
            padding: 14px;
            background-color: #3B99FC;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 8px;
            transition: background-color 0.2s;
        }
        
        .login-button:hover {
            background-color: #2a8ae8;
        }
        
        .login-button:active {
            background-color: #1d7bd8;
        }
        
        .error-message {
            background-color: #ffebee;
            color: #c62828;
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            text-align: center;
            border: 1px solid #ef9a9a;
        }
        
        .info-text {
            text-align: center;
            color: #666;
            font-size: 13px;
            margin-top: 32px;
        }
        
        @media (max-width: 480px) {
            .login-container {
                padding: 30px 20px;
            }
            
            .app-title {
                font-size: 28px;
                margin-bottom: 50px;
            }
        }
    </style>
</head>
<body>
    <div class="header-bar">
        <div class="app-name">EventosUy</div>
    </div>
    
    <div class="login-container">
        <h1 class="app-title">EventosUy</h1>
        
        <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <input 
                type="text" 
                class="input-field" 
                id="username" 
                name="username" 
                placeholder="Nickname o Email" 
                required 
                autofocus
            >
            
            <input 
                type="password" 
                class="input-field" 
                id="password" 
                name="password" 
                placeholder="Contrasena" 
                required
            >
            
            <button type="submit" class="login-button">Iniciar Sesion</button>
            
            <p class="info-text">
                Solo asistentes pueden acceder a la aplicacion movil
            </p>
        </form>
    </div>
</body>
</html>

