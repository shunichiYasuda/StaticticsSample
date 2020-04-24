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
	//�t�B�[���h��
	int numOfField;
	//�f�[�^��
	int numOfData;
	//�f�[�^�e�[�u��
	double[][] dataTable;
	//�t�B�[���h�����R�[�h
	String[] fieldNameRecord;
	//�f�[�^�[�����R�[�h
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
		//1�񂸂��ρE���U���v�Z
		log.appendText("\n");
		for(int j=0;j<dataTable[0].length;j++) {
			log.appendText("\t"+fieldNameRecord[j+1]);
			//1��̃f�[�^�z��
			double[] theCol = Stat.getColumn(j,dataTable);
			//���ϒl���v�Z
			double a = Stat.ave(theCol);
			//�\��
			log.appendText("\t"+a);
			//�W���΍����v�Z
			a = Stat.stdDev(theCol);
			//�\��
			log.appendText("\t"+a+"\n");
			//
			double[] std = Stat.standardize(theCol);
			//���ϒl���v�Z
			a = round(Stat.ave(std),3);
			//�\��
			log.appendText("\t"+a);
			//�W���΍����v�Z
			a = round(Stat.stdDev(std),3);
			//�\��
			log.appendText("\t"+a+"\n");
		}
		log.appendText("\n");
		//�W�����_�\
		double[][] stdScoreTable = Stat.standardize(dataTable);
		showTable(stdScoreTable);
	}// end of execAction()
	//
	double round(double in, int scale) {
		BigDecimal bd = BigDecimal.valueOf(in);
		double r =bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
		return r;
	}
	void showTable(double[][] in ) {
		for(int i= 0;i<in.length;i++) {
			for(int j=0;j<in[0].length;j++) {
				double v = in[i][j];
				log.appendText("\t"+ round(v,3));
			}
			log.appendText("\n");
		}
	}
} //end of class SampleController{}
