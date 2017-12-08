/* 파일 : InsertFrame.java					*
 * 과목명 : 소프트웨어개발프로세스				*
 * 서술 : 삽입 버튼을 눌렀을 시 속성들을 입력할 수 있는 프레임 생성	*/

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/********************** 삽입버튼 눌렀을 때 뜨는 프레임 ****************************/
public class InsertFrame extends JFrame {

	private String[] colNames;
	private JButton btnOK, btnCancel;
	private DatabaseController dbController;
	private BookPanel guitarPanel;
	private List components;

	public InsertFrame(String title, ObjectPanel instrumentPanel, DatabaseController dbController,
			ArrayList _components) {
		this.dbController = dbController;
		this.guitarPanel = guitarPanel;
		this.components = new ArrayList();

		colNames = dbController.getColumnNames();

		/********************** 프레임 안의 컴포넌트들 설정 ****************************/
		int i = 0;
		ArrayList enumList = new ArrayList();
		for (Object component : _components) {
			enumList.clear();
			if (component instanceof JTextField) {
				components.add(new JTextField(10));
			} else if (component instanceof JComboBox) {
//				JComboBox cbBox = new JComboBox();
//				switch (colNames[i + 1].toString()) {
//				case "Builder":
//					Collections.addAll(enumList, Builder.values());
//					break;
//				case "Type":
//					Collections.addAll(enumList, GuitarType.values());
//					break;
//				case "Style":
//					Collections.addAll(enumList, Style.values());
//					break;
//				case "TopWood":
//				case "BackWood":
//					Collections.addAll(enumList, Wood.values());
//					break;
//				}
//				for (Object item : enumList)
//					cbBox.addItem(item);
//				components.add(cbBox);
			}
			i++;
		}

		btnOK = new JButton("확인");
		
		/********************** 확인 버튼 눌렀을 시 데이터베이스에 추가 ****************************/
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				try {
//					Double.parseDouble(((JTextField) components.get(1)).getText());
//				} catch (NumberFormatException exception) {
//					JOptionPane.showMessageDialog(null, "올바른 정보를 입력하시오.");
//				}
				Map properties = new LinkedHashMap();
				System.out.println("compoents size = " + components.size());
				for (int i = 0; i < components.size() - 1; i++) {
					Object component = components.get(i + 1);
					if (component instanceof JTextField)
						properties.put(colNames[i + 2],
							((JTextField) component).getText());
					else if (component instanceof JComboBox)
						properties.put(colNames[i + 2],
							((JComboBox) component).getSelectedItem());
				}
//				properties.put("instrumentType", instrumentType);
				BookProperties spec = new BookProperties(properties);
				Book book = 
						new Book(Integer.parseInt(((JTextField) components.get(0)).getText()), spec);

				if (dbController.insertBook(book)) {
					if (!instrumentPanel.isSearching())
						instrumentPanel.updateTable(null);
					setVisible(false);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, 
						"SerialNumber가 중복되지 않게 입력하시오.");
			}
		});

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				dispose();
			}
		});

		GridLayout gridLayout = new GridLayout(colNames.length, 2, 10, 10);
		setLayout(gridLayout);

		/********************** 패널에 컴포넌트 추가 ****************************/
		i = 1;
		for (Object component : components) {
			add(new JLabel((colNames[i].toString() + " : "), Label.LEFT));
			add((Component) component);
			i++;
		}
		add(btnOK);
		add(btnCancel);

		setTitle(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
		setSize(400, 400);
		setVisible(true);
	}
}