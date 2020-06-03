package com.hackerrank.stocktrade;

import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.model.User;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TradesRepository {
    private List<Trade> store = new ArrayList<>();

    public TradesRepository() {
        initialize();
    }

    public void initialize() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE trades (id INT NOT NULL, type VARCHAR(50) NOT NULL, symbol VARCHAR(50) NOT NULL,"
                    + "shares INT NOT NULL, price DECIMAL(10,2) NOT NULL, timestamp DATETIME NOT NULL,"
                    + "userid VARCHAR(50) NOT NULL, username VARCHAR(50) NOT NULL, PRIMARY KEY (id))");
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void cleanup() throws SQLException, ClassNotFoundException, IOException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE trades");
            connection.commit();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:mem:trades",
                "sa",
                "sa");
    }

    public boolean deleteAll() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sql = String.format("DELETE FROM trades");
            statement.executeUpdate(sql);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(Trade trade) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sql = String.format("INSERT INTO trades (id, type, symbol, shares, price, timestamp, userid, username) "
                    + "VALUES (%1$s,'%2$s', '%3$s', %4$s, %5$s, '%6$s', %7$s, '%8$s')",
                    trade.getId(), trade.getType(), trade.getSymbol(), trade.getShares(), trade.getPrice(), trade.getTimestamp(), trade.getUser().getId(), trade.getUser().getName());
            statement.executeUpdate(sql);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Trade getById(long tradeId) {
        Trade trade = null;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM trades where id = '" + tradeId + "'");
            if (result.next()) {
                trade = new Trade();
                trade.setId(result.getLong("id"));
                trade.setType(result.getString("type"));
                trade.setSymbol(result.getString("symbol"));
                trade.setShares(result.getInt("shares"));
                trade.setPrice(result.getFloat("price"));
                trade.setTimestamp(result.getTimestamp("timestamp"));
                trade.setUser(new User(result.getLong("userid"), result.getString("username")));
            }
            return trade;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trade;
    }

    public List<Trade> all() {
        List<Trade> trades = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM trades");
            while (result.next()) {
                Trade trade = new Trade();
                trade.setId(result.getLong("id"));
                trade.setType(result.getString("type"));
                trade.setSymbol(result.getString("symbol"));
                trade.setShares(result.getInt("shares"));
                trade.setPrice(result.getFloat("price"));
                trade.setTimestamp(result.getTimestamp("timestamp"));
                trade.setUser(new User(result.getLong("userid"), result.getString("username")));
                trades.add(trade);
            }
            return trades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }

    public List<Trade> getTradesByUserId(long userID) {
        List<Trade> trades = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM trades where userid = '" + userID + "'");
            while (result.next()) {
                Trade trade = new Trade();
                trade.setId(result.getLong("id"));
                trade.setType(result.getString("type"));
                trade.setSymbol(result.getString("symbol"));
                trade.setShares(result.getInt("shares"));
                trade.setPrice(result.getFloat("price"));
                trade.setTimestamp(result.getTimestamp("timestamp"));
                trade.setUser(new User(result.getLong("userid"), result.getString("username")));
                trades.add(trade);
            }
            return trades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }

    public List<Trade> getTradesBySymbol(String stockSymbol) {
        List<Trade> trades = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM trades where symbol = '" + stockSymbol + "'");
            while (result.next()) {
                Trade trade = new Trade();
                trade.setId(result.getLong("id"));
                trade.setType(result.getString("type"));
                trade.setSymbol(result.getString("symbol"));
                trade.setShares(result.getInt("shares"));
                trade.setPrice(result.getFloat("price"));
                trade.setTimestamp(result.getTimestamp("timestamp"));
                trade.setUser(new User(result.getLong("userid"), result.getString("username")));
                trades.add(trade);
            }
            return trades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }

    public List<Trade> getTradesByUserIdAndSymbol(Long userID, String stockSymbol) {
        List<Trade> trades = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM trades where symbol = '" + stockSymbol + "' AND userid = '" + userID + "'");
            while (result.next()) {
                Trade trade = new Trade();
                trade.setId(result.getLong("id"));
                trade.setType(result.getString("type"));
                trade.setSymbol(result.getString("symbol"));
                trade.setShares(result.getInt("shares"));
                trade.setPrice(result.getFloat("price"));
                trade.setTimestamp(result.getTimestamp("timestamp"));
                trade.setUser(new User(result.getLong("userid"), result.getString("username")));
                trades.add(trade);
            }
            return trades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }
}
