/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package scripting;

import client.MapleClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import tools.FileoutputUtil;
import static tools.StringUtil.codeString;

public abstract class AbstractScriptManager {

    private static final ScriptEngineManager sem = new ScriptEngineManager();

    protected Invocable getInvocable(String path, MapleClient c) {
        return getInvocable(path, c, false);
    }

    protected Invocable getInvocable(String path, MapleClient c, boolean npc) {
        InputStream fr = null;
        try {
            path = "scripts/" + path;
            ScriptEngine engine = null;

            if (c != null) {
                engine = c.getScriptEngine(path);
            }
            if (engine == null) {
                File scriptFile = new File(path);
                if (!scriptFile.exists()) {
                    return null;
                }
                
                //engine = sem.getEngineByName("javascript");
                 engine = sem.getEngineByName("nashorn");
                if (c != null) {
                    c.setScriptEngine(path, engine);
                }
                fr = new FileInputStream(scriptFile);
                //BufferedReader bf = new BufferedReader(new InputStreamReader(fr, "UTF-8"));
                BufferedReader bf = new BufferedReader(new InputStreamReader(fr, codeString(path)));
                String lines = "load('nashorn:mozilla_compat.js');";
                lines += bf.lines().collect(Collectors.joining(System.lineSeparator()));
                engine.eval(lines);
            } else if (c != null && npc) {
                c.getPlayer().dropMessage(-1, "You already are talking to this NPC. Use @ea if this is not intended.");
            }
            return (Invocable) engine;
        } catch (FileNotFoundException | UnsupportedEncodingException | ScriptException e) {
            System.err.println("Error executing script. Path: " + path + "\nException " + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing script. Path: " + path + "\nException " + e);
            return null;
        } catch (Exception ex) {
            System.err.println("Error executing script. Path: " + path + "\nException " + ex);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing script. Path: " + path + "\nException " + ex);
            return null;
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
