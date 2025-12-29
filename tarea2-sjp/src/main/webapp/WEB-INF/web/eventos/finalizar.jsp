<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Finalizar Evento - EventosUY</title>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="currentPage" value="finalizar-evento" />
    </jsp:include>
</head>
<body>
<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<c:if test="${sessionScope.tipo_usuario ne 'Organizador'}">
    <c:url var="loginUrl" value="/iniciar-sesion" />
    <c:redirect url="${loginUrl}" />
</c:if>

<div class="main-layout">
    <!-- Include Sidebar Component -->
    <jsp:include page="/WEB-INF/web/template/sidebar.jsp" />
    
    <!-- Main Content -->
    <div class="content-area">
        <div class="page-content">
            <h1 class="page-title">Finalizar Evento</h1>
    <p>Marcar un evento como finalizado significa que <strong>no se podrán crear más ediciones</strong> para ese evento 
    y <strong>no aparecerá en los listados ni búsquedas</strong> del sistema.</p>
    
    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger" role="alert">
            ${requestScope.error}
        </div>
    </c:if>
    
    <c:if test="${not empty requestScope.success}">
        <div class="alert alert-success" role="alert">
            ${requestScope.success}
        </div>
    </c:if>
    
    <c:choose>
        <c:when test="${empty requestScope.eventos || requestScope.eventos.length == 0}">
            <div class="alert alert-info" role="alert">
                No hay eventos disponibles para finalizar.
            </div>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/eventos/finalizar" method="post" onsubmit="return confirmarFinalizacion();">
                <div class="form-group">
                    <label for="nombreEvento">Seleccione el evento a finalizar:</label>
                    <select class="form-control" id="nombreEvento" name="nombreEvento" required>
                        <option value="">-- Seleccione un evento --</option>
                        <c:forEach var="evento" items="${requestScope.eventos}">
                            <option value="${evento}">${evento}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="confirmacion" name="confirmacion" value="SI" required>
                    <label class="form-check-label" for="confirmacion">
                        Confirmo que deseo finalizar este evento (esta acción no se puede deshacer)
                    </label>
                </div>
                
                <button type="submit" class="btn btn-danger">Finalizar Evento</button>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancelar</a>
            </form>
        </c:otherwise>
    </c:choose>
        </div>
    </div>
</div>

<!-- Include Footer -->
<jsp:include page="/WEB-INF/web/template/footer.jsp" />

<script>
function confirmarFinalizacion() {
    var eventoSelect = document.getElementById('nombreEvento');
    var nombreEvento = eventoSelect.options[eventoSelect.selectedIndex].text;
    var confirmacion = document.getElementById('confirmacion');
    
    if (!confirmacion.checked) {
        alert('Debe confirmar la finalización del evento');
        return false;
    }
    
    return confirm('¿Está seguro que desea finalizar el evento "' + nombreEvento + '"?\n\n' +
                   'Una vez finalizado:\n' +
                   '- No se podrán crear más ediciones\n' +
                   '- No aparecerá en listados ni búsquedas\n\n' +
                   'Esta acción no se puede deshacer.');
}
</script>

<style>
.alert {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
}

.alert-danger {
    color: #721c24;
    background-color: #f8d7da;
    border-color: #f5c6cb;
}

.alert-success {
    color: #155724;
    background-color: #d4edda;
    border-color: #c3e6cb;
}

.alert-info {
    color: #004085;
    background-color: #cce5ff;
    border-color: #b8daff;
}

.form-group {
    margin-bottom: 15px;
}

.form-control {
    display: block;
    width: 100%;
    padding: 8px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.form-check {
    padding-left: 20px;
}

.form-check-input {
    margin-left: -20px;
    margin-right: 5px;
}

.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    border: 1px solid transparent;
    border-radius: 4px;
    text-decoration: none;
}

.btn-danger {
    color: #fff;
    background-color: #d9534f;
    border-color: #d43f3a;
}

.btn-danger:hover {
    background-color: #c9302c;
    border-color: #ac2925;
}

.btn-secondary {
    color: #fff;
    background-color: #6c757d;
    border-color: #6c757d;
}

.btn-secondary:hover {
    background-color: #5a6268;
    border-color: #545b62;
}
</style>

</body>
</html>
