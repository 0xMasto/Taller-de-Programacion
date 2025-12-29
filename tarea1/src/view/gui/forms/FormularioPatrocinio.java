package view.gui.forms;

import logica.interfaces.iControladorEventos;
import exception.CostoRegistrosExcedidoException;
import exception.PatrocinioDuplicadoException;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.math.BigDecimal;

import java.util.List;
import java.util.Set;

/**
 * Formulario para crear nuevos patrocinios
 */
public class FormularioPatrocinio extends BaseInternalForm {
      // Componentes del formulario
    private iControladorEventos controladorEventos;
    private JComboBox<String> eventosComboBox;
    private JComboBox<String> edicionesComboBox;
    private JComboBox<String> institucionesComboBox;
    private JComboBox<String> tipoRegistroComboBox;
    private JComboBox<String> nivelPatrocinioComboBox;
    private JTextField txtAporteEconomico;
    private JTextField txtCantidadRegistros;
    private JTextField txtCodigoPatrocinio;
    private JLabel lblCostoRegistros;
    private JLabel lblPorcentajeAporte;

    public FormularioPatrocinio(iControladorEventos controladorEventos) {
        super("Alta de Patrocinio");
        this.controladorEventos = controladorEventos;
        setupSpecificComponents();
        cargarEventos();
    }
    
    private void setupSpecificComponents() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Evento
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        eventosComboBox = new JComboBox<>();
        eventosComboBox.addItem(""); // Opción vacía
        contentPanel.add(eventosComboBox, gbc);

        // Edición
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        edicionesComboBox = new JComboBox<>();
        edicionesComboBox.setEnabled(false); // Deshabilitado hasta seleccionar evento
        contentPanel.add(edicionesComboBox, gbc);

        // TipoRegistro selection
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Tipo de Registro:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tipoRegistroComboBox = new JComboBox<>();
        tipoRegistroComboBox.setEnabled(false); // Deshabilitado hasta seleccionar edicion
        contentPanel.add(tipoRegistroComboBox, gbc);

        // Institucion selection
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Institucion:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        institucionesComboBox = new JComboBox<>();
        contentPanel.add(institucionesComboBox, gbc);

        // NivelPatrocinio selection
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Nivel de Patrocinio:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nivelPatrocinioComboBox = new JComboBox<>(new String[]{"", "Platino", "Oro", "Plata", "Bronce"});
        contentPanel.add(nivelPatrocinioComboBox, gbc);

        // Aporte Economico
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Aporte Economico:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtAporteEconomico = new JTextField(10);
        contentPanel.add(txtAporteEconomico, gbc);

        // Cantidad Registros
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Cantidad Registros:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCantidadRegistros = new JTextField(10);
        contentPanel.add(txtCantidadRegistros, gbc);

        // Codigo Patrocinio
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Codigo Patrocinio:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtCodigoPatrocinio = new JTextField(10);
        contentPanel.add(txtCodigoPatrocinio, gbc);

        // Labels informativos
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        lblCostoRegistros = new JLabel("Costo de registros: $0.00");
        contentPanel.add(lblCostoRegistros, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        lblPorcentajeAporte = new JLabel("Porcentaje del aporte: 0%");
        contentPanel.add(lblPorcentajeAporte, gbc);

        
        setSize(500, 500);

        // Event listeners
        eventosComboBox.addActionListener(e -> actualizarEdiciones());
        edicionesComboBox.addActionListener(e -> actualizarTipoRegistro());
        txtAporteEconomico.addActionListener(e -> calcularCostosYPorcentaje());
        txtCantidadRegistros.addActionListener(e -> calcularCostosYPorcentaje());
        tipoRegistroComboBox.addActionListener(e -> calcularCostosYPorcentaje());
        cancelButton.addActionListener(e -> cancelar());
    }
    
