package statistics;

public class CVector_Col {
	private int dim; // ��x�N�g���̎���
	private double norm; // �m����
	private double[][] mat; // n�s1��̍s��Ƃ��Ď���
	//

	public CVector_Col(int n) {
		this.dim = n;
		this.mat = new double[n][1];
		this.norm();
	}

	//
	public CVector_Col(double[] in) {
		this.dim = in.length;
		this.mat = new double[this.dim][1];
		for (int i = 0; i < this.dim; i++) {
			this.mat[i][0] = in[i];
		}
		this.norm();
	}
	
	//
	public CVector_Col(CVector_Col in) {
		this.dim = in.dim;
		this.mat = new double[this.dim][1];
		for(int i=0;i<this.dim;i++) {
			this.mat[i][0] = in.mat[i][0];
		}
		this.norm();
	}

	//
	public void norm() {
		double r = 0.0;
		for (int i = 0; i < this.dim; i++) {
			r += this.mat[i][0] * this.mat[i][0];
		}
		r = Math.sqrt(r);
		this.norm = r;
	}

	// �]�u
	public CVector_Row transpose() {
		CVector_Row r = new CVector_Row(this.getArray());
		return r;
	}

	// �����Z�E�x�N�g�����m
	public CMatrix byVec(CVector_Row in) {
		int num_row = this.dim; // ���̃x�N�g���̎���
		int num_col = in.getDim(); // ���̍s�x�N�g���̎���
		CMatrix r = new CMatrix(num_row, num_col);
		for (int i = 0; i < num_row; i++) {
			for (int j = 0; j < num_col; j++) {
				r.setValue(i, j, this.getValue(i) * in.getValue(j));
			}
		}
		return r;
	}
	//�X�J���[�{
	public CVector_Col byScalar(double d) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] *= d;
		}
		r.norm();
		return r;
	}
	// �������g���X�J���[�{
	public void byScalarThis(double d) {
		for(int i=0;i<this.dim;i++) {
			this.mat[i][0] *= d;
		}
		this.norm();
	}
	//�X�J���[�̑����Z
	public CVector_Col addScalar(double d) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] += d;
		}
		r.norm();
		return r;
	}
	//�x�N�g���̑����Z
	public CVector_Col addVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] += in.mat[i][0];
		}
		r.norm();
		return r;
	}
	//�x�N�g���̈����Z
	public CVector_Col subtractVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] -= in.mat[i][0];
		}
		r.norm();
		return r;
	}
	//���K�����ꂽ�x�N�g����Ԃ��B
	public CVector_Col normalize() {
		CVector_Col r = new CVector_Col(this.dim);
		double n = this.norm;
		for (int i = 0; i < this.dim; i++) {
			double v = this.getValue(i) / n;
			r.setValue(i, v);
		}
		return r;
	}
	//�������g�𐳋K������
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

	// ��k�Ԗڂ̗v�f��Ԃ�
	public double getValue(int k) {
		return this.mat[k][0];
	}

	// double[] �Ƃ��ĕԂ�
	public double[] getArray() {
		double[] r = new double[this.dim];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[i][0];
		}
		return r;
	}

	// �m������Ԃ�
	public double getNorm() {
		return this.norm;
	}
	//�m������2���Ԃ�
	public double getNormSqare() {
		return this.norm*this.norm;
	}
	//�x�N�g���v�f�̍ő�v�f�̒l��Ԃ�
	public double getMax() {
		int pos = this.hwMaxPos();
		return this.getValue(pos);
	}
	
	// setter
	// �� i �v�f�ɒl������
	public void setValue(int i, double d) {
		this.mat[i][0] = d;
		this.norm();
	}

	// double[] �Œ��g�����ւ���
	public void setValue(double[] in) {
		for (int i = 0; i < this.dim; i++) {
			this.mat[i][0] = in[i];
		}
		this.norm();
	}

	// �ő�v�f�̏ꏊ�i�s�ԍ��j��Ԃ��B
	public int hwMaxPos() {
		int max = 0;
		for (int i = 0; i < this.dim; i++) {
			double maxValue = Math.abs(this.mat[max][0]);
			double value = Math.abs(this.mat[i][0]);
			if (maxValue < value)
				max = i;
		}
		return max;
	}
	//��k�s�ȉ��ōő�v�f�̍s�ԍ���Ԃ��B
	public int hwMaxPos(int k) {
		int max = k;
		for (int i = k; i < this.dim; i++) {
			double maxValue = Math.abs(this.mat[max][0]);
			double value = Math.abs(this.mat[i][0]);
			if (maxValue < value)
				max = i;
		}
		return max;
	}
}
