package view.gui.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Base class for internal forms with common functionality
 */
public abstract class BaseInternalForm extends JInternalFrame {
    protected JPanel contentPanel;
    protected JPanel buttonPanel;
    protected JButton acceptButton;
    protected JButton cancelButton;
    
    public BaseInternalForm(String title) {
        super(title, true, true, true, true); // resizable, closable, maximizable, iconifiable
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    protected void initializeComponents() {
        contentPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        acceptButton = new JButton("Aceptar");
        cancelButton = new JButton("Cancelar");
        
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
    }
    
    protected void setupLayout() {
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set default size
        setSize(500, 400);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
    
    protected void setupEventListeners() {
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAccept();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }
    
    protected abstract void onAccept();
    
    protected void onCancel() {
        clearForm();
        setVisible(false);
    }
    
    protected abstract void clearForm();
    
    protected void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    protected void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    protected boolean showConfirmationDialog(String message) {
        int option = JOptionPane.showConfirmDialog(
            this,
            message,
            "Confirmación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }
    
    protected JPanel createFieldPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, label.getPreferredSize().height));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}
