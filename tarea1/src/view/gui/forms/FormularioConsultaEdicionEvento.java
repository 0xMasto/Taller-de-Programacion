package view.gui.forms;

import logica.dto.CategoriaDTO;
import logica.dto.EventoDTO;
import logica.dto.EdicionDTO;
import logica.dto.TipoRegistroDTO;
import logica.dto.PatrocinioDTO;
import logica.dto.RegistroDTO;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;

import javax.swing.*;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formulario para consultar información de ediciones de eventos
 * Usa DTOs en lugar de modelos para la interfaz de usuario
 */
public class FormularioConsultaEdicionEvento extends BaseInternalForm {
    private JComboBox<String> eventComboBox;
    private JComboBox<String> editionComboBox;
    
    // Event details panel
    private JPanel eventDetailsPanel;
    private JTextField eventNameField;
    private JTextField eventSiglaField;
    private JTextField eventDescripcionField;
    private JTextField eventCategoriasField;
    
    // Edition details panel
    private JPanel editionDetailsPanel;
    private JTextField editionNameField;
    private JTextField editionSiglaField;
    private JTextField editionFechaInicioField;
    private JTextField editionFechaFinField;
    private JTextField editionFechaAltaField;
    private JTextField editionOrganizadorField;
    private JTextField editionCiudadField;
    private JTextField editionPaisField;
    
    // Registration types combobox
    private JComboBox<String> registrationTypesComboBox;
    
    // Registrations list
    private JList<String> registrationsList;
    private DefaultListModel<String> registrationsListModel;
    
    // Sponsorships combobox
    private JComboBox<String> sponsorshipsComboBox;
    
    private iControladorEventos controladorEventos;
    private iControladorUsuarios controladorUsuarios;
    
    public FormularioConsultaEdicionEvento(iControladorEventos controladorEventos, iControladorUsuarios controladorUsuarios) {
        super("Consulta de Ediciones de Eventos");
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = controladorUsuarios;
        setupSpecificComponents();
        loadEvents();
    }
    
    // Add a new constructor that accepts pre-selected values
    public FormularioConsultaEdicionEvento(iControladorEventos controladorEventos, iControladorUsuarios controladorUsuarios, 
                                         String preSelectedEvent, String preSelectedEdition) {
        super("Consulta de Ediciones de Eventos");
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = controladorUsuarios;
        setupSpecificComponents();
        loadEvents();
        
        // Pre-select the event and edition
        if (preSelectedEvent != null) {
            eventComboBox.setSelectedItem(preSelectedEvent);
            onEventSelected();
            
            if (preSelectedEdition != null) {
                editionComboBox.setSelectedItem(preSelectedEdition);
                onEditionSelected();
            }
        }
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Selection panel (top)
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selección"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Event selection
        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventComboBox = new JComboBox<>();
        eventComboBox.addActionListener(e -> onEventSelected());
        selectionPanel.add(eventComboBox, gbc);
        
        // Edition selection
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        selectionPanel.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionComboBox = new JComboBox<>();
        editionComboBox.addActionListener(e -> onEditionSelected());
        selectionPanel.add(editionComboBox, gbc);
        
        // Main content panel (center)
        JPanel mainContentPanel = new JPanel(new BorderLayout(30, 10)); // Add horizontal and vertical gaps
        
        // Event details panel (left side)
        setupEventDetailsPanel();
        mainContentPanel.add(eventDetailsPanel, BorderLayout.WEST);
        
        // Edition details panel (right side)
        setupEditionDetailsPanel();
        mainContentPanel.add(editionDetailsPanel, BorderLayout.CENTER);
        
        // Combo boxes panel (bottom)
        setupComboBoxesPanel();
        
        // Add panels to main layout
        contentPanel.add(selectionPanel, BorderLayout.NORTH);
        contentPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        setSize(800 , 700);
    }
    
