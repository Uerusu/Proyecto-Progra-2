/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 *
 * @author saula
 */
public enum NombresArchivos {
    ESTUDIANTES("estudiantes.txt"),
    ID_CONTROL("IdControl.txt"),
    USUARIOS("usuarios.txt"),
    BASE_MARCAS("AGL_001.TXT");

    private final String nombreArchivo;

    NombresArchivos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

}
