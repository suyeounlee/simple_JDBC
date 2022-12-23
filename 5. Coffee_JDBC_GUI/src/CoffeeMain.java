import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CoffeeMain extends JFrame implements ActionListener{

	String[] colNames = {"ID", "Name", "Origin", "Price", "Taste", "Roasting"};
	Object[][] rowData;

	CoffeeDao cdao = new CoffeeDao();
	ArrayList<CoffeeBean> list = null;

	String[] title = {"Add", "Update", "Delete", "Exit"}; 
	JButton[] buttons = new JButton[title.length];

	JTable table = null;
	JScrollPane scrollPane = null;
	JTextField textId, textName, textOrigin, textPrice, textTasting, textRoasting;

	public CoffeeMain() {
		list = cdao.getList();

		fillData();
		compose();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		super.setTitle("Latin America Coffee Price");
		super.setSize(500,650);
		super.setVisible(true);
	}

	public void compose() {
		Container contentPane = getContentPane();
		table = new JTable(rowData, colNames); //content, title
		table.addMouseListener(new MouseEventProc());
		scrollPane = new JScrollPane(table);

		contentPane.add(scrollPane, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(Color.lightGray);
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel lbId = new JLabel("Id");
		JLabel lbName = new JLabel("Name");
		JLabel lbOrigin = new JLabel("Origin");
		JLabel lbPrice = new JLabel("Price");
		JLabel lbTasting = new JLabel("Taste");
		JLabel lbRoasting = new JLabel("Roasting");

		panel.setLayout(null);

		lbId.setBounds(15,20, 80,15);
		lbName.setBounds(15, 40, 80,15);
		lbOrigin.setBounds(15,60, 80,15);
		lbPrice.setBounds(15, 80, 80,15);
		lbTasting.setBounds(15, 100, 80,15);
		lbRoasting.setBounds(15,120, 80,15);

		panel.add(lbId);
		panel.add(lbName);
		panel.add(lbOrigin);
		panel.add(lbPrice);
		panel.add(lbTasting);
		panel.add(lbRoasting);

		textId = new JTextField();
		textName = new JTextField();
		textOrigin = new JTextField();
		textPrice = new JTextField();
		textTasting = new JTextField();
		textRoasting = new JTextField();

		textId.setBounds(200,20,100,20);
		textName.setBounds(200,40,100,20);
		textOrigin.setBounds(200,60,100,20);
		textPrice.setBounds(200,80,100,20);
		textTasting.setBounds(200,100,100,20);
		textRoasting.setBounds(200,120,100,20);

		textId.setText("0");
		textId.setEnabled(false); 

		panel.add(textId);
		panel.add(textName);
		panel.add(textOrigin);
		panel.add(textPrice);
		panel.add(textTasting);
		panel.add(textRoasting);

		//String[] title = {"Add", "Update", "Delete", "Exit"};
		JPanel sPanel = new JPanel();
		contentPane.add(sPanel,BorderLayout.SOUTH);
		sPanel.setLayout(new GridLayout(1,4)); //panel default: flowlayout

		//JButton[] buttons = new JButton[title.length];
		//buttons[0] : add

		for(int i=0; i<title.length; i++) {
			buttons[i] = new JButton(title[i]);
			buttons[i].addActionListener(this); //this class
			sPanel.add(buttons[i]);
		}
	}

	public void fillData() {
		int cnt = 0;
		rowData = new Object[list.size()][colNames.length];

		for(int i=0; i<list.size(); i++) {
			CoffeeBean cb = list.get(i);
			rowData[i][cnt++] = cb.getId();
			rowData[i][cnt++] = cb.getName();
			rowData[i][cnt++] = cb.getOrigin();
			rowData[i][cnt++] = cb.getPrice();
			rowData[i][cnt++] = cb.getTasting();
			rowData[i][cnt++] = cb.getRoasting();
			cnt = 0;
		}
	}

	class MouseEventProc extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			//System.out.println("mouse clicked");

			int row = table.getSelectedRow();
			System.out.println("row: " + row);
			String id = table.getValueAt(row, 0).toString(); // id
			textId.setText(id);

			String name = (String)table.getValueAt(row, 1); //name
			textName.setText(name);

			textOrigin.setText((String)table.getValueAt(row, 2));
			textPrice.setText(table.getValueAt(row, 3).toString());
			textTasting.setText((String)table.getValueAt(row, 4));
			textRoasting.setText((String)table.getValueAt(row, 5));
		}
	}

	public static void main(String[] args) {
		new CoffeeMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Button clicked");
		Object obj = e.getSource();
		if(obj == buttons[0]) { // add
			System.out.println("Add button");
			insertData();

		} else if(obj == buttons[1]) { //update
			System.out.println("Update button");
			updateData();

		}else if(obj == buttons[2]) { //delete
			deleteData();
			System.out.println("Delete button");

		} else { //exit
			System.out.println("Exit button");
			System.exit(0);
		}		
	} //actionPerformed
	
	public void deleteData() {
		int row = table.getSelectedRow();
		System.out.println("deleteData() row: " + row);
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Select the row to delete.", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int id = (Integer)table.getValueAt(row, 0);

		int cnt = cdao.deleteData(id);
		if(cnt == -1) {
			System.out.println("SQL error");
		} else if (cnt == 0) {
			System.out.println("Delete failure - No record");
		} else {
			System.out.println("Delete Success");
			clearTextField();
			getAllProducts(); //select
		}

	} //deleteData()

	public void updateData() {
		
		String id = textId.getText();
		String name = textName.getText();
		String origin = textOrigin.getText();
		String price = textPrice.getText();
		String tasting = textTasting.getText();
		String roasting = textRoasting.getText();

		CoffeeBean cb = new CoffeeBean();
		cb.setId(Integer.parseInt(id)); //numberFormatException
		cb.setName(name);
		cb.setOrigin(origin); 
		cb.setPrice(Integer.parseInt(price));
		cb.setTasting(tasting);
		cb.setRoasting(roasting);

		int cnt = cdao.updateData(cb);
		if(cnt == -1) {
			System.out.println("Sql error");
		} else if (cnt == 0){
			System.out.println("Update failure");
		} else {
			System.out.println("Update success");
			clearTextField();
			getAllProducts();
		}
	} //updateData

	public void insertData() {

		checkData();
		//String id = textId.getText(); // 0
		String name = textName.getText();
		String origin = textOrigin.getText();
		String price = textPrice.getText();
		String tasting = textTasting.getText();
		String roasting = textRoasting.getText();

		CoffeeBean cb = new CoffeeBean();
		cb.setName(name);
		
		cb.setOrigin(origin); //numberFormatException
		cb.setPrice(Integer.parseInt(price));
		cb.setTasting(tasting);
		cb.setRoasting(roasting);

		int cnt = cdao.insertData(cb);
		if(cnt == -1) {
			System.out.println("Sql error");
		} else if (cnt == 0){
			System.out.println("Insert failure");
		} else {
			System.out.println("Insert success");
			clearTextField();
			getAllProducts();
		}

	} //insertData

	public void checkData() {
		if(textName.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter product name.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textName.requestFocus();
			return;
		}

		if(textOrigin.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter origin.", "Error", JOptionPane.ERROR_MESSAGE);
			textOrigin.requestFocus();
			return;
		}

		if(textPrice.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter price.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textPrice.requestFocus();
			return;
		}

		if(textTasting.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter taste note.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textTasting.requestFocus();
			return;
		}

		if(textRoasting.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter roasting date.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textRoasting.requestFocus();
			return;
		}

	} //checkData

	public void clearTextField() { //after add, clear text fields
		textName.setText("");
		textOrigin.setText("");
		textPrice.setText("");
		textTasting.setText("");
		textRoasting.setText("");

	}//clearTextField

	public void getAllProducts() {
		list = cdao.getList();

		fillData();
		table = new JTable(rowData, colNames); //content, title

		table.addMouseListener(new MouseEventProc());

		scrollPane.setViewportView(table);

	} //getAllProducts

}
