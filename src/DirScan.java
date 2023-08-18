import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DirScan {
    public static String folder;
    public static String NC;
    public static String res;
    //public static String res2;
    public static void main(String[] args) throws IOException {

        findDirByFolderName();

    }
    public static String findDirByFolderName() throws IOException {
        Path directory = Paths.get("D:/_profile/Desktop/Типові меблі ЕЛКОН ДІЗАЙН/ТИПОВІ КУХНІ/");

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

        //System.out.println("from FindDir" + res);
        return res;
    }

    public static String findFileInFolder(String res2) throws IOException {
        String temp = res2.replace("\\","/") + "/Програма BHX/" + folder + "/";

        Path directory = Paths.get(temp);
        String NC2 = NC.replace(".mpr","") + "_R.mpr";

        try (Stream<Path> stream = Files.walk(directory)) {
            Path foundPath = stream
                    .filter(path -> path.toFile().isFile() && (path.getFileName().toString().equals(NC) || path.getFileName().toString().equals(NC2)))
                    .findFirst()
                    .orElse(null);

            return foundPath != null ? foundPath.toAbsolutePath().toString() : null;
        }
        //System.out.println("from FindDir" + res);
    }
}
