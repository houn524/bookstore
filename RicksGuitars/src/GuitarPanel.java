/* ���� : GuitarPanel.java			*
 * ����� : ����Ʈ��������μ���				*
 * ���� : ���α׷��� Guitar ���� �г�			*
 * 		IntrumentPanel�� ��ӹ޴´�.		*/

import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JTextField;

/********************** ��Ÿ �г� ���� ****************************/
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