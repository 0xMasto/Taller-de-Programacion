package view.gui.forms;

import logica.dto.AsistenteDTO;
import logica.dto.EdicionDTO;
import logica.dto.EventoDTO;
import logica.dto.TipoRegistroDTO;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

/**
 * Formulario para crear registros de asistentes a eventos
 */
public class FormularioRegistro extends BaseInternalForm {
    private JComboBox<EventoDTO> eventComboBox;
    private JComboBox<EdicionDTO> editionComboBox;
    private JComboBox<TipoRegistroDTO> registrationTypeComboBox;
    private JComboBox<AsistenteDTO> assistantComboBox;
    
    private DefaultComboBoxModel<EventoDTO> eventComboBoxModel;
    private DefaultComboBoxModel<EdicionDTO> editionComboBoxModel;
    private DefaultComboBoxModel<TipoRegistroDTO> registrationTypeComboBoxModel;
    private DefaultComboBoxModel<AsistenteDTO> assistantComboBoxModel;
    
    private iControladorEventos controladorEventos;
    private iControladorUsuarios controladorUsuarios;
    
    public FormularioRegistro(iControladorEventos controladorEventos, iControladorUsuarios controladorUsuarios) {
        super("Crear Registro");
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = controladorUsuarios;
        setupSpecificComponents();
        loadEvents();
        loadAssistants();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Registro"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Event selection
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(createEventComboBox(), gbc);
        
        // Edition selection
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(createEditionComboBox(), gbc);
        
        // Registration type selection
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Tipo de Registro:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(createRegistrationTypeComboBox(), gbc);
        
        // Assistant selection
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Asistente:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formPanel.add(createAssistantComboBox(), gbc);
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        
        setSize(500, 300);
    }
    
