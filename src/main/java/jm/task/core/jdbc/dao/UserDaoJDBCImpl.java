package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String table = "CREATE TABLE users (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    name VARCHAR(50) NOT NULL," +
                "    lastName VARCHAR(50) NOT NULL," +
                "    age TINYINT NOT NULL" +
                ");";

        try (Connection connection = Util.getConection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(table);
            System.out.println("Таблица была создана!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        String drop = "drop table users";
        try (Connection connection = Util.getConection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(drop);
            System.out.println("Таблица была удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastName, age) values(?, ?, ?)";

        try(Connection connection = Util.getConection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error with SaveUser");
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";

        try (Connection connection = Util.getConection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int personId = preparedStatement.executeUpdate();

            if (personId > 0) {
                System.out.println("Пользователь удален");
            } else {
                System.out.println("Пользователь с таким id не найден");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя");
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String command = "select * from users";
        try (Connection connection = Util.getConection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось вывести всех пользователей");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String clean = "delete from users";
        try (Connection connection = Util.getConection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(clean);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
