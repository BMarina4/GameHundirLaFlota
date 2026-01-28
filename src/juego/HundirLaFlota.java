package juego;

import processing.core.PApplet;
import processing.core.PImage;

public class HundirLaFlota extends PApplet {

    // --- LÓGICA DEL JUEGO ---
    PImage imgMarco;
    int filas = 8;
    int columnas = 8;
    int tamañoCelda = 50;
    int margen = 100;

    // Matriz: 0 = Agua, 1 = Barco, 2 = Fallo (X), 3 = Tocado (O),
    int[][] tablero = new int[filas][columnas];

    int barcosRestantes = 4;

    public static void main(String[] args) {
        PApplet.main("juego.HundirLaFlota");
    }

    @Override
    public void settings() {
        size(columnas * tamañoCelda + 200, filas * tamañoCelda + 200);
    }

    @Override
    public void setup() {
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/imagenes/marco.jpg");

            if (is != null) {
                imgMarco = new PImage(javax.imageio.ImageIO.read(is));
            } else {
                System.err.println("Error: Could not find /imagenes/marco.jpg inside the JAR!");
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading the image file!");
            e.printStackTrace();
        }
        imgMarco.resize(width, height);

        surface.setTitle("Hundir la Flota - Gráfico");

        int xr = random_num(0, columnas - 2);
        int yr = random_num(0, filas - 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tablero[xr + i][yr + j] = 1;
            }
        }

    }

    @Override
    public void draw() {
        background(255);
        dibujarTablero();
        dibujarEjeColumnas();
        dibujarEjeFilas();

        if (barcosRestantes == 0) {
            mostrarVictoria();
        }
    }

    void dibujarEjeFilas() {
        fill(200);
        textAlign(CENTER, CENTER);
        textSize(22);

        for (int i = 0; i < filas; i++) {
            int x = margen - 20;
            int y = i * tamañoCelda + margen + (tamañoCelda / 2);
            text(i + 1, x, y);
        }
    }

    void dibujarEjeColumnas() {
        fill(200);
        textAlign(CENTER, CENTER);
        textSize(22);
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
        for (int i = 0; i < columnas; i++) {
            int x = i * tamañoCelda + margen + (tamañoCelda / 2);
            int y = margen - 20;
            text(letras[i], x, y);
        }

    }

    void dibujarTablero() {
        image(imgMarco, 0, 0);
        stroke(200);
        fill(20, 30, 60);

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                int x = c * tamañoCelda + margen;
                int y = f * tamañoCelda + margen;

                stroke(200);
                fill(20, 30, 60);
                rect(x, y, tamañoCelda, tamañoCelda);

                if (tablero[f][c] == 2) {
                    fill(150);
                    line(x, y, x + tamañoCelda, y + tamañoCelda);
                    line(x + tamañoCelda, y, x, y + tamañoCelda);
                } else if (tablero[f][c] == 3) {
                    fill(255, 0, 0);
                    noStroke();
                    ellipse(x + tamañoCelda / 2, y + tamañoCelda / 2, 40, 40);
                }
            }
        }
    }

    void reiniciarJuego() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                tablero[f][c] = 0;
            }
        }
        barcosRestantes = 4;

        int xr = random_num(0, columnas - 2);
        int yr = random_num(0, filas - 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tablero[xr + i][yr + j] = 1;
            }
        }
    }

    @Override
    public void mousePressed() {
        if (barcosRestantes == 0) {
            reiniciarJuego();
            return;
        }

        int c = (mouseX - margen) / tamañoCelda;
        int f = (mouseY - margen) / tamañoCelda;

        if (f >= 0 && f < filas && c >= 0 && c < columnas) {
            if (tablero[f][c] == 1) {
                tablero[f][c] = 3;
                barcosRestantes--;
            } else if (tablero[f][c] == 0) {
                tablero[f][c] = 2;
            }
        }
    }

    void mostrarVictoria() {
        fill(0, 0, 0, 100);
        rect(0, 0, width, height);
        fill(255);
        textAlign(CENTER, CENTER);
        textSize(32);
        text("YOU WIN!!!", width / 2, height / 2);

        textSize(18);
        text("Click to play again", width / 2, height / 2 + 40);

    }

    int random_num(int min, int max) {
        int randomNum = (int) (Math.random() * (max - min + 1) + min);
        return randomNum;
    }
}
