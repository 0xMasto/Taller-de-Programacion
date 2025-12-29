package view.gui.forms;

import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import exception.NombreEdicionException;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.GridBagLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Formulario para crear ediciones de eventos
 */
public class FormularioEdicionEvento extends BaseInternalForm {
    private JTextField nameField;
    private JTextField acronymField;
    private JComboBox<String> organizerComboBox;
    private JTextField cityField;
    private JTextField countryField;
    private JXDatePicker fechaAltaPicker;
    private JComboBox<String> eventComboBox;
    private JXDatePicker startDatePicker;
    private JXDatePicker endDatePicker;
    private iControladorEventos controladorEventos;
    private iControladorUsuarios controladorUsuarios;
    
    
    
    
    public FormularioEdicionEvento(iControladorEventos controladorEventos, iControladorUsuarios controladorUsuarios) {
        super("Alta de Edición de Evento");
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = controladorUsuarios;
        setupSpecificComponents();
        loadComboBoxData();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Event selection
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        eventComboBox = new JComboBox<>();
        contentPanel.add(eventComboBox, gbc);
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        contentPanel.add(nameField, gbc);
        
        // Acronym field
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        acronymField = new JTextField(20);
        contentPanel.add(acronymField, gbc);
        
        // Start date field
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Fecha Inicio:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        // Crear JXDatePicker
        startDatePicker = new JXDatePicker();
        startDatePicker.setDate(new Date()); // Fecha actual
        startDatePicker.setFormats("dd/MM/yyyy"); // Formato de fecha
        contentPanel.add(startDatePicker, gbc);
        // IMPORTANTE: Resetear weighty para los siguientes componentes
        gbc.weighty = 0.0;
        gbc.gridy++; // Incrementar gridy para el siguiente componente
        
        
        
        // End date field
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Fecha Fin:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        // Crear JXDatePicker
        endDatePicker = new JXDatePicker();
        endDatePicker.setDate(new Date()); // Fecha actual
        endDatePicker.setFormats("dd/MM/yyyy"); // Formato de fecha
        contentPanel.add(endDatePicker, gbc);
        // IMPORTANTE: Resetear weighty para los siguientes componentes
        gbc.weighty = 0.0;
        gbc.gridy++; // Incrementar gridy para el siguiente componente
        
        
        // Organizer selection
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Organizador:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        organizerComboBox = new JComboBox<>();
        contentPanel.add(organizerComboBox, gbc);
        
        // City field
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cityField = new JTextField(20);
        contentPanel.add(cityField, gbc);
        
        // Country field
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        countryField = new JTextField(20);
        contentPanel.add(countryField, gbc);
        
        // Fecha Alta field
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Fecha Alta:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fechaAltaPicker = new JXDatePicker();
        fechaAltaPicker.setDate(new Date()); // Fecha actual
        fechaAltaPicker.setFormats("dd/MM/yyyy"); // Formato de fecha
        contentPanel.add(fechaAltaPicker, gbc);
        gbc.weighty = 0.0;
        gbc.gridy++;
        
        setSize(500, 450);
    }
    
    private void loadComboBoxData() {
        // Load events
        eventComboBox.removeAllItems();
        for (String evento : controladorEventos.listarEventos()) {
            eventComboBox.addItem(evento);
        }
        
        // Load organizers
        organizerComboBox.removeAllItems();
       
        
        for (String organizador : controladorUsuarios.listarOrganizadores()) {
            organizerComboBox.addItem(organizador);
        }
    }
    
    @Override
    protected void onAccept() {
        try {
            String selectedEvent = (String) eventComboBox.getSelectedItem();
            if (selectedEvent == null) {
                showErrorMessage("Debe seleccionar un evento.");
                return;
            }      
            String selectedOrganizer = (String) organizerComboBox.getSelectedItem();
            if (selectedOrganizer == null) {
                showErrorMessage("Debe seleccionar un organizador.");
                return;
            }
            String name = nameField.getText().trim();
            String acronym = acronymField.getText().trim();
            LocalDate startDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = endDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();     
            LocalDate fechaAlta = fechaAltaPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (name.isEmpty() || acronym.isEmpty() || city.isEmpty() || country.isEmpty()) {
                showErrorMessage("Todos los campos son obligatorios.");
                return;
            }
            // Check if start date is before end date
            if (startDate.isAfter(endDate)) {
                showErrorMessage("La fecha de inicio debe ser anterior a la fecha de fin.");
                return;
            }
            controladorEventos.altaEdicionEvento(
            name, acronym, startDate, endDate, fechaAlta, selectedOrganizer, city, country, selectedEvent
            );
            String error = "<html>Edicion " + "<b>" + name + "</b>" + " creada exitosamente.";
            JOptionPane.showMessageDialog(this, error);          
        }catch(NombreEdicionException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
          
        clearForm();
    }
    
    @Override
    protected void clearForm() {
        nameField.setText("");
        acronymField.setText("");
        cityField.setText("");
        countryField.setText("");
        if (eventComboBox.getItemCount() > 0) {
            eventComboBox.setSelectedIndex(0);
        }
        if (organizerComboBox.getItemCount() > 0) {
            organizerComboBox.setSelectedIndex(0);
        }
    }
}
