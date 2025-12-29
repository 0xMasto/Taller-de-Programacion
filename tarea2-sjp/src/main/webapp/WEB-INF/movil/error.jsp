<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("pageTitle", "Error - EventosUY Móvil");
%>
<jsp:include page="template/head.jsp"/>
<jsp:include page="template/navbar.jsp"/>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card border-danger">
                <div class="card-header bg-danger text-white">
                    <h5 class="mb-0">Error</h5>
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "Ha ocurrido un error inesperado" %>
                    </p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                        Volver al Inicio
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="template/footer.jsp"/>
