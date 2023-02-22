package tcpProject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 2/21/2023 7:38 PM
 */
public class Server {
    public static void main(String[] args) throws Exception{
        String serverName = "Server of Wei Liang";

        //Listening at port 9999
        ServerSocket serverSocket = new ServerSocket(9900);
        System.out.println(serverName+" waiting for connection...");

        //connected to client, and open socket
        Socket socket = serverSocket.accept();
        System.out.println("connected");

        //read the message from client
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String m = bufferedReader.readLine();
        String[] message = m.split(":");
        String clientName = message[0];
        Integer clientNumber = Integer.parseInt(message[1]);
        System.out.println("Client: "+clientName+", Server"+serverName);

        //server pick a random number and add it up with client number
        Random random = new Random();
        int rand = random.nextInt(100)+1;
        int sum = rand+clientNumber;

        //write data to client
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(serverName+":"+sum);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //close steams and sockets
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();

    }
}
