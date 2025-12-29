package view.gui.forms;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import logica.interfaces.iControladorEventos;
import logica.model.EstadoEnum;

public class FormularioAceptarRechazar extends BaseInternalForm {

    private static final long serialVersionUID = 1L;

    private final iControladorEventos controladorEventos;
    private JComboBox<String> eventComboBox;
    private JComboBox<String> editionComboBox;
    private boolean cargandoEventos = false;

    public FormularioAceptarRechazar(iControladorEventos controladorEventos) {
        super("Aceptar o Rechazar Edición de Evento");
        this.controladorEventos = controladorEventos;
        setupComponents();
        loadEvents();
    }

    private void setupComponents() {
        contentPanel.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        contentPanel.add(formPanel, BorderLayout.CENTER);

        formPanel.add(new JLabel("Evento:"));
        eventComboBox = new JComboBox<>();
        eventComboBox.addActionListener(e -> {
            if (!cargandoEventos) {
                onEventSelected();
            }
        });
        formPanel.add(eventComboBox);

        formPanel.add(new JLabel("Edición:"));
        editionComboBox = new JComboBox<>();
        formPanel.add(editionComboBox);

        acceptButton.setText("Aceptar");
        cancelButton.setText("Rechazar");
        // No agregamos ActionListeners aquí, se manejan en BaseInternalForm
    }

    private void loadEvents() {
        cargandoEventos = true;
        eventComboBox.removeAllItems();
        List<String> eventos = controladorEventos.listarEventos();

        for (String nombreEvento : eventos) {
            eventComboBox.addItem(nombreEvento);
        }
        
        cargandoEventos = false;
    }

    private void onEventSelected() {
        editionComboBox.removeAllItems();
        String nombreEvento = (String) eventComboBox.getSelectedItem();

        if (nombreEvento == null || nombreEvento.trim().isEmpty()) {
            return;
        }

        List<String> ediciones = controladorEventos.listarEdicionesIngresadas(nombreEvento);

        if (ediciones == null || ediciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Este evento no tiene ediciones pendientes (INGRESADAS).",
                "Sin ediciones disponibles",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (String nombreEdicion : ediciones) {
            editionComboBox.addItem(nombreEdicion);
        }

        editionComboBox.setSelectedIndex(0);
    }

    private void cambiarEstadoSeleccionado(EstadoEnum nuevoEstado) {
        String nombreEdicion = (String) editionComboBox.getSelectedItem();
        String nombreEvento = (String) eventComboBox.getSelectedItem();

        if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar una edición primero.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            controladorEventos.cambiarEstadoEdicion(nombreEdicion, nuevoEstado);

            String mensaje = String.format("La edición '%s' del evento '%s' fue %s correctamente.",
                nombreEdicion,
                nombreEvento != null ? nombreEvento : "desconocido",
                nuevoEstado == EstadoEnum.ACEPTADA ? "aceptada" : "rechazada");

            JOptionPane.showMessageDialog(this,
                mensaje,
                "Operación exitosa",
                JOptionPane.INFORMATION_MESSAGE);

            onEventSelected();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Ocurrió un error al cambiar el estado: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void onAccept() {
        cambiarEstadoSeleccionado(EstadoEnum.ACEPTADA);
    }

    @Override
    protected void onCancel() {
        cambiarEstadoSeleccionado(EstadoEnum.RECHAZADA);
        clearForm();
        setVisible(false);
    }

    @Override
    protected void clearForm() {
        eventComboBox.setSelectedIndex(-1);
        editionComboBox.removeAllItems();
    }
}