/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import tools.HexTool;
import tools.Pair;
import tools.Randomizer;

/**
 *
 * @author wubin
 */
public class LieDetectorScript {

    private static final String IMG_DIRECTORY = "scripts/liedetector/";
    private static final String CAPTCHA_VERIFIER = "98818D40B83AECCFB7AFD7FD9653E1037519AC61";
    private static final String CAPTCHA_SERVER = "http://localhost/captcha.php?verify=98818D40B83AECCFB7AFD7FD9653E1037519AC61";

    public static Pair<String, String> getImageBytes() {
        try {
            URL url = new URL("http://localhost/captcha.php?verify=98818D40B83AECCFB7AFD7FD9653E1037519AC61");

            InputStream inputStream = url.openStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            String imgByte = HexTool.toString(output.toByteArray());
            return new Pair(imgByte.substring(39, imgByte.length()), output.toString().split("CAPTCHA")[0]);
        } catch (IOException ex) {
            File directory = new File(IMG_DIRECTORY);
            if (!directory.exists()) {
                System.err.println("lieDetector folder does not exist!");
                return null;
            }
            String[] filename = directory.list();
            String answer = filename[Randomizer.nextInt(filename.length)];
            answer = answer.substring(0, answer.length() - 4);
            try {
                return new Pair(HexTool.toString(getBytesFromFile(new File(IMG_DIRECTORY + answer + ".jpg"))), answer);
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        byte[] bytes;
        try {
            InputStream is = new FileInputStream(file);
            long length = file.length();
            if (length > 2147483647) {
                return null;
            }
            bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                System.err.println("[Lie Detector Script] Could not completely read file " + file.getName());
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        return bytes;
    }
}