    private JComboBox<EventoDTO> createEventComboBox() {
        eventComboBoxModel = new DefaultComboBoxModel<>();
        eventComboBox = new JComboBox<>(eventComboBoxModel);
        eventComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EventoDTO) {
                    EventoDTO evento = (EventoDTO) value;
                    setText(evento.getNombre() + " (" + evento.getSigla() + ")");
                }
                return this;
            }
        });
        
        eventComboBox.addActionListener(e -> {
            EventoDTO selectedEvent = (EventoDTO) eventComboBox.getSelectedItem();
            if (selectedEvent != null) {
                loadEditions(selectedEvent);
            }
        });
        
        return eventComboBox;
    }
    
    private JComboBox<EdicionDTO> createEditionComboBox() {
        editionComboBoxModel = new DefaultComboBoxModel<>();
        editionComboBox = new JComboBox<>(editionComboBoxModel);
        editionComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EdicionDTO) {
                    EdicionDTO edicion = (EdicionDTO) value;
                    setText(edicion.getNombre() + " - " + edicion.getCiudad() + ", " + edicion.getPais());
                }
                return this;
            }
        });
        
        editionComboBox.addActionListener(e -> {
            EdicionDTO selectedEdition = (EdicionDTO) editionComboBox.getSelectedItem();
            if (selectedEdition != null) {
                loadRegistrationTypes(selectedEdition);
            }
        });
        
        return editionComboBox;
    }
    
    private JComboBox<TipoRegistroDTO> createRegistrationTypeComboBox() {
        registrationTypeComboBoxModel = new DefaultComboBoxModel<>();
        registrationTypeComboBox = new JComboBox<>(registrationTypeComboBoxModel);
        registrationTypeComboBox.setRenderer(new DefaultListCellRenderer() {
    @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoRegistroDTO) {
                    TipoRegistroDTO tipo = (TipoRegistroDTO) value;
                    setText(tipo.getNombre() + " - $" + tipo.getCosto());
                }
                return this;
            }
        });
        
        return registrationTypeComboBox;
    }
    
    private JComboBox<AsistenteDTO> createAssistantComboBox() {
        assistantComboBoxModel = new DefaultComboBoxModel<>();
        assistantComboBox = new JComboBox<>(assistantComboBoxModel);
        assistantComboBox.setRenderer(new DefaultListCellRenderer() {
    @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof AsistenteDTO) {
                    AsistenteDTO asistente = (AsistenteDTO) value;
                    setText(asistente.getNickname() + " - " + asistente.getNombre() + " " + asistente.getApellido());
                }
                return this;
            }
        });
        
        return assistantComboBox;
    }
    
    private void loadEvents() {
        eventComboBoxModel.removeAllElements();
        editionComboBoxModel.removeAllElements();
        registrationTypeComboBoxModel.removeAllElements();
        
        List<EventoDTO> eventos = controladorEventos.listarEventosDTO();
        for (EventoDTO evento : eventos) {
            eventComboBoxModel.addElement(evento);
        }
        
        if (eventos.isEmpty()) {
            eventComboBoxModel.addElement(null);
            eventComboBox.setSelectedItem(null);
        }
    }
    
    private void loadEditions(EventoDTO evento) {
        editionComboBoxModel.removeAllElements();
        registrationTypeComboBoxModel.removeAllElements();
        
        List<EdicionDTO> ediciones = controladorEventos.listarEdicionesDeEventoDTO(evento.getNombre());
        for (EdicionDTO edicion : ediciones) {
            editionComboBoxModel.addElement(edicion);
        }
        
        if (ediciones.isEmpty()) {
            editionComboBoxModel.addElement(null);
            editionComboBox.setSelectedItem(null);
        }
    }
    
    private void loadRegistrationTypes(EdicionDTO edicion) {
        registrationTypeComboBoxModel.removeAllElements();
        
        List<TipoRegistroDTO> tiposRegistro = controladorEventos.obtenerTiposDeRegistroDeEdicionDTO(edicion.getNombre());
        for (TipoRegistroDTO tipo : tiposRegistro) {
            registrationTypeComboBoxModel.addElement(tipo);
        }
        
        if (tiposRegistro.isEmpty()) {
            registrationTypeComboBoxModel.addElement(null);
            registrationTypeComboBox.setSelectedItem(null);
        }
    }
    
    private void loadAssistants() {
        assistantComboBoxModel.removeAllElements();
        List<AsistenteDTO> asistentes = controladorUsuarios.listarAsistentesDTO();
        for (AsistenteDTO asistente : asistentes) {
            assistantComboBoxModel.addElement(asistente);
        }
        
        if (asistentes.isEmpty()) {
            assistantComboBoxModel.addElement(null);
            assistantComboBox.setSelectedItem(null);
        }
    }

    @Override
    protected void onAccept() {
       try {
            // Validate required fields
            if (eventComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un evento", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (editionComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una edición", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (registrationTypeComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de registro", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (assistantComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un asistente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get selected values
            EdicionDTO selectedEdition = (EdicionDTO) editionComboBox.getSelectedItem();
            TipoRegistroDTO selectedType = (TipoRegistroDTO) registrationTypeComboBox.getSelectedItem();
            AsistenteDTO selectedAssistant = (AsistenteDTO) assistantComboBox.getSelectedItem();
            

            
            // Create registration
            controladorEventos.registrarAsistente(
                selectedAssistant.getNickname(),
                selectedType.getNombre(),
                selectedEdition.getNombre()
            );
            
            JOptionPane.showMessageDialog(this, 
                "Registro creado exitosamente para " + selectedAssistant.getNombre(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            clearForm();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear el registro: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void clearForm() {
        if (eventComboBox.getItemCount() > 0) {
            eventComboBox.setSelectedIndex(0);
        }
        if (editionComboBox.getItemCount() > 0) {
            editionComboBox.setSelectedIndex(0);
        }
        if (registrationTypeComboBox.getItemCount() > 0) {
            registrationTypeComboBox.setSelectedIndex(0);
        }
        if (assistantComboBox.getItemCount() > 0) {
            assistantComboBox.setSelectedIndex(0);
        }
    }
}
