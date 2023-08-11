import com.fazecast.jSerialComm.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SerialPortListener {
    public static int i = 0;

    public static void main(String[] args) {

        // List all available serial ports
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        String portName = null;
        for (SerialPort port : serialPorts) {
            //System.out.println(port.getSystemPortName() + " " + port.getDescriptivePortName());
            if (port.getDescriptivePortName().contains("Barcode Scanner") ){
                portName=port.getSystemPortName();
            }
        }

        // Select the port you want to listen to (COM6 in this case)
        //SerialPort chosenPort = SerialPort.getCommPort("COM8");
        SerialPort chosenPort = SerialPort.getCommPort(portName);

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
                    System.out.println(chosenPort.getDescriptivePortName());


                    File log = new File("D:/_profile/Desktop/log.txt");
                    if (!log.exists()) {
                        try {
                            log.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(log, true);
                        fw.write(data + "\n");
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        setProgram(data);
                    } catch (AWTException | InterruptedException | IOException e) {
                        System.out.println(e);
                    }

                    i++;
                    if (i == 2) {
                        chosenPort.closePort();
                        System.exit(0);
                    }
                }
            });
        } else {
            System.err.println("Failed to open the port.");
        }


    }

    public static void setProgram(String message) throws AWTException, InterruptedException, IOException {

        int xCoord = 423;   // 393
        int yCoord = 308;   // 240

        // Move the cursor
        int mask = InputEvent.BUTTON1_DOWN_MASK;
        Robot robot = new Robot();
        robot.mouseMove(xCoord, yCoord);
        robot.mousePress(mask);
        Thread.sleep(50);
        robot.mouseRelease(mask);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_DELETE);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_DELETE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(message);
        clipboard.setContents(strSel, null);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

}
