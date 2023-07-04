import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fuelling extends JFrame {
    private JFrame frame;
    private JTextField tfVehicleId, tfFuellingId, tfDay, tfLiters;
    private JButton btnSubmit, btnUpdate, btnDelete, btnView;
    private DBAccess db;

    public Fuelling(DBAccess db) {
        this.db = db;
        initializeComponents();
        registerListeners();
        addComponentsToFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        frame = new JFrame("Fuelling");
        JLabel lblVehicleId = new JLabel("Vehicle ID:");
        tfVehicleId = new JTextField(10);
        JLabel lblFuellingId = new JLabel("Fuelling ID:");
        tfFuellingId = new JTextField(10);
        JLabel lblDay = new JLabel("Day:");
        tfDay = new JTextField(10);
        JLabel lblLiters = new JLabel("Liters:");
        tfLiters = new JTextField(10);
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
                String day = tfDay.getText();
                String liters = tfLiters.getText();
                db.insertFuellingData(vehicleId, fuellingId, day, liters);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleId = tfVehicleId.getText();
                String fuellingId = tfFuellingId.getText();
                String day = tfDay.getText();
                String liters = tfLiters.getText();
                db.updateFuellingData(vehicleId, fuellingId, day, liters);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vehicleId = tfVehicleId.getText();
                String fuellingId = tfFuellingId.getText();
                db.deleteFuellingData(vehicleId, fuellingId);
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.viewFuellingData();
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
        panel.add(new JLabel("Date:"));
        panel.add(tfDay);
        panel.add(new JLabel("Liters:"));
        panel.add(tfLiters);
        panel.add(btnSubmit);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);

        frame.add(panel);
    }
}
