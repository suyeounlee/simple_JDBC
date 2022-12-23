import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.*;

public class SignUpMain extends JFrame implements ActionListener {

	SignUpDao sdao = new SignUpDao();
	JButton btn1, btn2;
	JTextField textId, textPw, textName;

	SignUpMain() {
		init();
	}

	public void init() {
		JFrame frame = new JFrame("Sign-Up");
		frame.setSize(300,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JLabel idLabel = new JLabel("ID");
		JLabel pwLabel = new JLabel("PW");
		JLabel nameLabel = new JLabel("Name");
		idLabel.setBounds(20, 40, 80, 25);
		pwLabel.setBounds(20, 70, 80, 25);
		nameLabel.setBounds(20, 100, 80, 25);
		panel.add(idLabel);
		panel.add(pwLabel);
		panel.add(nameLabel);

		textId = new JTextField();
		textPw = new JTextField();
		textName = new JTextField();

		textId.setBounds(100, 40, 80, 25);
		textPw.setBounds(100, 70, 80, 25);
		textName.setBounds(100, 100, 80, 25);

		panel.add(textId);
		panel.add(textPw);
		panel.add(textName);

		btn1 = new JButton("Sign-up");
		btn2 = new JButton("Close");

		btn1.setBounds(40, 150, 80, 25);
		btn2.setBounds(140, 150, 80, 25);
		panel.add(btn1);
		panel.add(btn2);

		btn1.addActionListener(this);
		btn2.addActionListener(this);

		frame.setVisible(true);
	}

	public static void main(String[] args) {

		new SignUpMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == btn1) {
			System.out.println("btn1 clicked");
			signup();

		} else {
			System.out.println("btn2 clicked");
			System.exit(0);
		}
	} //actionPerformed

	public void signup() {
		
		String id = textId.getText();
		String pw = textPw.getText();
		String name = textName.getText();
		
		if(id.length() == 0 || pw.length()==0 || name.length() == 0) {
			JOptionPane.showMessageDialog(this, "Insert data.", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		SignUpBean sign = new SignUpBean();
		sign.setId(id);
		sign.setPw(pw);
		sign.setName(name);
		
		int cnt = sdao.insertData(sign);
		if(cnt == -1) {
			System.out.println("SQL Failure");
		} else if (cnt == 0) {
			System.out.println("Insert failure");
		} else {
			System.out.println("Insert Success");
			LoginMain loginMain = new LoginMain();
			loginMain.login();
		}
		
	} //signUp

}
