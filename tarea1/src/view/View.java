package view;

/**
 * Base interface for all views in the MVC architecture
 */
public interface View {
    /**
     * Display the view
     */
    void show();
    
    /**
     * Hide the view
     */
    void hide();
    
    /**
     * Clear all form data
     */
    void clear();
    
    /**
     * Show error message to user
     */
    void showError(String message);
    
    /**
     * Show success message to user
     */
    void showSuccess(String message);
}
