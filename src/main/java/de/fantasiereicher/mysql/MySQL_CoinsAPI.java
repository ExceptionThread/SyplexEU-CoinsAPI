package de.fantasiereicher.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.bukkit.entity.Player;

public class MySQL_CoinsAPI {
    private de.fantasiereicher.mysql.MySQL mysql;

    public static void createTable() {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("CREATE TABLE IF NOT EXISTS Coins (Name VARCHAR(100), UUID VARCHAR(100), Coins INT(100))");
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void register(Player p) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("INSERT INTO Coins (Name, UUID, Coins) VALUES (?, ?, ?)");
            ps.setString(1, p.getName());
            ps.setString(2, p.getUniqueId().toString());
            ps.setInt(3, 0);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isRegistered(Player p) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("SELECT * FROM Coins WHERE UUID= ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            rs.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean isRegistered(String name) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("SELECT * FROM Coins WHERE Name= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            rs.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static int getCoins(Player p) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("SELECT * FROM Coins WHERE UUID= ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int coins = rs.getInt("Coins");
            rs.close();
            ps.close();
            return coins;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static int getCoinsUUID(String uuid) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("SELECT * FROM Coins WHERE UUID= ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int coins = rs.getInt("Coins");
            rs.close();
            ps.close();
            return coins;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static void setCoins(Player p, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
            ps.setInt(1, coins);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addCoins(Player p, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
            ps.setInt(1, getCoins(p) + coins);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCoins(Player p, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE UUID= ?");
            ps.setInt(1, getCoins(p) - coins);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getCoins(String name) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("SELECT * FROM Coins WHERE Name= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int coins = rs.getInt("Coins");
            rs.close();
            ps.close();
            return coins;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static void setCoins(String name, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Name= ?");
            ps.setInt(1, coins);
            ps.setString(2, name);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addCoins(String name, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Name= ?");
            ps.setInt(1, getCoins(name) + coins);
            ps.setString(2, name);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCoins(String name, int coins) {
        try {
            PreparedStatement ps = de.fantasiereicher.mysql.MySQL.getStatement("UPDATE Coins SET Coins= ? WHERE Name= ?");
            ps.setInt(1, getCoins(name) - coins);
            ps.setString(2, name);
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
