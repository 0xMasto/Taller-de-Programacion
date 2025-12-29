package view.gui.forms;

import logica.dto.CategoriaDTO;
import logica.dto.EventoDTO;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import logica.interfaces.Fabrica;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.List;


import java.util.stream.Collectors;

/**
 * Formulario para consultar información de eventos
 */
public class FormularioConsultaEvento extends BaseInternalForm {
    private JComboBox<String> eventComboBox;
    private JTextField nombreField;
    private JTextField siglaField;
    private JTextField descripcionField;
    private JTextField fechaAltaField;
    private JTextField categoriasField;
    private JComboBox<String> edicionesComboBox;
    private iControladorEventos controladorEventos;
    private iControladorUsuarios controladorUsuarios; 
    
    public FormularioConsultaEvento(iControladorEventos controladorEventos) {
        super("Consulta de Eventos");
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = Fabrica.getInstancia().getControladorUsuario();
        setupSpecificComponents();
        loadEvents();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Selection panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selección de Evento"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Event combobox
        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventComboBox = new JComboBox<>();
        eventComboBox.addActionListener(e -> showEventDetails());
        selectionPanel.add(eventComboBox, gbc);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Evento"));
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre field
        gbc.gridx = 0; gbc.gridy = 0;
        detailsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nombreField = new JTextField(20);
        nombreField.setEditable(false);
        detailsPanel.add(nombreField, gbc);
        
        // Sigla field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        siglaField = new JTextField(20);
        siglaField.setEditable(false);
        detailsPanel.add(siglaField, gbc);
        
        // Descripción field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        descripcionField = new JTextField(20);
        descripcionField.setEditable(false);
        detailsPanel.add(descripcionField, gbc);
        
        // Fecha Alta field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Fecha Alta:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fechaAltaField = new JTextField(20);
        fechaAltaField.setEditable(false);
        detailsPanel.add(fechaAltaField, gbc);
        
        // Categorías field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Categorías:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        categoriasField = new JTextField(20);
        categoriasField.setEditable(false);
        detailsPanel.add(categoriasField, gbc);
        
        // Ediciones field - changed from JTextField to JComboBox
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Ediciones:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        edicionesComboBox = new JComboBox<>();
        edicionesComboBox.addActionListener(e -> showEditionDetails());
        detailsPanel.add(edicionesComboBox, gbc);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionPanel, detailsPanel);
        splitPane.setDividerLocation(300);
        
        contentPanel.add(splitPane, BorderLayout.CENTER);
        
