/* 파일 : GuitarPanel.java			*
 * 과목명 : 소프트웨어개발프로세스				*
 * 서술 : 프로그램의 Guitar 탭의 패널			*
 * 		IntrumentPanel을 상속받는다.		*/

import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JTextField;

/********************** 기타 패널 생성 ****************************/
public class GuitarPanel extends InstrumentPanel {

	public GuitarPanel() {
		super(InstrumentType.GUITAR);
		// TODO Auto-generated constructor stub
		JTextField txtNumStrings = new JTextField(10);
		super.components.add(txtNumStrings);

		JLabel label = new JLabel("NumStrings : ", Label.LEFT);
		super.editSpecPane.add(label);
		super.editSpecPane.add(txtNumStrings);
	}
}