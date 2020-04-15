package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class SampleController {
	//フィールド数
	int numOfField;
	//データ数
	int numOfData;
	//データテーブル
	double[][] dataTable;
	//フィールド名レコード
	String[] fieldNameRecord;
	//データ個票名レコード
	String[] idNameRecord;
	@FXML
	TextArea log = new TextArea();
	@FXML
	private void quitAction() {
		System.exit(0);
	}
	@FXML
	private void openAction() {
		List<String> theList = new ArrayList<String>();
		FileChooser fc =new FileChooser();
		fc.setTitle("Open data file");
		fc.setInitialDirectory(new File("."));
		File file = fc.showOpenDialog(null);
		log.appendText("data file is set:"+file.getAbsolutePath()+"\n");
		try {
			FileReader fr =new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line =null;
			while((line=br.readLine())!=null) {
				theList.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		fieldNameRecord = theList.get(0).split(",");
		numOfField = fieldNameRecord.length;
		idNameRecord = new String[theList.size()-1];
		for(int i=1;i<theList.size();i++) {
			idNameRecord[i-1] = theList.get(i).split(",")[0];
		}
		numOfData = idNameRecord.length;
		//
		dataTable = new double[numOfData][numOfField-1];
		for(int i=0;i<dataTable.length;i++) {
			String tmpStr[] = theList.get(i+1).split(",");
			for(int j=0;j<dataTable[0].length;j++) {
				double v = Double.parseDouble(tmpStr[j+1]);
				dataTable[i][j] = v;
			}
		}
		//
	}
	@FXML
	private void execAction() {
		for(String s : fieldNameRecord) {
			log.appendText(s+"\n");
		}
		//
		for(String s : idNameRecord) {
			log.appendText(s+"\n");
		}
		//
		for(double[] itemA : dataTable) {
			for(double v : itemA) {
				log.appendText("\t"+v);
			}
			log.appendText("\n");
		}
		//1列ずつ平均・分散を計算
		log.appendText("\n");
		for(int j=0;j<dataTable[0].length;j++) {
			log.appendText("\t"+fieldNameRecord[j+1]);
			//1列のデータ配列
			double[] theCol = getColumn(j,dataTable);
			//平均値を計算
			double a = ave(theCol);
			//表示
			log.appendText("\t"+a);
			//標準偏差を計算
			a = stdDev(theCol);
			//表示
			log.appendText("\t"+a+"\n");
			//
			double[] std = standardize(theCol);
			//平均値を計算
			a = round(ave(std),3);
			//表示
			log.appendText("\t"+a);
			//標準偏差を計算
			a = round(stdDev(std),3);
			//表示
			log.appendText("\t"+a+"\n");
		}
		log.appendText("\n");
		//標準得点表
		double[][] stdScoreTable = standardize(dataTable);
		showTable(stdScoreTable);
	}// end of execAction()
	//
	//some methods
	double[] getColumn(int point, double[][] table) {
		double[] r= new double[table.length];
		for(int i=0;i<r.length;i++) {
			r[i]= table[i][point];
		}
		return r;
	}
	//
	double[] getRow(int point,double[][] table) {
		double[] r = new double[table[0].length];
		for(int j=0;j<r.length;j++) {
			r[j] = table[point][j];
		}
		return r;
	}
	//
	void showTable(double[][] in) {
		for( double[] item : in) {
			for(double v : item) {
				v = round(v,3);
				log.appendText("\t"+ v);
			}
			log.appendText("\n");
		}
	}
	//表示する有効桁数の指定
	double round(double in, int n) {
		double r;
		BigDecimal bd = new BigDecimal(in);
		bd = bd.setScale(n, RoundingMode.HALF_UP);
		r = bd.doubleValue();
		return r;
	}
	//some statistics
	double ave(double[] in) {
		double r = 0;
		double sum = 0;
		for(int i=0;i<in.length;i++) {
			sum += in[i];
		}
		r = sum / (double)in.length;
		return r;
	}
	//
	double var(double[] in) {
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
	double unbiasedVar(double[] in) {
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
	double[] deviate(double[] in) {
		double[] r = new double[in.length];
		double a = ave(in);
		for(int i=0;i<r.length;i++) {
			r[i] = in[i] - a;
		}
		return r;
	}
	//standard deviation
	double stdDev(double[] in) {
		double r = 0;
		double v = var(in);
		r = Math.sqrt(v);
		return r;
	}
	//standard score
	double[] standardize(double[] in) {
		double[] r = new double[in.length];
		double sd = stdDev(in);
		double [] d = deviate(in);
		for(int i=0;i<r.length;i++) {
			r[i] = d[i]/sd;
		}
		return r;
	}
	//標準得点表に変換する
	double[][] standardize(double[][] in){
		double[][] r = new double[in.length][in[0].length];
		//in の1列ずつ処理
		for(int j=0;j<r[0].length;j++) {
			double[] tmp = new double[r.length];
			tmp = standardize(getColumn(j,in));
			for(int i=0;i<r.length;i++) {
				r[i][j] = tmp[i];
			}
		}
		return r;
	}
} //end of class SampleController{}
