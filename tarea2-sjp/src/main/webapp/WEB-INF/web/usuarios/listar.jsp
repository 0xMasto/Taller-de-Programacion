<%@page import="publicar.ws.client.AsistenteDTO"%>
<%@page import="publicar.ws.client.OrganizadorDTO"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="/WEB-INF/errorPages/500.jsp" %>
<!doctype html>
<html>
   <head>
	   <jsp:include page="/WEB-INF/web/template/head.jsp">
           <jsp:param name="title" value="Usuarios :: eventos.uy" />
       </jsp:include>
   </head>
    <body>
        <jsp:include page="/WEB-INF/web/template/header.jsp"/>

		<div class="main-layout">
			<!-- Sidebar -->
			<jsp:include page="/WEB-INF/web/template/sidebar.jsp"/>

			<!-- Main Content -->
			<div class="content-area">
				<!-- Filter Tabs -->
				<div class="filter-tabs">
					<button class="filter-tab active" data-type="all" onclick="filterUsers('all')">Todos</button>
					<button class="filter-tab" data-type="organizador" onclick="filterUsers('organizador')">Organizadores</button>
					<button class="filter-tab" data-type="asistente" onclick="filterUsers('asistente')">Asistentes</button>
				</div>

				<!-- Users List -->
				<ul class="users-list">
					<% 
						Collection<AsistenteDTO> asistentes = (Collection<AsistenteDTO>) request.getAttribute("asistentes");
						Collection<OrganizadorDTO> organizadores = (Collection<OrganizadorDTO>) request.getAttribute("organizadores");

					// Mostrar organizadores
					if (organizadores != null) {
						for (OrganizadorDTO organizador : organizadores) {
				%>
				<a href="${pageContext.request.contextPath}/usuarios?action=detail&nickname=<%= organizador.getNickname() %>" style="color: #333; text-decoration: none;">
					<li class="user-card" data-user-type="organizador">
						<div class="user-image">
							<% 
								String orgImagen = organizador.getImagen();
								if (orgImagen != null && !orgImagen.isEmpty() && !orgImagen.equals("-")) { 
							%>
								<img src="${pageContext.request.contextPath}/media/images/<%= orgImagen %>" 
									 alt="<%= organizador.getNombre() %>" 
									 style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
							<% } else { %>
								<span style="color: white; font-size: 32px; font-weight: bold;">
									<%= organizador.getNombre().substring(0, 1).toUpperCase() %>
								</span>
							<% } %>
						</div>
						<div class="user-content">
							<h3><%= organizador.getNombre() %></h3>
							<p><strong>@<%= organizador.getNickname() %></strong> • <span class="user-type-badge badge-organizador">Organizador</span></p>
							<% 
								String orgDesc = organizador.getDescripcion();
								if (orgDesc != null && !orgDesc.isEmpty()) { 
							%>
								<p><%= orgDesc %></p>
							<% } %>
							<p><strong>Email:</strong> <%= organizador.getCorreo() %></p>
						</div>
					</li>
				</a>
				<% 
						}
					}

					// Mostrar asistentes
					if (asistentes != null) {
						for (AsistenteDTO asistente : asistentes) {
				%>
				<a href="${pageContext.request.contextPath}/usuarios?action=detail&nickname=<%= asistente.getNickname() %>" style="color: #333; text-decoration: none;">
					<li class="user-card" data-user-type="asistente">
						<div class="user-image">
							<% 
								String asisImagen = asistente.getImagen();
								if (asisImagen != null && !asisImagen.isEmpty() && !asisImagen.equals("-")) { 
							%>
								<img src="${pageContext.request.contextPath}/media/images/<%= asisImagen %>" 
									 alt="<%= asistente.getNombre() %>" 
									 style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;">
							<% } else { %>
								<span style="color: white; font-size: 32px; font-weight: bold;">
									<%= asistente.getNombre().substring(0, 1).toUpperCase() %>
								</span>
							<% } %>
						</div>
						<div class="user-content">
							<h3><%= asistente.getNombre() %> <%= asistente.getApellido() %></h3>
							<p><strong>@<%= asistente.getNickname() %></strong> • <span class="user-type-badge badge-asistente">Asistente</span></p>
							<% if (asistente.getInstitucion() != null) { %>
								<p>Estudiante - <%= asistente.getInstitucion().getNombre() %></p>
							<% } %>
							<p><strong>Email:</strong> <%= asistente.getCorreo() %></p>
						</div>
					</li>
				</a>
				<% 
						}
					}
					%>
				</ul>
			</div>
		</div>
		
		<script>
		function filterUsers(type) {
			const cards = document.querySelectorAll('.user-card');
			const tabs = document.querySelectorAll('.filter-tab');
			
			// Update tab active state
			tabs.forEach(tab => {
				if (tab.dataset.type === type) {
					tab.classList.add('active');
				} else {
					tab.classList.remove('active');
				}
			});
			
			// Filter cards
			cards.forEach(card => {
				const userType = card.dataset.userType;
				if (type === 'all' || userType === type) {
					card.parentElement.style.display = 'block';
				} else {
					card.parentElement.style.display = 'none';
				}
			});
		}
		</script>
</body>
</html>