package Bean;

import Interfaces.ElementoIndicador;
import Main.Sketch;
import processing.core.PShape;
import processing.core.PVector;

/**
 * @author CPZ
 */
public class Indicador implements ElementoIndicador {

    private final Sketch sk;
    private final PVector pos;
    private int c, cOn, cOff;
    private float medida;
    private String nombre, codigo, funcion;
    private boolean bOn;
    private Label lb;
    private PShape shape;

    public Indicador(Sketch sk) {
        this.sk = sk;
        pos = new PVector();
    }

    @Override
    public void update(Object... o) {
        if (o == null || o.length == 0) {
            return;
        }
        bOn = (boolean) o[0];
        if (bOn) {
            c = cOn;
        } else {
            c = cOff;
        }
    }

    @Override
    public void draw() {
        sk.shapeMode(Sketch.CENTER);
        // se grafica el indicador y su estado actual
        sk.fill(c);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida, medida);
        // se grafica la etiqueta
        lb.draw();
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
    
    public boolean isOn() {
        return bOn;
    }

    public void setPos(float x, float y) {
        pos.set(x, y);
    }

    public void setMedida(float f) {
        if (sk.width > sk.height) {
            medida = f * sk.height;
        } else {
            medida = f * sk.width;
        }
    }

    public void setShape(PShape shape) {
        this.shape = shape;
        this.shape.disableStyle();
    }

    public void setColorOff(int cOff) {
        this.cOff = cOff;
    }

    public void setColorOn(int cOn) {
        this.cOn = cOn;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public void setEtiqueta(String nombre, float tamTexto, float dTexto, int c) {
        this.nombre = nombre;
        lb = new Label(sk);
        lb.setNombre(nombre);
        lb.setPos(pos.x, pos.y - dTexto);
        lb.setTamTexto(tamTexto);
        lb.setColorTexto(c);
    }

    public void updateEtiqueta(String texto, int c) {
        lb.setNombre(texto);
        lb.setColorTexto(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre  : ").append(nombre).append("\n");
        sb.append("Codigo  : ").append(codigo).append("\n");
        sb.append("Funcion : ").append(funcion).append("\n");
        sb.append("Posicion: (").append(pos.x).append(" , ").append(pos.y).append(")\n");
        return sb.toString();
    }
}
