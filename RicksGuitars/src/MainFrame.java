import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	private InstrumentPanel guitarPane, mandolinPane;

	public MainFrame(String title) {
		setTitle(title);
		setSize(1200, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTabbedPane();
	}

	private void createTabbedPane() {
		JTabbedPane tPane = new JTabbedPane();
		add(tPane);

		guitarPane = new GuitarPanel();
		tPane.addTab("Guitar", guitarPane);

		mandolinPane = new MandolinPanel();
		tPane.addTab("Mandolin", mandolinPane);
	}

}
