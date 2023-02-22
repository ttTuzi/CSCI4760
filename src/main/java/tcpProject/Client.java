package tcpProject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 2/21/2023 7:38 PM
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //let user input number btw 1-100
        System.out.println("enter number btw 1-100");
        int number = scanner.nextInt();
        while(!(number>=1 && number<=100)){
            System.out.println("enter number btw 1-100");
            number = scanner.nextInt();
        }
        System.out.println("you entered number: "+number);

        String clientName = "Client of Wei Liang";

        //connect to local host at port#9999, and open a socket if connected
        Socket socket = new Socket(InetAddress.getLocalHost(), 9900);
        System.out.println("connected");

        //get outPutStream from socket
        OutputStream outputStream = socket.getOutputStream();

        //write data into pipe
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(clientName+":"+number);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //receive message
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String m = bufferedReader.readLine();
        String[] message = m.split(":");
        System.out.println("Server name is :"+message[0]+", number: "+message[1]);

        //close streams and socket
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();



    }
}
