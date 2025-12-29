package view.gui.forms;

import logica.dto.TipoRegistroDTO;
import logica.interfaces.iControladorEventos;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Set;

/**
 * Formulario para consultar tipos de registro
 * Implementa el caso de uso: consulta jerárquica Evento -> Edición -> Tipo de Registro
 * Usa DTOs y controladores en lugar de modelos y manejadores
 */
public class FormularioConsultaTipoRegistro extends BaseInternalForm {
    private JComboBox<String> eventComboBox;
    private JComboBox<String> editionComboBox;
    private JComboBox<String> registrationTypeComboBox;
    
    // Add these field declarations
    private JTextField nombreField;
    private JTextField eventoField;
    private JTextField edicionField;
    private JTextField descripcionField;
    private JTextField costoField;
    private JTextField cupoField;
    
    private iControladorEventos controladorEventos;
    
    public FormularioConsultaTipoRegistro(iControladorEventos controladorEventos) {
        super("Consulta de Tipo de Registro");
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        loadEvents();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel titleLabel = new JLabel("Consulta Jerárquica de Tipos de Registro", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(titleLabel, gbc);
        
        // Events Section
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        contentPanel.add(createSectionPanel("Eventos", createEventComboBox()), gbc);
        
        // Editions Section
        gbc.gridx = 1;
        contentPanel.add(createSectionPanel("Ediciones", createEditionComboBox()), gbc);
        
        // Registration Types Section
        gbc.gridx = 2;
        contentPanel.add(createSectionPanel("Tipos de Registro", createRegistrationTypeComboBox()), gbc);
        
        // Details Section
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPanel.add(createDetailsPanel(), gbc);
        
        setSize(1000, 700);
    }
    
    private JPanel createSectionPanel(String title, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
    
    private JComboBox<String> createEventComboBox() {
        eventComboBox = new JComboBox<>();
        eventComboBox.addItem("Seleccione un evento..."); // Better placeholder text
        
        // Enhanced styling
        eventComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        eventComboBox.setBackground(Color.WHITE);
        eventComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Add tooltip
        eventComboBox.setToolTipText("Seleccione un evento para ver sus ediciones");
        
        eventComboBox.addActionListener(e -> {
            String selectedEvent = (String) eventComboBox.getSelectedItem();
            if (selectedEvent != null && !selectedEvent.isEmpty() && !selectedEvent.equals("Seleccione un evento...")) {
                loadEditions(selectedEvent);
            } else {
                clearEditionsAndTypes();
            }
        });
        
        return eventComboBox;
    }
    
    private JComboBox<String> createEditionComboBox() {
        editionComboBox = new JComboBox<>();
        editionComboBox.addItem("Seleccione una edición..."); // Better placeholder text
        
        // Enhanced styling
        editionComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        editionComboBox.setBackground(Color.WHITE);
        editionComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Disable initially until event is selected
        editionComboBox.setEnabled(false);
        editionComboBox.setBackground(new Color(245, 245, 245));
        
        // Add tooltip
        editionComboBox.setToolTipText("Seleccione una edición para ver sus tipos de registro");
        
        editionComboBox.addActionListener(e -> {
            String selectedEdition = (String) editionComboBox.getSelectedItem();
            if (selectedEdition != null && !selectedEdition.isEmpty() && !selectedEdition.equals("Seleccione una edición...")) {
                loadRegistrationTypes(selectedEdition);
            } else {
                clearRegistrationTypes();
            }
        });
        
        return editionComboBox;
    }
    
    private JComboBox<String> createRegistrationTypeComboBox() {
        registrationTypeComboBox = new JComboBox<>();
        registrationTypeComboBox.addItem("Seleccione un tipo de registro..."); // Better placeholder text
        
        // Enhanced styling
        registrationTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registrationTypeComboBox.setBackground(Color.WHITE);
        registrationTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Disable initially until edition is selected
        registrationTypeComboBox.setEnabled(false);
        registrationTypeComboBox.setBackground(new Color(245, 245, 245));
        
        // Add tooltip
        registrationTypeComboBox.setToolTipText("Seleccione un tipo de registro para ver sus detalles");
        
        registrationTypeComboBox.addActionListener(e -> {
            String selectedType = (String) registrationTypeComboBox.getSelectedItem();
            if (selectedType != null && !selectedType.isEmpty() && !selectedType.equals("Seleccione un tipo de registro...")) {
                showRegistrationTypeDetails(selectedType);
            } else {
                clearDetails();
            }
        });
        
        return registrationTypeComboBox;
    }
    
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalles del Tipo de Registro"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nombreField = new JTextField(20);
        nombreField.setEditable(false);
        panel.add(nombreField, gbc);
        
        // Evento field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventoField = new JTextField(20);
        eventoField.setEditable(false);
        panel.add(eventoField, gbc);
        
        // Edición field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panel.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        edicionField = new JTextField(20);
        edicionField.setEditable(false);
        panel.add(edicionField, gbc);
        
        // Descripción field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        descripcionField = new JTextField(20);
        descripcionField.setEditable(false);
        panel.add(descripcionField, gbc);
        
        // Costo field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panel.add(new JLabel("Costo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        costoField = new JTextField(20);
        costoField.setEditable(false);
        panel.add(costoField, gbc);
        
        // Cupo field
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panel.add(new JLabel("Cupo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cupoField = new JTextField(20);
        cupoField.setEditable(false);
        panel.add(cupoField, gbc);
        
        // Add a spacer to push content to the top
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(new JPanel(), gbc);
        
        return panel;
    }
    
    private void loadEvents() {
        eventComboBox.removeAllItems();
        eventComboBox.addItem("Seleccione un evento..."); // Better placeholder text
        
        List<String> eventos = controladorEventos.listarEventos();
        for (String nombreEvento : eventos) {
            eventComboBox.addItem(nombreEvento);
        }
        
        clearEditionsAndTypes();
    }
    
    private void loadEditions(String eventName) {
        editionComboBox.removeAllItems();
        editionComboBox.addItem("Seleccione una edición..."); // Better placeholder text
        
        List<String> ediciones = controladorEventos.listarEdicionesDeEvento(eventName);
        for (String nombreEdicion : ediciones) {
            editionComboBox.addItem(nombreEdicion);
        }
        
        // Enable the combo box and restore normal background
        editionComboBox.setEnabled(true);
        editionComboBox.setBackground(Color.WHITE);
        
        if (editionComboBox.getItemCount() == 1) { // Only placeholder option
            editionComboBox.addItem("No hay ediciones registradas");
            editionComboBox.setEnabled(false);
            editionComboBox.setBackground(new Color(245, 245, 245));
        }
        
        clearRegistrationTypes();
    }
    
    private void loadRegistrationTypes(String editionName) {
        registrationTypeComboBox.removeAllItems();
        registrationTypeComboBox.addItem("Seleccione un tipo de registro..."); // Better placeholder text
        
        Set<String> tiposRegistro = controladorEventos.obtenerTiposDeRegistroDeEdicion(editionName);
        for (String nombreTipo : tiposRegistro) {
            registrationTypeComboBox.addItem(nombreTipo);
        }
        
        // Enable the combo box and restore normal background
        registrationTypeComboBox.setEnabled(true);
        registrationTypeComboBox.setBackground(Color.WHITE);
        
        if (registrationTypeComboBox.getItemCount() == 1) { // Only placeholder option
            registrationTypeComboBox.addItem("No hay tipos de registro configurados");
            registrationTypeComboBox.setEnabled(false);
            registrationTypeComboBox.setBackground(new Color(245, 245, 245));
        }
    }
    
    private void showRegistrationTypeDetails(String nombreTipoRegistro) {
        // Get the current event and edition names
        String eventName = (String) eventComboBox.getSelectedItem();
        String editionName = (String) editionComboBox.getSelectedItem();
        
        try {
            // Get the TipoRegistroDTO from the controller
            List<TipoRegistroDTO> tiposRegistro = controladorEventos.obtenerTiposDeRegistroDeEdicionDTO(editionName);
            
            // Find the specific tipo registro by name
            TipoRegistroDTO tipoRegistro = null;
            for (TipoRegistroDTO tipo : tiposRegistro) {
                if (tipo.getNombre().equals(nombreTipoRegistro)) {
                    tipoRegistro = tipo;
                    break;
                }
            }
            
            if (tipoRegistro != null) {
                // Set individual field values from DTO
                nombreField.setText(tipoRegistro.getNombre());
                eventoField.setText(eventName);
                edicionField.setText(editionName);
                descripcionField.setText(tipoRegistro.getDescripcion());
                costoField.setText(String.valueOf(tipoRegistro.getCosto()));
                cupoField.setText(tipoRegistro.getCupo() + " (Disponibles: " + tipoRegistro.getCupoDisponible() + ")");
            } else {
                // Fallback if DTO not found
                nombreField.setText(nombreTipoRegistro);
                eventoField.setText(eventName);
                edicionField.setText(editionName);
                descripcionField.setText("Información no disponible");
                costoField.setText("No disponible");
                cupoField.setText("No disponible");
            }
        } catch (Exception e) {
            // Handle any exceptions and show fallback data
            nombreField.setText(nombreTipoRegistro);
            eventoField.setText(eventName);
            edicionField.setText(editionName);
            descripcionField.setText("Error al cargar datos");
            costoField.setText("Error");
            cupoField.setText("Error");
        }
    }
    
    private void clearEditionsAndTypes() {
        editionComboBox.removeAllItems();
        editionComboBox.addItem("Seleccione una edición...");
        editionComboBox.setEnabled(false);
        editionComboBox.setBackground(new Color(245, 245, 245));
        clearRegistrationTypes();
    }
    
    private void clearRegistrationTypes() {
        registrationTypeComboBox.removeAllItems();
        registrationTypeComboBox.addItem("Seleccione un tipo de registro...");
        registrationTypeComboBox.setEnabled(false);
        registrationTypeComboBox.setBackground(new Color(245, 245, 245));
        clearDetails();
    }
    
    private void clearDetails() {
        nombreField.setText("");
        eventoField.setText("");
        edicionField.setText("");
        descripcionField.setText("");
        costoField.setText("");
        cupoField.setText("");
    }
    
    @Override
    protected void onAccept() {
        // Refresh the data
        loadEvents();
    }
    
    @Override
    protected void clearForm() {
        loadEvents();
    }
}
