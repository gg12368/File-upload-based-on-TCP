1.2文件上传案例的服务器端：
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
文件上传案例的服务器端：读取客户端上传的文件，保存到服务器端的硬盘上，给客户端回写上传成功

明确：
    数据源：客户端上传的文件
    目的地：服务器的硬盘 E:\\upload\\1.jpg

实现步骤：
    1.创建一个服务器ServerSocket，和系统要指定的端口号
    2.使用ServerSocket对象中的方法accept，获取请求的客户端Socket对象
    3.使用Socket对象中的方法getInputStream，获取到网络字节输入流InputStream对象
    4.判断E:\\upload文件夹是否存在，不存在则创建
    5.创建一个本地字节输出流FileOutputStream对象，构造方法中指定要输出的目的地
    6.使用网络字节输出流InputStream对象中的方法read，读取客户端上传的文件
    7.使用本地字节输出流FileOutputStream对象中的方法write，把读取到的文件保存到服务器的硬盘上
    8.使用Socket对象中的方法getOutputStream，获取到网络字节输出流OutputStream对象
    9.使用网络字节输出流OutputStream对象中的方法read给客户端回写上传成功
    10.释放资源，FileOutputStream，Socket，ServerSocket
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //1.创建一个服务器ServerSocket，和系统要指定的端口号
        ServerSocket serverSocket=new ServerSocket(8888);
        //2.使用ServerSocket对象中的方法accept，获取请求的客户端Socket对象
        Socket socket=serverSocket.accept();
        // 3.使用Socket对象中的方法getInputStream，获取到网络字节输入流InputStream对象
        InputStream is=socket.getInputStream();
        File file=new File("E:\\upload");
        //4.判断E:\\upload文件夹是否存在，不存在则创建
        if(!file.exists()){
            file.mkdir();
        }
        //5.创建一个本地字节输出流FileOutputStream对象，构造方法中指定要输出的目的地
        FileOutputStream fos=new FileOutputStream(file+"\\1.jpg");
        //6.使用网络字节输出流InputStream对象中的方法read，读取客户端上传的文件
        int len=0;
        byte[] bytes=new byte[1024];
        while((len=is.read(bytes))!=-1){
            //7.使用本地字节输出流FileOutputStream对象中的方法write，把读取到的文件保存到服务器的硬盘上
            fos.write(bytes,0,len);
        }
        // 8.使用Socket对象中的方法getOutputStream，获取到网络字节输出流OutputStream对象
        // 9.使用网络字节输出流OutputStream对象中的方法read给客户端回写上传成功
        socket.getOutputStream().write("上传成功".getBytes());
        //10.释放资源，FileOutputStream，Socket，ServerSocket
        fos.close();
        socket.close();
        serverSocket.close();
    }
}

运行现象：
图片下载到相应路径的文件夹中，但没有打印出上传成功。客户端和服务器端一直在运行，没有停止。
