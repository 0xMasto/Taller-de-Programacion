package view.gui.forms;

import logica.dto.CategoriaDTO;
import logica.interfaces.iControladorEventos;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

/**
 * Formulario para crear nuevos eventos
 */
public class FormularioEvento extends BaseInternalForm {
    private JTextField nameField;
    private JTextField acronymField;
    private JTextArea descriptionArea;
    private JList<CategoriaDTO> categoryList;
    private DefaultListModel<CategoriaDTO> categoryListModel;
    private iControladorEventos controladorEventos;
    
    public FormularioEvento(iControladorEventos controladorEventos) {
        super("Alta de Evento");
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        contentPanel.add(nameField, gbc);
        
        // Acronym field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        acronymField = new JTextField(20);
        contentPanel.add(acronymField, gbc);
        
        // Description area
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(8, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        contentPanel.add(new JScrollPane(descriptionArea), gbc);
        
        // Category selection
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        contentPanel.add(new JLabel("Categorías:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        
        // Load categories from controller
        List<CategoriaDTO> categorias = controladorEventos.listarCategoriasDTO();
        for (CategoriaDTO categoria : categorias) {
            categoryListModel.addElement(categoria);
        }
        
        categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        contentPanel.add(new JScrollPane(categoryList), gbc);
        
        setSize(500, 500);
    }
    
    @Override
    protected void onAccept() {
        try {
            String name = nameField.getText().trim();
            String acronym = acronymField.getText().trim();
            String description = descriptionArea.getText().trim();
            
            // Validación de campos obligatorios
            if (name.isEmpty() || acronym.isEmpty() || description.isEmpty()) {
                showErrorMessage("Todos los campos son obligatorios.");
                return;
            }
            
            // Get selected categories
            List<CategoriaDTO> selectedCategories = categoryList.getSelectedValuesList();
            if (selectedCategories.isEmpty()) {
                showErrorMessage("Debe seleccionar al menos una categoría.");
                return;
            }
            
            // Create event
            controladorEventos.altaEvento(name, acronym, description, selectedCategories);
            
            showSuccessMessage("Evento creado exitosamente");
            clearForm();
            
        } catch (Exception e) {
            showErrorMessage("Error inesperado: " + e.getMessage());
        }
    }
    
    @Override
    protected void clearForm() {
        nameField.setText("");
        acronymField.setText("");
        descriptionArea.setText("");
        categoryList.clearSelection();
    }
}
