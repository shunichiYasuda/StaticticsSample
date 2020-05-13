package statistics;

public class Stat {
	public static double ave(double[] in) {
		double r = 0;
		double sum = 0;
		for (int i = 0; i < in.length; i++) {
			sum += in[i];
		}
		r = sum / (double) in.length;
		return r;
	}
	//ベクトルバージョン
	public static double ave(CVector_Col in) {
		double r = 0;
		double sum = 0;
		for (int i = 0; i < in.getDim(); i++) {
			sum += in.getValue(i);
		}
		r = sum / (double) in.getDim();
		return r;
	}
	//ベクトルバージョン　行ベクトル
	public static double ave(CVector_Row in) {
		double r = 0;
		double sum = 0;
		for (int i = 0; i < in.getDim(); i++) {
			sum += in.getValue(i);
		}
		r = sum / (double) in.getDim();
		return r;
	}

	//
	public static double var(double[] in) {
		double v = 0;
		double[] dev = deviate(in);
		double sum = 0;
		for (int i = 0; i < dev.length; i++) {
			sum += dev[i] * dev[i];
		}
		v = sum / (double) dev.length;
		return v;
	}
	//ベクトルバージョン
	public static double var(CVector_Col in) {
		double v = 0;
		double[] d = in.getArray();
		double[] dev = deviate(d);
		double sum = 0;
		for (int i = 0; i < dev.length; i++) {
			sum += dev[i] * dev[i];
		}
		v = sum / (double) dev.length;
		return v;
	}
	public static double car(CVector_Row in) {
		double v = 0;
		double[] d = in.getArray();
		double[] dev = deviate(d);
		double sum = 0;
		for (int i = 0; i < dev.length; i++) {
			sum += dev[i] * dev[i];
		}
		v = sum / (double) dev.length;
		return v;
	}

	//
	public static double unbiasedVar(double[] in) {
		double v = 0;
		double[] dev = deviate(in);
		double sum = 0;
		for (int i = 0; i < dev.length; i++) {
			sum += dev[i] * dev[i];
		}
		v = sum / (double) (dev.length - 1);
		return v;
	}

	// deviation
	public static double[] deviate(double[] in) {
		double[] r = new double[in.length];
		double a = ave(in);
		for (int i = 0; i < r.length; i++) {
			r[i] = in[i] - a;
		}
		return r;
	}
	//ベクトルバージョン
	public static CVector_Col deviate(CVector_Col in) {
		CVector_Col r = new CVector_Col(in);
		double a = ave(r);
		for (int i = 0; i < r.getDim(); i++) {
			r.addScalar((-1)*a);
		}
		return r;
	}
	//
	public static CVector_Row deviate(CVector_Row in) {
		CVector_Row r = new CVector_Row(in);
		double a = ave(r);
		for (int i = 0; i < r.getDim(); i++) {
			r.addScalar((-1)*a);
		}
		return r;
	}

	// covariate (x,y)
	public static double covariate(double[] x, double[] y) {
		int size = x.length;
		double r = 0.0;
		double[] dev_x = deviate(x);
		double[] dev_y = deviate(y);
		for (int i = 0; i < size; i++) {
			r += dev_x[i] * dev_y[i];
		}
		r = r / x.length;
		return r;
	}

	// standard deviation
	public static double stdDev(double[] in) {
		double r = 0;
		double v = var(in);
		r = Math.sqrt(v);
		return r;
	}

	// standard score
	public static double[] standardize(double[] in) {
		double[] r = new double[in.length];
		double sd = stdDev(in);
		double[] d = deviate(in);
		for (int i = 0; i < r.length; i++) {
			r[i] = d[i] / sd;
		}
		return r;
	}

	// 標準得点表に変換する
	public static double[][] standardize(double[][] in) {
		double[][] r = new double[in.length][in[0].length];
		// in の1列ずつ処理
		for (int j = 0; j < r[0].length; j++) {
			double[] tmp = new double[r.length];
			tmp = standardize(getColumn(j, in));
			for (int i = 0; i < r.length; i++) {
				r[i][j] = tmp[i];
			}
		}
		return r;
	}

	//
	public static double[] getColumn(int point, double[][] table) {
		double[] r = new double[table.length];
		for (int i = 0; i < r.length; i++) {
			r[i] = table[i][point];
		}
		return r;
	}

	//
	public static double[] getRow(int point, double[][] table) {
		double[] r = new double[table[0].length];
		for (int j = 0; j < r.length; j++) {
			r[j] = table[point][j];
		}
		return r;
	}

	// 分散・共分散行列を返す
	public static double[][] covarianceMatrix(double[][] in) {
		// 変数の数
		int numOfVariables = in[0].length;
		// 分散共分散行列
		double[][] covariMat = new double[numOfVariables][numOfVariables];
		for (int i = 0; i < numOfVariables; i++) {
			double[] x = Stat.getColumn(i, in);
			for (int j = 0; j < numOfVariables; j++) {
				double[] y = Stat.getColumn(j, in);
				covariMat[i][j] = Stat.covariate(x, y);
			}
		}
		//
		return covariMat;
	}
	//相関行列を返す
	public static double[][] correlationMatrix(double[][] in){
		double[][] stdScoreTable = Stat.standardize(in);
		//標準化された得点表の分散・共分散行列が相関行列
		return Stat.covarianceMatrix(stdScoreTable);
	}
}
