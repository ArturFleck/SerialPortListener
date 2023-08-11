import java.io.IOException;

public class ActivateRunningProgram {
    public static void main(String[] args) {
        try {
            String batchFilePath = "ActivateNotepad.bat";

            // Create a ProcessBuilder instance for the batch file
            ProcessBuilder processBuilder = new ProcessBuilder(batchFilePath);

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Check if the process exited successfully
            if (exitCode == 0) {
                System.out.println("Batch file executed successfully.");
            } else {
                System.out.println("Batch file execution failed. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
