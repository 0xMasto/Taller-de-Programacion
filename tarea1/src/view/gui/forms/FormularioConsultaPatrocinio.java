package view.gui.forms;

import logica.interfaces.iControladorEventos;
import logica.dto.PatrocinioDTO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/**
 * Formulario para consultar patrocinios
 * Solo trabaja con datos del controlador, sin conocer los modelos
 */
public class FormularioConsultaPatrocinio extends BaseInternalForm {
    private iControladorEventos controladorEventos;
    private JComboBox<String> sponsorshipComboBox;
    private JTextField codigoField;
    private JTextField nivelField;
    private JTextField institucionField;
    private JTextField montoField;
    private JTextField edicionField;
    private JTextField eventoField;
    private JTextField tipoRegistroField;
    private JTextField registrosGratuitosField;
    
    public FormularioConsultaPatrocinio(iControladorEventos controladorEventos) {
        super("Consulta de Patrocinio");
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        loadSponsorships();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Selection panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selección de Patrocinio"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Sponsorship combobox
        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("Patrocinio:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        sponsorshipComboBox = new JComboBox<>();
        sponsorshipComboBox.addActionListener(e -> showSponsorshipDetails());
        selectionPanel.add(sponsorshipComboBox, gbc);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Patrocinio"));
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Código field
        gbc.gridx = 0; gbc.gridy = 0;
        detailsPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        codigoField = new JTextField(20);
        codigoField.setEditable(false);
        detailsPanel.add(codigoField, gbc);
        
        // Nivel field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Nivel:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nivelField = new JTextField(20);
        nivelField.setEditable(false);
        detailsPanel.add(nivelField, gbc);
        
        // Institución field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Institución:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        institucionField = new JTextField(20);
        institucionField.setEditable(false);
        detailsPanel.add(institucionField, gbc);
        
        // Monto field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Monto:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        montoField = new JTextField(20);
        montoField.setEditable(false);
        detailsPanel.add(montoField, gbc);
        
        // Edición field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        edicionField = new JTextField(20);
        edicionField.setEditable(false);
        detailsPanel.add(edicionField, gbc);
        
        // Evento field
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventoField = new JTextField(20);
        eventoField.setEditable(false);
        detailsPanel.add(eventoField, gbc);
        
        // Tipo de Registro field
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Tipo de Registro:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        tipoRegistroField = new JTextField(20);
        tipoRegistroField.setEditable(false);
        detailsPanel.add(tipoRegistroField, gbc);
        
        // Registros Gratuitos field
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Registros Gratuitos:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        registrosGratuitosField = new JTextField(20);
        registrosGratuitosField.setEditable(false);
        detailsPanel.add(registrosGratuitosField, gbc);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionPanel, detailsPanel);
        splitPane.setDividerLocation(300);
        
        contentPanel.add(splitPane, BorderLayout.CENTER);
        
        setSize(800, 700);
    }
    
    private void loadSponsorships() {
        sponsorshipComboBox.removeAllItems();
        
        // Get sponsorship codes from controller
        List<String> patrocinios = controladorEventos.listarPatrocinios();
        for (String patrocinio : patrocinios) {
            sponsorshipComboBox.addItem(patrocinio);
        }
        
        if (sponsorshipComboBox.getItemCount() > 0) {
            sponsorshipComboBox.setSelectedIndex(0);
            showSponsorshipDetails();
        } else {
            clearDetailsFields();
        }
    }
    
    private void showSponsorshipDetails() {
        String codigoPatrocinio = (String) sponsorshipComboBox.getSelectedItem();
        if (codigoPatrocinio == null) {
            clearDetailsFields();
            return;
        }
        
        // Get sponsorship details from controller
        try {
            PatrocinioDTO patrocinio = controladorEventos.buscarPatrocinioDTO(codigoPatrocinio);
            
            if (patrocinio != null) {
                // Display sponsorship details
                codigoField.setText(patrocinio.getCodigoPatrocinio());
                nivelField.setText(patrocinio.getNivelPatrocinio());
                
                // Institution details
                if (patrocinio.getInstitucion() != null) {
                    institucionField.setText(patrocinio.getInstitucion().getNombre());
                } else {
            institucionField.setText("N/A");
                }
                
                // Financial details
                if (patrocinio.getAporteEconomico() != null) {
                    montoField.setText("$" + patrocinio.getAporteEconomico().toString());
                } else {
            montoField.setText("N/A");
                }
                
                // Event and edition details
                eventoField.setText(patrocinio.getEvento() != null ? patrocinio.getEvento() : "N/A");
                edicionField.setText(patrocinio.getEdicion() != null ? patrocinio.getEdicion() : "N/A");
                tipoRegistroField.setText(patrocinio.getTipoRegistro() != null ? patrocinio.getTipoRegistro() : "N/A");
                
                // Free registrations
                registrosGratuitosField.setText(String.valueOf(patrocinio.getCantidadRegistros()));
                
            } else {
                // If sponsorship not found, show error message
                clearDetailsFields();
                codigoField.setText("Patrocinio no encontrado");
            }
        } catch (Exception e) {
            // If there's any error, clear the fields and show error
            clearDetailsFields();
            codigoField.setText("Error al cargar datos: " + e.getMessage());
        }
    }
    
    private void clearDetailsFields() {
        codigoField.setText("Seleccione un patrocinio para ver sus detalles");
        nivelField.setText("");
        institucionField.setText("");
        montoField.setText("");
        edicionField.setText("");
        eventoField.setText("");
        tipoRegistroField.setText("");
        registrosGratuitosField.setText("");
    }
    
    @Override
    protected void onAccept() {
        // Refresh sponsorship list
        loadSponsorships();
    }
    
    @Override
    protected void clearForm() {
        loadSponsorships();
    }
}
