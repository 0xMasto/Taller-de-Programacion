<%@page import="publicar.ws.client.AsistenteDTO"%>
<%@page import="publicar.ws.client.OrganizadorDTO"%>
<%@page import="publicar.ws.client.RegistroDTO"%>
<%@page import="publicar.ws.client.EdicionDTO"%>
<%@page import="java.util.Collection"%>
<%@page import="ws.WSTypeConverter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp" %>
<!doctype html>
<html lang="es">
<head>
	<jsp:include page="/WEB-INF/web/template/head.jsp">
		<jsp:param name="title" value="Perfil de Usuario :: eventos.uy" />
	</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/web/template/header.jsp"/>

	<% 
		Object usuario = request.getAttribute("usuario");
		String tipo = (String) request.getAttribute("tipoUsuario");
		AsistenteDTO asistente = usuario instanceof AsistenteDTO ? (AsistenteDTO) usuario : null;
		OrganizadorDTO organizador = usuario instanceof OrganizadorDTO ? (OrganizadorDTO) usuario : null;
		
		// Capitalize first letter for display
		if (tipo != null && tipo.length() > 0) {
			tipo = tipo.substring(0, 1).toUpperCase() + tipo.substring(1);
		}
	%>

	<div class="main-layout">
		<!-- Sidebar -->
		<jsp:include page="/WEB-INF/web/template/sidebar.jsp"/>

		<!-- Main Content -->
		<div class="content-area">
			<!-- Profile Content -->
			<div class="profile-main">
				<div class="profile-avatar-section">
					<div class="profile-avatar-large">
						<% 
							String userImagen = asistente != null ? asistente.getImagen() : organizador.getImagen();
							String userName = asistente != null ? asistente.getNombre() : organizador.getNombre();
							if (userImagen != null && !userImagen.isEmpty() && !userImagen.equals("-")) { 
						%>
							<img src="${pageContext.request.contextPath}/media/images/<%= userImagen %>" 
								 alt="<%= userName %>" 
								 style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">
						<% } else { %>
							<span style="color: white; font-size: 48px; font-weight: bold;">
								<%= userName.substring(0, 1).toUpperCase() %>
							</span>
						<% } %>
					</div>
					<div class="profile-name">
						<h2><%= userName %><% if (asistente != null) { %> <%= asistente.getApellido() %><% } %></h2>
						<p>
							<strong>@<%= asistente != null ? asistente.getNickname() : organizador.getNickname() %></strong> 
							• 
							<span class="user-type-badge <%= tipo.equals("Asistente") ? "badge-asistente" : "badge-organizador" %>">
								<%= tipo %>
							</span>
						</p>
						<% if (asistente != null && asistente.getInstitucion() != null) { %>
							<p style="color: #666;">Estudiante - <%= asistente.getInstitucion().getNombre() %></p>
						<% } else if (organizador != null && organizador.getDescripcion() != null && !organizador.getDescripcion().isEmpty()) { %>
							<p style="color: #666;"><%= organizador.getDescripcion() %></p>
						<% } %>
					</div>
				</div>

				<!-- Profile Tabs -->
				<div class="profile-tabs">
					<div class="profile-tab active">Información</div>
					<% if (organizador != null) { %>
						<div class="profile-tab">Ediciones</div>
					<% } %>
				</div>

				<!-- Profile Content -->
				<div class="profile-content">
					<!-- Information Tab -->
					<div class="tab-content active">
						<div class="profile-section">
							<div class="section-title">Información Pública</div>
							<div class="profile-info">
								<p><strong>Nombre:</strong> <%= userName %><% if (asistente != null) { %> <%= asistente.getApellido() %><% } %></p>
								<p><strong>Nickname:</strong> <%= asistente != null ? asistente.getNickname() : organizador.getNickname() %></p>
								<p><strong>Email:</strong> <%= asistente != null ? asistente.getCorreo() : organizador.getCorreo() %></p>
								<% if (asistente != null) { %>
									<% if (asistente.getInstitucion() != null) { %>
										<p><strong>Institución:</strong> <%= asistente.getInstitucion().getNombre() %></p>
									<% } %>
									<p><strong>Fecha de nacimiento:</strong> <%= WSTypeConverter.formatDateString(asistente.getFechaNacimiento()) %></p>
								<% } %>
								<% if (organizador != null && organizador.getSitioWeb() != null && !organizador.getSitioWeb().isEmpty()) { %>
									<p><strong>Sitio Web:</strong> <a href="<%= organizador.getSitioWeb() %>" target="_blank"><%= organizador.getSitioWeb() %></a></p>
								<% } %>
								<p><strong>Tipo de usuario:</strong> <%= tipo %></p>
							</div>
						</div>

						<% if (organizador != null && organizador.getDescripcion() != null && !organizador.getDescripcion().isEmpty()) { %>
							<div class="profile-section">
								<div class="section-title">Descripción</div>
								<div class="profile-info">
									<p><%= organizador.getDescripcion() %></p>
								</div>
							</div>
						<% } %>
					</div>

					<!-- Second Tab: Ediciones (Organizador only) -->
					<div class="tab-content">
						<% if (organizador != null) { %>
							<!-- Ediciones Tab -->
							<div class="profile-section">
								<div class="section-title">Ediciones organizadas</div>
								<% 
									Collection<EdicionDTO> ediciones = (Collection<EdicionDTO>) request.getAttribute("ediciones");
									Boolean isOwnProfile = (Boolean) request.getAttribute("isOwnProfile");
									if (ediciones != null && !ediciones.isEmpty()) {
								%>
									<div class="registrations-list">
										<% for (EdicionDTO edicion : ediciones) { 
											String estadoBadgeClass = "";
											String estadoText = edicion.getEstado() != null ? edicion.getEstado() : "SIN DEFINIR";
											
											if ("ACEPTADA".equals(edicion.getEstado())) {
												estadoBadgeClass = "badge-accepted";
											} else if ("INGRESADA".equals(edicion.getEstado())) {
												estadoBadgeClass = "badge-pending";
											} else if ("RECHAZADA".equals(edicion.getEstado())) {
												estadoBadgeClass = "badge-rejected";
											}
										%>
											<div class="registration-card">
												<div class="registration-header">
													<div>
														<div class="registration-title"><%= edicion.getNombre() %></div>
														<div style="margin-top: 8px;">
															<span class="status-badge <%= estadoBadgeClass %>"><%= estadoText %></span>
														</div>
													</div>
												</div>
												<div class="registration-details" style="margin-top: 10px;">
													<% if (edicion.getCiudad() != null && edicion.getPais() != null) { %>
														<p><strong>Ubicación:</strong> <%= edicion.getCiudad() %>, <%= edicion.getPais() %></p>
													<% } %>
													<% if (edicion.getFechaInicio() != null && edicion.getFechaFin() != null) { %>
														<p><strong>Fechas:</strong> 
															<%= WSTypeConverter.formatDateString(edicion.getFechaInicio()) %> - 
															<%= WSTypeConverter.formatDateString(edicion.getFechaFin()) %>
														</p>
													<% } %>
												</div>
												<div style="margin-top: 15px;">
													<a href="${pageContext.request.contextPath}/ediciones?id=<%= java.net.URLEncoder.encode(edicion.getNombre(), "UTF-8") %><% if (edicion.getEvento() != null) { %>&evento=<%= java.net.URLEncoder.encode(edicion.getEvento(), "UTF-8") %><% } %>" 
													   class="btn btn-primary">Ver Edición</a>
												</div>
											</div>
										<% } %>
									</div>
								<% } else { %>
									<p style="color: #666; text-align: center; padding: 20px;">
										<%= (isOwnProfile != null && isOwnProfile) 
											? "No has organizado ninguna edición aceptada aún." 
											: "Este organizador no tiene ediciones aceptadas." %>
									</p>
								<% } %>
							</div>
						<% } %>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		// Tab switching functionality
		(function() {
			var tabs = document.querySelectorAll('.profile-tab');
			var contents = document.querySelectorAll('.tab-content');
			if (!tabs.length || !contents.length) return;
			
			tabs.forEach(function(tab, index) {
				tab.addEventListener('click', function() {
					tabs.forEach(function(t) { t.classList.remove('active'); });
					contents.forEach(function(c) { c.classList.remove('active'); });
					tab.classList.add('active');
					if (contents[index]) { contents[index].classList.add('active'); }
				});
			});
		})();
	</script>
</body>
</html>

