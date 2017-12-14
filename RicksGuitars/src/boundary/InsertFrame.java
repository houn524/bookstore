/* ??Ό : InsertFrame.java					*
 * κ³Όλͺ©λͺ? : κ°μ²΄μ§??₯??€?λΆμλ°μ€κΌ?				*
 * ??  : ?½? λ²νΌ? ???? ? ??±?€? ?? ₯?  ? ?? ?? ? ??±	*/
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

/********************** ?½?λ²νΌ ???? ? ?¨? ?? ? ****************************/
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

		/********************** ?? ? ?? μ»΄ν¬??Έ?€ ?€?  ****************************/
		int i = 0;
		ArrayList enumList = new ArrayList();
		for (Object component : _components) {
			enumList.clear();
			if (component instanceof JTextField) {
				components.add(new JTextField(10));
			}
			i++;
		}

		btnOK = new JButton("??Έ");
		
		/********************** ??Έ λ²νΌ ???? ? ?°?΄?°λ² μ΄?€? μΆκ? ****************************/
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
						"SerialNumberκ°? μ€λ³΅?μ§? ?κ²? ?? ₯???€.");
			}
		});

		btnCancel = new JButton("μ·¨μ");
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

		/********************** ?¨?? μ»΄ν¬??Έ μΆκ? ****************************/
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