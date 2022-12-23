import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


//project right click -> properties -> Library -> ClassPath -> Add External JARs -> ojdbc7.jar file
public class ProductMain extends JFrame implements ActionListener /*, MouseListener */{

	String[] columnNames = {"Id", "Name", "Stock", "Price", "Category", "InputDate"};
	Object[][] rowData ;
	ProductDao pdao = new ProductDao();

	ArrayList<ProductBean> list = null;


	String[] title = {"Add", "Update", "Delete", "Exit"}; 
	JButton[] buttons = new JButton[title.length];
	JTextField textId, textName, textStock, textPrice, textCategory, textInputdate;
	JTable table = null;
	JScrollPane scrollPane = null;

	ProductMain(String title) {
		super(title);
		list = pdao.getAllProducts();
		//rowData = new Object[list.size()][columnNames.length];

		fillData();
		compose();

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		super.setSize(500,650);
		super.setVisible(true);
		//super.setResizable(false);

	} //constructor


	public void fillData() { //fill data in JTable
		int cnt = 0;
		rowData = new Object[list.size()][columnNames.length];
		System.out.println("list.size()" + list.size());

		for(int i=0; i<list.size(); i++) {
			ProductBean bean = list.get(i);
			rowData[i][cnt++] = bean.getId();
			rowData[i][cnt++] = bean.getName();
			rowData[i][cnt++] = bean.getStock();
			rowData[i][cnt++] = bean.getPrice();
			rowData[i][cnt++] = bean.getCategory();
			rowData[i][cnt++] = bean.getInputdate();
			cnt = 0;
		}
	}

	public void compose() {
		Container contentPane = getContentPane();

		table = new JTable(rowData, columnNames); //content, title
		table.addMouseListener(new MouseEventProc());
		scrollPane = new JScrollPane(table);

		contentPane.add(scrollPane, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(Color.lightGray);
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel lbId = new JLabel("ID");
		JLabel lbName = new JLabel("Name");
		JLabel lbStock = new JLabel("Stock");
		JLabel lbPrice = new JLabel("Price");
		JLabel lbCategory = new JLabel("Category");
		JLabel lbInputdate = new JLabel("Inputdate");

		panel.setLayout(null); //default Flow Layout

		lbId.setBounds(15,20,80,15);
		lbName.setBounds(15,40,80,15);
		lbStock.setBounds(15,60,80,15);
		lbPrice.setBounds(15,80,80,15);
		lbCategory.setBounds(15,100,80,15);
		lbInputdate.setBounds(15,120,80,15);

		panel.add(lbId);
		panel.add(lbName);
		panel.add(lbStock);
		panel.add(lbPrice);
		panel.add(lbCategory);
		panel.add(lbInputdate);

		textId = new JTextField();
		textName = new JTextField();
		textStock = new JTextField();
		textPrice = new JTextField();
		textCategory = new JTextField();
		textInputdate = new JTextField();

		textId.setBounds(200,20,100,20);
		textName.setBounds(200,40,100,20);
		textStock.setBounds(200,60,100,20);
		textPrice.setBounds(200,80,100,20);
		textCategory.setBounds(200,100,100,20);
		textInputdate.setBounds(200,120,100,20);

		textId.setText("0");
		textId.setEnabled(false); 

		panel.add(textId);
		panel.add(textName);
		panel.add(textStock);
		panel.add(textPrice);
		panel.add(textCategory);
		panel.add(textInputdate);

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

	} //compose

	class MouseEventProc extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			//System.out.println("mouse clicked");

			int row = table.getSelectedRow();
			System.out.println("row: " + row);
			String id = table.getValueAt(row, 0).toString(); // id
			textId.setText(id);

			String name = (String)table.getValueAt(row, 1); //name
			textName.setText(name);

			textStock.setText(table.getValueAt(row, 2).toString());
			textPrice.setText(table.getValueAt(row, 3).toString());
			textCategory.setText((String)table.getValueAt(row, 4));
			textInputdate.setText((String)table.getValueAt(row, 5));

		}
	}

	public static void main(String[] args) {
		new ProductMain("Product Inventory Program");
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

		int cnt = pdao.deleteData(id);
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
		String stock = textStock.getText();
		String price = textPrice.getText();
		String category = textCategory.getText();
		String inputdate = textInputdate.getText();

		ProductBean pb = new ProductBean();
		pb.setId(Integer.parseInt(id));
		pb.setName(name);
		pb.setStock(Integer.parseInt(stock)); //numberFormatException
		pb.setPrice(Integer.parseInt(price));
		pb.setCategory(category);
		pb.setInputdate(inputdate);

		int cnt = pdao.updateData(pb);
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
		String stock = textStock.getText();
		String price = textPrice.getText();
		String category = textCategory.getText();
		String inputdate = textInputdate.getText();

		ProductBean pb = new ProductBean();
		pb.setName(name);
		pb.setStock(Integer.parseInt(stock)); //numberFormatException
		pb.setPrice(Integer.parseInt(price));
		pb.setCategory(category);
		pb.setInputdate(inputdate);

		int cnt = pdao.insertData(pb);
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

		if(textStock.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter stock.", "Error", JOptionPane.ERROR_MESSAGE);
			textStock.requestFocus();
			return;
		}

		if(textPrice.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter price.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textPrice.requestFocus();
			return;
		}

		if(textCategory.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter category.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textCategory.requestFocus();
			return;
		}

		if(textInputdate.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Enter input date.", "Error", JOptionPane.INFORMATION_MESSAGE);
			textInputdate.requestFocus();
			return;
		}

	} //checkData

	public void clearTextField() { //after add, clear text fields
		textName.setText("");
		textStock.setText("");
		textPrice.setText("");
		textCategory.setText("");
		textInputdate.setText("");

	}//clearTextField

	public void getAllProducts() {
		list = pdao.getAllProducts();

		fillData();
		table = new JTable(rowData, columnNames); //content, title

		//		scrollPane.remove(table);
		//		table.revalidate();
		//		table.repaint();

		table.addMouseListener(new MouseEventProc());

		scrollPane.setViewportView(table);

	} //getAllProducts

	//	@Override
	//	public void mouseClicked(MouseEvent e) {
	//		System.out.println("mouse clicked");
	//	}
	//
	//	@Override
	//	public void mousePressed(MouseEvent e) {
	//		//System.out.println("mouse pressed");
	//	}
	//
	//	@Override
	//	public void mouseReleased(MouseEvent e) {
	//		//System.out.println("mouse released");
	//	}
	//
	//	@Override
	//	public void mouseEntered(MouseEvent e) {
	//		//System.out.println("mouse entered");
	//	}
	//
	//	@Override
	//	public void mouseExited(MouseEvent e) {
	//		//System.out.println("mouse exited");
	//	}

}