        setSize(800, 600);
    }
    
    private void loadEvents() {
        eventComboBox.removeAllItems();
        
        List<String> eventos = controladorEventos.listarEventos();
        
        for (String nombreEvento : eventos) {
            eventComboBox.addItem(nombreEvento);
        }
        
        if (eventComboBox.getItemCount() > 0) {
            eventComboBox.setSelectedIndex(0);
            showEventDetails();
        } else {
            clearDetailsFields();
        }
    }
    
    private void showEventDetails() {
        String eventName = (String) eventComboBox.getSelectedItem();
        if (eventName == null) {
            clearDetailsFields();
            return;
        }
        
        EventoDTO evento = controladorEventos.getEventoDTO(eventName);
        EventoDTO eventoDTO = controladorEventos.buscarEventoDTO(eventName);
        
        if (evento == null) {
            clearDetailsFields();
            return;
        }
        nombreField.setText(evento.getNombre());
        siglaField.setText(evento.getSigla());
        descripcionField.setText(evento.getDescripcion());
        fechaAltaField.setText(evento.getFechaAlta() != null ? evento.getFechaAlta().toString() : "No disponible");
        
        // Show categories
        List<CategoriaDTO> categorias = evento.getCategorias();
        if (categorias != null && !categorias.isEmpty()) {
            StringBuilder categoriesStr = new StringBuilder();
            for (CategoriaDTO categoria : categorias) {
                if (categoriesStr.length() > 0) {
                    categoriesStr.append(", ");
                }
                categoriesStr.append(categoria.getNombre());
            }
            categoriasField.setText(categoriesStr.toString());
        } else {
            categoriasField.setText("Sin categorías asignadas");
        }
        
        // Editions are loaded via loadEditions() method below - no need for additional logic here
        
        if (eventoDTO != null) {
            nombreField.setText(eventoDTO.getNombre());
            siglaField.setText(eventoDTO.getSigla());
            descripcionField.setText(eventoDTO.getDescripcion());
            fechaAltaField.setText(eventoDTO.getFechaAlta().toString());
            
            // Show categories from DTO
            List<CategoriaDTO> categoriasDTO = eventoDTO.getCategorias();
            if (categoriasDTO != null && !categoriasDTO.isEmpty()) {
                String categoriesStr = categoriasDTO.stream()
                    .map(CategoriaDTO::getNombre)
                    .collect(Collectors.joining(", "));
                categoriasField.setText(categoriesStr);
            } else {
                categoriasField.setText("Sin categorías asignadas");
            }
            
            // Load editions into combo box
            loadEditions(eventName);
        }
    }
    
    private void loadEditions(String eventName) {
        edicionesComboBox.removeAllItems();
        
        // Add empty option first
        edicionesComboBox.addItem("");
        
        List<String> ediciones = controladorEventos.listarEdicionesDeEvento(eventName);
        for (String nombreEdicion : ediciones) {
            edicionesComboBox.addItem(nombreEdicion);
        }
        
        if (edicionesComboBox.getItemCount() > 1) { // More than just the empty option
            edicionesComboBox.setSelectedIndex(0); // Select the empty option by default
        } else {
            edicionesComboBox.addItem("No hay ediciones registradas");
        }
    }
    
    private void showEditionDetails() {
        String editionName = (String) edicionesComboBox.getSelectedItem();
        if (editionName == null || editionName.equals("") || editionName.equals("No hay ediciones registradas")) {
            return;
        }
        
        // Open the edition consultation form with the selected edition
        openEditionConsultationForm(editionName);
    }
    
    private void openEditionConsultationForm(String editionName) {
        // Create the edition consultation form
        FormularioConsultaEdicionEvento editionForm = new FormularioConsultaEdicionEvento(controladorEventos, controladorUsuarios);
        
        // Get the desktop pane from the parent window
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            // Close any existing edition consultation form
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(editionForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            // Add the new form to the desktop pane
            desktopPane.add(editionForm);
            editionForm.setVisible(true);
            
            // Center the frame in the desktop pane
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = editionForm.getSize();
            editionForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                editionForm.setSelected(true);
            } catch (Exception e) {
                // Ignore selection exceptions
            }
            
            // Pre-select the event and edition in the new form
            preSelectEventAndEdition(editionForm, editionName);
        }
    }
    
    private void preSelectEventAndEdition(FormularioConsultaEdicionEvento editionForm, String editionName) {
        // Get the current event name
        String eventName = (String) eventComboBox.getSelectedItem();
        
        // Use reflection or a public method to set the selections
        // Since we can't modify the FormularioConsultaEdicionEvento directly,
        // we'll use a SwingUtilities.invokeLater to ensure the form is fully loaded
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the event selection
                JComboBox<String> eventCombo = getComboBoxByName(editionForm, "eventComboBox");
                if (eventCombo != null) {
                    eventCombo.setSelectedItem(eventName);
                }
                
                // Set the edition selection
                JComboBox<String> editionCombo = getComboBoxByName(editionForm, "editionComboBox");
                if (editionCombo != null) {
                    editionCombo.setSelectedItem(editionName);
                }
            } catch (Exception e) {
                // If reflection fails, just show the form without pre-selection
                System.err.println("Could not pre-select event and edition: " + e.getMessage());
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getComboBoxByName(JInternalFrame frame, String name) {
        // Use reflection to access the private fields
        try {
            java.lang.reflect.Field field = frame.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (JComboBox<String>) field.get(frame);
        } catch (Exception e) {
            return null;
        }
    }
    
    private void clearDetailsFields() {
        nombreField.setText("Seleccione un evento para ver sus detalles");
        siglaField.setText("");
        descripcionField.setText("");
        fechaAltaField.setText("");
        categoriasField.setText("");
        edicionesComboBox.removeAllItems();
    }
    
    @Override
    protected void onAccept() {
        // Refresh event list
        loadEvents();
        clearDetailsFields();
    }
    
    @Override
    protected void clearForm() {
        loadEvents();
        clearDetailsFields();
    }

   
}
