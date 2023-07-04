import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FuelType extends JFrame {
    private JFrame frame;
    private JTextField tfVehicleId, tfFuellingId, tfName, tfPricePerLiter;
    private JButton btnSubmit, btnUpdate, btnDelete, btnView;
    private DBAccess db;

    public FuelType(DBAccess db) {
        this.db = db;
        initializeComponents();
        registerListeners();
        addComponentsToFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        frame = new JFrame("Fuel Type");
        JLabel lblVehicleId = new JLabel("Vehicle ID:");
        tfVehicleId = new JTextField(10);
        JLabel lblFuellingId = new JLabel("Fuelling ID:");
        tfFuellingId = new JTextField(10);
        JLabel lblName = new JLabel("Name:");
        tfName = new JTextField(20);
        JLabel lblPricePerLiter = new JLabel("Price per Liter:");
        tfPricePerLiter = new JTextField(10);
        btnSubmit = new JButton("Submit");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");
    }

    private void registerListeners() {
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleId = tfVehicleId.getText();
                String fuellingId = tfFuellingId.getText();
                String name = tfName.getText();
                String pricePerLiter = tfPricePerLiter.getText();
                db.insertFuelTypeData(vehicleId, fuellingId, name, pricePerLiter);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleId = tfVehicleId.getText();
                String fuellingId = tfFuellingId.getText();
                String name = tfName.getText();
                String pricePerLiter = tfPricePerLiter.getText();
                db.updateFuelTypeData(vehicleId, fuellingId, name, pricePerLiter);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleId = tfVehicleId.getText();
                String fuellingId = tfFuellingId.getText();
                db.deleteFuelTypeData(vehicleId, fuellingId);
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.viewFuelTypeData();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Vehicle ID:"));
        panel.add(tfVehicleId);
        panel.add(new JLabel("Fuelling ID:"));
        panel.add(tfFuellingId);
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Price per Liter:"));
        panel.add(tfPricePerLiter);
        panel.add(btnSubmit);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);

        frame.add(panel);
    }
}
