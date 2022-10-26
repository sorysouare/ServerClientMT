import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT extends Thread{

    public boolean isActive=true;
    public int numeroClient;

    public static void main(String[] args) {
        new ServerMT().start();
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket= new ServerSocket(123);
            while (isActive) {
                Socket socketClient= serverSocket.accept();
                numeroClient++;
                new Conversation(socketClient, numeroClient).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    class Conversation extends Thread{

        Socket socket;
        int nombreClient;
        public Conversation(Socket socket, int nombreClient){
            this.nombreClient=nombreClient;
            this.socket=socket;
        }
        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr= new InputStreamReader(is);
                BufferedReader bf= new BufferedReader(isr);
                PrintWriter pw= new PrintWriter(socket.getOutputStream(), true);

                String ip=socket.getRemoteSocketAddress().toString();
                pw.println("Bienvenue Vous etes le client "+numeroClient);
                System.out.println("Connexion du client numéro "+numeroClient+" IP= "+ip);

                while (isActive) {
                    String re= bf.readLine();
                    String reponse= "Length = "+re.length();
                    pw.println(reponse);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

