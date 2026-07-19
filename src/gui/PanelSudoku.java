package gui;

import model.Sudoku;
import model.Cromosoma;
import javax.swing.*;
import java.awt.*;

public class PanelSudoku extends JPanel {
    private Sudoku sudoku;
    private Cromosoma solucion;
    private static final int TAMAÑO = 9;
    private static final int SUBCUADRICULA = 3;
    private static final int TAMAÑO_CELDA = 50;
    private static final int MARGEN = 5;

    public PanelSudoku() {
        setPreferredSize(new Dimension(TAMAÑO * TAMAÑO_CELDA + 1, TAMAÑO * TAMAÑO_CELDA + 1));
        setBackground(Color.WHITE);
        this.sudoku = null;
        this.solucion = null;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.solucion = null;
        repaint();
    }

    public void setSolucion(Cromosoma solucion) {
        this.solucion = solucion;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (sudoku == null) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g.getFontMetrics();
            String texto = "Abre un Sudoku para comenzar";
            g.drawString(texto, (getWidth() - fm.stringWidth(texto)) / 2, getHeight() / 2);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int fila = 0; fila <= TAMAÑO; fila++) {
            int grosor = (fila % SUBCUADRICULA == 0) ? 3 : 1;
            g2d.setStroke(new BasicStroke(grosor));
            g2d.drawLine(0, fila * TAMAÑO_CELDA, TAMAÑO * TAMAÑO_CELDA, fila * TAMAÑO_CELDA);
            g2d.drawLine(fila * TAMAÑO_CELDA, 0, fila * TAMAÑO_CELDA, TAMAÑO * TAMAÑO_CELDA);
        }

        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();

        int[][] tableroMostrar = (solucion != null) ? solucion.obtenerGenes() : sudoku.obtenerTablero();
        int[][] tableroOriginal = sudoku.obtenerTableroOriginal();

        for (int fila = 0; fila < TAMAÑO; fila++) {
            for (int columna = 0; columna < TAMAÑO; columna++) {
                int valor = tableroMostrar[fila][columna];
                
                if (valor != 0) {
                    if (tableroOriginal[fila][columna] != 0) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(new Color(0, 100, 200));
                    }

                    String texto = String.valueOf(valor);
                    int x = columna * TAMAÑO_CELDA + (TAMAÑO_CELDA - fm.stringWidth(texto)) / 2;
                    int y = fila * TAMAÑO_CELDA + ((TAMAÑO_CELDA - fm.getHeight()) / 2) + fm.getAscent();
                    g2d.drawString(texto, x, y);
                }
            }
        }
    }
}
