import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class DirectoryScanner {
    public static String nnn;
    public static String folder = "МВ 72 151Л";
    public static String NC = "17.mpr";

    public static void main(String[] args) {
        String path = "D:/_profile/Desktop/Типові меблі ЕЛКОН ДІЗАЙН/ТИПОВІ КУХНІ/";

        System.out.println("----------------\n");

        listf(path);
        System.out.println(nnn);
    }

    public static String runS(){
        String path = "D:/_profile/Desktop/Типові меблі ЕЛКОН ДІЗАЙН/ТИПОВІ КУХНІ/";

        //System.out.println("----------------\n");

        System.out.println(path);
        System.out.println(folder);
        System.out.println(NC);

        listf(path);
        return nnn;
    }

    public static List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();


        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile() && (file.getName().contains(NC)) && (file.getAbsolutePath().contains(folder))) {
                 {
                    System.out.println(file.getAbsolutePath());
                    System.out.println(file.getName());
                    nnn= file.getAbsolutePath();
                }
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        return resultList;
    }
}
