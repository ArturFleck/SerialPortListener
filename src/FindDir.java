import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FindDir {
    public static String res;
    public static void main(String[] args) throws IOException {

        Path directory = Paths.get("D:/_profile/Desktop/Типові меблі ЕЛКОН ДІЗАЙН/ТИПОВІ КУХНІ/");
        String folder  = "МВ 72 151Л";


        Files.walk(directory)
                .filter(Files::isDirectory)
                .map(Path::toFile)
                .filter(file -> file.getName().equals(folder))
                .findFirst()
                .ifPresent(file -> {
                    //System.out.println("Found target directory at: " + file.getAbsolutePath());
                    res = file.getAbsolutePath();
                    //System.exit(0); // Stop the program after finding the first occurrence
                });


        System.out.println(res);


        /*
            List<File> directories =
                    Files.walk(directory)
                            .filter(Files::isDirectory)
                            .map(Path::toFile)
                            .collect(Collectors.toList());

        System.out.println(directories.contains(folder));

        for (File fff: directories){
            String temp = String.valueOf(fff);
            if (temp.contains(folder))
                System.out.println(fff);
        }
*/



        /*Path directory = Paths.get("D:\\directory\\to\\list");

        Files.walk(directory, 1).filter(entry -> !entry.equals(directory))
                .filter(Files::isDirectory).forEach(subdirectory ->
                {
                    // do whatever you want with the subdirectories
                    System.out.println(subdirectory.getFileName());
                });*/
    }
}
