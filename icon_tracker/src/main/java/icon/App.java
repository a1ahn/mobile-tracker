package icon;

import java.io.IOException;

public class App {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		GetLastBlock LastBlock = new GetLastBlock();
	    LastBlock.getLastBlock();
	}
}
