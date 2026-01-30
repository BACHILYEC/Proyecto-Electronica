package Arduino;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fazecast.jSerialComm.SerialPort;

public class Arduino {

    private static SerialPort puerto;
    private static BufferedReader reader;

    public static String leerSerial() {
        try {
            if (puerto == null) {
                puerto = SerialPort.getCommPort("COM5");
                puerto.setBaudRate(9600);
                puerto.setComPortTimeouts(
                        SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
                        0,
                        0);

                if (!puerto.openPort()) {
                    return null;
                }

                reader = new BufferedReader(
                        new InputStreamReader(puerto.getInputStream()));
            }

            return reader.readLine();

        } catch (Exception e) {
            return null;
        }
    }

}
