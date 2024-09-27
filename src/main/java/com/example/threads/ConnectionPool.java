package com.example.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ConnectionPool {
    private static ConnectionPool connectionPoolInstance;
    private final BlockingQueue<Connection> availableConnections;
    private final int poolSize = 5;
    private final AtomicInteger connectionIdGenerator = new AtomicInteger(1);

    private ConnectionPool() {
        this.availableConnections = new ArrayBlockingQueue<>(poolSize);
        IntStream.range(0, poolSize)
                .forEach(i -> availableConnections.add(new Connection("Connection-" + connectionIdGenerator.getAndIncrement())));
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPoolInstance == null) {
            connectionPoolInstance = new ConnectionPool();
        }
        return connectionPoolInstance;
    }

    public CompletableFuture<Connection> acquireConnection() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return availableConnections.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        });
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to release connection: " + e.getMessage());
            }
        }
    }
}
