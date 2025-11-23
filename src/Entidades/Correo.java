/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author saula
 */
public class Correo extends Usuario{ //Herencia de Usuario
    
    private String mensaje, asunto;
    private ArrayList<File> archivosAdjuntos;
    
    public Correo(){
        
        this.archivosAdjuntos = new ArrayList<>();
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public ArrayList<File> getArchivos() {
        return archivosAdjuntos;
    }

    public void setArchivos(ArrayList<File> Archivos) {
        this.archivosAdjuntos = Archivos;
    }
    
    public void agregarArchivoAdjunto(File archivoAdjunto){ //Se adjunta el archivo para agregarlo
        this.archivosAdjuntos.add(archivoAdjunto);
    }

    public ArrayList<File> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    public void setArchivosAdjuntos(ArrayList<File> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }
    
}