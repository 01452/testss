package com.example.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);
		int port = 9000;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (true) {
				System.out.println("Server is waiting...");
				Socket socket = serverSocket.accept();
				System.out.println("Connection established");
				System.out.println("Client host: " + socket.getInetAddress() + ":" + socket.getPort());
				ClientHandler task = new ClientHandler(socket);
				executorService.execute(task);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		}
	}

}