    private void setupEventDetailsPanel() {
        eventDetailsPanel = new JPanel(new GridBagLayout());
        eventDetailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Evento"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Event name
        gbc.gridx = 0; gbc.gridy = 0;
        eventDetailsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventNameField = new JTextField(15);
        eventNameField.setEditable(false);
        eventDetailsPanel.add(eventNameField, gbc);
        
        // Event sigla
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        eventDetailsPanel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventSiglaField = new JTextField(15);
        eventSiglaField.setEditable(false);
        eventDetailsPanel.add(eventSiglaField, gbc);
        
        // Event description
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        eventDetailsPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventDescripcionField = new JTextField(15);
        eventDescripcionField.setEditable(false);
        eventDetailsPanel.add(eventDescripcionField, gbc);
        
        // Event categories
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        eventDetailsPanel.add(new JLabel("Categorías:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        eventCategoriasField = new JTextField(15);
        eventCategoriasField.setEditable(false);
        eventDetailsPanel.add(eventCategoriasField, gbc);
    }
    
    private void setupEditionDetailsPanel() {
        editionDetailsPanel = new JPanel(new GridBagLayout());
        editionDetailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles de la Edición"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Edition name
        gbc.gridx = 0; gbc.gridy = 0;
        editionDetailsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionNameField = new JTextField(15);
        editionNameField.setEditable(false);
        editionDetailsPanel.add(editionNameField, gbc);
        
        // Edition sigla
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionSiglaField = new JTextField(15);
        editionSiglaField.setEditable(false);
        editionDetailsPanel.add(editionSiglaField, gbc);
        
        // Edition start date
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Fecha Inicio:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionFechaInicioField = new JTextField(15);
        editionFechaInicioField.setEditable(false);
        editionDetailsPanel.add(editionFechaInicioField, gbc);
        
        // Edition end date
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Fecha Fin:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionFechaFinField = new JTextField(15);
        editionFechaFinField.setEditable(false);
        editionDetailsPanel.add(editionFechaFinField, gbc);
        
        // Edition fecha alta
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Fecha Alta:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionFechaAltaField = new JTextField(15);
        editionFechaAltaField.setEditable(false);
        editionDetailsPanel.add(editionFechaAltaField, gbc);
        
        // Edition organizer
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Organizador:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionOrganizadorField = new JTextField(15);
        editionOrganizadorField.setEditable(false);
        editionDetailsPanel.add(editionOrganizadorField, gbc);
        
        // Edition city
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionCiudadField = new JTextField(15);
        editionCiudadField.setEditable(false);
        editionDetailsPanel.add(editionCiudadField, gbc);
        
        // Edition country
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        editionDetailsPanel.add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        editionPaisField = new JTextField(15);
        editionPaisField.setEditable(false);
        editionDetailsPanel.add(editionPaisField, gbc);
    }
    
    private void setupComboBoxesPanel() {
        // Create panel for combo boxes and list
        JPanel comboBoxesPanel = new JPanel(new GridBagLayout());
        comboBoxesPanel.setBorder(BorderFactory.createTitledBorder("Información Detallada"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Registration types combobox
        setupRegistrationTypesComboBox();
        gbc.gridx = 0; gbc.gridy = 0;
        comboBoxesPanel.add(new JLabel("Tipos de Registro:"), gbc);
        gbc.gridx = 1;
        comboBoxesPanel.add(registrationTypesComboBox, gbc);
        
        // Registrations list
        setupRegistrationsList();
        gbc.gridx = 0; gbc.gridy = 1;
        comboBoxesPanel.add(new JLabel("Registros:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JScrollPane registrationsScrollPane = new JScrollPane(registrationsList);
        registrationsScrollPane.setPreferredSize(new Dimension(300, 150));
        comboBoxesPanel.add(registrationsScrollPane, gbc);
        
        // Sponsorships combobox
        setupSponsorshipsComboBox();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        comboBoxesPanel.add(new JLabel("Patrocinios:"), gbc);
        gbc.gridx = 1;
        comboBoxesPanel.add(sponsorshipsComboBox, gbc);
        
        // Add combo boxes panel to main content
        contentPanel.add(comboBoxesPanel, BorderLayout.SOUTH);
    }
    
    private void setupRegistrationTypesComboBox() {
        registrationTypesComboBox = new JComboBox<>();
        registrationTypesComboBox.addItem(""); // Empty option first
        
        // Add selection listener to open consultation form
        registrationTypesComboBox.addActionListener(e -> {
            String selectedItem = (String) registrationTypesComboBox.getSelectedItem();
            if (selectedItem != null && !selectedItem.isEmpty()) {
                openRegistrationTypeConsultationForm(selectedItem);
            }
        });
    }
    
    private void setupRegistrationsList() {
        registrationsListModel = new DefaultListModel<>();
        registrationsList = new JList<>(registrationsListModel);
        registrationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Add selection listener to open consultation form
        registrationsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedItem = registrationsList.getSelectedValue();
                if (selectedItem != null && !selectedItem.isEmpty()) {
                    openRegistrationConsultationForm(selectedItem);
                }
            }
        });
    }
    
    private void setupSponsorshipsComboBox() {
        sponsorshipsComboBox = new JComboBox<>();
        sponsorshipsComboBox.addItem(""); // Empty option first
        
        // Add selection listener to open consultation form
        sponsorshipsComboBox.addActionListener(e -> {
            String selectedItem = (String) sponsorshipsComboBox.getSelectedItem();
            if (selectedItem != null && !selectedItem.isEmpty()) {
                openSponsorshipConsultationForm(selectedItem);
            }
        });
    }
    
    private void loadEvents() {
        eventComboBox.removeAllItems();
        editionComboBox.removeAllItems();
        
        // Use DTO method to get events
        List<EventoDTO> eventos = controladorEventos.listarEventosDTO();
        for (EventoDTO evento : eventos) {
            eventComboBox.addItem(evento.getNombre());
        }
        
        if (eventComboBox.getItemCount() > 0) {
            eventComboBox.setSelectedIndex(0);
            onEventSelected();
        } else {
            clearAllFields();
        }
    }
    
    private void onEventSelected() {
        String eventName = (String) eventComboBox.getSelectedItem();
        if (eventName == null) {
            clearEventDetails();
            clearEditionDetails();
            clearComboBoxes();
            return;
        }
        
        // Load event details using DTO
        EventoDTO evento = controladorEventos.buscarEventoDTO(eventName);
        if (evento != null) {
            showEventDetails(evento);
            loadEditions(eventName);
        }
    }
    
    private void loadEditions(String eventName) {
        editionComboBox.removeAllItems();
        
        // Use DTO method to get editions
        List<EdicionDTO> ediciones = controladorEventos.listarEdicionesDeEventoDTO(eventName);
        for (EdicionDTO edicion : ediciones) {
            editionComboBox.addItem(edicion.getNombre());
        }
        
        if (editionComboBox.getItemCount() > 0) {
            editionComboBox.setSelectedIndex(0);
            onEditionSelected();
        } else {
            clearEditionDetails();
            clearComboBoxes();
        }
    }
    
    private void onEditionSelected() {
        String editionName = (String) editionComboBox.getSelectedItem();
        if (editionName == null) {
            clearEditionDetails();
            clearComboBoxes();
            return;
        }
        
        // Load edition details using DTO
        EdicionDTO edicion = controladorEventos.buscarEdicionDTO(editionName);
        if (edicion != null) {
            showEditionDetails(edicion);
            loadRegistrationTypes(edicion);
            loadRegistrations(edicion);
            loadSponsorships(edicion);
        }
    }
    
    private void showEventDetails(EventoDTO evento) {
        eventNameField.setText(evento.getNombre());
        eventSiglaField.setText(evento.getSigla());
        eventDescripcionField.setText(evento.getDescripcion());
        
        List<CategoriaDTO> categorias = evento.getCategorias();
        if (categorias != null && !categorias.isEmpty()) {
            String categoriesStr = categorias.stream()
                .map(CategoriaDTO::getNombre)
                .collect(Collectors.joining(", "));
            eventCategoriasField.setText(categoriesStr);
        } else {
            eventCategoriasField.setText("Sin categorías asignadas");
        }
    }
    
    private void showEditionDetails(EdicionDTO edicion) {
        editionNameField.setText(edicion.getNombre());
        editionSiglaField.setText(edicion.getSigla());
        editionFechaInicioField.setText(edicion.getFechaInicio().toString());
        editionFechaFinField.setText(edicion.getFechaFin().toString());
        editionFechaAltaField.setText(edicion.getFechaAlta() != null ? edicion.getFechaAlta().toString() : "No disponible");
        editionOrganizadorField.setText(edicion.getOrganizador());
        editionCiudadField.setText(edicion.getCiudad());
        editionPaisField.setText(edicion.getPais());
    }
    
    private void loadRegistrationTypes(EdicionDTO edicion) {
        registrationTypesComboBox.removeAllItems();
        registrationTypesComboBox.addItem(""); // Empty option first
        
        // Use DTO method to get registration types
        List<TipoRegistroDTO> tiposRegistro = controladorEventos.obtenerTiposDeRegistroDeEdicionDTO(edicion.getNombre());
        for (TipoRegistroDTO tipo : tiposRegistro) {
            registrationTypesComboBox.addItem(tipo.getNombre());
        }
        
        if (registrationTypesComboBox.getItemCount() == 1) { // Only empty option
            registrationTypesComboBox.addItem("No hay tipos de registro configurados");
        }
    }
    
    private void loadRegistrations(EdicionDTO edicion) {
        registrationsListModel.clear();
        
        List<RegistroDTO> registros = controladorEventos.obtenerRegistrosDeEdicionDTO(edicion.getNombre());
        
        for (RegistroDTO registro : registros) {
            // Create a display string for the registration
            String displayText = "Registro " + registro.getTipoRegistro().getNombre() + 
                               " - " + registro.getFechaRegistro().toString();
            registrationsListModel.addElement(displayText);
        }
        
        if (registros.isEmpty()) {
            registrationsListModel.addElement("No hay registros para esta edición");
        }
    }
    
    private void loadSponsorships(EdicionDTO edicion) {
        sponsorshipsComboBox.removeAllItems();
        sponsorshipsComboBox.addItem(""); // Empty option first
        
        List<PatrocinioDTO> patrocinios = controladorEventos.obtenerPatrociniosDeEdicionDTO(edicion.getNombre());
        for (PatrocinioDTO patrocinio : patrocinios) {
            sponsorshipsComboBox.addItem(patrocinio.getCodigoPatrocinio());
        }
        
        if (sponsorshipsComboBox.getItemCount() == 1) { // Only empty option
            sponsorshipsComboBox.addItem("No hay patrocinios registrados");
        }
    }
    
    private void clearEventDetails() {
        eventNameField.setText("");
        eventSiglaField.setText("");
        eventDescripcionField.setText("");
        eventCategoriasField.setText("");
    }
    
    private void clearEditionDetails() {
        editionNameField.setText("");
        editionSiglaField.setText("");
        editionFechaInicioField.setText("");
        editionFechaFinField.setText("");
        editionOrganizadorField.setText("");
        editionCiudadField.setText("");
        editionPaisField.setText("");
    }
    
    private void clearComboBoxes() {
        registrationTypesComboBox.removeAllItems();
        registrationTypesComboBox.addItem("");
        registrationsListModel.clear();
        sponsorshipsComboBox.removeAllItems();
        sponsorshipsComboBox.addItem("");
    }
    
    private void clearAllFields() {
        clearEventDetails();
        clearEditionDetails();
        clearComboBoxes();
    }
    
    @Override
    protected void onAccept() {
        loadEvents();
    }
    
    @Override
    protected void clearForm() {
        loadEvents();
    }
    
    private void openRegistrationTypeConsultationForm(String nombreTipoRegistro) {
        FormularioConsultaTipoRegistro tipoRegistroForm = new FormularioConsultaTipoRegistro(controladorEventos);
        
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(tipoRegistroForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            desktopPane.add(tipoRegistroForm);
            tipoRegistroForm.setVisible(true);
            
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = tipoRegistroForm.getSize();
            tipoRegistroForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                tipoRegistroForm.setSelected(true);
            } catch (Exception e) {
            }
            
            preSelectInRegistrationTypeForm(tipoRegistroForm, nombreTipoRegistro);
        }
    }
    
    private void openRegistrationConsultationForm(String selectedRegistration) {
        FormularioConsultaRegistro registroForm = new FormularioConsultaRegistro(controladorUsuarios, controladorEventos);
        
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(registroForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            desktopPane.add(registroForm);
            registroForm.setVisible(true);
            
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = registroForm.getSize();
            registroForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                registroForm.setSelected(true);
            } catch (Exception e) {
            }
            
            preSelectInRegistrationForm(registroForm, selectedRegistration);
        }
    }
    
    private void openSponsorshipConsultationForm(String codigoPatrocinio) {
        // Create the sponsorship consultation form
        FormularioConsultaPatrocinio patrocinioForm = new FormularioConsultaPatrocinio(controladorEventos);
        
        // Get the desktop pane from the parent window
        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, this);
        if (desktopPane != null) {
            // Close any existing sponsorship consultation form
            for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
                if (existingFrame.getTitle().equals(patrocinioForm.getTitle())) {
                    existingFrame.dispose();
                    break;
                }
            }
            
            // Add the new form to the desktop pane
            desktopPane.add(patrocinioForm);
            patrocinioForm.setVisible(true);
            
            // Center the frame in the desktop pane
            Dimension desktopSize = desktopPane.getSize();
            Dimension frameSize = patrocinioForm.getSize();
            patrocinioForm.setLocation(
                (desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2
            );
            
            try {
                patrocinioForm.setSelected(true);
            } catch (Exception e) {
                // Ignore selection exceptions
            }
            
            // Pre-select the sponsorship in the new form
            preSelectInSponsorshipForm(patrocinioForm, codigoPatrocinio);
        }
    }
    
