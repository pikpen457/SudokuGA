package gui;

import io.LectorSudoku;
import model.Sudoku;
import ga.AlgoritmoGenetico;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VentanaPrincipal extends JFrame {
    private PanelSudoku panelSudoku;
    private JButton btnAbrir;
    private JButton btnResolver;
    private JLabel lblGeneraciones;
    private JLabel lblFitness;
    private Sudoku sudokuActual;

    public VentanaPrincipal() {
        setTitle("SudokuGA - Resolvedor con Algoritmo Genético");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        btnAbrir = new JButton("Abrir Sudoku");
        btnAbrir.addActionListener(e -> abrirSudoku());
        panelControles.add(btnAbrir);
        
        btnResolver = new JButton("Resolver");
        btnResolver.addActionListener(e -> resolverSudoku());
        btnResolver.setEnabled(false);
        panelControles.add(btnResolver);
        
        panelPrincipal.add(panelControles, BorderLayout.NORTH);
        
        panelSudoku = new PanelSudoku();
        panelPrincipal.add(panelSudoku, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblGeneraciones = new JLabel("Generaciones: 0");
        lblFitness = new JLabel("Fitness: 0");
        panelInfo.add(lblGeneraciones);
        panelInfo.add(lblFitness);
        panelPrincipal.add(panelInfo, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }

    private void abrirSudoku() {
        JFileChooser fileChooser = new JFileChooser("sudokus");
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                if (LectorSudoku.validarFormato(archivo.getAbsolutePath())) {
                    sudokuActual = LectorSudoku.leerSudoku(archivo.getAbsolutePath());
                    panelSudoku.setSudoku(sudokuActual);
                    btnResolver.setEnabled(true);
                    lblGeneraciones.setText("Generaciones: 0");
                    lblFitness.setText("Fitness: 0");
                } else {
                    JOptionPane.showMessageDialog(this, "Formato de archivo inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resolverSudoku() {
        if (sudokuActual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, abre un Sudoku primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnResolver.setEnabled(false);
        
        new Thread(() -> {
            AlgoritmoGenetico ag = new AlgoritmoGenetico(sudokuActual, 10000);
            ag.ejecutar();
            
            SwingUtilities.invokeLater(() -> {
                panelSudoku.setSolucion(ag.obtenerMejorSolucion());
                lblGeneraciones.setText("Generaciones: " + ag.obtenerGeneracionActual());
                lblFitness.setText("Fitness: " + (int)ag.obtenerFitnessFinal());
                
                if (ag.esSolucionEncontrada()) {
                    JOptionPane.showMessageDialog(this, "¡Sudoku resuelto en " + ag.obtenerGeneracionActual() + " generaciones!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró solución perfecta. Mejor fitness: " + (int)ag.obtenerFitnessFinal(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                
                btnResolver.setEnabled(true);
            });
        }).start();
    }
}
