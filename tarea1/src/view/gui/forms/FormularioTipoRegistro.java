package view.gui.forms;

import logica.dto.EdicionDTO;
import logica.dto.EventoDTO;
import logica.interfaces.iControladorEventos;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.List;

/**
 * Formulario para crear tipos de registro para ediciones de eventos
 */
public class FormularioTipoRegistro extends BaseInternalForm {
    private JComboBox<EventoDTO> eventComboBox;
    private JComboBox<EdicionDTO> editionComboBox;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField costField;
    private JTextField capacityField;
    
    private DefaultComboBoxModel<EventoDTO> eventComboBoxModel;
    private DefaultComboBoxModel<EdicionDTO> editionComboBoxModel;
    
    private iControladorEventos controladorEventos;
    
    public FormularioTipoRegistro(iControladorEventos controladorEventos) {
        super("Crear Tipo de Registro");
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        loadEvents();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Tipo de Registro"));
        
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
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(descriptionArea), gbc);
        
        // Cost field
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.weighty = 0.0;
        formPanel.add(new JLabel("Costo ($):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        costField = new JTextField(20);
        costField.setText("0.00");
        formPanel.add(costField, gbc);
        
        // Capacity field
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Cupo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        capacityField = new JTextField(20);
        capacityField.setText("100");
        formPanel.add(capacityField, gbc);
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        
        setSize(500, 400);
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
        
        return editionComboBox;
    }
    
    private void loadEvents() {
        eventComboBoxModel.removeAllElements();
        editionComboBoxModel.removeAllElements();
        
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
        
        List<EdicionDTO> ediciones = controladorEventos.listarEdicionesDeEventoDTO(evento.getNombre());
        for (EdicionDTO edicion : ediciones) {
            editionComboBoxModel.addElement(edicion);
        }
        
        if (ediciones.isEmpty()) {
            editionComboBoxModel.addElement(null);
            editionComboBox.setSelectedItem(null);
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
            
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del tipo de registro es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (descriptionArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La descripción es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse numeric fields
            BigDecimal cost;
            int capacity;
            
            try {
                cost = new BigDecimal(costField.getText().trim());
                if (cost.compareTo(BigDecimal.ZERO) < 0) {
                    JOptionPane.showMessageDialog(this, "El costo debe ser un valor positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El costo debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                capacity = Integer.parseInt(capacityField.getText().trim());
                if (capacity <= 0) {
                    JOptionPane.showMessageDialog(this, "El cupo debe ser un número positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El cupo debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get selected values
            EventoDTO selectedEvent = (EventoDTO) eventComboBox.getSelectedItem();
            EdicionDTO selectedEdition = (EdicionDTO) editionComboBox.getSelectedItem();
            
            // Create registration type
            controladorEventos.altaTipoRegistro(
                selectedEvent.getNombre(),
                selectedEdition.getNombre(),
                nameField.getText().trim(),
                descriptionArea.getText().trim(),
                cost,
                capacity
            );
            
            JOptionPane.showMessageDialog(this, 
                "Tipo de registro creado exitosamente: " + nameField.getText().trim(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear el tipo de registro: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
        costField.setText("0.00");
        capacityField.setText("100");
        
        if (eventComboBox.getItemCount() > 0) {
            eventComboBox.setSelectedIndex(0);
        }
        if (editionComboBox.getItemCount() > 0) {
            editionComboBox.setSelectedIndex(0);
        }
    }
}