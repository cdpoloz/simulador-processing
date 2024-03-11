package Bean;

import processing.core.PApplet;

/**
 * @author CPZ
 */
public class Timer {

    private final PApplet sk;
    private long tiempoInicio, tiempoActual, tiempoOn;
    private int periodo;
    private float dutyCycle;
    private boolean running;
    private String nombre;

    public Timer(PApplet sk) {
        this.sk = sk;
    }

    public void iniciar() {
        tiempoInicio = sk.millis();
        running = true;
        if (nombre != null) {
            System.out.println("Iniciando timer: " + nombre);
        }
    }

    public void iniciar(int periodo) {
        this.periodo = periodo;
        tiempoInicio = sk.millis();
        running = true;
        if (nombre != null) {
            System.out.println("Iniciando timer: " + nombre);
        }
    }

    public void iniciar(int periodo, float dutyCycle) {
        this.periodo = periodo;
        this.dutyCycle = dutyCycle;
        tiempoInicio = sk.millis();
        running = true;
        if (nombre != null) {
            System.out.println("Iniciando timer: " + nombre);
        }
    }

    public void update() {
        tiempoActual = sk.millis();
    }

    public boolean periodoPulso() {
        //tiempoActual = sk.millis();
        if (tiempoActual - tiempoInicio > periodo) {
            tiempoInicio = tiempoActual;
            return true;
        } else {
            return false;
        }
    }

    public boolean periodoFin() {
        //tiempoActual = sk.millis();
        return tiempoActual - tiempoInicio > periodo;
    }

    public boolean periodoOnOff() {
        //tiempoActual = sk.millis();
        if (tiempoActual - tiempoInicio > 2 * periodo) {
            tiempoInicio = tiempoActual;
        }
        return tiempoActual - tiempoInicio < periodo;
    }

    public boolean periodoDC(float dutyCycle) {
        this.dutyCycle = PApplet.constrain(dutyCycle, 0, 1);
        tiempoOn = (int) (periodo * this.dutyCycle);
        //tiempoActual = sk.millis();
        if (tiempoActual - tiempoInicio > periodo) {
            tiempoInicio = tiempoActual;
        }
        return tiempoActual - tiempoInicio < tiempoOn;
    }

    public boolean isRunning() {
        return running;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void apagar() {
        periodo = 0;
        dutyCycle = 0;
        tiempoInicio = 0;
        tiempoActual = 0;
        tiempoOn = 0;
        running = false;
        if (nombre != null) {
            System.out.println("Apagando: " + nombre);
        }
    }

}
