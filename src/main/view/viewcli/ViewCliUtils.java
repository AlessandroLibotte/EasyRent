package main.view.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ViewCliUtils {

    private ViewCliUtils() {}

    public static void printMsgln(String msg){ System.out.println(msg);}
    public static void printMsg(String msg){ System.out.print(msg);}

    public static int dynamicMenu(List<String> list) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i = 1;
        for (String s: list) {
            printMsgln("\t" + i + ") " + s);
            i++;
        }
        printMsgln("\t"+ i+ ") Back");

        printMsg(": ");

        try{
            return Integer.parseInt(br.readLine());
        } catch(IOException e){
            ViewCliUtils.printMsgln("Errore durante l' operazione");
            return -1;
        }

    }

}
