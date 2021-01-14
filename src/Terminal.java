import java.io.IOException;

public class Terminal {

    //运行可执行程序
    public static void terminal_run(String command) throws IOException, InterruptedException {
        Runtime rn = Runtime.getRuntime();
        Process p = null;
        p = rn.exec(new String[]{"/bin/sh","-c",command});
        p.waitFor();
    };

}
