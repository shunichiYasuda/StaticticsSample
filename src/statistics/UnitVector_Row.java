package statistics;

public class UnitVector_Row extends CVector_Row {
	public UnitVector_Row(int n) {
		super(n);
		this.setValue(0,1.0);
	}
	public UnitVector_Row(int n,int pos) {
		super(n);
		this.setValue(pos, 1.0);
	}
}
