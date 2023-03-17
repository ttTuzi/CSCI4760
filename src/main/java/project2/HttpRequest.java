package project2;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 3/16/2023 8:22 PM
 */
final class HttpRequest implements Runnable {
   final static String CRLF = "/r/n";
   Socket socket;

    public HttpRequest() {
    }

    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processRequest() throws Exception{
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        //Http request message
        String requestLine = br.readLine();

        //Display the request line
        String headerLine = null;
        while((headerLine= br.readLine()).length()!=0){
            System.out.println(headerLine);
        }

        // Extract the filename from the request line.
        StringTokenizer tokens  = new StringTokenizer(requestLine);
        String fileName = tokens.nextToken();
        fileName = "."+fileName;

        // Open the requested file.
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }

        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-type: text/html" + CRLF;
            entityBody = "<HTML>" +
                    "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                    "<BODY>Not Found</BODY></HTML>";
        }

        // Send the status line.
        os.writeBytes(statusLine);
        // Send the content type line.
        os.writeBytes(contentTypeLine);
        // Send a blank line to indicate the end of the header lines.
        os.writeBytes(CRLF);

        if (fileExists) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(entityBody);
        }
        //close
        br.close();
        os.close();
        socket.close();

    }
    private static String contentType(String fileName) {
        if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        } else if(fileName.endsWith(".gif")) {
            return "image/gif";
        } else if(fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
            return "image/jpeg";
        } else {
            return "application/octet-stream";
        }
    }

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        // Construct a 1K buffer to hold bytes on their way to the socket.
        byte[] buffer = new byte[1024];
        int bytes = 0;
        // Copy requested file into the socket's output stream.
        while((bytes = fis.read(buffer)) != -1 ) {
            os.write(buffer, 0, bytes);
        }
    }
}