    private void cargarEventos() {
        eventosComboBox.removeAllItems();
        eventosComboBox.addItem(""); // Opción vacía
        
        List<String> eventos = controladorEventos.listarEventos();
        for (String evento : eventos) {
            eventosComboBox.addItem(evento);
        }
        
        institucionesComboBox.removeAllItems();
        institucionesComboBox.addItem(""); // Opción vacía
        List<String> instituciones = controladorEventos.listarInstituciones();
        for (String institucion : instituciones) {
            institucionesComboBox.addItem(institucion);
        }

    }
 private void actualizarEdiciones() {
        int selectedIndex = eventosComboBox.getSelectedIndex();
        edicionesComboBox.removeAllItems();
        
        if (selectedIndex > 0) { // Si hay algo seleccionado (no la opción vacía)
            edicionesComboBox.setEnabled(true);
            edicionesComboBox.addItem(null); // Opción vacía
            
            // Obtener el evento seleccionado
            String eventoSeleccionado = (String) eventosComboBox.getSelectedItem();
            List<String> ediciones = controladorEventos.listarEdicionesDeEvento(eventoSeleccionado);
            if (ediciones != null) {
                for (String edicion : ediciones) {
                    edicionesComboBox.addItem(edicion);
                }
            }
        } else {
            edicionesComboBox.setEnabled(false);
        }
    }
    private void actualizarTipoRegistro() {
        int selectedIndex = edicionesComboBox.getSelectedIndex();
        tipoRegistroComboBox.removeAllItems();
        
        if (selectedIndex > 0) { // Si hay algo seleccionado (no la opción vacía)
            tipoRegistroComboBox.setEnabled(true);
            tipoRegistroComboBox.addItem(null); // Opción vacía
            
            // Obtener el evento seleccionado
            String edicionSeleccionada = (String) edicionesComboBox.getSelectedItem();

            Set<String> tiposRegistro = controladorEventos.obtenerTiposDeRegistroDeEdicion(edicionSeleccionada);
            if (tiposRegistro != null) {
                for (String tr : tiposRegistro) {
                    tipoRegistroComboBox.addItem(tr);
                }
            }
        } else {
            tipoRegistroComboBox.setEnabled(false);
        }    
    }

    private void calcularCostosYPorcentaje() {
        try {
            String evento = (String) eventosComboBox.getSelectedItem();
            String edicion = (String) edicionesComboBox.getSelectedItem();
            String tipoRegistro = (String) tipoRegistroComboBox.getSelectedItem();
            String cantidadStr = txtCantidadRegistros.getText();
            String aporteStr = txtAporteEconomico.getText();
            
            if (evento != null && !evento.isEmpty() &&
                edicion != null && !edicion.isEmpty() &&
                tipoRegistro != null && !tipoRegistro.isEmpty() && 
                !cantidadStr.isEmpty() && !aporteStr.isEmpty()) {
                
                int cantidad = Integer.parseInt(cantidadStr);
                BigDecimal aporte = new BigDecimal(aporteStr);
                
                BigDecimal costoRegistros = controladorEventos.calcularCostoRegistros(evento, edicion, tipoRegistro, cantidad);
                BigDecimal porcentaje = controladorEventos.calcularPorcentajeAporte(aporte, costoRegistros);
                
                lblCostoRegistros.setText("Costo de registros: $" + costoRegistros.toString());
                lblPorcentajeAporte.setText("Porcentaje del aporte: " + porcentaje.toString() + "%");
                
                // Resaltar si supera el 20%
                if (porcentaje.compareTo(new BigDecimal(20)) > 0) {
                    lblPorcentajeAporte.setForeground(Color.RED);
                    lblPorcentajeAporte.setText(lblPorcentajeAporte.getText() + " (EXCEDE EL 20%)");
                } else {
                    lblPorcentajeAporte.setForeground(Color.BLACK);
                }
            }
        } catch (Exception e) {
            // Ignorar errores de cálculo temporal
        }
    }

