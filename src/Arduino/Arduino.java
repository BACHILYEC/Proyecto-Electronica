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

                SerialPort[] puertos = SerialPort.getCommPorts();

                for (SerialPort p : puertos) {
                    p.setBaudRate(9600);
                    p.setComPortTimeouts(
                            SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

                    if (p.openPort()) {
                        puerto = p;
                        reader = new BufferedReader(
                                new InputStreamReader(puerto.getInputStream()));
                        break;
                    }
                }

                if (puerto == null)
                    return null;
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
