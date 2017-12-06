/* ���� : InstrumentPanel.java					*
 * ����� : ����Ʈ��������μ���							*
 * ���� : ��� �ǱⰡ ���������� ������ �ִ� �Ӽ����� ǥ���� �� �ִ�		*
 * 		������Ʈ���� �����ϰ� �߰������ִ� Ŭ����				*
 * 		�� Ŭ������ GuitarPanel�� MandolinPanel Ŭ������	*
 * 		��� �޴´�.									*/

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class InstrumentPanel extends JPanel {

	private Object[][] data;
	private DefaultTableModel defaultTableModel;
	private DefaultTableCellRenderer celAlign, celCheckBox;
	private String[] title;
	private JTable table;
	private JCheckBox chkBox;
	private int selectedRow;
	private Inventory inventory, defaultInventory;
	private InstrumentType instrumentType;
	private DatabaseController dbController;
	private InstrumentPanel instance;
	private boolean isSearching = false;
	private InstrumentSpec searchingSpec;
	protected JPanel editSpecPane;
	protected List components;

	public InstrumentPanel(InstrumentType instrumentType) {
		this.instrumentType = instrumentType;
		instance = this;

		components = new ArrayList();

		dbController = new DatabaseController();
		dbController.connectSQL();

		table = new JTable();

		celAlign = new DefaultTableCellRenderer();
		celCheckBox = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JCheckBox box = new JCheckBox();
				box.setSelected(((Boolean) value).booleanValue());
				box.setHorizontalAlignment(JLabel.CENTER);
				return box;
			}
		};
		celAlign.setHorizontalAlignment(JLabel.CENTER);

		/********************** üũ�ڽ� ���� ****************************/
		chkBox = new JCheckBox();
		chkBox.setHorizontalAlignment(JLabel.CENTER);
		chkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectedRow = table.getSelectedRow();
				if ((Boolean) defaultTableModel.getValueAt(selectedRow, 0) == false)
					defaultTableModel.setValueAt(false, selectedRow, 0);
				else
					defaultTableModel.setValueAt(true, selectedRow, 0);
			}
		});

		updateTable(null);

		Border border = BorderFactory.createTitledBorder("악기 재고");

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(border);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/********************** �ؽ�Ʈ�ʵ� ���� ****************************/
		JTextField txtSerialNumber = new JTextField(10);
		txtSerialNumber.setEditable(false);
		JTextField txtPrice = new JTextField(10);
		JTextField txtModel = new JTextField(10);
		// txtNumStrings = new JTextField(10);

		/********************** �޺��ڽ� ���� ****************************/
		JComboBox cbBuilder = new JComboBox();
		cbBuilder.setEditable(true);
		for (Builder builder : Builder.values())
			cbBuilder.addItem(builder.toString());

		JComboBox cbType = new JComboBox();
		cbType.setEditable(true);
		for (GuitarType guitarType : GuitarType.values())
			cbType.addItem(guitarType.toString());

		JComboBox cbBackWood = new JComboBox();
		cbBackWood.setEditable(true);
		JComboBox cbTopWood = new JComboBox();
		cbTopWood.setEditable(true);
		for (Wood wood : Wood.values()) {
			cbBackWood.addItem(wood.toString());
			cbTopWood.addItem(wood.toString());
		}


		/********************** ��ư ���� ****************************/
		JButton btnAllCheck = new JButton("전체선택");
		btnAllCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (btnAllCheck.getText() == "전체선택") {
					btnAllCheck.setText("선택해제");
					for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
						defaultTableModel.setValueAt(true, i, 0);
					}
				} else {
					btnAllCheck.setText("전체선택");
					for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
						defaultTableModel.setValueAt(false, i, 0);
					}
				}
			}
		});

		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowCount = defaultTableModel.getRowCount();
				ArrayList<Integer> rowList = new ArrayList<Integer>();
				for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
					if ((boolean) defaultTableModel.getValueAt(i, 0) == true) {
						defaultTableModel.removeRow(i);
						if(dbController.deleteInstrument(inventory.get(i))) {
							clearSpecField();
							inventory.removeInstrument(i);
						}
						i--;
					}
				}
			}
		});

		JButton btnInsert = new JButton("삽입");
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InsertFrame insertFrame = new InsertFrame("악기 추가", instance, dbController, (ArrayList) components,
						instrumentType);
			}
		});

		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!isSearching)
					defaultInventory = inventory;
				searchingSpec = getSpecField();
				searchTable(searchingSpec);
				isSearching = true;
				clearSpecField();
			}
		});

		JButton btnEdit = new JButton("수정");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				InstrumentSpec spec = getSpecField();

				Instrument instrument = new Instrument(txtSerialNumber.getText(),
						Double.parseDouble(txtPrice.getText()), spec);

				dbController.updateData(instrument);

				updateTable(null);

				if (isSearching)
					searchTable(searchingSpec);

				clearSpecField();
			}
		});

		JButton btnAllShow = new JButton(Character.toString((char)27));
		btnAllShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTable(null);
				clearSpecField();
				isSearching = false;
			}
		});

		/********************** ������Ʈ�� ArrayList�� ���� ****************************/
		components.add(txtSerialNumber);
		components.add(txtPrice);
		components.add(cbBuilder);
		components.add(txtModel);
		components.add(cbType);
		components.add(cbTopWood);
		components.add(cbBackWood);

		/********************** �г� ���� ****************************/
		JPanel invenButtonPane = new JPanel();
		invenButtonPane.setLayout(new FlowLayout());
		invenButtonPane.add(btnAllCheck);
		invenButtonPane.add(btnDelete);
		invenButtonPane.add(btnInsert);

		JPanel inventoryPane = new JPanel();
		inventoryPane.setLayout(new BorderLayout());
		inventoryPane.add(invenButtonPane, BorderLayout.NORTH);
		inventoryPane.add(scroll, BorderLayout.CENTER);
		inventoryPane.setPreferredSize(new Dimension(800, 800));

		JPanel editPane = new JPanel();
		JPanel editButtonPane = new JPanel();
		editButtonPane.setLayout(new FlowLayout());
		editButtonPane.add(btnAllShow);
		editButtonPane.add(btnSearch);
		editButtonPane.add(btnEdit);

		editSpecPane = new JPanel();
		GridLayout gridLayout = new GridLayout(title.length + 3, 2, 0, 10);
		editSpecPane.setLayout(gridLayout);

		/********************** �гο� ������Ʈ �߰� ****************************/
		int i = 1;
		for (Object component : components) {
			editSpecPane.add(new JLabel((title[i].toString() + " : "), Label.LEFT));
			editSpecPane.add((Component) component);
			i++;
		}

		Border specBorder;
		specBorder = BorderFactory.createTitledBorder("악기 속성");
		editSpecPane.setBorder(specBorder);

		BoxLayout boxLayout = new BoxLayout(editPane, BoxLayout.Y_AXIS);
		editPane.setLayout(new BorderLayout());
		editPane.add(editButtonPane, BorderLayout.NORTH);
		editPane.add(editSpecPane, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(inventoryPane, BorderLayout.CENTER);
		add(editPane, BorderLayout.EAST);
	}

	/********************** �����ͺ��̽����� �����͵��� �а� ���̺� �ѷ��� ****************************/
	public void updateTable(ArrayList<Instrument> searchResult) {
		if (searchResult != null) {
			inventory = new Inventory();
			for (Instrument instrument : searchResult) {
				inventory.addInstrument(instrument);
			}

			data = null;
			data = new Object[searchResult.size()][title.length];

			for (int i = 0; i < searchResult.size(); i++) {
				for (int j = 0; j < title.length; j++)
					data[i][j] = searchResult.get(i).toArray()[j];
			}
		} else {
			inventory = new Inventory();
			data = dbController.getInstruments(instrumentType);
			title = dbController.getColumnNames();

			for (int i = 0; i < data.length; i++) {
				Map properties = new LinkedHashMap();
				for (int j = 3; j < title.length; j++)
					properties.put(title[j], data[i][j]);
				properties.put("instrumentType", instrumentType);
				InstrumentSpec spec = new InstrumentSpec(properties);
				inventory.addInstrument((String) data[i][1], Double.parseDouble((String) data[i][2]), spec);
			}
		}

		/********************** ���̺� ���� �Ұ� ****************************/
		defaultTableModel = new DefaultTableModel(data, title) {
			public boolean isCellEditable(int i, int c) {
				if (c != 0)
					return false;
				else
					return true;
			}
		};

		/********************** ���̺� ���� ****************************/
		table.setModel(defaultTableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("").setPreferredWidth(10);
		table.getColumn("").setCellRenderer(celCheckBox);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					int row = table.getSelectedRow();

					try {
						int i = 1;
						
						for (Object component : components) {
							if (component instanceof JTextField) {
								if (defaultTableModel.getValueAt(row, i) instanceof Double)					
									((JTextField) component).setText(String.valueOf(defaultTableModel.getValueAt(row, i)));
								else
									((JTextField) component).setText((String) defaultTableModel.getValueAt(row, i));
							} else if (component instanceof JComboBox)
									((JComboBox) component).setSelectedItem((String) defaultTableModel.getValueAt(row, i));
							i++;
						}
					} catch (ArrayIndexOutOfBoundsException exception) {}
				}
			}
		});

		table.getColumn("").setCellEditor(new DefaultCellEditor(chkBox));
		TableColumnModel colModel = table.getColumnModel();
		for (int i = 1; i < defaultTableModel.getColumnCount(); i++) {
			colModel.getColumn(i).setCellRenderer(celAlign);
		}
	}

	/********************** �Ǳ� �Ӽ���� �˻� ****************************/
	public void searchTable(InstrumentSpec spec) {
		ArrayList<Instrument> searchResult;

		searchResult = defaultInventory.search(spec);

		updateTable(searchResult);
	}

	/************ ȭ�� �����ʿ� �ִ� �Ӽ����� �о InstrumentSpec ��ü�� ���� ************/
	public InstrumentSpec getSpecField() {
		Map properties = new LinkedHashMap();
		for (int i = 0; i < components.size() - 2; i++) {
			Object component = components.get(i + 2);
			if (component instanceof JComboBox)
				properties.put(title[i + 3], ((JComboBox) component).getSelectedItem());
			else if (component instanceof JTextField)
				properties.put(title[i + 3], ((JTextField) component).getText());

		}
		properties.put("instrumentType", instrumentType);

		InstrumentSpec spec = new InstrumentSpec(properties);
		return spec;
	}

	/********************** ȭ�� �����ʿ� �ִ� �Ӽ����� ����ֱ� ****************************/
	private void clearSpecField() {
		for (Object component : components) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
			else if (component instanceof JComboBox)
				((JComboBox) component).setSelectedItem("");
		}
	}

	/********************** �˻� ���� �� �Ǻ� ****************************/
	public boolean isSearching() {
		return isSearching;
	}
}