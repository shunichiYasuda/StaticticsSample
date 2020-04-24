package statistics;

public class Stat {
	public static double ave(double[] in) {
		double r = 0;
		double sum = 0;
		for(int i=0;i<in.length;i++) {
			sum += in[i];
		}
		r = sum / (double)in.length;
		return r;
	}
	//
	public static double var(double[] in) {
		double v = 0;
		double[] dev = deviate(in);
		double sum = 0;
		for(int i=0;i< dev.length;i++) {
			sum += dev[i]*dev[i];
		}
		v=sum /(double)dev.length;
		return v;
	}
	//
	public static double unbiasedVar(double[] in) {
		double v =  0;
		double[] dev = deviate(in);
		double sum = 0;
		for(int i=0;i< dev.length;i++) {
			sum += dev[i]*dev[i];
		}
		v=sum /(double)(dev.length-1);
		return v;
	}
	//deviation
	public static double[] deviate(double[] in) {
		double[] r = new double[in.length];
		double a = ave(in);
		for(int i=0;i<r.length;i++) {
			r[i] = in[i] - a;
		}
		return r;
	}
	//standard deviation
	public static double stdDev(double[] in) {
		double r = 0;
		double v = var(in);
		r = Math.sqrt(v);
		return r;
	}
	//standard score
	public static double[] standardize(double[] in) {
		double[] r = new double[in.length];
		double sd = stdDev(in);
		double [] d = deviate(in);
		for(int i=0;i<r.length;i++) {
			r[i] = d[i]/sd;
		}
		return r;
	}
	//•W€“¾“_•\‚É•ÏŠ·‚·‚é
	public static double[][] standardize(double[][] in){
		double[][] r = new double[in.length][in[0].length];
		//in ‚Ì1—ñ‚¸‚Âˆ—
		for(int j=0;j<r[0].length;j++) {
			double[] tmp = new double[r.length];
			tmp = standardize(getColumn(j,in));
			for(int i=0;i<r.length;i++) {
				r[i][j] = tmp[i];
			}
		}
		return r;
	}
	//
	public static double[] getColumn(int point, double[][] table) {
		double[] r= new double[table.length];
		for(int i=0;i<r.length;i++) {
			r[i]= table[i][point];
		}
		return r;
	}
	//
	public static double[] getRow(int point,double[][] table) {
		double[] r = new double[table[0].length];
		for(int j=0;j<r.length;j++) {
			r[j] = table[point][j];
		}
		return r;
	}
}
