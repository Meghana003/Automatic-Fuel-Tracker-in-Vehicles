import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VehicleData {
    private JFrame frame;
    private JTextField tfUserId, tfVehicleId, tfYear, tfBrand, tfModel;
    private JButton btnSubmit, btnUpdate, btnDelete, btnView;
    private DBAccess db;
	private JMenu actions;
	private JMenuBar menubar;
	private JMenuItem mifuelling,mifueltype;

    public VehicleData(DBAccess db) {
        this.db = db;
        initializeComponents();
        registerListeners();
        addComponentsToFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializeComponents() {
		frame = new JFrame("Vehicle Data");
		menubar = new JMenuBar();
		actions = new JMenu("Actions");
		//mifuelling = new JMenu("Fuelling");
		//mifueltype = new JMenu("Fuel type");
        mifuelling = new JMenuItem("Fuelling");
		mifueltype = new JMenuItem("Fuel type");
        JLabel lblUserId = new JLabel("User ID:");
        tfUserId = new JTextField(10);
        JLabel lblVehicleId = new JLabel("Vehicle ID:");
        tfVehicleId = new JTextField(10);
        JLabel lblYear = new JLabel("Year:");
        tfYear = new JTextField(5);
        JLabel lblBrand = new JLabel("Brand:");
        tfBrand = new JTextField(20);
        JLabel lblModel = new JLabel("Model:");
        tfModel = new JTextField(20);
        btnSubmit = new JButton("Submit");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");
    }

    private void registerListeners() {
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = tfUserId.getText();
                String vehicleId = tfVehicleId.getText();
                String year = tfYear.getText();
                String brand = tfBrand.getText();
                String model = tfModel.getText();
                db.insertVehicleData(userId, vehicleId, year, brand, model);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = tfUserId.getText();
                String vehicleId = tfVehicleId.getText();
                String year = tfYear.getText();
                String brand = tfBrand.getText();
                String model = tfModel.getText();
                db.updateVehicleData(userId, vehicleId, year, brand, model);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = tfUserId.getText();
                String vehicleId = tfVehicleId.getText();
                db.deleteVehicleData(userId, vehicleId);
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String userId = tfUserId.getText();
                //String vehicleId = tfVehicleId.getText();
                db.viewVehicleData();
            }
        });
		/*mifueltype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FuelType fueltypewindow = new FuelType(db);
				fueltypewindow.setVisible(true);
		
			}
		});

		mifuelling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fuelling fuellingwindow = new Fuelling(db);
				fuellingwindow.setVisible(true);
			}
		});*/
		mifuelling.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fuelling fuellingWindow = new Fuelling(db);
                fuellingWindow.setVisible(true);
            }
        });

        mifueltype.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FuelType fuelTypeWindow = new FuelType(db);
                fuelTypeWindow.setVisible(true);
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
		frame.setJMenuBar(menubar);
		menubar.add(actions);
		actions.add(mifuelling);
		actions.add(mifueltype);
		//menubar.add(mifuelling);
		//menubar.add(mifueltype);
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("User ID:"));
        panel.add(tfUserId);
        panel.add(new JLabel("Vehicle ID:"));
        panel.add(tfVehicleId);
        panel.add(new JLabel("Year:"));
        panel.add(tfYear);
        panel.add(new JLabel("Brand:"));
        panel.add(tfBrand);
        panel.add(new JLabel("Model:"));
        panel.add(tfModel);
        panel.add(btnSubmit);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);

        frame.add(panel);
    }
}
