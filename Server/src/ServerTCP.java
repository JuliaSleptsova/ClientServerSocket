import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread {
    ServerSocket serverSocket;

    public ServerTCP() {
        try {
            serverSocket = new ServerSocket(8000);
            System.out.println(serverSocket.toString());
        } catch (IOException e) {
            fail(e, "Cloud not start server.");
        }
        System.out.println("Server is running...");
        this.start();
    }

    public static void fail(Exception e, String str) {
        System.out.println(str + "." + e);
    }

    public void run() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                Connection con = new Connection(client);
            }
        } catch (IOException e) {
            fail(e, "Not listening");
        }
    }

    public static void main(String[] args) {
        new ServerTCP();
    }
}

class Connection extends Thread {
    protected Socket netClient;
    protected BufferedReader fromClient;
    protected PrintStream toClient;

    public Connection(Socket client) {
        netClient = client;
        try {
            fromClient = new BufferedReader(new InputStreamReader(netClient.getInputStream()));
            toClient = new PrintStream(netClient.getOutputStream());
        } catch (IOException e) {
            try {
                netClient.close();
            } catch (IOException e1) {
                System.err.println("Unable to set up streams " + e1);
                return;
            }
        }
        this.start();
    }

    public void run() {
        String login, password;
        try {
            while (true) {
                toClient.println("Login: ");
                login = fromClient.readLine();
                if (login == null || login.equals("Bye")) {
                    System.out.println("Exit");
                    return;
                } else
                    System.out.println(login + " logged in");

                toClient.println("Password: ");
                password = fromClient.readLine();
            }
        } catch (IOException e) {

        } finally {
            try {
                netClient.close();
            } catch (IOException e) {

            }
        }
    }
}
