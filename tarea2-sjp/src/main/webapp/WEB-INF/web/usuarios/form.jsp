<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Registrarse - eventos.uy" />
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
            <div style="max-width: 600px; margin: 0 auto;">
                <div class="page-content">
                    <h1 class="page-title" style="text-align: center;">Crear Cuenta Nueva</h1>
                    <p style="text-align: center; color: #666; margin-bottom: 30px;">Únete a la comunidad de eventos.uy</p>
                    
                    <!-- Mostrar errores si existen -->
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger">
                            <strong>Error:</strong> <%= request.getAttribute("error") %>
                        </div>
                    <% } %>
                    
                    <form id="registerForm" method="POST" action="${pageContext.request.contextPath}/usuarios?action=new" enctype="multipart/form-data">
                        <!-- User Type Selection -->
                        <div class="form-group">
                            <label class="form-label">Tipo de Usuario:</label>
                            <div style="display: flex; gap: 20px; margin-top: 10px;">
                                <label style="display: flex; align-items: center; gap: 8px;">
                                    <input type="radio" name="tipoUsuario" id="tipoAsistente" value="Asistente" 
                                           <%= "Asistente".equals(request.getAttribute("tipoUsuario")) || request.getAttribute("tipoUsuario") == null ? "checked" : "" %>>
                                    <span class="user-type-badge badge-asistente">Asistente</span>
                                </label>
                                <label style="display: flex; align-items: center; gap: 8px;">
                                    <input type="radio" name="tipoUsuario" id="tipoOrganizador" value="Organizador"
                                           <%= "Organizador".equals(request.getAttribute("tipoUsuario")) ? "checked" : "" %>>
                                    <span class="user-type-badge badge-organizador">Organizador</span>
                                </label>
                            </div>
                        </div>

                        <!-- Basic Information -->
                        <div class="grid-2">
                            <div class="form-group">
                                <label class="form-label">Nickname:</label>
                                <input type="text" 
                                       class="form-control" 
                                       name="nickname"
                                       id="nickname"
                                       value="<%= request.getAttribute("nickname") != null ? request.getAttribute("nickname") : "" %>"
                                       placeholder="Nombre de usuario único" 
                                       pattern="[a-zA-Z0-9_]{3,20}"
                                       minlength="3"
                                       maxlength="20"
                                       required>
                                <small id="nicknameMsg" class="form-text"></small>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Nombre:</label>
                                <input type="text" 
                                       class="form-control" 
                                       name="nombre"
                                       id="nombre"
                                       value="<%= request.getAttribute("nombre") != null ? request.getAttribute("nombre") : "" %>"
                                       placeholder="Tu nombre" 
                                       maxlength="100"
                                       required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Email:</label>
                            <input type="email" 
                                   class="form-control" 
                                   name="correo"
                                   id="correo"
                                   value="<%= request.getAttribute("correo") != null ? request.getAttribute("correo") : "" %>"
                                   placeholder="correo@ejemplo.com" 
                                   required>
                            <small id="emailMsg" class="form-text"></small>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Foto de Perfil (opcional):</label>
                            <input type="file" 
                                   class="form-control" 
                                   name="imagen"
                                   id="imagen"
                                   accept="image/jpeg,image/jpg,image/png,image/gif">
                            <small class="form-text">Formatos aceptados: JPG, PNG, GIF. Tamaño máximo: 2MB</small>
                        </div>

                        <div class="grid-2">
                            <div class="form-group">
                                <label class="form-label">Contraseña:</label>
                                <input type="password" 
                                       class="form-control" 
                                       name="contrasenia"
                                       id="contrasenia"
                                       placeholder="Contraseña segura" 
                                       minlength="6"
                                       required>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Confirmar Contraseña:</label>
                                <input type="password" 
                                       class="form-control" 
                                       name="contraseniaConfirm"
                                       id="contraseniaConfirm"
                                       placeholder="Repite la contraseña" 
                                       minlength="6"
                                       required>
                            </div>
                        </div>
                        
                        <div id="passwordMatchMessage" style="display: none; margin-top: -10px; margin-bottom: 15px;"></div>

                        <!-- Asistente-specific fields -->
                        <div id="assistantFields">
                            <div class="grid-2">
                                <div class="form-group">
                                    <label class="form-label">Apellido:</label>
                                    <input type="text" 
                                           class="form-control" 
                                           name="apellido"
                                           id="apellido"
                                           value="<%= request.getAttribute("apellido") != null ? request.getAttribute("apellido") : "" %>"
                                           placeholder="Tu apellido"
                                           maxlength="100">
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Fecha de Nacimiento:</label>
                                    <input type="date" 
                                           class="form-control"
                                           name="fechaNacimiento"
                                           id="fechaNacimiento"
                                           value="<%= request.getAttribute("fechaNacimiento") != null ? request.getAttribute("fechaNacimiento") : "" %>"
                                           max="<%= java.time.LocalDate.now().minusYears(13) %>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Institución (opcional):</label>
                                <select class="form-control" 
                                        name="institucion"
                                        id="institucion">
                                    <option value="">-- Sin institución --</option>
                                    <%
                                        Collection<String> instituciones = (Collection<String>) request.getAttribute("instituciones");
                                        if (instituciones != null) {
                                            String seleccionada = (String) request.getAttribute("institucion");
                                            for (String inst : instituciones) {
                                    %>
                                                <option value="<%= inst %>" <%= inst.equals(seleccionada) ? "selected" : "" %>>
                                                    <%= inst %>
                                                </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                        </div>

                        <!-- Organizador-specific fields -->
                        <div id="organizerFields" style="display: none;">
                            <div class="form-group">
                                <label class="form-label">Descripción:</label>
                                <textarea class="form-control" 
                                          name="descripcion"
                                          id="descripcion"
                                          rows="3" 
                                          required
                                          maxlength="500"
                                          placeholder="Describe tu organización o actividad..."><%= request.getAttribute("descripcion") != null ? request.getAttribute("descripcion") : "" %></textarea>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Sitio Web (opcional):</label>
                                <input type="url" 
                                       class="form-control" 
                                       name="sitioWeb"
                                       id="sitioWeb"
                                       value="<%= request.getAttribute("sitioWeb") != null ? request.getAttribute("sitioWeb") : "" %>"
                                       placeholder="https://www.ejemplo.com">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success" style="width: 100%; margin-bottom: 15px;">Crear Cuenta</button>
                        
                        <div style="text-align: center;">
                            <p>¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/iniciar-sesion">Inicia sesión aquí</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const tipoRadios = document.querySelectorAll('input[name="tipoUsuario"]');
            const assistantFields = document.getElementById('assistantFields');
            const organizerFields = document.getElementById('organizerFields');
            const passwordInput = document.getElementById('contrasenia');
            const passwordConfirmInput = document.getElementById('contraseniaConfirm');
            const passwordMatchMessage = document.getElementById('passwordMatchMessage');

            function toggleFields() {
                const selectedType = document.querySelector('input[name="tipoUsuario"]:checked').value;
                
                if (selectedType === 'Asistente') {
                    assistantFields.style.display = 'block';
                    organizerFields.style.display = 'none';
                    
                    // Make assistant fields required
                    document.getElementById('apellido').required = true;
                    document.getElementById('fechaNacimiento').required = true;
                    
                    // Remove required from organizer fields
                    document.getElementById('descripcion').required = false;
                    document.getElementById('sitioWeb').required = false;
                } else if (selectedType === 'Organizador') {
                    assistantFields.style.display = 'none';
                    organizerFields.style.display = 'block';
                    
                    // Organizer fields are optional
                    document.getElementById('descripcion').required = true;
                    document.getElementById('sitioWeb').required = false;
                    
                    // Remove required from assistant fields
                    document.getElementById('apellido').required = false;
                    document.getElementById('fechaNacimiento').required = false;
                }
            }

            // Add event listeners to radio buttons
            tipoRadios.forEach(radio => {
                radio.addEventListener('change', toggleFields);
            });

            // Password match validation
            function checkPasswordMatch() {
                const password = passwordInput.value;
                const passwordConfirm = passwordConfirmInput.value;
                
                if (passwordConfirm === '') {
                    passwordMatchMessage.style.display = 'none';
                    return;
                }
                
                passwordMatchMessage.style.display = 'block';
                if (password === passwordConfirm) {
                    passwordMatchMessage.textContent = '✓ Las contraseñas coinciden';
                    passwordMatchMessage.style.color = '#28a745';
                } else {
                    passwordMatchMessage.textContent = '✗ Las contraseñas no coinciden';
                    passwordMatchMessage.style.color = '#dc3545';
                }
            }
            
            passwordInput.addEventListener('input', checkPasswordMatch);
            passwordConfirmInput.addEventListener('input', checkPasswordMatch);

            // Form validation on submit
            document.getElementById('registerForm').addEventListener('submit', function(e) {
                const password = passwordInput.value;
                const passwordConfirm = passwordConfirmInput.value;
                
                if (password !== passwordConfirm) {
                    e.preventDefault();
                    alert('Las contraseñas no coinciden. Por favor, verifica.');
                    passwordConfirmInput.focus();
                    return false;
                }
                
                const tipo = document.querySelector('input[name="tipoUsuario"]:checked').value;
                if (tipo === 'Asistente') {
                    const apellido = document.getElementById('apellido').value.trim();
                    const fechaNacimiento = document.getElementById('fechaNacimiento').value;
                    
                    if (!apellido) {
                        e.preventDefault();
                        alert('El apellido es obligatorio para asistentes.');
                        document.getElementById('apellido').focus();
                        return false;
                    }
                    if (!fechaNacimiento) {
                        e.preventDefault();
                        alert('La fecha de nacimiento es obligatoria para asistentes.');
                        document.getElementById('fechaNacimiento').focus();
                        return false;
                    }
                }
            });

            // Initialize on page load
            toggleFields();
            
            // AJAX Validation for Nickname
            let nicknameTimeout;
            document.getElementById('nickname').addEventListener('keyup', function() {
                clearTimeout(nicknameTimeout);
                const nickname = this.value.trim();
                const msgElement = document.getElementById('nicknameMsg');
                
                if (nickname.length < 3) {
                    msgElement.textContent = 'Mínimo 3 caracteres';
                    msgElement.style.color = '#666';
                    return;
                }
                
                msgElement.textContent = 'Verificando...';
                msgElement.style.color = '#666';
                
                nicknameTimeout = setTimeout(function() {
                    fetch('${pageContext.request.contextPath}/checkNickname?nickname=' + encodeURIComponent(nickname))
                        .then(response => response.json())
                        .then(data => {
                            msgElement.textContent = data.message;
                            msgElement.style.color = data.available ? '#28a745' : '#dc3545';
                        })
                        .catch(error => {
                            msgElement.textContent = 'Error al verificar';
                            msgElement.style.color = '#dc3545';
                        });
                }, 500);
            });
            
            // AJAX Validation for Email
            let emailTimeout;
            document.getElementById('correo').addEventListener('keyup', function() {
                clearTimeout(emailTimeout);
                const email = this.value.trim();
                const msgElement = document.getElementById('emailMsg');
                
                if (!email.includes('@')) {
                    msgElement.textContent = '';
                    return;
                }
                
                msgElement.textContent = 'Verificando...';
                msgElement.style.color = '#666';
                
                emailTimeout = setTimeout(function() {
                    fetch('${pageContext.request.contextPath}/checkEmail?email=' + encodeURIComponent(email))
                        .then(response => response.json())
                        .then(data => {
                            msgElement.textContent = data.message;
                            msgElement.style.color = data.available ? '#28a745' : '#dc3545';
                        })
                        .catch(error => {
                            msgElement.textContent = 'Error al verificar';
                            msgElement.style.color = '#dc3545';
                        });
                }, 500);
            });
        });
    </script>

</body>
</html>
