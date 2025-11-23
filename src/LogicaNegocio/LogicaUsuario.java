/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import AccesoDatos.AccesoDatos;
import AccesoDatos.IdControl;
import Entidades.Usuario;
import Utilidades.NombresArchivos;
import java.io.IOException;

/**
 *
 * @author saula
 */
public class LogicaUsuario {

    private AccesoDatos accesoDatos;
    private IdControl idControl;

    public void agregarUsuario(Usuario usuario) throws IOException {

        accesoDatos = new AccesoDatos();
        idControl = new IdControl();
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        usuario.setId(idControl.getNextId(NombresArchivos.USUARIOS.getNombreArchivo()));
        accesoDatos.setRegistro(String.valueOf(usuario.getId()) + ","
                + usuario.getNombre() + ","
                + usuario.getApellido1() + ","
                + usuario.getApellido2() + ","
                + usuario.getEmail() + ","
                + usuario.getUsuario() + ","
                + usuario.getPassword());
        accesoDatos.agregarRegistro();

    }

    public void actualizarUsuario(Usuario usuario) throws IOException {
        
        accesoDatos = new AccesoDatos();
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setRegistro(String.valueOf(usuario.getId()) + ","
                + usuario.getNombre() + ","
                + usuario.getApellido1() + ","
                + usuario.getApellido2() + ","
                + usuario.getEmail() + ","
                + usuario.getUsuario() + ","
                + usuario.getPassword());
        accesoDatos.setEliminar(false);
        accesoDatos.modificarRegistro();
        

    }

    public void eliminarUsuario(Usuario usuario) throws IOException {
        
        accesoDatos = new AccesoDatos();
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        accesoDatos.setIdRegistro(usuario.getId());
        accesoDatos.setEliminar(true);
        accesoDatos.modificarRegistro();
        
    }

    public void listarUsuario(Usuario usuario) throws IOException {

        accesoDatos = new AccesoDatos();
        accesoDatos.setNombreArchivo(NombresArchivos.USUARIOS.getNombreArchivo());
        accesoDatos.listarRegistros();
        for(String[] datos : accesoDatos.getListaRegistros()){
            Usuario usr = new Usuario(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);
            usuario.agregarListaUsuarios(usr);   
        }
        

    }

}
