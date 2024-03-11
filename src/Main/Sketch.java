package Main;

import Bean.Button;
import Bean.Gauge;
import Bean.Indicador;
import Bean.Label;
import Bean.Switch;
import SwingGUI.Aviso;
import Interfaces.ElementoUI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PShape;
import processing.event.MouseEvent;

/**
 * @author CPZ
 */
public class Sketch extends PApplet {

    private final ArrayList<ElementoUI> lstE;
    private final ArrayList<Button> lstBtn;
    private final ArrayList<Indicador> lstInd;
    private final ArrayList<Switch> lstSw;
    private final ArrayList<Label> lstLb;
    private final ArrayList<Gauge> lstGau;
    private int cBackground;
    private boolean controlesEditables;
    private int amarilloChevere, magentaChevere;

    public Sketch() {
        super();
        // se inicializan las variables simples
        lstE = new ArrayList();
        lstBtn = new ArrayList();
        lstInd = new ArrayList();
        lstSw = new ArrayList();
        lstLb = new ArrayList();
        lstGau = new ArrayList();
    }

    // CONFIGURACIÓN PARA LA ANIMACIÓN *****************************************
    @Override
    public void settings() {
        size(1200, 600, P2D);
        smooth(8);
        cBackground = color(128);
    }

    // CONFIGURACIÓN ADICIONAL DE LA VENTANA ***********************************
    @Override
    public void setup() {
        // se situa la ventana al medio de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int posX = (int) (screenSize.getWidth() * 0.5f - width * 0.5f);
        int posY = (int) (screenSize.getHeight() * 0.5f - height * 0.5f);
        surface.setLocation(posX, posY);
        surface.setTitle("Elementos UI");
        // se ajusta la cantidad de cuadros por segundo de la animación
        frameRate(60);
        // se configuran los elementos UI
        amarilloChevere = color(255, 204, 0);
        magentaChevere = color(255, 0, 102);
        int colorEtiqueta = color(255);
        int colorOff = color(64);
        int colorOn = amarilloChevere;
        int colorHover = color(255);
        float tamTexto = 0.025f;
        float dTexto1 = 0.065f;
        float dTexto2 = 0.06f;
        // configuración de elementos visuales
        PShape shape = loadShape("data" + File.separator + "circulo_1080x1080px.svg");
        // configuración de indicador
        Indicador ind = new Indicador(this);
        ind.setPos(0.15f, 0.5f);
        ind.setMedida(0.06f);
        ind.setShape(shape);
        ind.setColorOff(colorOff);
        ind.setColorOn(colorOn);
        ind.setFuncion("Indicar si se cumplen condiciones múltiples");
        ind.setEtiqueta("Condiciones\nmúltiples", tamTexto, dTexto1 + 0.02f, colorEtiqueta);
        ind.setCodigo("indCondMult");
        ind.update(false);
        lstInd.add(ind);
        // configuración de switches (2 y 4 estados)
        Switch sw = new Switch(this);
        sw.setPos(0.25f, 0.5f);
        sw.setMedida(0.06f);
        sw.setShape(shape);
        ArrayList<String> lstEstados = new ArrayList();
        lstEstados.add("Off");
        lstEstados.add("On");
        sw.setEstados(lstEstados, "colorAuto", colorOff, amarilloChevere);
        sw.setColorHover(colorHover);
        sw.setFuncion("Conmutar estado");
        sw.setShortcut("b");
        sw.setEtiqueta("SW1", "", tamTexto, dTexto1, dTexto2, colorEtiqueta);
        sw.setCodigo("swEstado");
        lstSw.add(sw);
        sw = new Switch(this);
        sw.setPos(0.35f, 0.5f);
        sw.setMedida(0.06f);
        sw.setShape(shape);
        lstEstados = new ArrayList();
        lstEstados.add("Estado 1");
        lstEstados.add("Estado 2");
        lstEstados.add("Estado 3");
        lstEstados.add("Estado 4");
        sw.setEstados(lstEstados, "colorAuto", colorOff, magentaChevere);
        sw.setColorHover(colorHover);
        sw.setFuncion("Conmutar estados múltiples");
        sw.setShortcut("s");
        sw.setEtiqueta("SW2", "", tamTexto, dTexto1, dTexto2, colorEtiqueta);
        sw.setCodigo("swMultEstados");
        lstSw.add(sw);
        // configuración de etiquetas
        Label lb = new Label(this);
        lb.setNombre("");
        lb.setCodigo("lbEtiqueta");
        lb.setPos(0.45f, 0.5f);
        lb.setTamTexto(tamTexto);
        lb.setColorTexto(colorEtiqueta);
        lstLb.add(lb);
        // configuración de gauges
        Gauge gau = new Gauge(this);
        gau.setPos(0.55f, 0.5f);
        gau.setMedida(0.06f);
        gau.setShape(shape);
        gau.setColorOff(colorOff);
        gau.setColorInicial(colorOff);
        gau.setColorFinal(magentaChevere);
        gau.setColorHover(colorHover);
        gau.setColorAlerta(amarilloChevere);
        gau.setCantidadDecimales(0);
        gau.setPrefijo("");
        gau.setSufijo("%");
        gau.setFuncion("Aumentar variable A");
        gau.setEtiqueta("Variable A", "gauVarA", tamTexto, dTexto1, dTexto2, 0.04f, colorEtiqueta);
        gau.setCodigo("gauA");
        gau.setRango(0, 100, 5);
        gau.setTipo("interactivo");
        gau.setModo("mouse");
        gau.setPeriodoAlerta(250);
        lstGau.add(gau);
        // configuración de botón
        Button btn = new Button(this);
        btn.setPos(0.65f, 0.5f);
        btn.setMedida(0.06f);
        btn.setShape(shape);
        btn.setColorOff(colorOff);
        btn.setColorOn(colorOn);
        btn.setColorHover(colorHover);
        btn.setFuncion("Lanzar aviso");
        btn.setShortcut("n");
        btn.setEtiqueta("Aviso", "", tamTexto, dTexto1, colorEtiqueta);
        btn.setCodigo("btnAviso");
        lstBtn.add(btn);
        // se agregan los elementos a la lista completa
        for (Indicador o : lstInd) {
            lstE.add(o);
        }
        for (Switch o : lstSw) {
            lstE.add(o);
        }
        for (Label o : lstLb) {
            lstE.add(o);
        }
        for (Gauge o : lstGau) {
            lstE.add(o);
        }
        for (Button o : lstBtn) {
            lstE.add(o);
        }
        // se habilitan los controles para el usuario
        controlesEditables = true;
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        updateControlesEditables();
        // draw
        background(cBackground);
        for (ElementoUI e : lstE) {
            e.draw();
        }
    }

