import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryScanner {
    public static void main(String[] args) {
        String path = "D:\\_profile\\Desktop\\Типові меблі ЕЛКОН ДІЗАЙН\\ТИПОВІ КУХНІ\\Кухня верх\\";
        ArrayList<File> directories = new ArrayList<File>(
                Arrays.asList(
                        new File(path).listFiles(File::isDirectory)
                )
        );

        /*for (File names: directories)
            System.out.println(names);*/

        System.out.println("----------------\n");

        List<File> fileList = new ArrayList<>();
        fileList = listf(path);

 /*       for (File names: fileList){
            System.out.println(names);
        }*/

        List<String> str = new ArrayList<>();
        str.add("asd");
        str.add("asdf");
        str.add("qwe.mpr");

        for (String sss : str){
            if (sss.contains(".mpr"))
                System.out.println(sss);
        }
    }

    public static List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {

            if (file.isFile() && (file.getName().contains(".mpr"))) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        return resultList;
    }
}
