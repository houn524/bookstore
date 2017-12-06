/* ���� : InsertFrame.java					*
 * ����� : ����Ʈ��������μ���				      	*
 * ���� : ���� ��ư�� ������ �� �Ӽ����� �Է��� �� �ִ� ������ ����	*/

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

/********************** ���Թ�ư ������ �� �ߴ� ������ ****************************/
public class InsertFrame extends JFrame {

	private String[] colNames;
	private JButton btnOK, btnCancel;
	private DatabaseController dbController;
	private GuitarPanel guitarPanel;
	private List components;

	public InsertFrame(String title, InstrumentPanel instrumentPanel, DatabaseController dbController,
			ArrayList _components, InstrumentType instrumentType) {
		this.dbController = dbController;
		this.guitarPanel = guitarPanel;
		this.components = new ArrayList();

		colNames = dbController.getColumnNames();

		/********************** ������ ���� ������Ʈ�� ���� ****************************/
		int i = 0;
		ArrayList enumList = new ArrayList();
		for (Object component : _components) {
			enumList.clear();
			if (component instanceof JTextField) {
				components.add(new JTextField(10));
			} else if (component instanceof JComboBox) {
				JComboBox cbBox = new JComboBox();
				switch (colNames[i + 1].toString()) {
				case "Builder":
					Collections.addAll(enumList, Builder.values());
					break;
				case "Type":
					Collections.addAll(enumList, GuitarType.values());
					break;
				case "Style":
					Collections.addAll(enumList, Style.values());
					break;
				case "TopWood":
				case "BackWood":
					Collections.addAll(enumList, Wood.values());
					break;
				}
				for (Object item : enumList)
					cbBox.addItem(item);
				components.add(cbBox);
			}
			i++;
		}

		btnOK = new JButton("확인");
		
		/********************** Ȯ�� ��ư ������ �� �����ͺ��̽��� �߰� ****************************/
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Double.parseDouble(((JTextField) components.get(1)).getText());
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "올바른 정보를 입력하시오.");
				}
				Map properties = new LinkedHashMap();
				for (int i = 0; i < components.size() - 2; i++) {
					Object component = components.get(i + 2);
					if (component instanceof JTextField)
						properties.put(colNames[i + 3],
							((JTextField) component).getText());
					else if (component instanceof JComboBox)
						properties.put(colNames[i + 3],
							((JComboBox) component).getSelectedItem());
				}
				properties.put("instrumentType", instrumentType);
				InstrumentSpec spec = new InstrumentSpec(properties);
				Instrument instrument = 
						new Instrument(((JTextField) components.get(0)).getText(),
						Double.parseDouble(((JTextField) components.get(1)).
						getText()), spec);

				if (dbController.insertInstrument(instrument)) {
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

		/********************** �гο� ������Ʈ �߰� ****************************/
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