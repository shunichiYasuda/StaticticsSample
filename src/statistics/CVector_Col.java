package statistics;

public class CVector_Col {
	private int dim; // 列ベクトルの次数
	private double norm; // ノルム
	private double[][] mat; // n行1列の行列として実装
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

	// 転置
	public CVector_Row transpose() {
		CVector_Row r = new CVector_Row(this.getArray());
		return r;
	}

	// かけ算・ベクトル同士
	public CMatrix byVec(CVector_Row in) {
		int num_row = this.dim; // このベクトルの次数
		int num_col = in.getDim(); // 後ろの行ベクトルの次数
		CMatrix r = new CMatrix(num_row, num_col);
		for (int i = 0; i < num_row; i++) {
			for (int j = 0; j < num_col; j++) {
				r.setValue(i, j, this.getValue(i) * in.getValue(j));
			}
		}
		return r;
	}
	//スカラー倍
	public CVector_Col byScalar(double d) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] *= d;
		}
		r.norm();
		return r;
	}
	// 自分自身をスカラー倍
	public void byScalarThis(double d) {
		for(int i=0;i<this.dim;i++) {
			this.mat[i][0] *= d;
		}
		this.norm();
	}
	//スカラーの足し算
	public CVector_Col addScalar(double d) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] += d;
		}
		r.norm();
		return r;
	}
	//ベクトルの足し算
	public CVector_Col addVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] += in.mat[i][0];
		}
		r.norm();
		return r;
	}
	//ベクトルの引き算
	public CVector_Col subtractVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this);
		for(int i=0;i<r.dim;i++) {
			r.mat[i][0] -= in.mat[i][0];
		}
		r.norm();
		return r;
	}
	//正規化されたベクトルを返す。
	public CVector_Col normalize() {
		CVector_Col r = new CVector_Col(this.dim);
		double n = this.norm;
		for (int i = 0; i < this.dim; i++) {
			double v = this.getValue(i) / n;
			r.setValue(i, v);
		}
		return r;
	}
	//自分自身を正規化する
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

	// 第k番目の要素を返す
	public double getValue(int k) {
		return this.mat[k][0];
	}

	// double[] として返す
	public double[] getArray() {
		double[] r = new double[this.dim];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[i][0];
		}
		return r;
	}

	// ノルムを返す
	public double getNorm() {
		return this.norm;
	}
	//ノルムの2乗を返す
	public double getNormSqare() {
		return this.norm*this.norm;
	}
	//ベクトル要素の最大要素の値を返す
	public double getMax() {
		int pos = this.hwMaxPos();
		return this.getValue(pos);
	}
	
	// setter
	// 第 i 要素に値を入れる
	public void setValue(int i, double d) {
		this.mat[i][0] = d;
		this.norm();
	}

	// double[] で中身を入れ替える
	public void setValue(double[] in) {
		for (int i = 0; i < this.dim; i++) {
			this.mat[i][0] = in[i];
		}
		this.norm();
	}

	// 最大要素の場所（行番号）を返す。
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
	//第k行以下で最大要素の行番号を返す。
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
