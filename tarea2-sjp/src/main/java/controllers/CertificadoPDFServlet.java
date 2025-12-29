package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.RegistroDTO;

/**
 * Servlet for generating PDF attendance certificates
 */
@WebServlet("/certificado")
public class CertificadoPDFServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nickname = request.getParameter("asistente");
        String nombreEdicion = request.getParameter("edicion");
        
        if (nickname == null || nombreEdicion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros faltantes");
            return;
        }
        
        try {
            AsistenteDTO asistente = port.buscarAsistenteDTO(nickname);
            EdicionDTO edicion = port.buscarEdicionDTO(nombreEdicion);
            RegistroDTO[] registrosArray = WSTypeConverter.toRegistroDTOArray(port.getRegistrosPorAsistentes(nickname));
            
            if (asistente == null || edicion == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asistente o edición no encontrados");
                return;
            }
            
            // Find the specific registration
            RegistroDTO registro = null;
            for (RegistroDTO reg : registrosArray) {
                if (reg.getNombreEdicion().equals(nombreEdicion)) {
                    registro = reg;
                    break;
                }
            }
            
            if (registro == null || !registro.isAsistio()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No hay constancia de asistencia");
                return;
            }
            
            // Generate PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", 
                "attachment; filename=Certificado_" + nickname + "_" + nombreEdicion.replaceAll(" ", "_") + ".pdf");
            
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            OutputStream os = response.getOutputStream();
            PdfWriter.getInstance(document, os);
            
            document.open();
            
            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("CERTIFICADO DE ASISTENCIA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);
            
            // Certificate body
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            
            Paragraph intro = new Paragraph();
            intro.setAlignment(Element.ALIGN_JUSTIFIED);
            intro.add(new Chunk("Se certifica que ", bodyFont));
            intro.add(new Chunk(asistente.getNombre() + " " + asistente.getApellido(), boldFont));
            intro.add(new Chunk(" (", bodyFont));
            intro.add(new Chunk(asistente.getNickname(), bodyFont));
            intro.add(new Chunk(") asistió a la edición ", bodyFont));
            intro.add(new Chunk(edicion.getNombre(), boldFont));
            intro.add(new Chunk(" del evento ", bodyFont));
            intro.add(new Chunk(edicion.getEvento() != null ? edicion.getEvento() : "Evento", boldFont));
            intro.add(new Chunk(".", bodyFont));
            intro.setSpacingAfter(20);
            document.add(intro);
            
            // Event details
            document.add(new Paragraph(" ", bodyFont));
            document.add(new Paragraph("Detalles del Evento:", boldFont));
            document.add(new Paragraph("Ciudad: " + edicion.getCiudad(), bodyFont));
            document.add(new Paragraph("País: " + edicion.getPais(), bodyFont));
            document.add(new Paragraph("Fecha de Inicio: " + edicion.getFechaInicio(), bodyFont));
            document.add(new Paragraph("Fecha de Fin: " + edicion.getFechaFin(), bodyFont));
            
            // Footer
            Paragraph footer = new Paragraph();
            footer.setSpacingBefore(40);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.add(new Chunk("Emitido el: " + LocalDate.now(), bodyFont));
            document.add(footer);
            
            Paragraph signature = new Paragraph();
            signature.setSpacingBefore(60);
            signature.setAlignment(Element.ALIGN_CENTER);
            signature.add(new Chunk("___________________________________", bodyFont));
            signature.add(Chunk.NEWLINE);
            signature.add(new Chunk("EventosUY - Sistema de Gestión de Eventos", bodyFont));
            document.add(signature);
            
            document.close();
            os.flush();
            os.close();
            
        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF", e);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error al generar certificado: " + e.getMessage());
        }
    }
}

