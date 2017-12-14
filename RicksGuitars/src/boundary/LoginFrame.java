/* ?��?�� : LoginFrame.java						*
 * 과목�? : 객체�??��?��?��?��분석및설�?					*
 * ?��?�� : �?리자 로그?�� 버튼?�� ?��???�� ?�� 보여주는 ?��?��?��	*/
package boundary;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	
	public LoginFrame(String title, BookPanel bookPanel, DatabaseController dbController) {
		
		GridLayout gridLayout = new GridLayout(3, 2, 20, 20);
		setLayout(gridLayout);
		
		add(new JLabel("ID : ", Label.LEFT));
		JTextField txtId = new JTextField(10);
		add(txtId);
		add(new JLabel("PASSWORD : ", Label.LEFT));
		JPasswordField txtPw = new JPasswordField(10);
		add(txtPw);
		
		JButton btnOK = new JButton("?��?��");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dbController.checkAccount(txtId.getText(), txtPw.getText())) {
					bookPanel.login(true);
					setVisible(false);
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "존재?���? ?��?�� 계정?��");
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

	public void create() {
			return;
		}

	public void Operation1() {
			return;
		}

	public LoginFrame() {
			return;
		}
}
