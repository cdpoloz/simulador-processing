package Bean;

import Main.Sketch;
import processing.core.PVector;
import Interfaces.ElementoInteractivo;
import processing.core.PShape;

/**
 * @author CPZ
 */
public class Button implements ElementoInteractivo {

    private final Sketch sk;
    private final PVector pos;
    private int c, cOn, cOff, cHover;
    private float medida;
    private String nombre, codigo, funcion, shortcut;
    private boolean bOn;
    private Label lb;
    private PShape shape;

    public Button(Sketch sk) {
        this.sk = sk;
        pos = new PVector();
    }

    @Override
    public void update(Object... o) {
        bOn = isHovering() && sk.mousePressed && sk.mouseButton == Sketch.LEFT;
        if (bOn) {
            c = cOn;
        } else {
            c = cOff;
        }
        if (isHovering()) {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 255);
        } else {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 0);
        }
    }

    @Override
    public void draw() {
        sk.shapeMode(Sketch.CENTER);
        // se grafica el efecto hover
        sk.fill(cHover);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida * 1.2f, medida * 1.2f);
        // se grafica el botón y su estado actual
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

    @Override
    public boolean isHovering() {
        return Sketch.dist(sk.mouseX, sk.mouseY, pos.x * sk.width, pos.y * sk.height) < medida * 0.5f;
    }

    public boolean isOn() {
        return bOn;
    }

    public void turnOff() {
        c = cOff;
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

    public void setColorHover(int cHover) {
        this.cHover = cHover;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public void setEtiqueta(String nombre, String codigo, float tamTexto, float dTexto, int c) {
        this.nombre = nombre;
        lb = new Label(sk);
        lb.setNombre(nombre);
        lb.setCodigo(codigo);
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
        sb.append("Atajo   : ").append(shortcut).append("\n");
        return sb.toString();
    }

}
