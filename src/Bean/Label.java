package Bean;

import Interfaces.ElementoIndicador;
import Main.Sketch;
import processing.core.PVector;

/**
 * @author CPZ
 */
public class Label implements ElementoIndicador {

    private final Sketch sk;
    private final PVector pos;
    private float tamTexto;
    private String nombre, codigo;
    private int c;

    public Label(Sketch sk) {
        this.sk = sk;
        pos = new PVector();
    }

    @Override
    public void update(Object... o) {
        if (o == null) {
            return;
        }
        if (o.length == 1 && o[0] instanceof String t) {
            this.nombre = t;
        } else if (o.length == 2 && o[0] instanceof String t) {
            this.nombre = t;
            this.c = (int) o[1];
        } else if (o.length == 2 && o[1] instanceof String t) {
            this.nombre = t;
            this.c = (int) o[0];
        }
    }

    @Override
    public void draw() {
        if (nombre.equals("")) {
            return;
        }
        // se grafica el texto
        sk.fill(c);
        sk.noStroke();
        sk.textAlign(Sketch.CENTER, Sketch.CENTER);
        sk.textSize(tamTexto);
        sk.text(nombre, pos.x * sk.width, pos.y * sk.height);
    }
    
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    @Override
    public String getCodigo() {
        return codigo;
    }

    public void setPos(float x, float y) {
        pos.set(x, y);
    }
    
    public PVector getPos() {
        return pos;
    }

    public void setTamTexto(float tamTexto) {
        if (sk.width > sk.height) {
            this.tamTexto = tamTexto * sk.height;
        } else {
            this.tamTexto = tamTexto * sk.width;
        }
    }

    public void setColorTexto(int c) {
        this.c = c;
    }

    public int getColorTexto() {
        return c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Texto   : ").append(nombre).append("\n");
        sb.append("Codigo  : ").append(codigo).append("\n");
        sb.append("Posicion: (").append(pos.x).append(" , ").append(pos.y).append(")\n");
        return sb.toString();
    }

}
