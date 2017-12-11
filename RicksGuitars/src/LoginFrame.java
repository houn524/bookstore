import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	private DatabaseController dbController;
	
	public LoginFrame(String title, ObjectPanel objectPanel, DatabaseController dbController) {
		this.dbController = dbController;
		
		GridLayout gridLayout = new GridLayout(3, 2, 20, 20);
		setLayout(gridLayout);
		
		add(new JLabel("ID : ", Label.LEFT));
		JTextField txtId = new JTextField(10);
		add(txtId);
		add(new JLabel("PASSWORD : ", Label.LEFT));
		JTextField txtPw = new JTextField(10);
		add(txtPw);
		
		JButton btnOK = new JButton("확인");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dbController.checkAccount(txtId.getText(), txtPw.getText())) {
					objectPanel.login();
					setVisible(false);
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "존재하지 않는 계정임");
			}
		});
		add(btnOK);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				dispose();
			}
		});
		add(btnCancel);
		
		setTitle(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
		setSize(400, 250);
		setVisible(true);
	}
}
