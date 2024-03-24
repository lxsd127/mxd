/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import java.io.FileReader;
import java.io.IOException;;
import java.util.Properties;

public class ServerProperties {
    private static final Properties props = new Properties();

    private ServerProperties() {
    }

    static {
        String toLoad = "world.properties";
        loadProperties(toLoad);
    }

    public static void loadProperties(String s) {
        FileReader fr;
        try {
            fr = new FileReader(s);
            props.load(fr);
            fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }
}
