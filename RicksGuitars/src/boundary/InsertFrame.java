/* ?ŒŒ?¼ : InsertFrame.java					*
 * ê³¼ëª©ëª? : ê°ì²´ì§??–¥?‹œ?Š¤?…œë¶„ì„ë°ì„¤ê¼?				*
 * ?„œ?ˆ  : ?‚½?… ë²„íŠ¼?„ ?ˆŒ???„ ?‹œ ?†?„±?“¤?„ ?…? ¥?•  ?ˆ˜ ?ˆ?Š” ?”„? ˆ?„ ?ƒ?„±	*/
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

/********************** ?‚½?…ë²„íŠ¼ ?ˆŒ???„ ?•Œ ?œ¨?Š” ?”„? ˆ?„ ****************************/
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

		/********************** ?”„? ˆ?„ ?•ˆ?˜ ì»´í¬?„Œ?Š¸?“¤ ?„¤? • ****************************/
		int i = 0;
		ArrayList enumList = new ArrayList();
		for (Object component : _components) {
			enumList.clear();
			if (component instanceof JTextField) {
				components.add(new JTextField(10));
			}
			i++;
		}

		btnOK = new JButton("?™•?¸");
		
		/********************** ?™•?¸ ë²„íŠ¼ ?ˆŒ???„ ?‹œ ?°?´?„°ë² ì´?Š¤?— ì¶”ê? ****************************/
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
						"SerialNumberê°? ì¤‘ë³µ?˜ì§? ?•Šê²? ?…? ¥?•˜?‹œ?˜¤.");
			}
		});

		btnCancel = new JButton("ì·¨ì†Œ");
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

		/********************** ?Œ¨?„?— ì»´í¬?„Œ?Š¸ ì¶”ê? ****************************/
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