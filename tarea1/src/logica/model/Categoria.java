package logica.model;

import logica.dto.CategoriaDTO;


/**
 * Entidad Categoría con lógica de negocio (MVC Puro)
 */
public class Categoria {
    private String nombre;
    
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    // ========== Métodos de Lógica de Negocio (MVC Puro) ==========
    
    /**
     * Validar que los datos de la categoría sean válidos
     */

    
    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return nombre.equals(categoria.nombre);
    }
    
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
    
    public CategoriaDTO toDTO() {
        return new CategoriaDTO(nombre);
    }
}
