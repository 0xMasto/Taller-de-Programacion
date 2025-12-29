package view.gui.forms;

import logica.interfaces.iControladorEventos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form to display most visited events statistics
 */
public class FormularioEventosMasVisitados extends BaseInternalForm {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private final iControladorEventos controladorEventos;
    
    public FormularioEventosMasVisitados(iControladorEventos controladorEventos) {
        super("Eventos Más Visitados");
        this.controladorEventos = controladorEventos;
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Top 5 Eventos Más Visitados");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"#", "Nombre del Evento", "Cantidad de Visitas"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> loadData());
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        
        try {
            List<String[]> topEvents = controladorEventos.obtenerEventosMasVisitados(5);
            
            if (topEvents == null || topEvents.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay datos de visitas disponibles aún.", 
                    "Sin Datos", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int rank = 1;
            for (String[] eventData : topEvents) {
                Object[] rowData = {
                    rank++,
                    eventData[0],  // Nombre del evento
                    eventData[1]   // Cantidad de visitas
                };
                tableModel.addRow(rowData);
            }
            
        } catch (Exception e) {
            showErrorMessage("Error al cargar eventos más visitados: " + e.getMessage());
        }
    }

	@Override
	protected void onAccept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearForm() {
		// TODO Auto-generated method stub
		
	}
}

