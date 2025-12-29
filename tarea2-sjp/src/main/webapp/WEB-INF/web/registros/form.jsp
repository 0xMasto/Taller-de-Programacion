<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="title" value="Registro a Edición - eventos.uy" />
    </jsp:include>
</head>
<body>

<c:set var="edicion" value="${requestScope.edicion}" />
<c:set var="tiposRegistro" value="${requestScope.tiposRegistro}" />
<c:set var="asistenteDTO" value="${sessionScope.usuario_logueado}" />

<!-- Session validation: Only Asistente can access -->
<c:if test="${sessionScope.tipo_usuario ne 'Asistente'}">
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
            <h1 class="page-title">Registro a Edición de Evento</h1>

            <!-- Edition Summary -->
            <c:if test="${not empty edicion}">
                <div class="edition-detail-container" style="margin-bottom: 30px;">
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
                            <c:if test="${not empty edicion.evento}">
                                <p><strong>Evento:</strong> 
                                    <a href="${pageContext.request.contextPath}/eventos?id=${edicion.evento}">
                                        <c:out value="${edicion.evento}" />
                                    </a>
                                </p>
                            </c:if>
                            <p><strong>Sigla:</strong> <c:out value="${edicion.sigla}" /></p>
                            <p><strong>Ubicación:</strong> <c:out value="${edicion.ciudad}" />, <c:out value="${edicion.pais}" /></p>
                            <c:if test="${not empty edicionFechaInicio && not empty edicionFechaFin}">
                                <p><strong>Fechas:</strong> 
                                    <c:out value="${edicionFechaInicio}" /> - 
                                    <c:out value="${edicionFechaFin}" />
                                </p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Registration Status Messages -->
            <div id="registrationMessages">
                <!-- Success Message -->
                <c:if test="${not empty requestScope.success}">
                    <div class="alert alert-success">
                        <strong>✅ Registro Exitoso</strong><br>
                        <c:out value="${requestScope.success}" />
                    </div>
                </c:if>

                <!-- Error Messages -->
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger">
                        <strong>❌ Error en el registro</strong><br>
                        <c:out value="${requestScope.error}" />
                    </div>
                </c:if>
            </div>

            <!-- Registration Form -->
            <div class="registration-section">
                <div class="registration-header">Formulario de Registro</div>
                
                <form id="registrationForm" method="POST" 
                      action="${pageContext.request.contextPath}/registros?action=new" 
                      class="form-container" 
                      style="background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">

                    <!-- Hidden field for edition -->
                    <input type="hidden" name="edicion" value="${edicion.nombre}" />

                    <!-- Registration Type Selection -->
                    <div class="form-group">
                        <label class="form-label">Tipo de Registro *</label>
                        <c:choose>
                            <c:when test="${empty tiposRegistro}">
                                <div class="alert alert-warning">
                                    No hay tipos de registro disponibles para esta edición.
                                </div>
                            </c:when>
                            <c:otherwise>
                                <select class="form-control" name="tipoRegistro" id="tipoRegistro" required onchange="updateRegistrationTypeInfo()">
                                    <option value="">Selecciona un tipo de registro...</option>
                                    <c:forEach var="tipo" items="${tiposRegistro}">
                                        <option value="${tipo.nombre}" 
                                                data-costo="${tipo.costo}" 
                                                data-cupo="${tipo.cupoDisponible}"
                                                <c:if test="${requestScope.tipoRegistroSeleccionado eq tipo.nombre}">selected</c:if>>
                                            <c:out value="${tipo.nombre}" /> - 
                                            $<fmt:formatNumber value="${tipo.costo}" pattern="#,##0.00" /> 
                                            (Cupos disponibles: ${tipo.cupoDisponible})
                                        </option>
                                    </c:forEach>
                                </select>
                                <div id="typeInfo" class="form-text" style="margin-top: 10px;"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Registration Method Selection -->
                    <div class="form-group">
                        <label class="form-label">Forma de registro *</label>
                        <div class="checkbox-group" style="grid-template-columns: 1fr 1fr;">
                            <div class="checkbox-item">
                                <input type="radio" id="generalRegistration" name="registrationMethod" 
                                       value="general" checked onchange="toggleSponsorshipCode()">
                                <label for="generalRegistration">Registro General</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="radio" id="sponsorshipRegistration" name="registrationMethod" 
                                       value="sponsorship" onchange="toggleSponsorshipCode()">
                                <label for="sponsorshipRegistration">Con Código de Patrocinio</label>
                            </div>
                        </div>
                    </div>

                    <!-- Sponsorship Code Input (hidden by default) -->
                    <div class="form-group hidden" id="sponsorshipCodeGroup">
                        <label for="sponsorshipCode" class="form-label">Código de Patrocinio</label>
                        <input type="text" id="sponsorshipCode" name="sponsorshipCode" 
                               class="form-control" 
                               placeholder="Ingresa tu código de patrocinio" 
                               style="text-transform: uppercase;">
                        <div class="form-text">
                            El código debe ser válido para esta edición, tu institución y el tipo de registro seleccionado.
                        </div>
                    </div>

                    <!-- User Information (readonly) -->
                    <div class="form-group">
                        <label class="form-label">Información del usuario</label>
                        <div class="grid-2">
                            <div>
                                <label for="userName" class="form-label">Nombre completo</label>
                                <input type="text" id="userName" name="userName" 
                                       class="form-control" 
                                       value="${asistenteDTO.nombre} ${asistenteDTO.apellido}" 
                                       readonly>
                            </div>
                            <div>
                                <label for="userEmail" class="form-label">Email</label>
                                <input type="email" id="userEmail" name="userEmail" 
                                       class="form-control" 
                                       value="${asistenteDTO.correo}" 
                                       readonly>
                            </div>
                        </div>
                        <c:if test="${not empty asistenteDTO.institucion}">
                            <div>
                                <label for="userInstitution" class="form-label">Institución</label>
                                <input type="text" id="userInstitution" name="userInstitution" 
                                       class="form-control" 
                                       value="${asistenteDTO.institucion.nombre}" 
                                       readonly>
                            </div>
                        </c:if>
                    </div>

                    <!-- Form Actions -->
                    <div class="form-group" style="text-align: center; margin-top: 30px;">
                        <button type="submit" class="btn btn-success register-btn">Confirmar Registro</button>
                        <a href="${pageContext.request.contextPath}/ediciones?id=${edicion.nombre}" 
                           class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Toggle sponsorship code visibility
    function toggleSponsorshipCode() {
        const sponsorshipRadio = document.getElementById('sponsorshipRegistration');
        const sponsorshipCodeGroup = document.getElementById('sponsorshipCodeGroup');
        
        if (sponsorshipRadio.checked) {
            sponsorshipCodeGroup.classList.remove('hidden');
            document.getElementById('sponsorshipCode').required = true;
        } else {
            sponsorshipCodeGroup.classList.add('hidden');
            document.getElementById('sponsorshipCode').value = '';
            document.getElementById('sponsorshipCode').required = false;
        }
    }

    // Update registration type info dynamically
    function updateRegistrationTypeInfo() {
        const select = document.getElementById('tipoRegistro');
        const selectedOption = select.options[select.selectedIndex];
        const infoDiv = document.getElementById('typeInfo');
        
        if (selectedOption.value) {
            const costo = selectedOption.getAttribute('data-costo');
            const cupo = selectedOption.getAttribute('data-cupo');
            
            if (cupo <= 0) {
                infoDiv.innerHTML = '<div class="alert alert-danger" style="padding: 10px; margin: 0;"><strong>⚠️ Cupo Agotado</strong><br>Este tipo de registro ya no tiene cupos disponibles.</div>';
                document.querySelector('.register-btn').disabled = true;
            } else if (cupo <= 5) {
                infoDiv.innerHTML = '<div class="alert alert-warning" style="padding: 10px; margin: 0;"><strong>⚠️ Últimos cupos</strong><br>Quedan pocos cupos disponibles para este tipo de registro.</div>';
                document.querySelector('.register-btn').disabled = false;
            } else {
                infoDiv.innerHTML = '';
                document.querySelector('.register-btn').disabled = false;
            }
        } else {
            infoDiv.innerHTML = '';
            document.querySelector('.register-btn').disabled = false;
        }
    }

    // Initialize on page load
    document.addEventListener('DOMContentLoaded', function() {
        updateRegistrationTypeInfo();
    });
</script>

</body>
</html>

