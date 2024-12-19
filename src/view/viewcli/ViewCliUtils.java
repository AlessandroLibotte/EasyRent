package view.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ViewCliUtils {

    public static int dynamicMenu(List<String> list) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i = 1;
        for (String s: list) {
            Printer.printMsgln("\t" + i + ") " + s);
            i++;
        }
        Printer.printMsgln("\t"+ i+ ") Back");

        Printer.printMsg(": ");
        return Integer.parseInt(br.readLine());

    }


}
