import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Scanner input = new Scanner(System.in);
		ChatClient client = new ChatClient();
		
		System.out.println("Your user id is ?");
		client.setUser(input.next());
		
		System.out.println("type message or type /end to exit");
		System.out.print(client.getUser().getId()+":");
		String words = input.next();
		while (!words.equals("/end")) {
			client.send(words);
			System.out.print(client.getUser().getId()+":");
			words = input.next();
		}
		System.out.println("bye~bye~");
		input.close();
		client.close();
	}

}
