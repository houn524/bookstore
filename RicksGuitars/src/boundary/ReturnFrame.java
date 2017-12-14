/* ??Ό : ReturnFrame.java					*
 * κ³Όλͺ©λͺ? : κ°μ²΄μ§??₯??€?λΆμλ°μ€κΌ?					*
 * ??  : λ°λ© λ²νΌ? ???? ? λ³΄μ¬μ£Όλ ?? ?			*/
package boundary;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ReturnFrame extends JFrame {
	
	private DatabaseController dbController;
	
	public ReturnFrame(String title, BookPanel objectPanel, DatabaseController dbController) {
		this.dbController = dbController;
		
		GridLayout gridLayout = new GridLayout(2, 2, 20, 20);
		setLayout(gridLayout);
		
		add(new JLabel("SerialNumber : ", Label.LEFT));
		JTextField txtNumber = new JTextField(10);
		add(txtNumber);
		
		JButton btnOK = new JButton("λ°λ©");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dbController.checkoutBook(Integer.parseInt(txtNumber.getText()), false)) {
					if(objectPanel.isSearching()) {
						objectPanel.updateTable(null);
						objectPanel.searchTable();
					} else {
						objectPanel.updateTable(null);
					}

					setVisible(false);
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "μ‘΄μ¬?μ§? ?λ¦¬μΌ ?λ²μ");
			}
		});
		add(btnOK);
		
		JButton btnCancel = new JButton("μ·¨μ");
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
		setSize(400, 250);
		setVisible(true);
	}

	public ReturnFrame() {
			return;
		}
}
