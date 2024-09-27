package com.example.threads;

public class Connection {
    private final String id;

    public Connection(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void connect(){
        System.out.println("Connection to " + this.id);
    }

    public void disconnect(){
        System.out.println("Disconnection to " + this.id);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id='" + id + '\'' +
                '}';
    }
}
