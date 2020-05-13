package statistics;

public class CVector_Row {
	private int dim; // 列ベクトルの次数
	private double norm; // ノルム
	private double[][] mat; // 1行n列の行列として実装

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

	// 転置
	public CVector_Col transpose() {
		CVector_Col r = new CVector_Col(this.getArray());
		return r;
	}

	// かけ算後ろから列ベクトル。内積
	public double byVec(CVector_Col in) {
		int num_row = this.dim; // このベクトルの次数
		// 次数が違うと計算できないので次数はひとつだけでよい
		double d = 0.0;
		for (int i = 0; i < num_row; i++) {
			d += this.getValue(i) * in.getValue(i);
		}
		return d;
	}

	// 行ベクトルかけ行列 (1,p)(p,n) =(1,n)
	// 結果は行ベクトル
	public CVector_Row byMat(CMatrix in) {
		// 結果ベクトルの列数は行列の列数
		CVector_Row r = new CVector_Row(in.colN);
		// 行列 in から前から順番に列ベクトルを取り出す
		// その列ベクトルとこの行ベクトルのかけ算をおこない r の要素にする
		for (int j = 0; j < in.colN; j++) {
			double v = this.byVec(in.getCol(j));
			r.mat[0][j] = v;
		}
		r.norm();
		return r;
	}

	// ベクトルの足し算
	public CVector_Row addVec(CVector_Row in) {
		CVector_Row r = new CVector_Row(this);
		for (int j = 0; j < r.dim; j++) {
			r.mat[0][j] += in.mat[0][j];
		}
		r.norm();
		return r;
	}

	// 最大要素の場所（列番号）を返す。
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
	// スカラー倍
	public CVector_Row byScalar(double d) {
		CVector_Row r = new CVector_Row(this);
		for (int j = 0; j < r.dim; j++) {
			r.mat[0][j] *= d;
		}
		r.norm();
		return r;
	}

	// 自分自身をスカラー倍
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

	// 引き算
	public CVector_Row subtractVec(CVector_Row in) {
		CVector_Row r = new CVector_Row(this);
		for (int i = 0; i < r.dim; i++) {
			r.mat[0][i] -= in.mat[0][i];
		}
		r.norm();
		return r;
	}

	// 正規化したベクトルを返す。
	public CVector_Row normalize() {
		CVector_Row r = new CVector_Row(this.dim);
		double n = this.norm;
		for (int i = 0; i < this.dim; i++) {
			double v = this.getValue(i) / n;
			r.setValue(i, v);
		}
		return r;
	}

	// 自分自身を正規化する
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

	// double[] として返す。
	public double[] getArray() {
		double[] r = new double[this.dim];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[0][i];
		}
		return r;
	}

	// 第ｋ番目の要素を返す
	public double getValue(int k) {
		return this.mat[0][k];
	}

	// ノルムを返す
	public double getNorm() {
		return this.norm;
	}

	// ノルムの2乗を返す
	public double getNormSqare() {
		return this.norm * this.norm;
	}

	// ベクトル要素の最大値を返す
	public double getMax() {
		int pos = this.hwMaxPos();
		return this.getValue(pos);
	}

	// setter
	// 第 j 要素に値を入れる
	public void setValue(int j, double d) {
		this.mat[0][j] = d;
		this.norm();
	}

	// double[] で値を置き換える
	public void setValue(double[] in) {
		for (int j = 0; j < this.dim; j++) {
			this.mat[0][j] = in[j];
		}
		this.norm();
	}

}
