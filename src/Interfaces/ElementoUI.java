package Interfaces;

/**
 * @author CPZ
 */
public interface ElementoUI {

    void update(Object... o);

    void draw();

    void setNombre(String nombre);
    
    String getNombre();
    
    void setCodigo(String codigo);
    
    String getCodigo();

}
