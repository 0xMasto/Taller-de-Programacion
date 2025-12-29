package view.gui.forms;

import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import org.jdesktop.swingx.JXDatePicker;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import logica.dto.InstitucionDTO;
import exception.BusinessException;

public class FormularioUsuario extends BaseInternalForm {
    private JTextField nicknameField;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField lastNameField;
    private JXDatePicker birthDatePicker; 
    private JTextField descriptionField;
    private JTextField websiteField;
    private JComboBox<String> institutionComboBox;
    private JRadioButton assistantRadioButton;
    private JRadioButton organizerRadioButton;
    private ButtonGroup userTypeGroup;
    private JPanel asistentePanel;
    private JPanel organizadorPanel;
    private iControladorUsuarios controladorUsuarios;
    private iControladorEventos controladorEventos;

    public FormularioUsuario(iControladorUsuarios controladorUsuarios, iControladorEventos controladorEventos) {
        super("Alta de Usuario");
        this.controladorUsuarios = controladorUsuarios;
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        loadInstitutionsFromController();
    }

    private void setupSpecificComponents() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Tipo de usuario
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(new JLabel("Tipo de Usuario:"), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        assistantRadioButton = new JRadioButton("Asistente");
        contentPanel.add(assistantRadioButton, gbc);

        gbc.gridx = 1;
        organizerRadioButton = new JRadioButton("Organizador");
        contentPanel.add(organizerRadioButton, gbc);

        userTypeGroup = new ButtonGroup();
        userTypeGroup.add(assistantRadioButton);
        userTypeGroup.add(organizerRadioButton);
        assistantRadioButton.setSelected(true);

        // Nickname
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Nickname:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nicknameField = new JTextField(20);
        contentPanel.add(nicknameField, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        contentPanel.add(nameField, gbc);

        // Correo
        gbc.gridx = 0; gbc.gridy = 4;
        contentPanel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        contentPanel.add(emailField, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 5;
        contentPanel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20);
        contentPanel.add(passwordField, gbc);

        // Confirmación de contraseña
        gbc.gridx = 0; gbc.gridy = 6;
        contentPanel.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        confirmPasswordField = new JPasswordField(20);
        contentPanel.add(confirmPasswordField, gbc);

        // ----- Panel Asistente -----
        asistentePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcA = new GridBagConstraints();
        gbcA.insets = new Insets(5,5,5,5);
        gbcA.anchor = GridBagConstraints.WEST;

        gbcA.gridx = 0; gbcA.gridy = 0;
        asistentePanel.add(new JLabel("Apellido:"), gbcA);
        gbcA.gridx = 1; gbcA.fill = GridBagConstraints.HORIZONTAL; gbcA.weightx = 1.0;
        lastNameField = new JTextField(20);
        asistentePanel.add(lastNameField, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 1;
        asistentePanel.add(new JLabel("Fecha Nacimiento:"), gbcA);
        gbcA.gridx = 1; gbcA.fill = GridBagConstraints.HORIZONTAL;
        birthDatePicker = new JXDatePicker();
        birthDatePicker.setFormats("yyyy-MM-dd");
        asistentePanel.add(birthDatePicker, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 2;
        asistentePanel.add(new JLabel("Institución:"), gbcA);
        gbcA.gridx = 1; gbcA.fill = GridBagConstraints.HORIZONTAL;
        institutionComboBox = new JComboBox<>();
        asistentePanel.add(institutionComboBox, gbcA);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        contentPanel.add(asistentePanel, gbc);

        // ----- Panel Organizador -----
        organizadorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcO = new GridBagConstraints();
        gbcO.insets = new Insets(5,5,5,5);
        gbcO.anchor = GridBagConstraints.WEST;

        gbcO.gridx = 0; gbcO.gridy = 0;
        organizadorPanel.add(new JLabel("Descripción:"), gbcO);
        gbcO.gridx = 1; gbcO.fill = GridBagConstraints.HORIZONTAL; gbcO.weightx = 1.0;
        descriptionField = new JTextField(20);
        organizadorPanel.add(descriptionField, gbcO);

        gbcO.gridx = 0; gbcO.gridy = 1;
        organizadorPanel.add(new JLabel("Sitio Web:"), gbcO);
        gbcO.gridx = 1; gbcO.fill = GridBagConstraints.HORIZONTAL;
        websiteField = new JTextField(20);
        organizadorPanel.add(websiteField, gbcO);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        contentPanel.add(organizadorPanel, gbc);

        togglePanels();

        assistantRadioButton.addActionListener(e -> togglePanels());
        organizerRadioButton.addActionListener(e -> togglePanels());

        setSize(500, 650);
    }

    private void togglePanels() {
        asistentePanel.setVisible(assistantRadioButton.isSelected());
        organizadorPanel.setVisible(organizerRadioButton.isSelected());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadInstitutionsFromController() {
        institutionComboBox.removeAllItems();
        List<String> nombresInstituciones = controladorEventos.listarInstituciones();
        institutionComboBox.addItem("");
        for (String nombre : nombresInstituciones) {
            institutionComboBox.addItem(nombre);
        }
    }

    @Override
    protected void onAccept() {
        try {
            String nickname = nicknameField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

            // Validaciones generales
            if (nickname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showErrorMessage("Los campos Nickname, Nombre, Correo y Contraseña son obligatorios.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showErrorMessage("Las contraseñas no coinciden.");
                return;
            }

            if (controladorUsuarios.existeNickname(nickname)) {
                showErrorMessage("El nickname ya está en uso. Elija otro.");
                return;
            }

            if (controladorUsuarios.existeCorreo(email)) {
                showErrorMessage("El correo electrónico ya está en uso. Ingrese otro.");
                return;
            }

            // Alta de Asistente
            if (assistantRadioButton.isSelected()) {
                String lastName = lastNameField.getText().trim();
                Date fecha = birthDatePicker.getDate();
                String selectedInstitutionName = (String) institutionComboBox.getSelectedItem();

                if (lastName.isEmpty() || fecha == null) {
                    showErrorMessage("Para Asistente, Apellido y Fecha de nacimiento son obligatorios.");
                    return;
                }

                LocalDate birthDate = new java.sql.Date(fecha.getTime()).toLocalDate();

                if (selectedInstitutionName != null && selectedInstitutionName != "") {
                    controladorUsuarios.altaAsistente(
                        nickname, name, email, password, null, lastName, birthDate,
                        selectedInstitutionName
                        );
                } else {
                    controladorUsuarios.altaAsistente(
                        nickname, name, email, password, null, lastName, birthDate, null
                    );
                }

                showSuccessMessage("Asistente creado exitosamente: " + nickname);

            // Alta de Organizador
            } else if (organizerRadioButton.isSelected()) {
                String description = descriptionField.getText().trim();
                String website = websiteField.getText().trim();

                if (description.isEmpty()) {
                    showErrorMessage("Para Organizador, la Descripción es obligatoria.");
                    return;
                }

                controladorUsuarios.altaOrganizador(
                    nickname, name, email, password, null, description, website
                );

                showSuccessMessage("Organizador creado exitosamente: " + nickname);
            }

            clearForm();

        } catch (BusinessException e) {
            showErrorMessage("Error de negocio: " + e.getMessage());
        } catch (Exception e) {
            showErrorMessage("Error inesperado: " + e.getMessage());
        }
    }

    @Override
    protected void clearForm() {
        nicknameField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        lastNameField.setText("");
        birthDatePicker.setDate(null);
        descriptionField.setText("");
        websiteField.setText("");
        institutionComboBox.setSelectedItem(null);
        assistantRadioButton.setSelected(true);
        togglePanels();
    }
}
