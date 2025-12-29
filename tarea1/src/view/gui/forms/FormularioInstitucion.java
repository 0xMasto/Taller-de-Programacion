package view.gui.forms;

import logica.interfaces.iControladorEventos;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;

/**
 * Formulario para crear nuevas instituciones
 */
public class FormularioInstitucion extends BaseInternalForm {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField websiteField;
    private iControladorEventos controladorEventos;
    
    public FormularioInstitucion(iControladorEventos controladorEventos) {
        super("Alta de Institución");
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
        
        // Description area
        gbc.gridx = 0; gbc.gridy = 1;
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
        
        // Website field
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        contentPanel.add(new JLabel("Sitio Web:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        websiteField = new JTextField(20);
        contentPanel.add(websiteField, gbc);
        
        setSize(450, 350);
    }
    
    @Override
    protected void onAccept() {
        try {
            String name = nameField.getText().trim();
            String description = descriptionArea.getText().trim();
            String website = websiteField.getText().trim();
            
            if (name.isEmpty() || description.isEmpty() || website.isEmpty()) {
                showErrorMessage("Todos los campos son obligatorios.");
                return;
            }

            if (controladorEventos.existeInstitucion(name) == true) {
                showErrorMessage("La institución ya existe.");
                return;
            }

            controladorEventos.altaInstitucion(name, description, website);
               
                            
            showSuccessMessage("Institución creada exitosamente: " + name);
            clearForm();
            
        } catch (Exception e) {
            showErrorMessage("Error inesperado: " + e.getMessage());
        }
    }
    
    @Override
    protected void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
        websiteField.setText("");
    }
}
