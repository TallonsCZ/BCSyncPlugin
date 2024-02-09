package me.tallonscze.bcsynplugin.database;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private final HikariDataSource hikariDataSource;
    public DatabaseManager(String host, int port, String database, String username, String password) {


        String finalUrl = "jdbc:mariadb://" + host + ":" + port + "/" + database;
        hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(finalUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaxLifetime(1800000);
        hikariDataSource.setConnectionTimeout(5000);
        hikariDataSource.setLeakDetectionThreshold(30000);
    }

    public void setRankToDatabase(Player player, String group){
        String name = player.getName();
        try (Connection connection = hikariDataSource.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT COUNT(*) FROM `linked_accounts` WHERE `minecraft_name` = ?");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            preparedStatement.getResultSet().next();
            int rowCount = preparedStatement.getResultSet().getInt(1);

            if(rowCount == 0){
                PreparedStatement ipreparedStatement = connection
                        .prepareStatement("INSERT INTO `linked_accounts` (`minecraft_name`, `group`) VALUES (?, ?)");
                ipreparedStatement.setString(1, name);
                ipreparedStatement.setString(2, group);
                ipreparedStatement.execute();
            }else {
                PreparedStatement ipreparedStatement = connection
                        .prepareStatement("UPDATE `linked_accounts` SET `group` = ? WHERE `minecraft_name` = ?");
                ipreparedStatement.setString(2, name);
                ipreparedStatement.setString(1, group);
                ipreparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
