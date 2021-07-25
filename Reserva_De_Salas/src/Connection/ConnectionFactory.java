
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    
    public static Connection conectar(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/reservas?useTimezone=true&serverTimezone=UTC","root","0704");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}

