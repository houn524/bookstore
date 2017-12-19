/* 파일 : ReturnFrame.java					*
 * 과목명 : 객체지향시스템분석및설계					*
 * 서술 : 반납 버튼을 눌렀을 때 보여주는 프레임			*/
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
		
		JButton btnOK = new JButton("반납");
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
					JOptionPane.showMessageDialog(null, "존재하지 않는 시리얼 넘버입니다.");
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
		setSize(400, 250);
		setVisible(true);
	}
}
