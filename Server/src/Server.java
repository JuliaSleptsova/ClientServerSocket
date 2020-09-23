import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8000);
        System.out.println("Server started");

        Socket socket = server.accept();
        System.out.println("Client connected");

        OutputStream stream = socket.getOutputStream();
        OutputStreamWriter writerStream = new OutputStreamWriter(stream);
        BufferedWriter writer = new BufferedWriter(writerStream);

        writer.write("HELLO FROM SERVER");
        writer.newLine();
        writer.flush();

        writer.close();
        socket.close();
        server.close();
    }
}
