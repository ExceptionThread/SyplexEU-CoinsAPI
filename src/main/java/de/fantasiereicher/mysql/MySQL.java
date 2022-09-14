package de.fantasiereicher.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MySQL {
    private static Connection con;

    public String host;

    public String port;

    public String database;

    public String username;

    public String password;

    private final String PREFIX = "[MySQL] ";

    public MySQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        if (!isConnected())
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useSSL=false&characterEncoding=utf8&useUnicode=true&interactiveClient=true", this.username, this.password);
                System.out.println("[MySQL] Connected to database successfully!");
            } catch (SQLException e) {
                System.out.println("[MySQL] Connection to database failed (" + e.getMessage() + ")!");
                System.out.println();
                System.out.println("[MySQL] Connection Properties:");
                System.out.println("[MySQL]  Address: " + this.host + ":" + this.port);
                System.out.println("[MySQL]  Database: " + this.database);
                System.out.println("[MySQL]  Username: " + this.username);
                String censoredPassword = "";
                for (int i = 0; i < this.password.length(); i++)
                    censoredPassword = censoredPassword + "*";
                System.out.println("[MySQL]  Password: " + censoredPassword);
            }
    }

    public void disconnect() {
        if (isConnected())
            try {
                con.close();
                System.out.println("[MySQL] Connection to database closed successfully!");
            } catch (SQLException sQLException) {}
    }

    public static boolean isConnected() {
        try {
            return (con != null && !con.isClosed());
        } catch (SQLException sQLException) {
            return false;
        }
    }

    public void update(final String sql) {
        checkConnection();
        if (isConnected())
            (new FutureTask(new Runnable() {
                public void run() {
                    try {
                        Statement s = MySQL.con.createStatement();
                        s.executeUpdate(sql);
                        s.close();
                    } catch (SQLException ex) {
                        String msg = ex.getMessage();
                        System.out.println("[MySQL] An error occured while executing mysql update (" + msg + ")!");
                        if (msg.contains("The driver has not received any packets from the server.")) {
                            MySQL.con = null;
                            MySQL.this.connect();
                        }
                    }
                }
            },  Integer.valueOf(1))).run();
    }

    public ResultSet getResult(final String qry) {
        checkConnection();
        if (isConnected())
            try {
                FutureTask<ResultSet> task = new FutureTask<>(new Callable<ResultSet>() {
                    public ResultSet call() {
                        try {
                            Statement s = MySQL.con.createStatement();
                            ResultSet rs = s.executeQuery(qry);
                            return rs;
                        } catch (SQLException ex) {
                            String msg = ex.getMessage();
                            System.out.println("[MySQL] An error occured while executing mysql query (" + msg + ")!");
                            if (msg.contains("The driver has not received any packets from the server.")) {
                                MySQL.con = null;
                                MySQL.this.connect();
                            }
                            return null;
                        }
                    }
                });
                task.run();
                return task.get();
            } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
        return null;
    }

    private void checkConnection() {
        if (!isConnected())
            connect();
    }

    public Connection getConnection() {
        return con;
    }

    public static PreparedStatement getStatement(String sql) {
        if (isConnected())
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                return ps;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
}
