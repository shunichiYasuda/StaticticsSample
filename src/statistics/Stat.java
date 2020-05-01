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

	// •W€“¾“_•\‚É•ÏŠ·‚·‚é
	public static double[][] standardize(double[][] in) {
		double[][] r = new double[in.length][in[0].length];
		// in ‚Ì1—ñ‚¸‚Âˆ—
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

	// •ªŽUE‹¤•ªŽUs—ñ‚ð•Ô‚·
	public static double[][] covarianceMatrix(double[][] in) {
		// •Ï”‚Ì”
		int numOfVariables = in[0].length;
		// •ªŽU‹¤•ªŽUs—ñ
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
	//‘ŠŠÖs—ñ‚ð•Ô‚·
	public static double[][] correlationMatrix(double[][] in){
		double[][] stdScoreTable = Stat.standardize(in);
		//•W€‰»‚³‚ê‚½“¾“_•\‚Ì•ªŽUE‹¤•ªŽUs—ñ‚ª‘ŠŠÖs—ñ
		return Stat.covarianceMatrix(stdScoreTable);
	}
}
