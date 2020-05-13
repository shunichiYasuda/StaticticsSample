package statistics;

public class CVector_Row {
	private int dim; // ��x�N�g���̎���
	private double norm; // �m����
	private double[][] mat; // 1�sn��̍s��Ƃ��Ď���

	public CVector_Row(int n) {
		this.dim = n;
		this.mat = new double[1][n];
		norm();
	}

	//
	public CVector_Row(double[] in) {
		this.dim = in.length;
		this.mat = new double[1][this.dim];
		for (int i = 0; i < this.dim; i++) {
			this.mat[0][i] = in[i];
		}
		norm();
	}

	//
	public CVector_Row(CVector_Row in) {
		this.dim = in.dim;
		this.mat = new double[1][this.dim];
		for (int j = 0; j < this.dim; j++) {
			this.mat[0][j] = in.mat[0][j];
		}
		this.norm();
	}

	//
	public void norm() {
		double r = 0.0;
		for (int i = 0; i < this.dim; i++) {
			r += this.mat[0][i] * this.mat[0][i];
		}
		r = Math.sqrt(r);
		this.norm = r;
	}

	// �]�u
	public CVector_Col transpose() {
		CVector_Col r = new CVector_Col(this.getArray());
		return r;
	}

	// �����Z��납���x�N�g���B����
	public double byVec(CVector_Col in) {
		int num_row = this.dim; // ���̃x�N�g���̎���
		// �������Ⴄ�ƌv�Z�ł��Ȃ��̂Ŏ����͂ЂƂ����ł悢
		double d = 0.0;
		for (int i = 0; i < num_row; i++) {
			d += this.getValue(i) * in.getValue(i);
		}
		return d;
	}

	// �s�x�N�g�������s�� (1,p)(p,n) =(1,n)
	// ���ʂ͍s�x�N�g��
	public CVector_Row byMat(CMatrix in) {
		// ���ʃx�N�g���̗񐔂͍s��̗�
		CVector_Row r = new CVector_Row(in.colN);
		// �s�� in ����O���珇�Ԃɗ�x�N�g�������o��
		// ���̗�x�N�g���Ƃ��̍s�x�N�g���̂����Z�������Ȃ� r �̗v�f�ɂ���
		for (int j = 0; j < in.colN; j++) {
			double v = this.byVec(in.getCol(j));
			r.mat[0][j] = v;
		}
		r.norm();
		return r;
	}

	// �x�N�g���̑����Z
	public CVector_Row addVec(CVector_Row in) {
		CVector_Row r = new CVector_Row(this);
		for (int j = 0; j < r.dim; j++) {
			r.mat[0][j] += in.mat[0][j];
		}
		r.norm();
		return r;
	}

	// �ő�v�f�̏ꏊ�i��ԍ��j��Ԃ��B
	public int hwMaxPos() {
		int max = 0;
		for (int j = 0; j < this.dim; j++) {
			double maxValue = Math.abs(this.mat[0][max]);
			double value = Math.abs(this.mat[0][j]);
			if (maxValue < value)
				max = j;
		}
		return max;
	}

	//
	// �X�J���[�{
	public CVector_Row byScalar(double d) {
		CVector_Row r = new CVector_Row(this);
		for (int j = 0; j < r.dim; j++) {
			r.mat[0][j] *= d;
		}
		r.norm();
		return r;
	}

	// �������g���X�J���[�{
	public void byScalarThis(double d) {
		for (int j = 0; j < this.dim; j++) {
			this.mat[0][j] *= d;
		}
		this.norm();
	}
	//
	public CVector_Row addScalar(double d) {
		CVector_Row r = new CVector_Row(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] += d;
		}
		r.norm();
		return r;
	}

	// �����Z
	public CVector_Row subtractVec(CVector_Row in) {
		CVector_Row r = new CVector_Row(this);
		for (int i = 0; i < r.dim; i++) {
			r.mat[0][i] -= in.mat[0][i];
		}
		r.norm();
		return r;
	}

	// ���K�������x�N�g����Ԃ��B
	public CVector_Row normalize() {
		CVector_Row r = new CVector_Row(this.dim);
		double n = this.norm;
		for (int i = 0; i < this.dim; i++) {
			double v = this.getValue(i) / n;
			r.setValue(i, v);
		}
		return r;
	}

	// �������g�𐳋K������
	public void normalizeThis() {
		double n = this.norm;
		for (int i = 0; i < this.dim; i++) {
			double v = this.getValue(i) / n;
			this.setValue(i, v);
		}
	}

	// getter
	public int getDim() {
		return this.dim;
	}

	// double[] �Ƃ��ĕԂ��B
	public double[] getArray() {
		double[] r = new double[this.dim];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[0][i];
		}
		return r;
	}

	// �悋�Ԗڂ̗v�f��Ԃ�
	public double getValue(int k) {
		return this.mat[0][k];
	}

	// �m������Ԃ�
	public double getNorm() {
		return this.norm;
	}

	// �m������2���Ԃ�
	public double getNormSqare() {
		return this.norm * this.norm;
	}

	// �x�N�g���v�f�̍ő�l��Ԃ�
	public double getMax() {
		int pos = this.hwMaxPos();
		return this.getValue(pos);
	}

	// setter
	// �� j �v�f�ɒl������
	public void setValue(int j, double d) {
		this.mat[0][j] = d;
		this.norm();
	}

	// double[] �Œl��u��������
	public void setValue(double[] in) {
		for (int j = 0; j < this.dim; j++) {
			this.mat[0][j] = in[j];
		}
		this.norm();
	}

}
