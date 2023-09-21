// Daniel Rey Velez id : 699543


package co.edu.uniminuto.permisosappmovil;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declaración de constantes para solicitudes de permiso
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_AUDIO_PERMISSION = 2;
    private static final int REQUEST_FINGERPRINT_PERMISSION = 3;

    // Declaración de variables para botones y TextViews
    private Button btnTomarFoto;
    private Button btnGrabarAudio;
    private Button btnHuella;
    private Button btnVerificarPermisos;
    private TextView txtCameraPermisos;
    private TextView txtAudioPermisos;
    private TextView txtHuellaPermisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Llama al método "begin" para configurar los listeners e inicializar los elementos UI
        begin();
    }

    private void begin() {
        // Inicializa los botones y los TextViews
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnGrabarAudio = findViewById(R.id.btnGrabarAudio);
        btnHuella = findViewById(R.id.btnHuella);
        btnVerificarPermisos = findViewById(R.id.btnVerificarPermisos);
        txtCameraPermisos = findViewById(R.id.txtCameraPermisos);
        txtAudioPermisos = findViewById(R.id.txtAudioPermisos);
        txtHuellaPermisos = findViewById(R.id.txtHuellaPermisos);

        // Configura los OnClickListeners para los botones
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pedirPermiso(Manifest.permission.CAMERA, "Necesitamos acceso a la cámara para tomar fotos.", "Permiso de cámara concedido.", "Permiso de cámara denegado.");
            }
        });

        btnGrabarAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pedirPermiso(Manifest.permission.RECORD_AUDIO, "Necesitamos acceso al micrófono para grabar audio.", "Permiso de grabación de audio concedido.", "Permiso de grabación de audio denegado.");
            }
        });

        btnHuella.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pedirPermiso(Manifest.permission.USE_BIOMETRIC, "Necesitamos acceso a la huella dactilar para usar la autenticación biométrica.", "Permiso de huella dactilar concedido.", "Permiso de huella dactilar denegado.");
            }
        });

        btnVerificarPermisos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validarEstadoPermiso(Manifest.permission.CAMERA, txtCameraPermisos);
                validarEstadoPermiso(Manifest.permission.RECORD_AUDIO, txtAudioPermisos);
                validarEstadoPermiso(Manifest.permission.USE_BIOMETRIC, txtHuellaPermisos);
            }
        });
    }

    private void pedirPermiso(String permission, String rationaleMessage, String grantedMessage, String deniedMessage) { // En esta función se emplea los siguientes parametros
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {//Se verifica si el permiso que se encuentran en el parametro permission ya fue otorgado por el usuario
            // En caso de que el usuario no han dado permiso se proyecta la razon por la que se quiere implementar el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { // permission - le proyecta al usuario la razon por la que requiere el permio de acceder el recurso delmovil como la camara
                // Mostrar mensaje de justificación al usuario
                showToast(rationaleMessage); // Se proyecta al usuario el permiso otorgado
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, obtenerCodigoSolicitud(permission));
        } else {
            updatePermissionStatus(grantedMessage); // Actualizar el TextView- mensaje de que el permiso fue denegado por el usuario
        }
    }

    private int obtenerCodigoSolicitud(String permission) {
        if (Manifest.permission.CAMERA.equals(permission)) { /* Se realiza la comparación  del permiso (permission) con el permiso Manifest.permission.CAMERA
            que hace referencia al permiso de la camara, si llegan a ser iguales retorna REQUEST_CAMERA_PERMISSION, significa se esta solicitando el permiso*/
            return REQUEST_CAMERA_PERMISSION;
        } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {/* Se realiza la comparación  del permiso (permission) con el permiso Manifest.permission.RECORD_AUDIO
            que hace referencia al permiso de la grabación, si llegan a ser iguales retorna REQUEST_AUDIO_PERMISSIO, significa se esta solicitando el permiso*/
            return REQUEST_AUDIO_PERMISSION;
        } else if (Manifest.permission.USE_BIOMETRIC.equals(permission)) {/* Se realiza la comparación  del permiso (permission) con el permiso Manifest.permission.USE_BIOMETRIC
            que hace referencia al permiso de la huella dactilar, si llegan a ser iguales retorna REQUEST_FINGERPRINT_PERMISSION, significa se esta solicitando el permiso*/
            return REQUEST_FINGERPRINT_PERMISSION;
        }
        return -1;
    }

    private void validarEstadoPermiso(String permission, TextView statusTextView) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            // Permiso concedido
            statusTextView.setText("Permiso de " + nombrePermiso(permission) + ": concedido.");
        } else {
            // Permiso denegado
            statusTextView.setText("Permiso de " + nombrePermiso(permission) + ": denegado.");
        }
    }

    // Esta funcion toma el nombre de un permiso como entrada la cual devolvera una cadena  que representa el nombre del permiso

    private String nombrePermiso(String permission) {
        if (Manifest.permission.CAMERA.equals(permission)) {
            return "cámara";
        } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
            return "grabación de audio";
        } else if (Manifest.permission.USE_BIOMETRIC.equals(permission)) {
            return "huella dactilar";
        }
        return "permiso desconocido";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        /*requestCode ->Se identifica la solicitud del permiso que se realiza anteriormente
        * permissions -> Es un array de enteros el cual se compone de los nombres de los permisos que se le solicitaron usuario
        *grantResults -> Es un array de enteros el cual indica si el usario concedio o nego los permisos
        * */

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String estadoPermiso; // Se declara la variable estadoPermiso que se emplea para almacenar el estado de permiso una vez procesada la repuesta del usurio

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { /* se procede a verificar grantResults se encuentra disponible y si el
        primer resultado es igual a PackageManager.PERMISSION_GRANTED, lo indiciara que el permiso fue dadp por el usuairo*/
            // Permiso concedido
            estadoPermiso = "Permiso concedido.";
            // Si no el indicara que el usuario nego el permiso
        } else {
            // Permiso denegado
            estadoPermiso = mensajePermisoDenegado(requestCode);
        }

        updatePermissionStatus(estadoPermiso);
    }



    // Esta función toma el codigo de solicitud requestCode y se devuelve una cadena de texto que proyecta los permisos negados
    private String mensajePermisoDenegado(int requestCode) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                return "Permiso de cámara denegado.";
            case REQUEST_AUDIO_PERMISSION:
                return "Permiso de grabación de audio denegado.";
            case REQUEST_FINGERPRINT_PERMISSION:
                return "Permiso de huella dactilar denegado.";
            default:
                return "Permiso denegado.";
        }
    }

    private void updatePermissionStatus(String status) {
        showToast(status);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
