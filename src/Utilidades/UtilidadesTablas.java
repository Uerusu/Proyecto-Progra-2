package Utilidades;

import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class UtilidadesTablas {

    public static void autoAjustarColumnas(JTable tabla) {
        for (int col = 0; col < tabla.getColumnCount(); col++) {
            int anchoMaximo = 0;
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                var renderer = tabla.getCellRenderer(fila, col);
                var componente = tabla.prepareRenderer(renderer, fila, col);
                anchoMaximo = Math.max(componente.getPreferredSize().width, anchoMaximo);
            }
            tabla.getColumnModel().getColumn(col).setPreferredWidth(anchoMaximo + 10);
        }
    }

    public static void configurarTablaConDatos(JTable tabla, String[] columnas, List<String[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        for (String[] fila : datos) {
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setCellSelectionEnabled(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
        autoAjustarColumnas(tabla);
    }
}
