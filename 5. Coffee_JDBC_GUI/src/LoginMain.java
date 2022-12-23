import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginMain extends JFrame implements ActionListener {

	SignUpDao sdao = new SignUpDao();
	
	LoginMain() {
		login();
	} //constructor

	static JButton btn1;
	static JButton btn2;
	static JTextField textId;
	static JTextField textPw;
	JTextField textName;
	
	public void login() {
	
		JFrame frame = new JFrame("Login");
		frame.setSize(300,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		JLabel idLabel = new JLabel("ID");
		JLabel pwLabel = new JLabel("PW");

		idLabel.setBounds(20, 40, 80, 25);
		pwLabel.setBounds(20, 70, 80, 25);

		panel.add(idLabel);
		panel.add(pwLabel);

		textId = new JTextField();
		textPw = new JTextField();


		textId.setBounds(100, 40, 80, 25);
		textPw.setBounds(100, 70, 80, 25);


		panel.add(textId);
		panel.add(textPw);

		btn1 = new JButton("Login");
		btn2 = new JButton("Close");

		btn1.setBounds(40, 150, 80, 25);
		btn2.setBounds(140, 150, 80, 25);
		panel.add(btn1);
		panel.add(btn2);

		btn1.addActionListener(this);
		btn2.addActionListener(this);

		frame.setVisible(true);

	} //login

	public static void main(String[] args) {
		new LoginMain();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == btn1) {
			System.out.println("login clicked");
			String id = textId.getText();
			String pw = textPw.getText();
			System.out.println(id + "," + pw); 
			
			ArrayList<SignUpBean> list = sdao.checkId(id, pw);
			for(int i=0; i<list.size(); i++) {
				SignUpBean sb = list.get(i);
				if(sb.getId().equals(id) && sb.getPw().equals(pw)) {
					JOptionPane.showMessageDialog(this, "Login Success", "Login", JOptionPane.INFORMATION_MESSAGE);
					CoffeeMain coffee = new CoffeeMain();
					
				} else {
					JOptionPane.showMessageDialog(this, "Login failure", "Login", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
		} else {
			System.out.println("close clicked");
			System.exit(0);
		}
	}

}
