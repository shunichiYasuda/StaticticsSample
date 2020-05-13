package statistics;

public class UnitVector_Col extends CVector_Col {
	public UnitVector_Col(int n) {
		super(n);
		this.setValue(0, 1.0);
	}
	public UnitVector_Col(int n,int pos) {
		super(n);
		this.setValue(pos,1.0);
	}
}
