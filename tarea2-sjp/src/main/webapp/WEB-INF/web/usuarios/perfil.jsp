<%@page import="publicar.ws.client.AsistenteDTO"%>
<%@page import="publicar.ws.client.OrganizadorDTO"%>
<%@page import="publicar.ws.client.RegistroDTO"%>
<%@page import="publicar.ws.client.EdicionDTO"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp" %>
<!doctype html>
<html lang="es">
<head>
	<jsp:include page="/WEB-INF/web/template/head.jsp">
		<jsp:param name="title" value="Mi Perfil :: eventos.uy" />
	</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/web/template/header.jsp"/>

	<% 
		Object usuario = request.getAttribute("usuario");
		String tipo = (String) request.getAttribute("tipo");
		AsistenteDTO asistente = usuario instanceof AsistenteDTO ? (AsistenteDTO) usuario : null;
		OrganizadorDTO organizador = usuario instanceof OrganizadorDTO ? (OrganizadorDTO) usuario : null;
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
					<% if (asistente != null) { %>
						<div class="profile-tab">Mis Registros</div>
					<% } else { %>
						<div class="profile-tab">Mis Ediciones</div>
					<% } %>
				</div>

				<!-- Profile Content -->
				<div class="profile-content">
					<!-- Information Tab -->
					<div class="tab-content active">
						<div class="profile-section">
							<div class="section-title">Información Personal</div>
							<div class="profile-info">
								<p><strong>Nombre:</strong> <%= userName %><% if (asistente != null) { %> <%= asistente.getApellido() %><% } %></p>
								<p><strong>Nickname:</strong> <%= asistente != null ? asistente.getNickname() : organizador.getNickname() %></p>
								<p><strong>Email:</strong> <%= asistente != null ? asistente.getCorreo() : organizador.getCorreo() %></p>
								<% if (asistente != null) { %>
									<% if (asistente.getInstitucion() != null) { %>
										<p><strong>Institución:</strong> <%= asistente.getInstitucion().getNombre() %></p>
									<% } %>
									<p><strong>Fecha de nacimiento:</strong> <%= asistente.getFechaNacimiento() %></p>
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

					<!-- Second Tab: Mis Registros (Asistente) or Mis Ediciones (Organizador) -->
					<div class="tab-content">
						<% if (asistente != null) { %>
							<!-- Registros Tab -->
							<div class="profile-section">
								<div class="section-title">Historial de Registros</div>
								<% 
									Collection<RegistroDTO> registros = (Collection<RegistroDTO>) request.getAttribute("registros");
									Map<String, String> edicionToEvento = (Map<String, String>) request.getAttribute("edicionToEvento");
									if (registros != null && !registros.isEmpty()) {
								%>
									<div class="registrations-list">
										<% for (RegistroDTO registro : registros) { 
											String nombreEdicion = registro.getEdicion().getNombre();
											String nombreEvento = edicionToEvento != null ? edicionToEvento.get(nombreEdicion) : "N/A";
											String tipoRegistroNombre = registro.getTipoRegistro().getNombre();
										%>
											<div class="registration-card">
												<div class="registration-header">
													<div class="registration-title">
														<%= nombreEdicion %> - <%= tipoRegistroNombre %>
													</div>
													<div class="registration-date">
														<%= registro.getFechaRegistro() %>
													</div>
												</div>
												<div class="registration-info">
													<p><strong>Evento:</strong> <%= nombreEvento %></p>
													<p><strong>Costo:</strong> $<%= registro.getTipoRegistro().getCosto() %></p>
												</div>
												<div style="margin-top: 15px;">
													<a href="${pageContext.request.contextPath}/registros?action=detail&edicion=<%= nombreEdicion %>&tipo=<%= tipoRegistroNombre %>" 
													   class="btn btn-primary">Ver detalles</a>
												</div>
											</div>
										<% } %>
									</div>
								<% } else { %>
									<p style="color: #666; text-align: center; padding: 20px;">No tienes registros en eventos.</p>
								<% } %>
							</div>
						<% } else { %>
							<!-- Ediciones Tab -->
							<div class="profile-section">
								<div class="section-title">Ediciones organizadas</div>
								<% 
									Collection<EdicionDTO> ediciones = (Collection<EdicionDTO>) request.getAttribute("ediciones");
									Map<String, String> edicionToEventoOrg = (Map<String, String>) request.getAttribute("edicionToEventoOrg");
									if (ediciones != null && !ediciones.isEmpty()) {
								%>
									<div class="registrations-list">
										<% for (EdicionDTO edicion : ediciones) { 
											String nombreEdicion = edicion.getNombre();
											String nombreEvento = edicionToEventoOrg != null ? edicionToEventoOrg.get(nombreEdicion) : null;
										%>
											<div class="registration-card">
												<div class="registration-header">
													<div>
														<div class="registration-title"><%= nombreEdicion %></div>
													</div>
												</div>
												<div style="margin-top: 15px;">
													<% if (nombreEvento != null) { %>
														<a href="${pageContext.request.contextPath}/ediciones?id=<%= nombreEdicion %>&evento=<%= nombreEvento %>" 
														   class="btn btn-primary">Ver Edición</a>
													<% } else { %>
														<a href="${pageContext.request.contextPath}/ediciones?id=<%= nombreEdicion %>" 
														   class="btn btn-primary">Ver Edición</a>
													<% } %>
												</div>
											</div>
										<% } %>
									</div>
								<% } else { %>
									<p style="color: #666; text-align: center; padding: 20px;">No tienes ediciones organizadas.</p>
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

