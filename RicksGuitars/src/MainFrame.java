/* 파일 : MainFrame.java				*
 * 과목명 : 소프트웨어개발프로세스			*
 * 서술 : 메인 프레임을 생성해주고 탭을 추가시켜주는 클래스	*/

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/********************** 메인 프레임 ****************************/
public class MainFrame extends JFrame {

	private ObjectPanel guitarPane, mandolinPane;

	public MainFrame(String title) {
		setTitle(title);
		setSize(1200, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTabbedPane();
	}

	/********************** 탭 생성 ****************************/
	private void createTabbedPane() {
		JTabbedPane tPane = new JTabbedPane();
		add(tPane);

		guitarPane = new BookPanel();
		tPane.addTab("Book", guitarPane);

//		mandolinPane = new MandolinPanel();
//		tPane.addTab("Mandolin", mandolinPane);
	}

}
