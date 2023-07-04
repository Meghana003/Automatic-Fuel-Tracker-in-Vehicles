import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.*;
public class Mainpage{
	JFrame frame;
	JLabel lbluname,lblvecid,lblcity,lbluid,lbltitle,lbloption;
	JButton btnadd,btndelete,btnupdate,btnvehicle;
	JTextField tfuname, tfuid, tfvecid;
	JMenuItem miView;
    JMenu actions;
	JComboBox cityList;
	String[] cities = {"Hyderabad", "Bangalore", "Chennai", "Vijaywada","Kolkata","Mumbai","Delhi","Ahmedabad"};
	JMenuBar menubar;
	JMenu about,lkfl;
	JMenuItem miabtproject,miabtstudent,miMotif,miNimbus,miCross;
	JPanel p1,p2,p3,p4,p5,p6,p7,p8;
	DBAccess db;
	public Mainpage(){
		super();
	}
	public Mainpage(DBAccess db){
		this.db = db;
		initializeComponents();
		registerListeners();
		addComponentsToFrame();
		frame.setBackground(Color.lightGray);
		frame.setLayout(new GridLayout(8,1)); 
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void initializeComponents(){
		frame = new JFrame("USER DATA");
		menubar = new JMenuBar();
		
		about = new JMenu("About");
		miabtproject = new JMenuItem("About Project");
		miabtstudent = new JMenuItem("About Student");
		lkfl = new JMenu("LAF");
		miMotif = new JMenuItem("Motif");
		miNimbus = new JMenuItem("Nimbus");
		miCross = new JMenuItem("Cross Platform");
		miView = new JMenuItem("View");
        actions = new JMenu("Actions");
		lbltitle = new JLabel("AUTOMATIC FUEL TRACKER IN VEHICLES");
		lbluname = new JLabel("USER NAME: ");
		tfuname = new JTextField(12);
		lbluid = new JLabel("USER ID: ");
		tfuid= new JTextField(4);
		lblcity = new JLabel("Select city: ");
		cityList = new JComboBox(cities);
		cityList.setSelectedIndex(1);
		lblvecid = new JLabel("Vehicle ID:");
		tfvecid = new JTextField(15);
		btnadd = new JButton("SUBMIT");
		btndelete = new JButton("Delete");
        btnupdate = new JButton("Update");
		btnvehicle = new JButton("VEHICLES");
		lbloption = new JLabel("Click here for vehicle data");
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
	}
	public void registerListeners(){
	miabtproject.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Project: AUTOMATIC FUEL TRACKER IN VEHICLES","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		miabtstudent.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Name of Student: Phani Meghana\nRoll number: 1602-21-737-028","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		miMotif.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				try{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
				}
				catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException ex)
				{}
			}
		});
		miNimbus.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				try{
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
				}
				catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException ex)
				{}
			}
		});
	
		miCross.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				try{
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(frame);
				}
				catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException ex)
				{}
			}
		});
		miView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.viewData();
            }
        });
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(db.closeConnection()){
					JOptionPane.showMessageDialog(frame, "Connection closing.\n Exit?","INFORMATION", JOptionPane.INFORMATION_MESSAGE);;
				}
				System.exit(0);
			}
		});
		btnadd.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String userName = tfuname.getText();
            String userID = tfuid.getText();
            String vehicleID = tfvecid.getText();
            String city = (String) cityList.getSelectedItem();

            // Call the insertData method in the DBAccess class
            db.insertData(userName, userID, vehicleID, city);
        }});
		btndelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {	
                String userID = tfuid.getText();
                db.deleteData(userID);
            }
        });
		 btnupdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = tfuname.getText();
                String userID = tfuid.getText();
                String vehicleID = tfvecid.getText();
                String city = (String) cityList.getSelectedItem();
				db.updateData(userName, userID, vehicleID, city);
            }
        });
		btnvehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = tfuid.getText();
				if (db.checkUserExistence(userId)) {
					new VehicleData(db);
				} 
				else {
					JOptionPane.showMessageDialog(frame, "User does not exist. Submit user data first!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		


			
	}
	public void addComponentsToFrame(){
		about.add(miabtproject);
		about.add(miabtstudent);
		lkfl.add(miCross);
		lkfl.add(miMotif);
		lkfl.add(miNimbus);
		menubar.add(about);
		menubar.add(lkfl);
		actions.add(miView);
		menubar.add(actions);
		frame.setJMenuBar(menubar);
		p1.add(lbltitle);
		p2.add(lbluname);
		p2.add(tfuname);
		p3.add(lbluid);
		p3.add(tfuid);
		p4.add(lblvecid);
		p4.add(tfvecid);
		p5.add(lblcity);
		p5.add(cityList);
		p6.add(btnadd);
		p6.add(btndelete);
		p6.add(btnupdate);
		p7.add(lbloption);
		p8.add(btnvehicle);
		
		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);
		frame.add(p5);
		frame.add(p6);
		frame.add(p7);
		frame.add(p8);
		
	}
	public static void main(String[] args){
		DBAccess db = new DBAccess();
		//String classpath = "." + System.getProperty("path.separator") + "ojdbc8.jar";
    //System.setProperty("java.class.path", classpath);
		new Mainpage(db);
	}
}
		
		
		
		
		
		
		
		
		