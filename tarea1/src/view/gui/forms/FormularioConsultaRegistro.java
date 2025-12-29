package view.gui.forms;

import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import logica.dto.RegistroDTO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class FormularioConsultaRegistro extends BaseInternalForm {
   private iControladorUsuarios controladorUsuarios;
   private iControladorEventos controladorEventos;
   private JComboBox registrosCombobox;
   private JTextField fechaField;
   private JTextField costoRegistroField;
   private JTextField tipoRegistroField;
   private JTextField tipoRegistroDescField;
   private JTextField costoTipoRegistroField;
   private JTextField cupoTipoRegistroField;
   private JComboBox asistentesCombobox;
   private Set registros;

   public FormularioConsultaRegistro(iControladorUsuarios controladorUsuarios, iControladorEventos controladorEventos) {
      super("Consulta de Registro");
      this.controladorUsuarios = controladorUsuarios;
      this.controladorEventos = controladorEventos;
      this.setupSpecificComponents();
      this.loadAsistentes();
   }

   private void setupSpecificComponents() {
      this.contentPanel.setLayout(new BorderLayout());
      JPanel selectionPanel = new JPanel(new GridBagLayout());
      selectionPanel.setBorder(BorderFactory.createTitledBorder("Selección de Registro"));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.anchor = 17;
      gbc.gridx = 0;
      gbc.gridy = 0;
      selectionPanel.add(new JLabel("Asistentes:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.asistentesCombobox = new JComboBox();
      this.asistentesCombobox.addActionListener((e) -> {
         this.loadRegistros();
      });
      selectionPanel.add(this.asistentesCombobox, gbc);
      gbc.gridx = 0;
      gbc.gridy = 1;
      selectionPanel.add(new JLabel("Registros:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.registrosCombobox = new JComboBox();
      this.registrosCombobox.addActionListener((e) -> {
         this.showRegistrationDetails();
      });
      selectionPanel.add(this.registrosCombobox, gbc);
      JPanel detailsPanel = new JPanel(new GridBagLayout());
      detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Registro"));
      gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.anchor = 17;
      gbc.gridx = 0;
      gbc.gridy = 0;
      detailsPanel.add(new JLabel("Fecha:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.fechaField = new JTextField(20);
      this.fechaField.setEditable(false);
      detailsPanel.add(this.fechaField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.fill = 0;
      gbc.weightx = 0.0D;
      detailsPanel.add(new JLabel("Costo:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.costoRegistroField = new JTextField(20);
      this.costoRegistroField.setEditable(false);
      detailsPanel.add(this.costoRegistroField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.fill = 0;
      gbc.weightx = 0.0D;
      detailsPanel.add(new JLabel("Tipo de Registro:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.tipoRegistroField = new JTextField(20);
      this.tipoRegistroField.setEditable(false);
      detailsPanel.add(this.tipoRegistroField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.fill = 0;
      gbc.weightx = 0.0D;
      detailsPanel.add(new JLabel("Descripcion:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.tipoRegistroDescField = new JTextField(20);
      this.tipoRegistroDescField.setEditable(false);
      detailsPanel.add(this.tipoRegistroDescField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 4;
      gbc.fill = 0;
      gbc.weightx = 0.0D;
      detailsPanel.add(new JLabel("Costo:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.costoTipoRegistroField = new JTextField(20);
      this.costoTipoRegistroField.setEditable(false);
      detailsPanel.add(this.costoTipoRegistroField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 5;
      gbc.fill = 0;
      gbc.weightx = 0.0D;
      detailsPanel.add(new JLabel("Cupo:"), gbc);
      gbc.gridx = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0D;
      this.cupoTipoRegistroField = new JTextField(20);
      this.cupoTipoRegistroField.setEditable(false);
      detailsPanel.add(this.cupoTipoRegistroField, gbc);
      JSplitPane splitPane = new JSplitPane(1, selectionPanel, detailsPanel);
      splitPane.setDividerLocation(300);
      this.contentPanel.add(splitPane, "Center");
      this.setSize(800, 600);
   }

   private void loadAsistentes() {
      this.asistentesCombobox.removeAllItems();
      Set asistentes = this.controladorUsuarios.listarAsistentes();
      if (asistentes != null && !asistentes.isEmpty()) {
         Iterator var3 = asistentes.iterator();

         while(var3.hasNext()){
            String asistente = (String) var3.next();
            this.asistentesCombobox.addItem(asistente);
         }
      } else {
         this.clearDetailsFields();
         this.asistentesCombobox.addItem("En este momento, no hay ningun asistente con registros disponibles.");
      }

   }

   private void loadRegistros() {
      this.registrosCombobox.removeAllItems();
      String selectedItem = (String) this.asistentesCombobox.getSelectedItem();
      if (selectedItem != null && !selectedItem.equals("En este momento, no hay ningun asistente con registros disponibles.")) {
         this.registros = this.controladorUsuarios.getRegistrosPorAsistentes(selectedItem);
         Iterator var3 = this.registros.iterator();

         while (var3.hasNext()) {
            RegistroDTO registro = (RegistroDTO) var3.next();
            this.registrosCombobox.addItem(registro.getNombreEdicion());
         }

      } else {
         this.clearDetailsFields();
         this.registrosCombobox.addItem("Seleccione un asistente.");
      }
   }

   private void showRegistrationDetails() {
      String selectedAsistente = (String) this.asistentesCombobox.getSelectedItem();
      String selectedEdicion = (String) this.registrosCombobox.getSelectedItem();
      if (selectedAsistente != null && !selectedAsistente.equals("En este momento, no hay ningun asistente con registros disponibles.") && selectedEdicion != null && !"Seleccione un asistente.".equals(selectedEdicion)) {
         RegistroDTO registro = null;
         if (this.registros != null) {
            Iterator var5 = this.registros.iterator();

            while (var5.hasNext()) {
               RegistroDTO re = (RegistroDTO) var5.next();
               if (re.getNombreEdicion().equals(selectedEdicion)) {
                  registro = re;
                  break;
               }
            }
         }

         if (registro != null) {
            this.fechaField.setText(registro.getFechaRegistro().toString());
            this.costoRegistroField.setText(registro.getCosto().toString());
            this.tipoRegistroField.setText(registro.getTipoRegistro().getNombre());
            this.tipoRegistroDescField.setText(registro.getTipoRegistro().getDescripcion());
            this.costoTipoRegistroField.setText(registro.getTipoRegistro().getCosto().toString());
            this.cupoTipoRegistroField.setText(Integer.toString(registro.getTipoRegistro().getCupo()));
         } else {
            this.clearDetailsFields();
         }

      } else {
         this.clearDetailsFields();
      }
   }

   private void clearDetailsFields() {
      this.fechaField.setText("");
      this.costoRegistroField.setText("");
      this.tipoRegistroField.setText("");
      this.tipoRegistroDescField.setText("");
      this.costoTipoRegistroField.setText("");
      this.cupoTipoRegistroField.setText("");
   }

   protected void onAccept() {
      this.loadAsistentes();
   }

   protected void clearForm() {
      this.loadAsistentes();
      this.loadRegistros();
      this.clearDetailsFields();
   }
}