    private void preSelectInRegistrationTypeForm(FormularioConsultaTipoRegistro form, String nombreTipoRegistro) {
        // Get the current event and edition names
        String eventName = (String) eventComboBox.getSelectedItem();
        String editionName = (String) editionComboBox.getSelectedItem();
        
        // Use SwingUtilities.invokeLater to ensure the form is fully loaded
        SwingUtilities.invokeLater(() -> {
            try {
                // First, set the event selection and trigger loading
                JComboBox<String> eventCombo = getEventComboBoxFromForm(form);
                if (eventCombo != null && eventName != null) {
                    // Find and select the event
                    for (int i = 0; i < eventCombo.getItemCount(); i++) {
                        String item = eventCombo.getItemAt(i);
                        if (item != null && item.equals(eventName)) {
                            eventCombo.setSelectedIndex(i);
                            // Trigger the event selection action
                            eventCombo.getActionListeners()[0].actionPerformed(null);
                            break;
                        }
                    }
                }
                
                SwingUtilities.invokeLater(() -> {
                    try {
                        JComboBox<String> editionCombo = getEditionComboBoxFromForm(form);
                        if (editionCombo != null && editionName != null) {
                            for (int i = 0; i < editionCombo.getItemCount(); i++) {
                                String item = editionCombo.getItemAt(i);
                                if (item != null && item.equals(editionName)) {
                                    editionCombo.setSelectedIndex(i);
                                    editionCombo.getActionListeners()[0].actionPerformed(null);
                                    break;
                                }
                            }
                        }
                        SwingUtilities.invokeLater(() -> {
                            try {
                                JComboBox<String> tipoRegistroCombo = getTipoRegistroComboBoxFromForm(form);
                                if (tipoRegistroCombo != null && nombreTipoRegistro != null) {
                                    for (int i = 0; i < tipoRegistroCombo.getItemCount(); i++) {
                                        String item = tipoRegistroCombo.getItemAt(i);
                                        if (item != null && item.equals(nombreTipoRegistro)) {
                                            tipoRegistroCombo.setSelectedIndex(i);
                                            tipoRegistroCombo.getActionListeners()[0].actionPerformed(null);
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        });
                    } catch (Exception e) {
                    }
                });
            } catch (Exception e) {
            }
        });
    }
    
    private void preSelectInRegistrationForm(FormularioConsultaRegistro form, String selectedRegistration) {
        SwingUtilities.invokeLater(() -> {
            try {
                JComboBox<String> registrationCombo = getRegistrationComboBoxFromForm(form);
                if (registrationCombo != null && selectedRegistration != null) {
                    registrationCombo.setSelectedItem(selectedRegistration);
                }
            } catch (Exception e) {
            }
        });
    }
    
    private void preSelectInSponsorshipForm(FormularioConsultaPatrocinio form, String codigoPatrocinio) {
        SwingUtilities.invokeLater(() -> {
            try {
                JComboBox<String> sponsorshipCombo = getSponsorshipComboBoxFromForm(form);
                if (sponsorshipCombo != null && codigoPatrocinio != null) {
                    sponsorshipCombo.setSelectedItem(codigoPatrocinio);
                }
            } catch (Exception e) {
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getEventComboBoxFromForm(FormularioConsultaTipoRegistro form) {
        try {
            java.lang.reflect.Field field = form.getClass().getDeclaredField("eventComboBox");
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getEditionComboBoxFromForm(FormularioConsultaTipoRegistro form) {
        try {
            java.lang.reflect.Field field = form.getClass().getDeclaredField("editionComboBox");
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getTipoRegistroComboBoxFromForm(FormularioConsultaTipoRegistro form) {
        try {
            java.lang.reflect.Field field = form.getClass().getDeclaredField("registrationTypeComboBox");
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getRegistrationComboBoxFromForm(FormularioConsultaRegistro form) {
        try {
            java.lang.reflect.Field field = form.getClass().getDeclaredField("registrationComboBox");
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private JComboBox<String> getSponsorshipComboBoxFromForm(FormularioConsultaPatrocinio form) {
        try {
            java.lang.reflect.Field field = form.getClass().getDeclaredField("sponsorshipComboBox");
            field.setAccessible(true);
            return (JComboBox<String>) field.get(form);
        } catch (Exception e) {
            return null;
        }
    }
}
