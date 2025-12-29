<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "Mis Registros - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <c:choose>
        <c:when test="${empty registros}">
            <div class="alert alert-info text-center mt-4">
                <h5>No tienes registros actualmente</h5>
                <p>Aún no te has registrado en ninguna edición de evento.</p>
                <a href="${pageContext.request.contextPath}/eventos" class="btn btn-primary">
                    Ver Eventos Disponibles
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th>Edición</th>
                           
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="registro" items="${registros}">
                            <tr>
                                <td>${registro.edicion.nombre}</td>
                                <td>
                                    <a href="?action=detail&edicion=${registro.nombreEdicion}" 
                                       class="btn btn-outline-primary btn-sm">
                                        Ver
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../template/footer.jsp"/>
