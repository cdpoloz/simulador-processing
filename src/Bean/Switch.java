package Bean;

import Main.Sketch;
import java.util.ArrayList;
import processing.core.PVector;
import Interfaces.ElementoInteractivo;
import processing.core.PShape;

/**
 * @author CPZ
 */
public class Switch implements ElementoInteractivo {

    private final Sketch sk;
    private final PVector pos;
    private int estado, cantidadEstados;
    private ArrayList<Integer> lstColorEstados;
    private int c, cHover, cEtiqueta;
    private float medida;
    private String nombre, codigo, funcion, shortcut;
    private ArrayList<String> lstEstados;
    private Label lb, lbEstado;
    private PShape shape;

    public Switch(Sketch sk) {
        this.sk = sk;
        pos = new PVector();
    }

    @Override
    public void update(Object... o) {
        if (isHovering()) {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 255);
        } else {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 0);
        }
    }

    public void updateEstado(String modo) {
        // se actualiza el estado
        if (modo.equals("+")) {
            estado++;
        } else if (modo.equals("-")) {
            estado--;
        }
        
        if (estado == cantidadEstados) {
            estado = 0;
        } else if (estado == -1) {
            estado = cantidadEstados - 1;
        }
        // se actualiza el color
        c = lstColorEstados.get(estado);
        // se actualiza la etiqueta del estado
        lbEstado.update(lstEstados.get(estado), cEtiqueta);
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public void draw() {
        sk.shapeMode(Sketch.CENTER);
        // se grafica el efecto hover
        sk.fill(cHover);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida * 1.2f, medida * 1.2f);
        // se grafica el switch y su estado actual
        sk.fill(c);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida, medida);
        // se grafican las etiquetas
        lb.draw();
        lbEstado.draw();
    }

    @Override
    public boolean isHovering() {
        return Sketch.dist(sk.mouseX, sk.mouseY, pos.x * sk.width, pos.y * sk.height) < medida * 0.5f;
    }

    public int getEstado() {
        return estado;
    }

    public String getEstadoStr() {
        return lbEstado.getNombre();
    }

    @Override
    public String getNombre() {
        return lb.getNombre();
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

    public void setEstados(ArrayList<String> lstStr, String modo, Object... o) {
        lstEstados = new ArrayList();
        for (String s : lstStr) {
            this.lstEstados.add(s);
        }
        cantidadEstados = lstEstados.size();
        lstColorEstados = new ArrayList();
        switch (modo) {
            case "colorAuto" -> {
                int cIni = (int) o[0];
                int cFin = (int) o[1];
                for (int i = 0; i < this.lstEstados.size(); i++) {
                    float f = Sketch.map(i, 0, this.lstEstados.size() - 1, 0, 1);
                    lstColorEstados.add(sk.lerpColor(cIni, cFin, f));
                }
            }
            case "colorFijo" -> {
                ArrayList<Integer> lstInt = (ArrayList<Integer>) o[0];
                for (Integer i : lstInt) {
                    this.lstColorEstados.add(i);
                }
            }
            default -> {
            }
        }
        c = lstColorEstados.get(0);
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

    public void setEtiqueta(String nombre, String codigo, float tamTexto, float dTexto1, float dTexto2, int cEtiqueta) {
        this.nombre = nombre;
        lb = new Label(sk);
        lb.setNombre(nombre);
        lb.setCodigo(codigo);
        lb.setPos(pos.x, pos.y - dTexto1);
        lb.setTamTexto(tamTexto);
        lb.setColorTexto(cEtiqueta);
        lbEstado = new Label(sk);
        lbEstado.setNombre(lstEstados.get(0));
        lbEstado.setPos(pos.x, pos.y + dTexto2);
        lbEstado.setTamTexto(tamTexto);
        lbEstado.setColorTexto(cEtiqueta);
        this.cEtiqueta = cEtiqueta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre  : ").append(nombre).append("\n");
        sb.append("Codigo  : ").append(codigo).append("\n");
        sb.append("Funcion : ").append(funcion).append("\n");
        sb.append("Posicion: (").append(pos.x).append(" , ").append(pos.y).append(")\n");
        sb.append("Atajo   : ").append(shortcut).append("\n");
        sb.append("Estado  : ");
        for (String s : lstEstados) {
            sb.append(s).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n");
        return sb.toString();
    }

}
