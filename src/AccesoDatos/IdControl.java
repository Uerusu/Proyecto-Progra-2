/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import Utilidades.NombresArchivos;

/**
 *
 * @author vegab
 */
public class IdControl {

    private String archivoControl;
    private Map<String, Integer> idMap;

    public IdControl() throws IOException {
        this.archivoControl = NombresArchivos.ID_CONTROL.getNombreArchivo();
        this.idMap = new HashMap<>();
        loadIds();
    }

    private void loadIds() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoControl))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide la línea en partes separadas por "=".
                String[] parts = line.split("=");
                // Coloca el nombre del archivo y su ID correspondiente en el mapa.
                idMap.put(parts[0], Integer.valueOf(parts[1]));
            }
        }
    }

    private void saveIds() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoControl))) {
            for (Map.Entry<String, Integer> entry : idMap.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public synchronized int getNextId(String fileName) throws IOException {

        this.archivoControl = NombresArchivos.ID_CONTROL.getNombreArchivo();
        this.idMap = new HashMap<>();
        loadIds();
        // Obtiene el siguiente ID para el archivo o comienza con 1 si no existe.
        int nextId = idMap.getOrDefault(fileName, 1);
        // Actualiza el mapa con el nuevo ID incrementado.
        idMap.put(fileName, nextId + 1);
        // Guarda los IDs actualizados en el archivo de control.
        saveIds();
        // Devuelve el ID actual (antes de incrementar).
        return nextId;
    }

}
