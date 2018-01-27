
import java.sql.*;
import java.util.ArrayList;

public class Db {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USER = "abc";
    final String PASSWORD = "abc";

    Connection connection = null;
    Statement statement = null;
    DatabaseMetaData databaseMetaData = null;

    public Db() {

        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Sikeres kapcsolódás");
        } catch (SQLException e) {
            System.out.println("Sikertelen kapcsolódás");
            e.printStackTrace();
        }

        if(connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("Gond van a statementel!");
                e.printStackTrace();
            }
        }

        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Gond van a metaData-val!");
            e.printStackTrace();
        }

        try {
            ResultSet resultSet = databaseMetaData.getTables(null,"APP","USERS",null);
            if (!resultSet.next()) {
                statement.execute("CREATE TABLE users(name varchar(20), address varchar (20))");
            }
        } catch (SQLException e) {
            System.out.println("Gond van a tábla létrehozással!");
            e.printStackTrace();
        }
    }

    public void addUser(String name, String address) {
        try {
           // String sql = "insert into users (name,address) VALUES('"+name+"','"+address +"')";
            //statement.execute(sql);
            String sql = "insert into users (name,address) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Gond van az addUser metódussal!");
            e.printStackTrace();
        }
    }

    public void showAllUsers() {
        String sql = "select * from users";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println(name + " : " + address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTable(String accept) {
        if (accept.toLowerCase() == "yes") {
            String sql = "delete from users";
            try {
                statement.execute(sql);
                System.out.println("Sikeres törlés!");
            } catch (SQLException e) {
                System.out.println("Sikertelen törlés!");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Kérem erősítse meg a törlést!");
        }
    }

    public void showUsersMeta() {
        String sql = "select * from users";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            rs = statement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i<=columnCount;i++) {
                System.out.println(" | " + rsmd.getColumnName(i) +" | ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {

        String sql = "select * from users";
        ResultSet rs = null;
        ArrayList<User> users = null;
        try {
            rs = statement.executeQuery(sql);
            users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User(rs.getString("name"),rs.getString("address"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void addUser(User user) {

        try {
            String sql = "insert into users (name,address) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getAddress());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Gond van az addUser metódussal!");
            e.printStackTrace();
        }

    }

}
