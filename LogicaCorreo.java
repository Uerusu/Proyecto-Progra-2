package LogicaNegocio;
/**
 * Clase de lógica de negocio para el envío de correos electrónicos mediante protocolo SMTP.
 * 
 * <p>Esta clase utiliza la API JavaMail (javax.mail) para implementar el envío de correos
 * a través de un servidor SMTP con autenticación SSL/TLS. Es especialmente útil para
 * enviar comprobantes de pago y notificaciones a empleados.</p>
 * 
 * <p><b>Configuración del servidor SMTP:</b></p>
 * <ul>
 *   <li>Host: securemail.comredcr.com</li>
 *   <li>Puerto: 465 (SSL)</li>
 *   <li>Protocolo: TLSv1.2</li>
 *   <li>Autenticación: Requerida</li>
 * </ul>
 * 
 * @author vega
 * @version 1.0.0
 * @since 1.0.0
 * @see Entidades.Correo
 * @see javax.mail.Session
 * @see javax.mail.Transport
 */

import Entidades.Correo;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

    private Properties propiedades;
    private Session session;
     /**
     * Carga las propiedades de configuración del servidor SMTP.
     * Establece todos los parámetros necesarios para la conexión segura
     * al servidor de correo.
     * 
     * <p>Este método debe invocarse antes de crear la sesión SMTP.</p>
     * 
     * @see #crearSessionSmtp()
     */
    private void cargarPropiedades() {
        propiedades = new Properties();
        propiedades.put("mail.smtp.host", "securemail.comredcr.com");
        propiedades.put("mail.smtp.port", "465");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.ssl.enable", "true");
        propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }
        /**
     * Crea una sesión autenticada con el servidor SMTP.
     * Utiliza las propiedades cargadas y las credenciales hardcodeadas
     * para establecer una sesión segura que será utilizada para enviar correos.
     * 
     * @see #cargarPropiedades()
     * @see javax.mail.Session#getInstance(Properties, javax.mail.Authenticator)
     */

    private void crearSessionSmtp() {
        cargarPropiedades();
        session = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "curso_progra2@comredcr.com",
                        "u6X1h1p9@"
                );
            }
        });
    }
    /**
     * Construye el contenido multipart del correo electrónico.
     * Crea un objeto Multipart que incluye el cuerpo del mensaje en texto plano
     * y todos los archivos adjuntos proporcionados en el objeto Correo.
     * 
     * @param datosCorreo Objeto Correo con el mensaje y lista de archivos adjuntos.
     *                    El mensaje será codificado en UTF-8.
     * @return Objeto Multipart listo para ser asignado al contenido del MimeMessage.
     * @throws MessagingException Si ocurre un error al construir el contenido multipart.
     *                            Posibles causas: error al leer archivo adjunto,
     *                            formato de archivo no soportado.
     * @see javax.mail.Multipart
     * @see javax.mail.internet.MimeBodyPart
     * @see javax.activation.FileDataSource
     */

    private Multipart construirContenido(Correo datosCorreo) throws MessagingException {

        Multipart multipart = new MimeMultipart();

        MimeBodyPart cuerpo = new MimeBodyPart();
        cuerpo.setText(datosCorreo.getMensaje(), "utf-8");
        multipart.addBodyPart(cuerpo);

        if (datosCorreo.getArchivosAdjuntos() != null && !datosCorreo.getArchivosAdjuntos().isEmpty()) {
            for (File archivo : datosCorreo.getArchivosAdjuntos()) {
                if (archivo != null && archivo.exists()) {
                    MimeBodyPart adjunto = new MimeBodyPart();
                    adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo)));
                    adjunto.setFileName(archivo.getName());
                    multipart.addBodyPart(adjunto);
                }
            }
        }

        return multipart;
    }
     /**
     * Envía un correo electrónico de forma síncrona (bloqueante).
     * Valida los datos del destinatario, crea la sesión SMTP, construye el mensaje
     * completo con adjuntos y lo envía a través del servidor configurado.
     * 
     * 
     * @param datosCorreo Objeto Correo con toda la información necesaria:
     *                    email destino, asunto, mensaje y archivos adjuntos opcionales.
     *                    El email no debe ser null ni estar vacío.
     * @throws MessagingException Si ocurre cualquier error durante el envío:
     *         <ul>
     *           <li>Email destino es null o vacío (validación previa)</li>
     *           <li>Error al conectar con el servidor SMTP</li>
     *           <li>Credenciales de autenticación inválidas</li>
     *           <li>Email destino con formato inválido</li>
     *           <li>Error al adjuntar archivos</li>
     *           <li>Timeout de conexión</li>
     *           <li>Rechazo del servidor por políticas anti-spam</li>
     *         </ul>
     * @see #enviarCorreoThread(Correo)
     * @see #construirContenido(Correo)
     * @see javax.mail.Transport#send(javax.mail.Message)
     */
    public void enviarCorreo(Correo datosCorreo) throws MessagingException {

        if (datosCorreo == null || datosCorreo.getEmail() == null || datosCorreo.getEmail().isBlank()) {
            throw new MessagingException("El correo destino es nulo o vacío");
        }

        crearSessionSmtp();

        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress("curso_progra2@comredcr.com"));
        mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(datosCorreo.getEmail(), false)
        );
        mensaje.setSubject(datosCorreo.getAsunto()); 
        mensaje.setContent(construirContenido(datosCorreo));


        Transport.send(mensaje);
    }
    /**
     * Envía un correo electrónico de forma asíncrona en un thread separado.
     * Método de conveniencia que ejecuta enviarCorreo() en un hilo independiente,
     * permitiendo que la interfaz gráfica permanezca responsiva durante el envío.
     * 
     * @param datosCorreo Objeto Correo con toda la información del mensaje.
     *                    Los mismos requisitos que enviarCorreo().
     * @see #enviarCorreo(Correo)
     * @see Thread
     * @see javax.swing.JOptionPane
     */
    public void enviarCorreoThread(Correo datosCorreo) {
        new Thread(() -> {
            try {
                enviarCorreo(datosCorreo);
                JOptionPane.showMessageDialog(null, "Correo enviado correctamente");
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error enviando correo:\n" + e.getMessage()
                );
            }
        }).start();
    }
}
