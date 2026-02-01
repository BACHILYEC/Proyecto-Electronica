package Arduino;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fazecast.jSerialComm.SerialPort;

public class Arduino {

    private static SerialPort puerto;
    private static BufferedReader reader;

    public static String leerSerial() {
        try {
            if (puerto == null || !puerto.isOpen()) {
                puerto = SerialPort.getCommPort("COM5");
                puerto.setBaudRate(9600);
                puerto.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

                if (puerto.openPort()) {
                    reader = new BufferedReader(new InputStreamReader(puerto.getInputStream()));
                } else {
                    puerto = null;
                    return null;
                }
            }

            if (puerto.bytesAvailable() < 0) {
                throw new Exception("Puerto desconectado");
            }

            if (puerto.bytesAvailable() > 0) {
                return reader.readLine();
            }

        } catch (Exception e) {
            resetearConexion();
        }
        return null;
    }

    private static void resetearConexion() {
        try {
            if (puerto != null) {
                puerto.closePort();
            }
        } catch (Exception e) {
        }
        puerto = null;
        reader = null;
    }
}