import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.util.ArrayList;
import java.util.List;

public class ActiveWindowList {
    public static void main(String[] args) {
        startMyApp();
    }

    public static void startMyApp(){
        List<String> activeWindows = getActiveWindowTitles();

        boolean runOrNot = false;

        for (String title : activeWindows) {
            if (title.contains("Notepad++")) {
                runOrNot = true;
                System.out.println("not in here");
            }
            System.out.println("Active Window: " + title);
        }
        if (!runOrNot) {
            System.out.println("Start program");
            ActivateRunningProgram.main(new String[]{""});
        }
    }

    public static List<String> getActiveWindowTitles() {
        final List<String> windowTitles = new ArrayList<>();

        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            @Override
            public boolean callback(WinDef.HWND hWnd, Pointer data) {
                if (User32.INSTANCE.IsWindowVisible(hWnd)) {
                    char[] title = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, title, 512);
                    if (!Native.toString(title).equals("")) {
                        windowTitles.add(Native.toString(title));
                    }
                }
                return true;
            }
        }, null);

        return windowTitles;
    }
}
