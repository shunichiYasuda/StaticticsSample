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
import statistics.Stat;

public class SampleController {
	// フィールド数
	int numOfField;
	// データ数
	int numOfData;
	// データテーブル
	double[][] dataTable;
	// フィールド名レコード
	String[] fieldNameRecord;
	// データ個票名レコード
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
		FileChooser fc = new FileChooser();
		fc.setTitle("Open data file");
		fc.setInitialDirectory(new File("."));
		File file = fc.showOpenDialog(null);
		log.appendText("data file is set:" + file.getAbsolutePath() + "\n");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
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
		idNameRecord = new String[theList.size() - 1];
		for (int i = 1; i < theList.size(); i++) {
			idNameRecord[i - 1] = theList.get(i).split(",")[0];
		}
		numOfData = idNameRecord.length;
		//
		dataTable = new double[numOfData][numOfField - 1];
		for (int i = 0; i < dataTable.length; i++) {
			String tmpStr[] = theList.get(i + 1).split(",");
			for (int j = 0; j < dataTable[0].length; j++) {
				double v = Double.parseDouble(tmpStr[j + 1]);
				dataTable[i][j] = v;
			}
		}
		//
	}

	@FXML
	private void execAction() {
		//
		for (double[] itemA : dataTable) {
			for (double v : itemA) {
				log.appendText("\t" + v);
			}
			log.appendText("\n");
		}
		printLine("標準得点表");
		// 標準得点表
		double[][] stdScoreTable = Stat.standardize(dataTable);
		showTable(stdScoreTable);
		//変数の数
		int numOfVariables = dataTable[0].length;
		//平均値・標準偏差
		log.appendText("平均値：");
		for(int i=0;i<numOfVariables;i++) {
			double[] a = Stat.getColumn(i, dataTable);
			double ave = Stat.ave(a);
			log.appendText("\t"+ave);
		}
		log.appendText("\n");
		log.appendText("標準偏差:");
		for(int i=0;i<numOfVariables;i++) {
			double[] a = Stat.getColumn(i, dataTable);
			double dev = Stat.stdDev(a);
			log.appendText("\t"+dev);
		}
		log.appendText("\n");
		// 分散共分散行列
		double[][] covariMat =Stat.covarianceMatrix(dataTable);
		printLine("分散・共分散行列");
		showTable(covariMat);
		printLine("相関行列");
		for(int i=0;i<numOfVariables;i++) {
			double[] x = Stat.getColumn(i, dataTable);
			double sigma_x = Stat.stdDev(x);
			for(int j=0;j<numOfVariables;j++) {
				double[] y = Stat.getColumn(j, dataTable);
				double covar = Stat.covariate(x, y);
				double sigma_y = Stat.stdDev(y);
				covariMat[i][j] = covar/(sigma_x*sigma_y);
			}
		}
		//
		showTable(covariMat);
		printLine("相関行列2");
		covariMat =Stat.correlationMatrix(dataTable);
		showTable(covariMat);



	}// end of execAction()
		//

	private void printLine(String string) {
		log.appendText("----------" + string + "----------\n");
	}

	//
	double round(double in, int scale) {
		BigDecimal bd = BigDecimal.valueOf(in);
		double r = bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
		return r;
	}

	void showTable(double[][] in) {
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				double v = in[i][j];
				log.appendText("\t" + round(v, 3));
			}
			log.appendText("\n");
		}
	}
} // end of class SampleController{}
