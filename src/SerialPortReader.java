import com.fazecast.jSerialComm.*;

public class SerialPortReader {

    public static void main(String[] args) {
        // Select the port you want to connect to (COM7 in this case)
        SerialPort chosenPort = SerialPort.getCommPort("COM8");

        // Open the serial port
        if (chosenPort.openPort()) {
            System.out.println("Port opened successfully.");

            // Set the serial port parameters (e.g., baud rate, data bits, etc.)
            chosenPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

            // Create a listener to receive data
            chosenPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;

                    byte[] newData = new byte[chosenPort.bytesAvailable()];
                    int numRead = chosenPort.readBytes(newData, newData.length);
                    String data = new String(newData, 0, numRead);
                    System.out.println("Received data: " + data);
                }
            });
        } else {
            System.err.println("Failed to open the port.");
        }
    }
}
