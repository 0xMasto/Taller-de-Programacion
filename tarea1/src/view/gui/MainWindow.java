package view.gui;

import logica.interfaces.Fabrica;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import view.gui.forms.*;
import util.CSVLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main application window with menu and desktop pane for internal frames
 * Updated to use MVC architecture
 */
public class MainWindow extends JFrame {
    private JDesktopPane desktopPane;
    private iControladorUsuarios controladorUsuarios;
    private iControladorEventos controladorEventos;
    
    public MainWindow() {
        this.controladorUsuarios = Fabrica.getInstancia().getControladorUsuario();
        this.controladorEventos = Fabrica.getInstancia().getControladorEventos();
        initializeComponents();
        createMenuBar();
        setupWindow();
        //loadCSVDatainitial();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	// Initialize configuration
                	
                	MainWindow window = new MainWindow();
                    publicar.WebServices ws = new publicar.WebServices();
                    ws.publicar();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeComponents() {
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create toolbar panel
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create Load Data button
        JButton loadDataButton = new JButton("Cargar Datos CSV");
        loadDataButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadDataButton.setBackground(new Color(70, 130, 180));
        loadDataButton.setForeground(Color.WHITE);
        loadDataButton.setFocusPainted(false);
        loadDataButton.addActionListener(e -> loadCSVData());
        
        toolbarPanel.add(loadDataButton);
        
        // Add toolbar to main panel
        mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        
        // Create desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
        mainPanel.add(desktopPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // User Management Menu
        JMenu userMenu = new JMenu("Usuarios");
        userMenu.add(createMenuItem("Alta de Usuario", this::showUserForm));
        userMenu.add(createMenuItem("Consulta de Usuario", this::showUserConsultationForm));
        
        // Event Management Menu
        JMenu eventMenu = new JMenu("Eventos");
        eventMenu.add(createMenuItem("Alta de Evento", this::showEventForm));
        eventMenu.add(createMenuItem("Consulta de Evento", this::showEventConsultationForm));
        
        // Event Edition Management Menu
        JMenu editionMenu = new JMenu("Ediciones");
        editionMenu.add(createMenuItem("Alta de Edición de Evento", this::showEventEditionForm));
        editionMenu.add(createMenuItem("Consulta de Edición de Evento", this::showEventEditionConsultationForm));
        editionMenu.add(createMenuItem("Cambiar estado de Edición", this::showChangeEditionStatusForm)); // 👈 NUEVO
        
        // Registration Management Menu
        JMenu registrationMenu = new JMenu("Registros");
        registrationMenu.add(createMenuItem("Alta de Tipo de Registro", this::showRegistrationTypeForm));
        registrationMenu.add(createMenuItem("Consulta de Tipo de Registro", this::showRegistrationTypeConsultationForm));
        registrationMenu.add(createMenuItem("Registro a Edición de Evento", this::showRegistrationForm));
        registrationMenu.add(createMenuItem("Consulta de Registro", this::showRegistrationConsultationForm));
        
        // Institution Management Menu
        JMenu institutionMenu = new JMenu("Instituciones");
        institutionMenu.add(createMenuItem("Alta de Institución", this::showInstitutionForm));
        
        // Sponsorship Management Menu
        JMenu sponsorshipMenu = new JMenu("Patrocinios");
      
        sponsorshipMenu.add(createMenuItem("Alta de Patrocinio", this::showPatrocinioForm));
        sponsorshipMenu.add(createMenuItem("Consulta de Patrocinio", this::showSponsorshipConsultationForm));
        
        // Statistics Menu
        JMenu statisticsMenu = new JMenu("Estadísticas");
        statisticsMenu.add(createMenuItem("Eventos Más Visitados", this::showEventosMasVisitados));
        
        // Help Menu
        JMenu helpMenu = new JMenu("Ayuda");
        helpMenu.add(createMenuItem("Acerca de", this::showAbout));
        
        menuBar.add(userMenu);
        menuBar.add(eventMenu);
        menuBar.add(editionMenu);
        menuBar.add(registrationMenu);
        menuBar.add(institutionMenu);
        menuBar.add(sponsorshipMenu);
        menuBar.add(statisticsMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(action);
        return menuItem;
    }
    
    private void setupWindow() {
        setTitle("Sistema de Gestión de Eventos - eventos.uy");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        
        // Add window listener for proper closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        
        // Center the window
        setLocationRelativeTo(null);
    }
    
  
    
                private void showUserForm(ActionEvent e) {
                FormularioUsuario userForm = new FormularioUsuario(controladorUsuarios, controladorEventos);
                showInternalFrame(userForm);
            }
    
                private void showUserConsultationForm(ActionEvent e) {
                showInternalFrame(new FormularioConsultaUsuario(controladorUsuarios, controladorEventos));
            }
    
    
    private void showEventForm(ActionEvent e) {
        FormularioEvento eventForm = new FormularioEvento(controladorEventos);
        showInternalFrame(eventForm);
    }
    
                private void showEventConsultationForm(ActionEvent e) {
                showInternalFrame(new FormularioConsultaEvento(controladorEventos));
            }
    
                private void showEventEditionForm(ActionEvent e) {
                FormularioEdicionEvento eventEditionForm = new FormularioEdicionEvento(controladorEventos, controladorUsuarios);
                showInternalFrame(eventEditionForm);
            }
    
                private void showEventEditionConsultationForm(ActionEvent e) {
                showInternalFrame(new FormularioConsultaEdicionEvento(controladorEventos, controladorUsuarios));
            }
                
                private void showChangeEditionStatusForm(ActionEvent e) {
                    showInternalFrame(new FormularioAceptarRechazar(controladorEventos));
                }
    
                private void showRegistrationTypeForm(ActionEvent e) {
                showInternalFrame(new FormularioTipoRegistro(controladorEventos));
            }
    
                private void showRegistrationTypeConsultationForm(ActionEvent e) {
                showInternalFrame(new FormularioConsultaTipoRegistro(controladorEventos));
            }
    
    private void showRegistrationForm(ActionEvent e) {
        showInternalFrame(new FormularioRegistro(controladorEventos, controladorUsuarios));
    }
    
                private void showRegistrationConsultationForm(ActionEvent e) {
                showInternalFrame(new FormularioConsultaRegistro(controladorUsuarios, controladorEventos));
            }
    
                private void showInstitutionForm(ActionEvent e) {
                showInternalFrame(new FormularioInstitucion(controladorEventos));
            }
            private void showPatrocinioForm(ActionEvent e) {
                showInternalFrame(new FormularioPatrocinio(controladorEventos));
            }


            private void showSponsorshipConsultationForm(ActionEvent e) {
            showInternalFrame(new FormularioConsultaPatrocinio(controladorEventos));
        }
        
        private void showEventosMasVisitados(ActionEvent e) {
            showInternalFrame(new FormularioEventosMasVisitados(controladorEventos));
        }
    
    private void showAbout(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
            "Sistema de Gestión de Eventos\n" +
            "eventos.uy\n\n" +
            "Versión 1.0\n" +
            "Desarrollado con Java Swing\n\n" +
            "Este sistema permite gestionar usuarios, eventos,\n" +
            "ediciones de eventos, registros y patrocinios.",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showInternalFrame(JInternalFrame frame) {
        // Close any existing frame with the same title
        for (JInternalFrame existingFrame : desktopPane.getAllFrames()) {
            if (existingFrame.getTitle().equals(frame.getTitle())) {
                existingFrame.dispose();
                break;
            }
        }
        
        desktopPane.add(frame);
        frame.setVisible(true);
        
        // Center the frame in the desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
        
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            // Ignore selection exceptions
        }
    }
    
    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea salir de la aplicación?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    

    private void loadCSVData() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea cargar los datos desde los archivos CSV?\n" +
            "Esto sobrescribirá cualquier dato existente en el sistema.",
            "Confirmar carga de datos CSV", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Show loading dialog
      
                
                CSVLoader csvLoader = new CSVLoader(controladorEventos, controladorUsuarios);
                String resultado = csvLoader.cargarDatosDesdeCSV();
                
                JOptionPane.showMessageDialog(this,
                    resultado,
                    "Carga de datos CSV",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                String errorMessage = "❌ Error al cargar los datos CSV:\n\n";
                errorMessage += "Detalles del error:\n";
                errorMessage += e.getMessage() + "\n\n";
                errorMessage += "Verifique que:\n";
                errorMessage += "• Los archivos CSV estén en la carpeta 'data/'\n";
                errorMessage += "• Los archivos tengan el formato correcto\n";
                errorMessage += "• No haya errores en los datos CSV";
                
                JOptionPane.showMessageDialog(this,
                    errorMessage,
                    "Error en la carga",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
