package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ClientHandler implements Runnable {
	private Socket socket;

	public ClientHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Socket socket = this.socket;) {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter socketWriter = new PrintWriter(outputStream);
			BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));
			while (true) {
				String message = socketReader.readLine();
				if (message == null) {
					break;
				}
				System.out.println("Server received: " + message + ", time : " + LocalTime.now());
				socketWriter.println("Your message received:" + message + " : " + LocalDateTime.now());
				socketWriter.flush();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
