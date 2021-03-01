package com.example.current;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Demo.java
 * @Description TODO
 * @createTime 2021年03月01日 10:26:00
 */
public class Demo {
    //视频文件路径
    private static String videoPath = "";
    /**
     *
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     * @param videoFileName
     */
    public static void grabberVideoFramer(String videoFileName) {
        //Frame对象
        Frame frame = null;
        //标识,取第几帧图片作为封面
        int flag = 0 , index = 20;
        //获取视频文件
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath+videoFileName);
        try {
            fFmpegFrameGrabber.start();
            //getFrameRate()方法：获取视频文件信息,总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            while (flag <= ftp) {
                //获取帧
                frame = fFmpegFrameGrabber.grabImage();
                if (frame != null && index==flag) {
                    //文件绝对路径+名字
//                    String fileName = videoFramesPath ;
                    String fileName = videoPath+videoFileName.replace("mp4","jpg") ;
                    //文件储存对象
                    File outPut = new File(fileName);
                    if(!outPut.exists()) outPut.mkdirs() ;
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
                }
                flag++;
                try {
                    TimeUnit.SECONDS.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("============运行结束============");
            fFmpegFrameGrabber.stop();
        } catch (IOException E) {
            E.printStackTrace();

        }
    }
    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
    /**
     * 从网络http类型Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrlHttp(String urlStr, String fileName,
                                           String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.connect();

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        byte[] getData = readInputStream(inputStream);
        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        // 输出流
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream)
            throws IOException {
        byte[] b = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    /*
        测试.....
     */
    public static void main(String[] args) {

//        try {
//            String path = "http://172.18.194.157/中医问诊系统数据/视频/腰背痛/94 肾俞.mp4" ;
//            String fileName = path.substring(path.lastIndexOf("/")+1) ;
//            String nextPath = path.substring(path.indexOf("157/")+3,path.lastIndexOf("/")+1) ;
//            videoPath = "D:"+nextPath ;
//            downLoadFromUrlHttp(path,fileName,videoPath);
//            grabberVideoFramer(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(new File("D:/中医问诊系统数据/视频/腰背痛/94 肾俞.mp4").delete());

    }

}
