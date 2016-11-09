import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//import org.omg.CORBA.portable.OutputStream;

public class Main {
	
	private static Socket CLISOCK;
	
	public static void main(String[] args) {
		try{
			ServerSocket SRVSOCK = new ServerSocket(3334);
			System.out.println("Servidor iniciado na porta "+SRVSOCK.getLocalPort());
			
			System.out.println("Esperando conexão...");
			
			CLISOCK = SRVSOCK.accept();
			System.out.println("Cliente conectado do IP "+CLISOCK.getInetAddress().getHostAddress());
			
			//Ler a mensagem recebida pelo cliente
			InputStream is = CLISOCK.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String messageReceived = br.readLine();
			
            System.out.println("Mensagem recebida: "+messageReceived);
            
			String returnMessage;
					
			try
			{
				int number = Integer.parseInt(messageReceived);
				int returnValue = Fibonacci(number);
				returnMessage = String.valueOf(returnValue) + "\n";
            }
			catch(NumberFormatException e)
			{
				//Mensagem enviada pelo cliente é inválida
				returnMessage = "Numero ou expressão inválido!\n";
			}
			 
			 //Enviando resposta ao cliente
			OutputStream os = CLISOCK.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(returnMessage);
			
			System.out.println("Número enviado ao cliente: "+returnMessage);
			bw.flush();			
			
			SRVSOCK.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		finally
        {
            try
            {
                CLISOCK.close();
            }
            catch(Exception e){}
        }
		System.out.println("Servidor finalizado");
		
	}
	
	public static int Fibonacci(int n){
		if(n == 1 || n == 2){
			return 1;
		}else{
			return Fibonacci(n-1) + Fibonacci(n-2);
		}
	}
	
	
	
	
	
}
