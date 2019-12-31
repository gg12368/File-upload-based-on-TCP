图片下载到相应路径的文件夹中，但没有打印出上传成功。客户端和服务器端一直在运行，没有停止。
客户端和服务器端都打印出了打印1和打印2。
解决方法：上传文件，给客户端写一个结束标记。
改进客户端代码;

import java.io.*;
import java.net.Socket;

/*
文件上传案例的客户端：读取本地文件，上传到服务器，读取服务器写回的数据

明确：
    数据源：E:\\1.jpg
    目的地：服务器

实现步骤：
    1.创建一个本地字节输入流FileInputStream，构造方法中绑定读取的数据源
    2.创建一个客户端Socket对象，构造方法中绑定服务器端的IP地址和端口号
    3.使用Socket中的getOutputStream，获取网络字节输出流对象OutputStream对象
    4.使用本地字节输入流FileInputStream对象中的方法read读取本地的文件
    5.使用网络字节输出流OutputStream对象中的方法write，把读取到的文件上传到服务器
    6.使用Socket中的方法getInputStream，获取网络字节输入流InputStream对象
    7.使用网络字节输入流InputStream对象中的方法read读取服务器回写的数据
    8.释放资源FileInputStream，Socket
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        //1.创建一个本地字节输入流FileInputStream，构造方法中绑定读取的数据源
        FileInputStream fis=new FileInputStream("E:\\1.jpg");
        // 2.创建一个客户端Socket对象，构造方法中绑定服务器端的IP地址和端口号
        Socket socket=new Socket("127.0.0.1",8888);
        //3.使用Socket中的getOutputStream，获取网络字节输出流对象OutputStream对象
        OutputStream os=socket.getOutputStream();
        // 4.使用本地字节输入流FileInputStream对象中的方法read读取本地的文件
        int len=0;
        byte[] bytes=new byte[1024];
        while((len=fis.read(bytes))!=-1){
            //5.使用网络字节输出流OutputStream对象中的方法write，把读取到的文件上传到服务器
            os.write(bytes,0,len);
        }
        /*
        解决：
        上传完文件，给服务器写一个结束标记
        void shutdownOutput禁用此套接字的输出流
        对于TCP套接字，任何以前写入的数据都将被发送，并且后跟TCP的正常连接终止序列。
         */
        socket.shutdownOutput();
        //6.使用Socket中的方法getInputStream，获取网络字节输入流InputStream对象
        InputStream is=socket.getInputStream();
        System.out.println("打印1");
        //7.使用网络字节输入流InputStream对象中的方法read读取服务器回写的数据
        while((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        System.out.println("不会打印到1");
        //8.释放资源FileInputStream，Socket
        fis.close();
        socket.close();
    }
}

