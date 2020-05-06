

import java.io.*;

public class AlgorithmTest {
    public static void main(String[] args) {
        System.out.println("Test is right !");
        String host = System.getProperty("http.proxyHost") ;
        String port = System.getProperty("http.proxyPort") ;

        Writer writer = null;
        try {
            writer = new FileWriter("E:/Test.txt", true);
            writer.write("host:"+host+"\n");
            writer.write("port:"+port+"\n");

            writer.flush();

            if (writer != null) {
                writer.close();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