    public void updateControlesEditables() {
        if (!controlesEditables) {
            return;
        }
        // se actualizan los botones
        for (Button btn : lstBtn) {
            btn.update();
        }
        // se actualizan los switches
        for (Switch sw : lstSw) {
            sw.update();
        }
        // se actualizan los gauges
        for (Gauge gau : lstGau) {
            gau.update();
        }
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        if (!controlesEditables) {
            return;
        }
        // comportamiento de los botones
        updateBotonesPorMouseClic();
        // comportamiento de los switches
        updateSwitchesPorMouseClic();
        // ******************************* ejemplo de actualización de elementos
        actualizarElementosPorCondiciones();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        if (!controlesEditables) {
            return;
        }
        float d = event.getCount();
        updateGaugesPorMouseWheel(d);
    }

    @Override
    public void keyReleased() {
        if (!controlesEditables) {
            return;
        }
        switch (key) {
            case 'n' -> {
                Gauge gau = lstGau.get(0);
                if (gau.getTipo().equals("interactivo")) {
                    gau.setTipo("indicador");
                } else if (gau.getTipo().equals("indicador")) {
                    gau.setTipo("interactivo");
                }
                gau.updateValor(gau.getModo(), 0);
            }
            case 'm' -> {
            }
            case 's' -> {
            }
            case 'd' -> {
            }
            case 'i' -> {
                for (ElementoUI e : lstE) {
                    System.out.println(e);
                }
            }
            case 'p' -> {
            }
            default -> {
            }
        }
    }

    private void updateBotonesPorMouseClic() {
        if (mouseButton != LEFT) {
            return;
        }
        for (Button b : lstBtn) {
            if (!b.isHovering()) {
                continue;
            }
            switch (b.getCodigo()) {
                case "btnAviso" -> {
                    b.turnOff();
                    lanzarAvisoClic();
                }
                default -> {
                }
            }
        }
    }

    private void updateSwitchesPorMouseClic() {
        if (mouseButton == CENTER) {
            return;
        }
        for (Switch sw : lstSw) {
            if (!sw.isHovering()) {
                continue;
            }
            if (mouseButton == LEFT) {
                sw.updateEstado("+");
            } else if (mouseButton == RIGHT) {
                sw.updateEstado("-");
            }
        }
    }

    private void updateGaugesPorMouseWheel(float d) {
        for (Gauge gau : lstGau) {
            if (!gau.isHovering()) {
                continue;
            }
            gau.updateValor("mouse", d);
            switch (gau.getCodigo()) {
                case ("gauA") -> {
                    actualizarElementosPorCondiciones();
                }
                default -> {
                }
            }
        }
    }

    private void actualizarElementosPorCondiciones() {
        Switch sw1 = lstSw.get(0);
        Switch sw2 = lstSw.get(1);
        Gauge gau = lstGau.get(0);
        boolean b = sw1.getEstado() == 1 && sw2.getEstado() == 2 && gau.getValor() >= 75;
        for (Indicador ind : lstInd) {
            switch (ind.getCodigo()) {
                case ("indCondMult") -> {
                    ind.update(b);
                }
                default -> {
                }
            }
        }
        for (Label lb : lstLb) {
            switch (lb.getCodigo()) {
                case ("lbEtiqueta") -> {
                    if (b) {
                        lb.update("SW1 = ON\n&\nSW2 = 3\n&\nA >= 75%");
                    } else {
                        lb.update("");
                    }
                }
                default -> {
                }
            }
        }
    }

    private void lanzarAvisoClic() {
        Aviso aviso = new Aviso(this);
        aviso.setVisible(true);
        controlesEditables = false;
    }

    public void hicisteClic(Aviso aviso) {
        System.out.println("Hiciste clic!");
        aviso.setVisible(false);
        aviso.dispose();
        controlesEditables = true;
    }

    
}
