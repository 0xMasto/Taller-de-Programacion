package view.gui.forms;

import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import logica.dto.AsistenteDTO;
import logica.dto.OrganizadorDTO;
import logica.dto.EventoDTO;

/**
 * Formulario para consultar información de usuarios
 */
public class FormularioConsultaUsuario extends BaseInternalForm {
    private JComboBox<String> userComboBox;
    private JTextField nicknameField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField correoField;
    private JTextField fechaNacimientoField;
    private JTextField institucionField;
    private JTextField descripcionField;
    private JTextField sitioWebField;
    private JComboBox<String> informacionRelacionadaComboBox;
    
    private iControladorUsuarios controladorUsuarios;
    private iControladorEventos controladorEventos;
    
    // Labels for dynamic visibility
    private JLabel apellidoLabel;
    private JLabel fechaNacimientoLabel;
    private JLabel institucionLabel;
    private JLabel descripcionLabel;
    private JLabel sitioWebLabel;
    
    public FormularioConsultaUsuario(iControladorUsuarios controladorUsuarios, iControladorEventos controladorEventos) {
        super("Consulta de Usuarios");
        this.controladorUsuarios = controladorUsuarios;
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        loadUsers();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Selection panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selección de Usuario"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // User combobox
        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        userComboBox = new JComboBox<>();
        userComboBox.addActionListener(e -> showUserDetails());
        selectionPanel.add(userComboBox, gbc);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Usuario"));
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nickname field
        gbc.gridx = 0; gbc.gridy = 0;
        detailsPanel.add(new JLabel("Nickname:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nicknameField = new JTextField(20);
        nicknameField.setEditable(false);
        detailsPanel.add(nicknameField, gbc);
        
        // Nombre field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nombreField = new JTextField(20);
        nombreField.setEditable(false);
        detailsPanel.add(nombreField, gbc);
        
        // Apellido field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        apellidoLabel = new JLabel("Apellido:");
        detailsPanel.add(apellidoLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        apellidoField = new JTextField(20);
        apellidoField.setEditable(false);
        detailsPanel.add(apellidoField, gbc);
        
        // Correo field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        correoField = new JTextField(20);
        correoField.setEditable(false);
        detailsPanel.add(correoField, gbc);
        
        // Fecha Nacimiento field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        fechaNacimientoLabel = new JLabel("Fecha Nacimiento:");
        detailsPanel.add(fechaNacimientoLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fechaNacimientoField = new JTextField(20);
        fechaNacimientoField.setEditable(false);
        detailsPanel.add(fechaNacimientoField, gbc);
        
        // Institución field
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        institucionLabel = new JLabel("Institución:");
        detailsPanel.add(institucionLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        institucionField = new JTextField(20);
        institucionField.setEditable(false);
        detailsPanel.add(institucionField, gbc);
        
        // Descripción field
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        descripcionLabel = new JLabel("Descripción:");
        detailsPanel.add(descripcionLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        descripcionField = new JTextField(20);
        descripcionField.setEditable(false);
        detailsPanel.add(descripcionField, gbc);
        
        // Sitio Web field
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        sitioWebLabel = new JLabel("Sitio Web:");
        detailsPanel.add(sitioWebLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        sitioWebField = new JTextField(20);
        sitioWebField.setEditable(false);
        detailsPanel.add(sitioWebField, gbc);
        
        // Información Relacionada combobox
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        detailsPanel.add(new JLabel("Información Relacionada:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.weighty = 0.0;
        
        informacionRelacionadaComboBox = new JComboBox<>();
        detailsPanel.add(informacionRelacionadaComboBox, gbc);
        
        // Add a button for opening detailed view
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        JButton verDetalleButton = new JButton("Ver Detalle");
        verDetalleButton.addActionListener(e -> abrirConsultaDetallada());
        detailsPanel.add(verDetalleButton, gbc);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionPanel, detailsPanel);
        splitPane.setDividerLocation(300);
        
        contentPanel.add(splitPane, BorderLayout.CENTER);
        
        setSize(800, 600);
    }
    
    private void loadUsers() {
        userComboBox.removeAllItems();
        
        // Load all assistants
        Set<String> asistentes = controladorUsuarios.listarAsistentes();
        for (String asistente : asistentes) {
            userComboBox.addItem("A: " + asistente);
        }
        
        // Load all organizers
        List<String> organizadores = controladorUsuarios.listarOrganizadores();
        for (String organizador : organizadores) {
            userComboBox.addItem("O: " + organizador);
        }
        
        if (userComboBox.getItemCount() > 0) {
            userComboBox.setSelectedIndex(0);
            showUserDetails();
        } else {
            clearDetailsFields();
        }
    }
    
    private void showUserDetails() {
        String selectedItem = (String) userComboBox.getSelectedItem();
        if (selectedItem == null) {
            clearDetailsFields();
            return;
        }
        
        // Extract user type and nickname from the combobox item
        String userType = selectedItem.startsWith("A: ") ? "Asistente" : "Organizador";
        String selectedNickname = selectedItem.substring(3); // Remove "A: " or "O: " prefix
        
        if ("Asistente".equals(userType)) {
            // Get assistant DTO using the new DTO method
            AsistenteDTO asistenteDTO = controladorUsuarios.buscarAsistenteDTO(selectedNickname);
            if (asistenteDTO != null) {
                mostrarInformacionAsistente(asistenteDTO);
                mostrarEdicionesRelacionadasAsistente(asistenteDTO);
            } else {
                showErrorMessage("No se pudo encontrar el asistente: " + selectedNickname);
            }
        } else if ("Organizador".equals(userType)) {
            // Get organizer DTO using the new DTO method
            OrganizadorDTO organizadorDTO = controladorUsuarios.buscarOrganizadorDTO(selectedNickname);
            if (organizadorDTO != null) {
                mostrarInformacionOrganizador(organizadorDTO);
                mostrarEdicionesRelacionadasOrganizador(organizadorDTO);
            } else {
                showErrorMessage("No se pudo encontrar el organizador: " + selectedNickname);
            }
        }
    }
    
    
    
    private void mostrarInformacionAsistente(AsistenteDTO asistenteDTO) {
        showAssistantFields();
        nicknameField.setText(asistenteDTO.getNickname());
        nombreField.setText(asistenteDTO.getNombre());
        correoField.setText(asistenteDTO.getCorreo());
        
        if (asistenteDTO.getApellido() != null) {
            apellidoField.setText(asistenteDTO.getApellido());
        }
        
        if (asistenteDTO.getFechaNacimiento() != null) {
            fechaNacimientoField.setText(asistenteDTO.getFechaNacimiento().toString());
        }
        
        if (asistenteDTO.getInstitucion() != null) {
            institucionField.setText(asistenteDTO.getInstitucion().getNombre());
        }
    }
    
    private void mostrarInformacionOrganizador(OrganizadorDTO organizadorDTO) {
        showOrganizerFields();
        nicknameField.setText(organizadorDTO.getNickname());
        nombreField.setText(organizadorDTO.getNombre());
        correoField.setText(organizadorDTO.getCorreo());
        
        if (organizadorDTO.getDescripcion() != null) {
            descripcionField.setText(organizadorDTO.getDescripcion());
        }
        
        if (organizadorDTO.getSitioWeb() != null) {
            sitioWebField.setText(organizadorDTO.getSitioWeb());
        }
    }
    
    private void mostrarEdicionesRelacionadasAsistente(AsistenteDTO asistenteDTO) {
        informacionRelacionadaComboBox.removeAllItems();
        boolean foundRegistros = false;
        
        // Get registros for this asistente (shows edicion + tipo de registro)
        List<String> registros = controladorUsuarios.obtenerRegistrosDeAsistente(asistenteDTO.getNickname());
        
        if (registros != null && !registros.isEmpty()) {
            for (String registro : registros) {
                informacionRelacionadaComboBox.addItem("REGISTRO: " + registro);
                foundRegistros = true;
            }
        }
        
        if (!foundRegistros) {
            informacionRelacionadaComboBox.addItem("No hay registros. Asegúrese de haber cargado los datos CSV.");
        }
    }
    
    private void mostrarEdicionesRelacionadasOrganizador(OrganizadorDTO organizadorDTO) {
        informacionRelacionadaComboBox.removeAllItems();
        boolean foundEditions = false;
        
        // Get ediciones organized by this organizador
        List<String> edicionesOrganizadas = controladorEventos.obtenerEdicionesOrganizadasPor(organizadorDTO.getNickname());
        
        if (edicionesOrganizadas != null && !edicionesOrganizadas.isEmpty()) {
            for (String edicion : edicionesOrganizadas) {
                informacionRelacionadaComboBox.addItem("EDICION: " + edicion);
                foundEditions = true;
            }
        }
        
        if (!foundEditions) {
            informacionRelacionadaComboBox.addItem("No hay ediciones organizadas");
        }
    }
    
    private void showAssistantFields() {
        // Show assistant-specific fields and labels
        apellidoLabel.setVisible(true);
        apellidoField.setVisible(true);
        fechaNacimientoLabel.setVisible(true);
        fechaNacimientoField.setVisible(true);
        institucionLabel.setVisible(true);
        institucionField.setVisible(true);
        
        // Hide organizer-specific fields and labels
        descripcionLabel.setVisible(false);
        descripcionField.setVisible(false);
        sitioWebLabel.setVisible(false);
        sitioWebField.setVisible(false);
    }
    
    private void showOrganizerFields() {
        // Hide assistant-specific fields and labels
        apellidoLabel.setVisible(false);
        apellidoField.setVisible(false);
        fechaNacimientoLabel.setVisible(false);
        fechaNacimientoField.setVisible(false);
        institucionLabel.setVisible(false);
        institucionField.setVisible(false);
        
        // Show organizer-specific fields and labels
        descripcionLabel.setVisible(true);
        descripcionField.setVisible(true);
        sitioWebLabel.setVisible(true);
        sitioWebField.setVisible(true);
    }
    
    private void clearDetailsFields() {
        nicknameField.setText("Seleccione un usuario para ver sus detalles");
        nombreField.setText("");
        apellidoField.setText("");
        correoField.setText("");
        fechaNacimientoField.setText("");
        institucionField.setText("");
        descripcionField.setText("");
        sitioWebField.setText("");
        informacionRelacionadaComboBox.removeAllItems();
        
        // Hide all fields and labels initially
        apellidoLabel.setVisible(false);
        apellidoField.setVisible(false);
        fechaNacimientoLabel.setVisible(false);
        fechaNacimientoField.setVisible(false);
        institucionLabel.setVisible(false);
        institucionField.setVisible(false);
        descripcionLabel.setVisible(false);
        descripcionField.setVisible(false);
        sitioWebLabel.setVisible(false);
        sitioWebField.setVisible(false);
    }
    
    /**
     * Abre el formulario de consulta detallada correspondiente al elemento seleccionado
     */
    private void abrirConsultaDetallada() {
        String selectedItem = (String) informacionRelacionadaComboBox.getSelectedItem();
        if (selectedItem == null || selectedItem.contains("No hay")) {
            return;
        }
        
        if (selectedItem.startsWith("REGISTRO: ")) {
            String registroInfo = selectedItem.substring(10); // Remove "REGISTRO: " prefix
            abrirConsultaRegistro(registroInfo);
        } else if (selectedItem.startsWith("EDICION: ")) {
            String edicionInfo = selectedItem.substring(9); // Remove "EDICION: " prefix
            abrirConsultaEdicion(edicionInfo);
        }
    }
    
    /**
     * Abre el formulario de consulta de registro con información preseleccionada
     */
    private void abrirConsultaRegistro(String registroInfo) {
        FormularioConsultaRegistro registroForm = new FormularioConsultaRegistro(controladorUsuarios, controladorEventos);
        
        // Get the desktop pane from the parent window
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            // Close any existing registration consultation form
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(registroForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            // Add the new form to the desktop pane
            desktopPane.add(registroForm);
            registroForm.setVisible(true);
            
            // Center the frame in the desktop pane
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = registroForm.getSize();
            registroForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                registroForm.setSelected(true);
            } catch (Exception e) {
                // Ignore selection exceptions
            }
            
            // Pre-select the registration information
            preSelectInRegistrationForm(registroForm, registroInfo);
        }
    }
    
    /**
     * Abre el formulario de consulta de edición de evento con información preseleccionada
     */
    private void abrirConsultaEdicion(String edicionInfo) {
        // Parse the edition info and find the corresponding event
        String[] eventAndEdition = findEventForEdition(edicionInfo);
        String eventName = eventAndEdition[0];
        String editionName = eventAndEdition[1];
        
        FormularioConsultaEdicionEvento edicionForm = new FormularioConsultaEdicionEvento(controladorEventos, controladorUsuarios);
        
        // Get the desktop pane from the parent window
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            // Close any existing edition consultation form
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(edicionForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            // Add the new form to the desktop pane
            desktopPane.add(edicionForm);
            edicionForm.setVisible(true);
            
            // Center the frame in the desktop pane
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = edicionForm.getSize();
            edicionForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                edicionForm.setSelected(true);
            } catch (Exception e) {
                // Ignore selection exceptions
            }
            
            // Pre-select the edition information
            preSelectInEditionForm(edicionForm, eventName, editionName);
        }
    }
    
    /**
     * Preselecciona la información del registro en el formulario de consulta de registro
     */
    private void preSelectInRegistrationForm(FormularioConsultaRegistro form, String registroInfo) {
        // Parse the registration info to extract edition name
        // registroInfo format: "edicion (tipo_registro)"
        String edicionName = parseEdicionFromRegistroInfo(registroInfo);
        String asistenteNickname = getCurrentSelectedAsistenteNickname();
        
        if (asistenteNickname != null && edicionName != null) {
            SwingUtilities.invokeLater(() -> {
                try {
                    // First, preselect the asistente
                    JComboBox<String> asistentesCombo = getAsistentesComboBoxFromForm(form);
                    if (asistentesCombo != null) {
                        asistentesCombo.setSelectedItem(asistenteNickname);
                        
                        // Wait a bit for the registros to load, then preselect the edition
                        SwingUtilities.invokeLater(() -> {
                            try {
                                JComboBox<String> registrosCombo = getRegistrosComboBoxFromForm(form);
                                if (registrosCombo != null) {
                                    registrosCombo.setSelectedItem(edicionName);
                                }
                            } catch (Exception e) {
                                // Pre-selection failed, continue without pre-selection
                            }
                        });
                    }
                } catch (Exception e) {
                    // Pre-selection failed, continue without pre-selection
                }
            });
        }
    }
    
    /**
     * Preselecciona la información de la edición en el formulario de consulta de edición
     */
    private void preSelectInEditionForm(FormularioConsultaEdicionEvento form, String eventName, String editionName) {
        if (eventName != null && editionName != null) {
            SwingUtilities.invokeLater(() -> {
                try {
                    // First, preselect the event
                    JComboBox<String> eventCombo = getEventComboBoxFromForm(form);
                    if (eventCombo != null) {
                        eventCombo.setSelectedItem(eventName);
                        
                        // Wait a bit for the editions to load, then preselect the edition
                        SwingUtilities.invokeLater(() -> {
                            try {
                                JComboBox<String> editionCombo = getEditionComboBoxFromForm(form);
                                if (editionCombo != null) {
                                    editionCombo.setSelectedItem(editionName);
                                }
                            } catch (Exception e) {
                                // Pre-selection failed, continue without pre-selection
                            }
                        });
                    }
                } catch (Exception e) {
                    // Pre-selection failed, continue without pre-selection
                }
            });
        }
    }
    
    /**
     * Encuentra el evento que contiene una edición específica
     */
    private String[] findEventForEdition(String edicionName) {
        List<EventoDTO> eventos = controladorEventos.listarEventosDTO();
        for (EventoDTO evento : eventos) {
            List<String> ediciones = controladorEventos.listarEdicionesDeEvento(evento.getNombre());
            if (ediciones != null && ediciones.contains(edicionName)) {
                return new String[]{evento.getNombre(), edicionName};
            }
        }
        return new String[]{null, edicionName}; // Return null event if not found
    }
    
    /**
     * Extrae el nombre de la edición del string de registro
     * Format: "edicion (tipo_registro)"
     */
    private String parseEdicionFromRegistroInfo(String registroInfo) {
        if (registroInfo != null && registroInfo.contains(" (")) {
            return registroInfo.substring(0, registroInfo.lastIndexOf(" ("));
        }
        return registroInfo;
    }
    
    /**
     * Obtiene el nickname del asistente actualmente seleccionado
     */
    private String getCurrentSelectedAsistenteNickname() {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser != null && selectedUser.startsWith("A: ")) {
            return selectedUser.substring(3); // Remove "A: " prefix
        }
        return null;
    }
    
    // Helper methods to access private fields using reflection
    private JComboBox<String> getAsistentesComboBoxFromForm(FormularioConsultaRegistro form) {
        return getComboBoxFromForm(form, "asistentesCombobox");
    }
    
    private JComboBox<String> getRegistrosComboBoxFromForm(FormularioConsultaRegistro form) {
        return getComboBoxFromForm(form, "registrosCombobox");
    }
    
    private JComboBox<String> getEventComboBoxFromForm(FormularioConsultaEdicionEvento form) {
        return getComboBoxFromForm(form, "eventComboBox");
    }
    
    private JComboBox<String> getEditionComboBoxFromForm(FormularioConsultaEdicionEvento form) {
        return getComboBoxFromForm(form, "editionComboBox");
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getComboBoxFromForm(Object form, String fieldName) {
        try {
            Field field = form.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    protected void onAccept() {
        // Refresh user list
        loadUsers();
    }
    
    @Override
    protected void clearForm() {
        loadUsers();
    }
}