        private void cancelar() {
        boolean confirmacion = showConfirmationDialog("¿Está seguro que desea cancelar? Se perderán los datos ingresados."); // confir
        if (confirmacion == true) {
            dispose();
        }
    }
    @Override
    protected void onAccept() {
       try {
            String evento = (String) eventosComboBox.getSelectedItem();
            String edicion = (String) edicionesComboBox.getSelectedItem();
            String tipoRegistro = (String) tipoRegistroComboBox.getSelectedItem();
            String institucion = (String) institucionesComboBox.getSelectedItem();
            String nivelPatrocinio = (String) nivelPatrocinioComboBox.getSelectedItem();
            String aporteEconomico = txtAporteEconomico.getText();
            String cantidadRegistros = txtCantidadRegistros.getText();
            String codigoPatrocinio = txtCodigoPatrocinio.getText();

            // Validaciones básicas
            if (evento == null || evento.isEmpty()) {
                showErrorMessage("Debe seleccionar un evento");
                return;
            }
            if (edicion == null || edicion.isEmpty()) {
                showErrorMessage("Debe seleccionar una edición");
                return;
            }
            if (tipoRegistro == null || tipoRegistro.isEmpty()) {
                showErrorMessage("Debe seleccionar un tipo de registro");
                return;
            }
            if (institucion == null || institucion.isEmpty()) {
                showErrorMessage("Debe seleccionar una institución");
                return;
            }
            if (nivelPatrocinio == null || nivelPatrocinio.isEmpty()) {
                showErrorMessage("Debe seleccionar un nivel de patrocinio");
                return;
            }
            if (aporteEconomico.isEmpty()) {
                showErrorMessage("El aporte económico no puede estar vacío");
                txtAporteEconomico.requestFocus();
                return;
            }
            if (cantidadRegistros.isEmpty()) {
                showErrorMessage("La cantidad de registros no puede estar vacía");
                txtCantidadRegistros.requestFocus();
                return;
            }
            if (codigoPatrocinio.isEmpty()) {
                showErrorMessage("El código de patrocinio no puede estar vacío");
                txtCodigoPatrocinio.requestFocus();
                return;
            }

            // Convertir y validar aporte
            BigDecimal aporte;
            try {
                aporte = new BigDecimal(aporteEconomico);
                if (aporte.compareTo(BigDecimal.ZERO) <= 0) {
                    showErrorMessage("El aporte económico debe ser mayor a cero");
                    txtAporteEconomico.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorMessage("El aporte económico debe ser un número válido");
                txtAporteEconomico.requestFocus();
                return;
            }

            // Validar cantidad de registros
            int cantRegistros;
            try {
                cantRegistros = Integer.parseInt(cantidadRegistros);
                if (cantRegistros <= 0) {
                    showErrorMessage("La cantidad de registros debe ser mayor a cero");
                    txtCantidadRegistros.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorMessage("La cantidad de registros debe ser un número entero válido");
                txtCantidadRegistros.requestFocus();
                return;
            }

            // Verificar porcentaje (regla del 20%)
            BigDecimal costoRegistros = controladorEventos.calcularCostoRegistros(evento, edicion, tipoRegistro, cantRegistros);
            BigDecimal porcentaje = controladorEventos.calcularPorcentajeAporte(aporte, costoRegistros);
            
            if (porcentaje.compareTo(new BigDecimal(20)) > 0) {
                showErrorMessage("El costo de los registros (" + porcentaje + 
                               "%) supera el 20% del aporte económico. " +
                               "Reduzca la cantidad de registros o aumente el aporte económico.");
                return;
            }

            // Intentar crear el patrocinio
            controladorEventos.altaPatrocinio(evento, edicion, tipoRegistro, institucion, 
                                             nivelPatrocinio, aporte, cantRegistros, codigoPatrocinio);
            showSuccessMessage("Patrocinio dado de alta exitosamente");
            clearForm();
            
        } catch (PatrocinioDuplicadoException e) {
            // Manejo específico para patrocinios duplicados
            boolean opcion = showConfirmationDialog("Ya existe un patrocinio de esta institución para la edición seleccionada. ¿Desea editar los datos?");
            
            if (!opcion) {
                cancelar();
            } else {
                institucionesComboBox.requestFocus();
            }
            
        } catch (CostoRegistrosExcedidoException e) {
            // Este caso ya no debería ocurrir porque lo validamos antes,
            // pero lo mantenemos por si acaso
            showErrorMessage(e.getMessage());
            
        } catch (Exception e) {
            showErrorMessage("Error al dar de alta el patrocinio: " + e.getMessage());
        }
    }
    
    @Override
    protected void clearForm() {
          eventosComboBox.setSelectedIndex(0);
        edicionesComboBox.removeAllItems();
        edicionesComboBox.setEnabled(false);
        tipoRegistroComboBox.removeAllItems();
        tipoRegistroComboBox.setEnabled(false);
        institucionesComboBox.setSelectedIndex(0);
        nivelPatrocinioComboBox.setSelectedIndex(0);
        txtAporteEconomico.setText("");
        txtCantidadRegistros.setText("");
        txtCodigoPatrocinio.setText("");
        lblCostoRegistros.setText("Costo de registros: $0.00");
        lblPorcentajeAporte.setText("Porcentaje del aporte: 0%");
        lblPorcentajeAporte.setForeground(Color.BLACK);
    }
}
