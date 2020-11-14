import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TunaTuncerHW1
{
    private static final String URL = "jdbc:mysql://localhost:3306/FoodHorse?useSSL=false";
    private static final String username = System.getenv("UNAME");
    private static final String password = System.getenv("PWORD");
    private static Connection connection = null;

    public static void establishConnection()
    {
        try{
            connection = DriverManager.getConnection(URL, username, password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnection()
    {
        try{
            if(connection != null)
                connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        establishConnection();
        // Whole code will be written there
        closeConnection();
    }
}
