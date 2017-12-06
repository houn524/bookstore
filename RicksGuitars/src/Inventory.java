import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

	private List inventory;

	public Inventory() {
		inventory = new LinkedList();
	}

	public void addInstrument(String serialNumber, double price, InstrumentSpec spec) {
		Instrument instrument = new Instrument(serialNumber, price, spec);
		inventory.add(instrument);
	}

	public void addInstrument(Instrument instrument) {
		inventory.add(instrument);
	}

	public void removeInstrument(int row) {
		inventory.remove(row);
	}

	public Instrument get(int row) {
		return (Instrument) inventory.get(row);
	}

	public Instrument get(String serialNumber) {
		for (Iterator i = inventory.iterator(); i.hasNext();) {
			Instrument instrument = (Instrument) i.next();
			if (instrument.getSerialNumber().equals(serialNumber)) {
				return instrument;
			}
		}
		return null;
	}

	public int size() {
		return inventory.size();
	}

	public ArrayList<Instrument> search(InstrumentSpec searchSpec) {
		ArrayList<Instrument> matchingInstruments = new ArrayList<Instrument>();
		for (Iterator i = inventory.iterator(); i.hasNext();) {
			Instrument instrument = (Instrument) i.next();
			if (instrument.getSpec().matches(searchSpec))
				matchingInstruments.add(instrument);
		}
		return matchingInstruments;
	}
}
