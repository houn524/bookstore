import java.awt.Label;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class MandolinPanel extends InstrumentPanel {

	public MandolinPanel() {
		super(InstrumentType.MANDOLIN);
		// TODO Auto-generated constructor stub

		JComboBox cbStyle = new JComboBox();
		cbStyle.setEditable(true);
		for (Style style : Style.values())
			cbStyle.addItem(style.toString());

		JLabel label = new JLabel("Style : ", Label.LEFT);
		super.editSpecPane.add(label);
		super.editSpecPane.add(cbStyle);

		super.components.add(cbStyle);
	}

}
