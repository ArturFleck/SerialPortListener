import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class SerialPortListener {
    public static int i = 0;

    public static void main(String[] args) {

        // List all available serial ports
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        String portName = null;
        for (SerialPort port : serialPorts) {
            //System.out.println(port.getSystemPortName() + " " + port.getDescriptivePortName());
            if (port.getDescriptivePortName().contains("Barcode Scanner")) {
                portName = port.getSystemPortName();
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
                    //System.out.println("Received data: " + data);
                    System.out.println(chosenPort.getDescriptivePortName());


                    File log = new File("D:/_profile/Desktop/log.txt");
                    if (!log.exists()) {
                        try {
                            log.createNewFile();
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(log, true);
                        fw.write(data + "\n");
                        fw.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    List<String> mes = Arrays.asList(data.split("\n"));
                    String temp = mes.get(1).replace("\r", "");
                    mes.set(1, temp);

                    for (int i = 0; i < mes.size(); i++)
                        System.out.println(i + "\t" + mes.get(i));

                    DirScan.NC = mes.get(0);
                    DirScan.folder = mes.get(1);
                    try {
                        String temp2 = DirScan.findDirByFolderName();
                        String temp3 = DirScan.findFileInFolder(temp2);
                        System.out.println(temp3);
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    DirectoryScanner.NC = mes.get(0);
                    DirectoryScanner.folder = mes.get(1);
                    String path = DirectoryScanner.runS();
                    if (path != null) {
                        data = path;
                    } else {
                        data = mes.get(0);
                    }
                    //System.out.println(data);

                    try {
                        ActivateWindow.activateMyApp();
                        Thread.sleep(200);
                        setProgram(data);

                        OutputStream outputStream = chosenPort.getOutputStream();
                        // Send the beep command. The exact command might vary based on your scanner's specifications.
                        String beepCommand = "B"; // Replace with the actual beep command
                        outputStream.write(beepCommand.getBytes());
                        outputStream.close();
                    } catch (AWTException | InterruptedException | IOException e) {
                        System.out.println(e);
                    }

                    i++;
                    if (i == 5) {
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
/*        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_DELETE);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_DELETE);*/

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(message);
        clipboard.setContents(strSel, null);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(100);
/*        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);*/
    }
}
