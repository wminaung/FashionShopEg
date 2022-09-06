
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


	
	static Connection initializeDatabase() throws SQLException {
		Connection con = null;
		
			con = DriverManager.getConnection("jdbc:sqlite:Fshop.db");
		
		if(con != null) {
			System.out.println("Successfully Connected Fshop");
		}
		return con;
	}
	

	
	
	//>>>end class<<<
}
