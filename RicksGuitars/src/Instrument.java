/* ���� : Instrument.java			*
 * ����� : ����Ʈ��������μ���			*
 * ���� : �Ǳ��� ������ ��� Ŭ����			*/

import java.util.ArrayList;

public class Instrument {

	private String serialNumber;
	private double price;
	private InstrumentSpec spec;

	public Instrument(String serialNumber, double price, InstrumentSpec spec) {
		this.serialNumber = serialNumber;
		this.price = price;
		this.spec = spec;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(float newPrice) {
		this.price = newPrice;
	}

	public InstrumentSpec getSpec() {
		return spec;
	}

	/********************** �Ǳ��� �Ӽ����� �迭�� ���� ��ȯ ****************************/
	public Object[] toArray() {
		ArrayList specList = new ArrayList(spec.getProperties().values());

		Object[] data = new Object[specList.size() + 3];
		data[0] = false;
		data[1] = serialNumber;
		data[2] = price;

		for (int i = 0; i < spec.getProperties().size(); i++) {
			data[i + 3] = specList.get(i);
		}
		return data;
	}
}