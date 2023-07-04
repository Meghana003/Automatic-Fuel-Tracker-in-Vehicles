//package vehicledb;
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DBAccess{
	String uid;
	static Connection con;
	Statement stmt;
	ResultSet rs,rsUpdate;
	Savepoint savePoint,savePoint1;
	int flag;
	public DBAccess(){
		
		try{
			//String classpath = "C:\\path\\to\\ojdbc8.jar";
            //System.setProperty("java.class.path", classpath);
			Class.forName("oracle.jdbc.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","phani","phani");
			con.setAutoCommit(false);
			stmt=con.createStatement();
			savePoint = con.setSavepoint("lastSave");
		}
		catch(ClassNotFoundException ex){
			System.out.println(ex);
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
	}
	
	//TO CHECK IF USER EXISTS
	public boolean checkUserExistence(String userId) {
        try {
            String query = "SELECT * FROM Users WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking user existence: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
	}
	// Function to check if a vehicle exists in the Vehicle table
	public boolean checkVehicleExistence(String vehicleId) {
		try {
			String query = "SELECT COUNT(*) AS count FROM vehicle WHERE vehicle_id = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, vehicleId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			pstmt.close();
			return count > 0;
		} 
		catch (SQLException e) {
			System.out.println("Error checking vehicle existence: " + e.getMessage());
			return false;
		}
	}

	
	// INSERT INTO USERS TABLE
   public void insertData(String user_name, String user_id, String vehicle_id, String city) {
        try {
            String query = "INSERT INTO users (user_id,vehicle_id,user_name,city) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, user_id);
            pstmt.setString(2, vehicle_id);
            pstmt.setString(3, user_name);
            pstmt.setString(4, city);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Data inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }
	
	//DELETE FROM USERS TABLE
	public void deleteData(String user_id) {
        try {
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, user_id);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            
            if (rowsAffected > 0) {
                System.out.println("Data deleted successfully!");
            } else {
                System.out.println("No data found with the given user ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
    }
	
	//UPDATE IN USERS TABLE
	public void updateData(String user_name, String user_id, String vehicle_id, String city) {
        try {
            String query = "UPDATE users SET vehicle_id = ?, user_name = ?, city = ? WHERE user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, vehicle_id);
            pstmt.setString(2, user_name);
            pstmt.setString(3, city);
            pstmt.setString(4, user_id);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                System.out.println("Data updated successfully!");
            } else {
                System.out.println("No data found with the given user ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }
	
	//VIEW USERS TABLE
	public void viewData() {
    try {
        String query = "SELECT user_name, vehicle_id, city FROM users"; 
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        // Create a table model to hold the data
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("User Name");
        tableModel.addColumn("Vehicle ID");
        tableModel.addColumn("City");

        while (rs.next()) {
            String userName = rs.getString("user_name");
            String vehicleId = rs.getString("vehicle_id");
            String city = rs.getString("city");

            tableModel.addRow(new Object[] { userName, vehicleId, city });
        }

        rs.close();
        pstmt.close();

        
        JTable dataTable = new JTable(tableModel);

        
        JScrollPane scrollPane = new JScrollPane(dataTable);

        
        JFrame frame = new JFrame("View Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    } 
	catch (SQLException e)
	{
        System.out.println("Error viewing data: " + e.getMessage());
    }
	}
	
	// INSERT INTO VEHICLES TABLE
	public void insertVehicleData(String userId, String vehicleId, String year, String brand, String model) {
        try {
            String query = "INSERT INTO Vehicle (user_id, vehicle_id, year, brand, model) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, vehicleId);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, brand);
            preparedStatement.setString(5, model);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehicle data inserted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting vehicle data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE VEHICLES TABLE
    public void updateVehicleData(String userId, String vehicleId, String year, String brand, String model) {
        try {
            String query = "UPDATE Vehicle SET year = ?, brand = ?, model = ? WHERE user_id = ? AND vehicle_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, brand);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, userId);
            preparedStatement.setString(5, vehicleId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vehicle data updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No records found for the specified user ID and vehicle ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating vehicle data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // DELETE FROM VEHICLES TABLE
    public void deleteVehicleData(String userId, String vehicleId) {
        try {
            String query = "DELETE FROM Vehicle WHERE user_id = ? AND vehicle_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, vehicleId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vehicle data deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No records found for the specified user ID and vehicle ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting vehicle data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewVehicleData() {
    try {
        String query = "SELECT * from vehicle"; 
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        // Create a table model to hold the data
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("User ID");
        tableModel.addColumn("Vehicle ID");
        tableModel.addColumn("Year");
		tableModel.addColumn("Brand");
		tableModel.addColumn("Model");
		

        while (rs.next()) {
            String userID = rs.getString("user_id");
            String vehicleId = rs.getString("vehicle_id");
            int year = rs.getInt("year");
			String brand = rs.getString("brand");
			String model = rs.getString("model");

            tableModel.addRow(new Object[] { userID, vehicleId, year, brand, model });
        }

        rs.close();
        pstmt.close();

        // Create a JTable with the table model
        JTable dataTable = new JTable(tableModel);

        // Create a scroll pane to contain the table
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Create a new window to display the table
        JFrame frame = new JFrame("View Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    } 
	catch (SQLException e)
	{
        System.out.println("Error viewing data: " + e.getMessage());
    }
	}
	// INSERT INTO FUEL_TYPE TABLE
	public void insertFuelTypeData(String vehicleId, String fuellingId, String name, String pricePerLiter) {
    try {
        // Check if the vehicle ID exists in the Vehicle table
        if (!checkVehicleExistence(vehicleId)) {
            System.out.println("Vehicle ID does not exist. Cannot add Fuel_type data.");
            return;
        }
        
        String query = "INSERT INTO fuel_type (vehicle_id, fuelling_id, name, price_per_liter) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, vehicleId);
        pstmt.setString(2, fuellingId);
        pstmt.setString(3, name);
        pstmt.setString(4, pricePerLiter);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Data inserted successfully into Fuel_type table.");
    } catch (SQLException e) {
        System.out.println("Error inserting data into Fuel_type table: " + e.getMessage());
    }
}
    
    // Function to delete data from the Fuel_type table
    public void deleteFuelTypeData(String vehicleId, String fuellingId) {
        try {
            String query = "DELETE FROM fuel_type WHERE vehicle_id = ? AND fuelling_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, vehicleId);
            pstmt.setString(2, fuellingId);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            System.out.println(rowsAffected + " row(s) deleted from Fuel_type table.");
        } catch (SQLException e) {
            System.out.println("Error deleting data from Fuel_type table: " + e.getMessage());
        }
    }
    
    // Function to update data in the Fuel_type table
    public void updateFuelTypeData(String vehicleId, String fuellingId, String name, String pricePerLiter) {
        try {
            String query = "UPDATE fuel_type SET name = ?, price_per_liter = ? WHERE vehicle_id = ? AND fuelling_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, pricePerLiter);
            pstmt.setString(3, vehicleId);
            pstmt.setString(4, fuellingId);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            System.out.println(rowsAffected + " row(s) updated in Fuel_type table.");
        } catch (SQLException e) {
            System.out.println("Error updating data in Fuel_type table: " + e.getMessage());
        }
    }
    
    // Function to view data from the Fuel_type table
    public void viewFuelTypeData() {
        try {
            String query = "SELECT * FROM fuel_type";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Vehicle ID");
            tableModel.addColumn("Fuelling ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Price per Liter");

            while (rs.next()) {
                String vehicleId = rs.getString("vehicle_id");
                String fuellingId = rs.getString("fuelling_id");
                String name = rs.getString("name");
                String pricePerLiter = rs.getString("price_per_liter");
                tableModel.addRow(new Object[]{vehicleId, fuellingId, name, pricePerLiter});
            }

            rs.close();
            pstmt.close();

            JTable dataTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(dataTable);

            JFrame frame = new JFrame("View Fuel Type Data");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println("Error viewing Fuel_type data: " + e.getMessage());
        }
    }
    
    // Function to insert data into the Fuelling table
    public void insertFuellingData(String vehicleId, String fuellingId, String day, String liters) {
    try {
        // Check if the vehicle ID exists in the Vehicle table
        if (!checkVehicleExistence(vehicleId)) {
            System.out.println("Vehicle ID does not exist. Cannot add Fuelling data.");
            return;
        }
        
        String query = "INSERT INTO fuelling (vehicle_id, fuelling_id, day, liters) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, vehicleId);
        pstmt.setString(2, fuellingId);
        pstmt.setString(3, day);
        pstmt.setString(4, liters);
        pstmt.executeUpdate();
        pstmt.close();
        System.out.println("Data inserted successfully into Fuelling table.");
    } catch (SQLException e) {
        System.out.println("Error inserting data into Fuelling table: " + e.getMessage());
    }
}
    
    // Function to delete data from the Fuelling table
    public void deleteFuellingData(String vehicleId, String fuellingId) {
        try {
            String query = "DELETE FROM fuelling WHERE vehicle_id = ? AND fuelling_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, vehicleId);
            pstmt.setString(2, fuellingId);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            System.out.println(rowsAffected + " row(s) deleted from Fuelling table.");
        } catch (SQLException e) {
            System.out.println("Error deleting data from Fuelling table: " + e.getMessage());
        }
    }
    
    // Function to update data in the Fuelling table
    public void updateFuellingData(String vehicleId, String fuellingId, String day, String liters) {
        try {
            String query = "UPDATE fuelling SET day = ?, liters = ? WHERE vehicle_id = ? AND fuelling_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, day);
            pstmt.setString(2, liters);
            pstmt.setString(3, vehicleId);
            pstmt.setString(4, fuellingId);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            System.out.println(rowsAffected + " row(s) updated in Fuelling table.");
        } catch (SQLException e) {
            System.out.println("Error updating data in Fuelling table: " + e.getMessage());
        }
    }
    
    // Function to view data from the Fuelling table
    public void viewFuellingData() {
        try {
            String query = "SELECT * FROM fuelling";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Vehicle ID");
            tableModel.addColumn("Fuelling ID");
            tableModel.addColumn("Day");
            tableModel.addColumn("Liters");

            while (rs.next()) {
                String vehicleId = rs.getString("vehicle_id");
                String fuellingId = rs.getString("fuelling_id");
                String day = rs.getString("day");
                String liters = rs.getString("liters");
                tableModel.addRow(new Object[]{vehicleId, fuellingId, day, liters});
            }

            rs.close();
            pstmt.close( );

            JTable dataTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(dataTable);

            JFrame frame = new JFrame("View Fuelling Data");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println("Error viewing Fuelling data: " + e.getMessage());
        }
    }
	public boolean closeConnection(){
		try{
			con.commit();
			if(!con.isClosed())
				con.close();
			return true;
		}
		catch(SQLException e){
			System.out.println(e);
			return false;
		}
	}
	 
}
	

	
