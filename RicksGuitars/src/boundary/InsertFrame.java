/* ?��?�� : InsertFrame.java					*
 * 과목�? : 객체�??��?��?��?��분석및설�?				*
 * ?��?�� : ?��?�� 버튼?�� ?��???�� ?�� ?��?��?��?�� ?��?��?�� ?�� ?��?�� ?��?��?�� ?��?��	*/
package boundary;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Book;
import entity.BookProperties;

/********************** ?��?��버튼 ?��???�� ?�� ?��?�� ?��?��?�� ****************************/
public class InsertFrame extends JFrame {

	private String[] colNames;
	private JButton btnOK, btnCancel;
	private DatabaseController dbController;
	private List components;

	public InsertFrame(String title, BookPanel objectPanel, DatabaseController dbController,
			ArrayList _components) {
		this.dbController = dbController;
		this.components = new ArrayList();

		colNames = dbController.getColumnNames();

		/********************** ?��?��?�� ?��?�� 컴포?��?��?�� ?��?�� ****************************/
		int i = 0;
		ArrayList enumList = new ArrayList();
		for (Object component : _components) {
			enumList.clear();
			if (component instanceof JTextField) {
				components.add(new JTextField(10));
			}
			i++;
		}

		btnOK = new JButton("?��?��");
		
		/********************** ?��?�� 버튼 ?��???�� ?�� ?��?��?��베이?��?�� 추�? ****************************/
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Map properties = new LinkedHashMap();
				for (int i = 0; i < components.size() - 1; i++) {
					Object component = components.get(i + 1);
					if (component instanceof JTextField)
						properties.put(colNames[i + 2],
							((JTextField) component).getText());
				}
				BookProperties spec = new BookProperties(properties);
				Book book = 
						new Book(Integer.parseInt(((JTextField) components.get(0)).getText()), spec);

				if (dbController.insertBook(book)) {
					if (!objectPanel.isSearching())
						objectPanel.updateTable(null);
					setVisible(false);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, 
						"SerialNumber�? 중복?���? ?���? ?��?��?��?��?��.");
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

		/********************** ?��?��?�� 컴포?��?�� 추�? ****************************/
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

	public InsertFrame() {
			return;
		}
}