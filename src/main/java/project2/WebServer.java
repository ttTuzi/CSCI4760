package project2;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 3/16/2023 8:21 PM
 */
public final class WebServer {
    public static void main(String[] args) throws Exception {
        //set port number
        int port  = 6789;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("WebServer waiting for connections on port 6789 ...");

        //Process HTTP service requests in an infinite loop
        while(true){
            Socket socket = serverSocket.accept();

            // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(socket);

            //Create a new thread to process the request
            Thread thread  = new Thread(request);

            //start the thread
            thread.start();
        }
    }
}
