<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    request.setAttribute("pageTitle", "Eventos - EventosUY Móvil");
%>
<jsp:include page="../template/head.jsp"/>
<jsp:include page="../template/navbar.jsp"/>

<div class="container mt-4">
    <h2 class="mb-4">Eventos Disponibles</h2>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </c:if>
    
    <c:if test="${empty eventos}">
        <div class="alert alert-info" role="alert">
            No hay eventos disponibles en este momento.
        </div>
    </c:if>
    
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <c:forEach var="evento" items="${eventos}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <c:choose>
                        <c:when test="${not empty evento.imagen  && evento.imagen != '-'}">
                            <img src="${pageContext.request.contextPath}/media/images/${evento.imagen}" 
                                 class="card-img-top" alt="${evento.nombre}" 
                                 style="height: 200px; object-fit: cover;"
                                 onerror="this.src='${pageContext.request.contextPath}/media/images/IMG-EV02.jpeg'">
                        </c:when>
                        <c:otherwise>
                            <span style="color: white; font-size: 15px; font-weight: bold;">
                                ${evento.sigla} 
                            </span>
                        </c:otherwise>
                    </c:choose>
                    <div class="card-body">
                        <h5 class="card-title">${evento.nombre}</h5>
                        <p class="card-text">
                            <small class="text-muted">
                                Sigla: ${evento.sigla}
                                <c:if test="${not empty evento.categorias}">
                                    <br>Categorías: 
                                    <c:forEach var="categoria" items="${evento.categorias}" varStatus="status">
                                        ${categoria.nombre}<c:if test="${!status.last}">, </c:if>
                                    </c:forEach>
                                </c:if>
                            </small>
                        </p>
                        <a href="${pageContext.request.contextPath}/eventos?action=detail&nombre=${evento.nombre}" 
                           class="btn btn-primary btn-sm w-100">
                            Ver Ediciones →
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../template/footer.jsp"/>
