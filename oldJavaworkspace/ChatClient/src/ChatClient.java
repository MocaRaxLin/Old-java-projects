import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private Socket client;
	private final int SERVER_PORT = 8765;
	private DataInputStream input;
	private DataOutputStream output;
	private User user;
	public ChatClient() throws UnknownHostException, IOException {
		//connect
		client = new Socket("127.0.0.1", SERVER_PORT);
		System.out.println("get server:" + client.getInetAddress());
		input = new DataInputStream(client.getInputStream());
		output = new DataOutputStream(client.getOutputStream());		
		System.out.println("Welcome to the server!");
	}

	public void setUser(String name){
		user = new User(name);
	}
	
	public User getUser(){
		return user;
	}
	public void send(String s) throws IOException {
		output.writeUTF(user.getId()+":"+s);
		output.flush();
	}

	public void close() throws IOException {
		output.close();
		input.close();
		client.close();
	}
}
