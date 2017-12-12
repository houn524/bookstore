/* 파일 : InstrumentPanel.java					*
 * 과목명 : 소프트웨어개발프로세스					*
 * 서술 : 모든 악기가 공통적으로 가지고 있는 속성들을 표현할 수 있는		*
 * 		컴포넌트들을 생성하고 추가시켜주는 클래스			*
 * 		이 클래스를 GuitarPanel과 MandolinPanel 클래스가	  	*
 * 		상속 받는다.					*/

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

public class ObjectPanel extends JPanel {

	private Object[][] data;
	private DefaultTableModel defaultTableModel;
	private DefaultTableCellRenderer celAlign, celCheckBox;
	private String[] title;
	private JTable table;
	private JCheckBox chkBox;
	private int selectedRow;
	private Inventory inventory, defaultInventory;
	private DatabaseController dbController;
	private ObjectPanel instance;
	private boolean isSearching = false;
	private boolean isLoggedin = false;
	private BookProperties searchingSpec;
	JButton btnDelete, btnInsert, btnLogin, btnLogout, btnEdit, btnCheckout;
	protected JPanel editSpecPane;
	protected List components;

	public ObjectPanel() {
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

		/********************** 체크박스 설정 ****************************/
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

		Border border = BorderFactory.createTitledBorder("책 재고");

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(border);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/********************** 텍스트필드 설정 ****************************/
		JTextField txtSerialNumber = new JTextField(10);
		txtSerialNumber.setEditable(false);
		JTextField txtTitle = new JTextField(10);
		JTextField txtPublisher = new JTextField(10);
		JTextField txtAuthor = new JTextField(10);

		/********************** 버튼 설정 ****************************/
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

		btnDelete = new JButton("삭제");
		btnDelete.setVisible(false);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowCount = defaultTableModel.getRowCount();
				ArrayList<Integer> rowList = new ArrayList<Integer>();
				for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
					if ((boolean) defaultTableModel.getValueAt(i, 0) == true) {
						defaultTableModel.removeRow(i);
						if(dbController.deleteInstrument(inventory.getByRow(i))) {
							clearSpecField();
							inventory.removeBook(i);
						}
						i--;
					}
				}
			}
		});

		btnInsert = new JButton("삽입");
		btnInsert.setVisible(false);
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InsertFrame insertFrame = new InsertFrame("책 추가", instance, dbController, (ArrayList) components);
			}
		});
		
		btnCheckout = new JButton("대출");
		btnCheckout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Integer> rowList = new ArrayList<Integer>();
				for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
					if ((boolean) defaultTableModel.getValueAt(i, 0) == true) {
						if(dbController.checkoutBook(inventory.getByRow(i), true)) {
							clearSpecField();
						}
					}
				}
				
				updateTable(null);

				if (isSearching) {
					defaultInventory = inventory;
					searchTable(searchingSpec);
				}
			}
		});
		
		btnLogin = new JButton("관리자 로그인");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame loginFrame = new LoginFrame("관리자 로그인", instance, dbController);
			}
		});
		
		btnLogout = new JButton("로그아웃");
		btnLogout.setVisible(false);
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logout();
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

		btnEdit = new JButton("수정");
		btnEdit.setVisible(false);
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				BookProperties spec = getSpecField();

				Book book = new Book(Integer.parseInt(txtSerialNumber.getText()), spec);

				dbController.updateData(book);

				updateTable(null);

				if (isSearching) {
					defaultInventory = inventory;
					searchTable(searchingSpec);
				}

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

		/********************** 컴포넌트를 ArrayList로 관리 ****************************/
		components.add(txtSerialNumber);
		components.add(txtTitle);
		components.add(txtPublisher);
		components.add(txtAuthor);

		/********************** 패널 설정 ****************************/
		JPanel invenButtonPane = new JPanel();
		invenButtonPane.setLayout(new FlowLayout());
		invenButtonPane.add(btnAllCheck);
		invenButtonPane.add(btnDelete);
		invenButtonPane.add(btnInsert);
		invenButtonPane.add(btnCheckout);
		invenButtonPane.add(btnLogin);
		invenButtonPane.add(btnLogout);

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
		GridLayout gridLayout = new GridLayout(title.length + 7, 2, 0, 10);
		editSpecPane.setLayout(gridLayout);

		/********************** 패널에 컴포넌트 추가 ****************************/
		int i = 1;
		for (Object component : components) {
			editSpecPane.add(new JLabel((title[i].toString() + " : "), Label.LEFT));
			editSpecPane.add((Component) component);
			i++;
		}

		Border specBorder;
		specBorder = BorderFactory.createTitledBorder("책 속성");
		editSpecPane.setBorder(specBorder);

		BoxLayout boxLayout = new BoxLayout(editPane, BoxLayout.Y_AXIS);
		editPane.setLayout(new BorderLayout());
		editPane.add(editButtonPane, BorderLayout.NORTH);
		editPane.add(editSpecPane, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(inventoryPane, BorderLayout.CENTER);
		add(editPane, BorderLayout.EAST);
	}

	/********************** 데이터베이스에서 데이터들을 읽고 테이블에 뿌려줌 ****************************/
	public void updateTable(ArrayList<Book> searchResult) {
		if (searchResult != null) {
			inventory = new Inventory();
			for (Book instrument : searchResult) {
				inventory.addBook(instrument);
			}

			data = null;
			data = new Object[searchResult.size()][title.length];

			for (int i = 0; i < searchResult.size(); i++) {
				for (int j = 0; j < title.length; j++)
					data[i][j] = searchResult.get(i).toArray()[j];
			}
		} else {
			inventory = new Inventory();
			data = dbController.getInstruments();
			title = dbController.getColumnNames();

			for (int i = 0; i < data.length; i++) {
				Map properties = new LinkedHashMap();
				for (int j = 2; j < title.length; j++) {
					properties.put(title[j], data[i][j]);
				}
					
				BookProperties spec = new BookProperties(properties);
				inventory.addBook(Integer.parseInt((String)data[i][1]), spec);
			}			
		}

		/********************** 테이블 수정 불가 ****************************/
		defaultTableModel = new DefaultTableModel(data, title) {
			public boolean isCellEditable(int i, int c) {
				if (c != 0)
					return false;
				else
					return true;
			}
		};

		/********************** 테이블 설정 ****************************/
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
								else if(defaultTableModel.getValueAt(row, i) instanceof Integer)
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

	/********************** 악기 속성들로 검색 ****************************/
	public void searchTable(BookProperties spec) {
		ArrayList<Book> searchResult;

		searchResult = defaultInventory.search(spec);

		updateTable(searchResult);
	}

	/************ 화면 오른쪽에 있는 속성들을 읽어서 InstrumentSpec 객체로 생성 ************/
	public BookProperties getSpecField() {
		Map properties = new LinkedHashMap();
		for (int i = 0; i < components.size() - 1; i++) {
			Object component = components.get(i + 1);
			System.out.println("getSpecField : " + title[i + 2] + " : " + ((JTextField)component).getText());
			if (component instanceof JComboBox)
				properties.put(title[i + 2], ((JComboBox) component).getSelectedItem());
			else if (component instanceof JTextField) {
				properties.put(title[i + 2], ((JTextField) component).getText());
			}

		}

		BookProperties spec = new BookProperties(properties);
		return spec;
	}

	/********************** 화면 오른쪽에 있는 속성들을 비워주기 ****************************/
	private void clearSpecField() {
		for (Object component : components) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
			else if (component instanceof JComboBox)
				((JComboBox) component).setSelectedItem("");
		}
	}

	/********************** 검색 중인 지 판별 ****************************/
	public boolean isSearching() {
		return isSearching;
	}
	
	public void login() {
		isLoggedin = true;
		btnDelete.setVisible(true);
		btnInsert.setVisible(true);
		btnLogin.setVisible(false);
		btnLogout.setVisible(true);
		btnEdit.setVisible(true);
		revalidate();
	}
	
	public void logout() {
		isLoggedin = false;
		btnDelete.setVisible(false);
		btnInsert.setVisible(false);
		btnLogin.setVisible(true);
		btnLogout.setVisible(false);
		btnEdit.setVisible(false);
		revalidate();
	}
}