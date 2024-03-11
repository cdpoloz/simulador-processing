package Bean;

import Main.Sketch;
import processing.core.PVector;
import Interfaces.ElementoInteractivo;
import processing.core.PShape;

/**
 * @author CPZ
 */
public class Gauge implements ElementoInteractivo {

    private final Sketch sk;
    private final PVector pos;
    private int c, cOff, cHover, cIni, cFin, cAlerta;
    private float medida, medidaArcoProgreso, valorMin, valorMax, valor, valorPrevio, dValor, ang, angIni, angFin, dInd;
    private Label lb, lbValor;
    private String nombre, codigo, funcion, formato, prefijo, sufijo, tipo, modo;
    private Indicador indAlerta;
    private final Timer t;
    private PShape shape;
    private boolean bIndAlerta;

    public Gauge(Sketch sk) {
        this.sk = sk;
        pos = new PVector();
        t = new Timer(sk);
    }

    public void updateValor(String modo, float d) {
        this.modo = modo;
        valorPrevio = valor;
        switch (modo) {
            case "mouse" ->
                valor += dValor * d * -1;
            case "valor" ->
                valor += d;
            default -> {
            }
        }
        if (tipo.equals("interactivo")) {
            valor = Sketch.constrain(valor, valorMin, valorMax);
        }
        c = sk.lerpColor(cIni, cFin, Sketch.map(valor, valorMin, valorMax, 0, 1));
        ang = Sketch.map(valor, valorMin, valorMax, angIni, angFin);
        ang = Sketch.constrain(ang, angIni, angFin);
        lbValor.update(prefijo + String.format(formato, valor) + sufijo);
    }

    public boolean cambioValor() {
        return valorPrevio != valor;
    }

    @Override
    public void update(Object... o) {
        if (isHovering()) {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 255);
        } else {
            cHover = sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 0);
        }
        // actualizamos el indicador de alerta si es necesario o no
        t.update();
        if (indAlerta != null && cambioValor()) {
            if (valor >= valorMin && valor <= valorMax) {
                bIndAlerta = false;
            } else if (t.periodoPulso()) {
                bIndAlerta = !bIndAlerta;
            }
            indAlerta.update(bIndAlerta);
        }
    }

    @Override
    public void draw() {
        sk.shapeMode(Sketch.CENTER);
        // se grafica el efecto hover
        sk.fill(cHover);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida * 1.2f, medida * 1.2f);
        // arco que muestra el progreso del gauge
        sk.noStroke();
        sk.fill(sk.color(sk.red(cHover), sk.green(cHover), sk.blue(cHover), 255));
        sk.arc(pos.x * sk.width, pos.y * sk.height, medidaArcoProgreso, medidaArcoProgreso, angIni, ang);
        // se grafica el gauge y su estado actual
        sk.fill(c);
        sk.shape(shape, pos.x * sk.width, pos.y * sk.height, medida, medida);
        // labels
        lb.draw();
        lbValor.draw();
        // indicador de alerta
        if (tipo.equals("indicador") && indAlerta != null) {
            indAlerta.draw();
        }
    }

    @Override
    public boolean isHovering() {
        return Sketch.dist(sk.mouseX, sk.mouseY, pos.x * sk.width, pos.y * sk.height) < medida * 0.5f;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return lb.getNombre();
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    public float getValor() {
        return valor;
    }

    public String getValorStr() {
        return lbValor.getNombre();
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
        medidaArcoProgreso = medida * 1.3f;
    }

    public void setShape(PShape shape) {
        this.shape = shape;
        this.shape.disableStyle();
    }

    public void setColorOff(int cOff) {
        this.cOff = cOff;
    }

    public void setColorInicial(int cIni) {
        this.cIni = cIni;
        c = cIni;
    }

    public void setColorFinal(int cFin) {
        this.cFin = cFin;
    }

    public void setColorHover(int cHover) {
        this.cHover = cHover;
    }

    public void setColorAlerta(int cAlerta) {
        this.cAlerta = cAlerta;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public void setEtiqueta(String nombre, String codigo, float tamTexto, float dTexto1, float dTexto2, float dInd, int cEtiqueta) {
        this.nombre = nombre;
        this.codigo = codigo;
        lb = new Label(sk);
        lb.setNombre(nombre);
        lb.setCodigo(codigo);
        lb.setPos(pos.x, pos.y - dTexto1);
        lb.setTamTexto(tamTexto);
        lb.setColorTexto(cEtiqueta);
        lbValor = new Label(sk);
        lbValor.setNombre(prefijo + String.format(formato, valor) + sufijo);
        lbValor.setCodigo("");
        lbValor.setPos(pos.x, pos.y + dTexto2);
        lbValor.setTamTexto(tamTexto);
        lbValor.setColorTexto(cEtiqueta);
        this.dInd = dInd;
    }

    public void setCantidadDecimales(int i) {
        formato = "%." + i + "f";
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public void setRango(float valorMin, float valorMax, float dValor) {
        this.valorMin = valorMin;
        this.valorMax = valorMax;
        this.dValor = dValor;
        valor = valorMin;
        angIni = Sketch.radians(130);
        angFin = Sketch.radians(410);
        ang = angIni;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        if (tipo.equals("indicador")) {
            indAlerta = new Indicador(sk);
            indAlerta.setPos(pos.x, lbValor.getPos().y + dInd);
            indAlerta.setMedida(0.02f);
            indAlerta.setShape(shape);
            indAlerta.setColorOff(cOff);
            indAlerta.setColorOn(cAlerta);
            indAlerta.setFuncion("Alertar por valor fuera de rango");
            indAlerta.setEtiqueta("", 1, 0, 0);
            indAlerta.setCodigo("");
            indAlerta.update(false);
            bIndAlerta = false;
        }
    }

    public void setPeriodoAlerta(int periodoAlerta) {
        t.apagar();
        t.iniciar(periodoAlerta);
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getModo() {
        return modo;
    }
    
    public void setModo(String modo) {
        this.modo = modo;
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
