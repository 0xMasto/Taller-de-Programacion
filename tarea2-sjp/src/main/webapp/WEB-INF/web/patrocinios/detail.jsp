<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Detalle del Patrocinio - EventosUY</title>
    <jsp:include page="/WEB-INF/web/template/head.jsp">
        <jsp:param name="currentPage" value="patrocinio-detail" />
    </jsp:include>
</head>
<body>
<!-- Include Header Component -->
<jsp:include page="/WEB-INF/web/template/header.jsp" />

<!-- Include Sidebar Component -->
<jsp:include page="/WEB-INF/web/template/sidebar.jsp" />

<div class="main">
    <h1>Detalle del Patrocinio</h1>
    
    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger" role="alert">
            ${requestScope.error}
        </div>
    </c:if>
    
    <c:set var="patrocinio" value="${requestScope.patrocinio}" />
    
    <c:if test="${not empty patrocinio}">
        <div style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin-top: 20px;">
            
            <!-- Patrocinio Information -->
            <div style="margin-bottom: 30px;">
                <h2 style="color: #007bff; margin-bottom: 20px;">
                    <c:out value="${patrocinio.institucion.nombre}" />
                </h2>
                
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 20px;">
                    <div>
                        <p><strong>Código de Patrocinio:</strong></p>
                        <p style="font-size: 1.1em; color: #666;">
                            <c:out value="${patrocinio.codigoPatrocinio}" />
                        </p>
                    </div>
                    
                    <div>
                        <p><strong>Nivel de Patrocinio:</strong></p>
                        <p style="font-size: 1.1em; color: #666;">
                            <c:out value="${patrocinio.nivelPatrocinio}" />
                        </p>
                    </div>
                    
                    <div>
                        <p><strong>Aporte Económico:</strong></p>
                        <p style="font-size: 1.1em; color: #28a745; font-weight: bold;">
                            $ <c:out value="${patrocinio.aporteEconomico}" />
                        </p>
                    </div>
                    
                    <div>
                        <p><strong>Cantidad de Registros:</strong></p>
                        <p style="font-size: 1.1em; color: #666;">
                            <c:out value="${patrocinio.cantidadRegistros}" />
                        </p>
                    </div>
                </div>
            </div>
            
            <!-- Institution Information -->
            <c:if test="${not empty patrocinio.institucion}">
                <div style="border-top: 2px solid #eee; padding-top: 20px;">
                    <h3 style="color: #333; margin-bottom: 15px;">Información de la Institución</h3>
                    
                    <p><strong>Nombre:</strong></p>
                    <p style="margin-bottom: 15px; color: #666;">
                        <c:url var="institucionUrl" value="/instituciones">
                            <c:param name="nombre" value="${patrocinio.institucion.nombre}" />
                        </c:url>
                        <a href="${institucionUrl}">
                            <c:out value="${patrocinio.institucion.nombre}" />
                        </a>
                    </p>
                    
                    <c:if test="${not empty patrocinio.institucion.descripcion}">
                        <p><strong>Descripción:</strong></p>
                        <p style="margin-bottom: 15px; color: #666;">
                            <c:out value="${patrocinio.institucion.descripcion}" />
                        </p>
                    </c:if>
                    
                    <c:if test="${not empty patrocinio.institucion.sitioWeb}">
                        <p><strong>Sitio Web:</strong></p>
                        <p style="margin-bottom: 15px;">
                            <a href="${patrocinio.institucion.sitioWeb}" target="_blank" style="color: #007bff;">
                                <c:out value="${patrocinio.institucion.sitioWeb}" />
                            </a>
                        </p>
                    </c:if>
                </div>
            </c:if>
            
            <!-- Related Edition -->
            <c:if test="${not empty patrocinio.nombreEdicion}">
                <div style="border-top: 2px solid #eee; padding-top: 20px; margin-top: 20px;">
                    <h3 style="color: #333; margin-bottom: 15px;">Edición Asociada</h3>
                    <p>
                        <c:url var="edicionUrl" value="/ediciones">
                            <c:param name="nombre" value="${patrocinio.nombreEdicion}" />
                        </c:url>
                        <a href="${edicionUrl}" class="btn btn-primary">
                            Ver Edición: <c:out value="${patrocinio.nombreEdicion}" />
                        </a>
                    </p>
                </div>
            </c:if>
            
            <!-- Navigation -->
            <div style="margin-top: 30px; padding-top: 20px; border-top: 2px solid #eee;">
                <a href="javascript:history.back()" class="btn btn-secondary">
                    ← Volver
                </a>
            </div>
        </div>
    </c:if>
</div>

<!-- Include Footer -->
<jsp:include page="/WEB-INF/web/template/footer.jsp" />

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

.btn {
    display: inline-block;
    padding: 8px 16px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.5;
    text-align: center;
    text-decoration: none;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    border: 1px solid transparent;
    border-radius: 4px;
    transition: all 0.15s ease-in-out;
}

.btn-primary {
    color: #fff;
    background-color: #007bff;
    border-color: #007bff;
}

.btn-primary:hover {
    background-color: #0056b3;
    border-color: #004085;
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

