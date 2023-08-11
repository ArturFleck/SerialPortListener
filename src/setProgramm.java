import java.io.IOException;

public class setProgramm {
    public static void main(String[] args) throws IOException, InterruptedException {

        String command = "cmd /c start notepad++.exe";
        Process startProcess = Runtime.getRuntime().exec(command);
        //startProcess.waitFor();
        Thread.sleep(50);
    }
}
