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
	// �t�B�[���h��
	int numOfField;
	// �f�[�^��
	int numOfData;
	// �f�[�^�e�[�u��
	double[][] dataTable;
	// �t�B�[���h�����R�[�h
	String[] fieldNameRecord;
	// �f�[�^�[�����R�[�h
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
		printLine("�W�����_�\");
		// �W�����_�\
		double[][] stdScoreTable = Stat.standardize(dataTable);
		showTable(stdScoreTable);
		//�ϐ��̐�
		int numOfVariables = dataTable[0].length;
		//���ϒl�E�W���΍�
		log.appendText("���ϒl�F");
		for(int i=0;i<numOfVariables;i++) {
			double[] a = Stat.getColumn(i, dataTable);
			double ave = Stat.ave(a);
			log.appendText("\t"+ave);
		}
		log.appendText("\n");
		log.appendText("�W���΍�:");
		for(int i=0;i<numOfVariables;i++) {
			double[] a = Stat.getColumn(i, dataTable);
			double dev = Stat.stdDev(a);
			log.appendText("\t"+dev);
		}
		log.appendText("\n");
		// ���U�����U�s��
		double[][] covariMat =Stat.covarianceMatrix(dataTable);
		printLine("���U�E�����U�s��");
		showTable(covariMat);
		printLine("���֍s��");
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
		printLine("���֍s��2");
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
