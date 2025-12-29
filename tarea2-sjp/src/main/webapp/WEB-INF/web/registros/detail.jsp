<%@page import="publicar.ws.client.RegistroDTO"%>
<%@page import="publicar.ws.client.EdicionDTO"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<jsp:include page="/WEB-INF/web/template/head.jsp">
		<jsp:param name="title" value="Detalle de Registro :: eventos.uy" />
	</jsp:include>
</head>
<body>
	<!-- Include Header Component -->
	<jsp:include page="/WEB-INF/web/template/header.jsp"/>

	<% 
		RegistroDTO registro = (RegistroDTO) request.getAttribute("registro");
		EdicionDTO edicion = (EdicionDTO) request.getAttribute("edicion");
		String edicionFechaInicio = (String) request.getAttribute("edicionFechaInicio");
		String edicionFechaFin = (String) request.getAttribute("edicionFechaFin");
		String registroFecha = (String) request.getAttribute("registroFecha");
	%>

	<div class="main-layout">
		<!-- Include Sidebar Component -->
		<jsp:include page="/WEB-INF/web/template/sidebar.jsp"/>

		<!-- Main Content -->
		<div class="content-area">
			<!-- Page Title -->
			<div class="page-content">
				<h1 class="page-title">Detalle de Registro</h1>
			</div>

			<!-- Edition Summary -->
			<div class="edition-detail-container" style="margin-bottom: 30px;">
				<div class="edition-main">
					<div class="event-image">
						<% if (edicion != null && edicion.getImagen() != null && !edicion.getImagen().isEmpty() && !edicion.getImagen().equals("-")) { %>
							<img src="${pageContext.request.contextPath}/media/images/<%= edicion.getImagen() %>" 
								 alt="<%= edicion.getNombre() %>" 
								 style="width: 100%; height: 100%; object-fit: cover;">
						<% } else { %>
							<div style="background-color: #007bff; color: white; width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; text-align: center; padding: 10px; font-size: 18px; font-weight: bold;">
								<%= edicion != null ? edicion.getNombre() : "Evento" %>
							</div>
						<% } %>
					</div>
					<div class="edition-info">
						<h2><%= edicion != null ? edicion.getNombre() : "Edición" %></h2>
						<% if (edicion != null) { %>
							<p><strong>Evento:</strong> <a href="${pageContext.request.contextPath}/eventos?nombre=<%= edicion.getEvento() %>"><%= edicion.getEvento() %></a></p>
							<% if (edicion.getSigla() != null && !edicion.getSigla().isEmpty()) { %>
								<p><strong>Sigla:</strong> <%= edicion.getSigla() %></p>
							<% } %>
							
							<% if (edicionFechaInicio != null && edicionFechaFin != null) { %>
								<p><strong>Fechas:</strong> <%= edicionFechaInicio %> - <%= edicionFechaFin %></p>
							<% } %>
						<% } %>
						<% if (registro != null && registro.getTipoRegistro() != null) { %>
							<p><strong>Tipo de Registro:</strong> <%= registro.getTipoRegistro().getNombre() %></p>
							<p><strong>Costo:</strong> $<%= registro.getTipoRegistro().getCosto() %></p>
						<% } %>
						<% if (registroFecha != null) { %>
							<p><strong>Fecha de Registro:</strong> <%= registroFecha %></p>
						<% } %>
					</div>
				</div>
			</div>

	

			<!-- Registration Details -->
			<div class="registration-section">
				<div class="registration-header">Información del Registro</div>
				
				<div class="form-container" style="background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
					<% if (registro != null && registro.getTipoRegistro() != null) { %>
						<div class="form-group">
							<label class="form-label">Tipo de Registro</label>
							<div class="info-box">
								<h4><%= registro.getTipoRegistro().getNombre() %></h4>
								<% if (registro.getTipoRegistro().getDescripcion() != null && !registro.getTipoRegistro().getDescripcion().isEmpty()) { %>
									<p><%= registro.getTipoRegistro().getDescripcion() %></p>
								<% } %>
								<p><strong>Costo:</strong> $<%= registro.getTipoRegistro().getCosto() %></p>
							</div>
						</div>
					<% } %>

					<% if (edicion != null) { %>
						<div class="form-group">
							<label class="form-label">Información de la Edición</label>
							<div class="info-box">
								<p><strong>Nombre:</strong> <%= edicion.getNombre() %></p>
								<% if (edicion.getDescripcion() != null && !edicion.getDescripcion().isEmpty()) { %>
									<p><strong>Descripción:</strong> <%= edicion.getDescripcion() %></p>
								<% } %>
								<% if (edicionFechaInicio != null && edicionFechaFin != null) { %>
									<p><strong>Período:</strong> <%= edicionFechaInicio %> - <%= edicionFechaFin %></p>
								<% } %>
							</div>
						</div>
					<% } %>

					<!-- Form Actions -->
					<div class="form-group" style="text-align: center; margin-top: 30px;">
						<a href="${pageContext.request.contextPath}/perfil" class="btn btn-secondary">Volver al Perfil</a>
						<% if (edicion != null) { %>
							<a href="${pageContext.request.contextPath}/ediciones?edicion=${edicion.nombre}&evento=${evento.nombre}" class="btn btn-primary">Ver Edición</a>							
						<% } %>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

