import java.util.ArrayList;
import java.util.List;

public class GuiProcessesList {
    public static void main(String[] args) {
        // Get the list of running processes
        /*
        ProcessHandle.allProcesses()
                .filter(GuiProcessesList::hasGui)
                .forEach(process -> System.out.println(process.pid() + ": " + process.info().command().orElse("")));*/

        //findMyApp("calc");

        System.out.println(findMyApp("calc"));
    }

    public static int findMyApp(String name){
        List<String> list = new ArrayList<>();

        ProcessHandle.allProcesses()
                .filter(GuiProcessesList::hasGui)
                .forEach(process -> list.add (process.pid() + ": " + process.info().command().orElse("")));

        int index=0;
        for (String s: list
        ) {
            if(s.contains(name)){
                int i = s.indexOf(":");
                s=s.substring(0,i);
                index = Integer.parseInt(s);
            }
        }
        return index;
    }
    private static boolean hasGui(ProcessHandle process) {
        return process.info().command().isPresent() && process.info().totalCpuDuration().isPresent();
    }
}
