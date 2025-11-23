/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import Entidades.Correo;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class LogicaCorreo {
    private Properties propiedades = null;
    private Session session = null;
    private final ArrayList<BodyPart> adjuntos = new ArrayList<>();
    
    
    private void cargarPropiedades(){
        propiedades = new Properties();
        propiedades.put("mail.smtp.ssl.enable", "true");
        propiedades.put("mail.smtp.host", "securemail.comredcr.com");
        propiedades.put("mail.smtp.port", "465");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }
    
    private void crearSessionSmtp() {
        cargarPropiedades();
        session = Session.getDefaultInstance(propiedades,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //Se usa autenticación con nombre de usuario y contraseña
                return new PasswordAuthentication("curso_progra2@comredcr.com", "u6X1h1p9@");
            }
        });
    }
    
    private void agregarAdjuntos(Correo datosCorreo) throws MessagingException{
        for(File archivo : datosCorreo.getArchivosAdjuntos()){
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo.getAbsolutePath())));
            adjunto.setFileName(archivo.getName());
            adjuntos.add(adjunto);
        }
    }
    
    public void enviarCorreo(Correo datosCorreo) throws MessagingException{
        //Crear config y sesión SMTP
        crearSessionSmtp();
        
        Message objCorreo = new MimeMessage(session);
        objCorreo.setFrom(new InternetAddress("curso_progra2@comredcr.com"));
        objCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(datosCorreo.getEmail()));
        objCorreo.setSubject(datosCorreo.getAsunto());
        
        //Crear el mensaje del correo
        BodyPart objBodyPart = new MimeBodyPart();
        objBodyPart.setText(datosCorreo.getMensaje());
        
        Multipart objMultipar = new MimeMultipart();
        objMultipar.addBodyPart(objBodyPart);
        
        if (datosCorreo.getArchivosAdjuntos()!=null){
            agregarAdjuntos(datosCorreo);
            for (BodyPart adjunto : adjuntos){
                objMultipar.addBodyPart(adjunto);
            }
        }
        
        objCorreo.setContent(objMultipar);
        
        Transport.send(objCorreo);
    }
    //======================VOID AÑADIDO PARA PODER ENVIAR CORREO MEDIANTE EL THREAD============================================

    public void enviarCorreoThread (Correo datosCorreo) {
        Thread Hilo = new Thread(() -> {
            try {
                enviarCorreo(datosCorreo);
                JOptionPane.showMessageDialog(null, "Correo enviado correctamente");
             } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Error enviando correo en:"+ e.getMessage());
             }
                });
        Hilo.start();
    }
    //================================================================================================================================
    public LogicaCorreo(){
        
    }
    
    
}
