/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoDatos;

/**
 *
 * @author saula
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AccesoDatos {

    private String registro, nombreArchivo;
    private ArrayList<String[]> listaRegistros;
    private int idRegistro;
    private boolean eliminar;

    public AccesoDatos() {

        listaRegistros = new ArrayList<>();
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public ArrayList<String[]> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(ArrayList<String[]> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public void agregarRegistro() throws IOException {

        try (BufferedWriter bW = new BufferedWriter(new FileWriter(this.nombreArchivo, true))) {

            bW.append(this.registro);
            bW.newLine();

        }

    }

    public void listarRegistros() throws IOException {

        try (BufferedReader bR = new BufferedReader(new FileReader(this.nombreArchivo))) {

            String linea;

            while ((linea = bR.readLine()) != null) {

                String[] datos = linea.split(",");

                this.listaRegistros.add(datos);

            }

        }

    }

    public void modificarRegistro() throws IOException {
        
        File archivoOriginal = new File(this.nombreArchivo);
        File archivoTemp = new File("temp_" + this.nombreArchivo);
        

        try (BufferedReader bR = new BufferedReader(new FileReader(archivoOriginal));
                BufferedWriter bW = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;

            while ((linea = bR.readLine()) != null) {

                String[] datos = linea.split(",");

                if (this.idRegistro == Integer.parseInt(datos[0])) {

                    if (this.eliminar) {

                        continue;

                    } else {

                        bW.append(this.registro);
                        bW.newLine();

                    }

                } else {
                    bW.append(linea);
                    bW.newLine();
                }

            }
            
        }
        
         if (!archivoOriginal.delete()){
                throw new IOException("No se puede eliminar");
            }
            
            if (!archivoTemp.renameTo(archivoOriginal)){
                throw new IOException("No se puede renombrar el archivo");
            }

    }

}