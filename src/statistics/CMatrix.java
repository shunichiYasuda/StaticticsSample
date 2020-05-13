package statistics;

public class CMatrix {
	private double[][] mat;
	public int colN, rowN; // 行数、列数
	public boolean isSquare; // 正方行列か？

	public CMatrix(int m, int n) {
		this.mat = new double[m][n]; // m行ｎ列行列
		this.rowN = m;
		this.colN = n;
		if (m == n) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
		// 基本は単位行列
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					this.mat[i][j] = 1.0;
				} else {
					this.mat[i][j] = 0.0;
				}
			}
		}
	} // end of CMatrix(int , int)

	public CMatrix(double[][] in) {
		this.rowN = in.length;
		this.colN = in[0].length;
		this.mat = new double[this.rowN][this.colN];
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				this.mat[i][j] = in[i][j];
			}
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	} // end of CMatrix(double[][] )

	public CMatrix(CMatrix in) {
		this.rowN = in.rowN;
		this.colN = in.colN;
		this.mat = new double[this.rowN][this.colN];
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				this.mat[i][j] = in.mat[i][j];
			}
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	}

	// 列ベクトルの配列から行列をつくる
	public CMatrix(CVector_Col[] in) {
		this.rowN = in[0].getDim();
		this.colN = in.length;
		this.mat = new double[this.rowN][this.colN];
		for (int j = 0; j < this.colN; j++) {
			this.setCol(j, in[j]);
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	}

	// 行ベクトルの配列から行列をつくる。
	public CMatrix(CVector_Row[] in) {
		this.colN = in[0].getDim();
		this.rowN = in.length;
		this.mat = new double[this.rowN][this.colN];
		for (int i = 0; i < this.rowN; i++) {
			this.setRow(i, in[i]);
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	}

	// 転置
	public CMatrix transpose() {
		CMatrix r = new CMatrix(this.colN, this.rowN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.mat[j][i];
			}
		}
		return r;
	}

	// 縮小行列
	public CMatrix contractMat(int i, int j) {
		// 第i行、第j列を取り去った縮小行列を返す。
		CMatrix r = new CMatrix(this.rowN - 1, this.colN - 1);
		for (int p = 0; p < i; p++) {
			for (int q = 0; q < j; q++) {
				r.mat[p][q] = this.mat[p][q];
			}
		}
		for (int p = i + 1; p < this.rowN; p++) {
			for (int q = j + 1; q < this.colN; q++) {
				r.mat[p - 1][q - 1] = this.mat[p][q];
			}
		}
		return r;
	}

	// 自分自身を縮小する
	public void contractMatThis(int i, int j) {
		double[][] con = new double[this.rowN - 1][this.colN - 1];
		for (int p = 0; p < i; p++) {
			for (int q = 0; q < j; q++) {
				con[p][q] = this.mat[p][q];
			}
		}
		for (int p = i + 1; p < this.rowN; p++) {
			for (int q = j + 1; q < this.colN; q++) {
				con[p - 1][q - 1] = this.mat[p][q];
			}
		}
		//
		this.rowN -= 1;
		this.colN -= 1;
		this.isSquare = this.isSquare();
		this.mat = con;
	}

	public CMatrix contractMat(int n) {
		// 行列の先頭 n 列 n行をはぎとる
		double[][] con = new double[this.rowN - n][this.colN - n];
		for (int p = n; p < this.rowN; p++) {
			for (int q = n; q < this.colN; q++) {
				con[p - n][q - n] = this.mat[p][q];
			}
		}
		CMatrix r = new CMatrix(con);
		return r;
	}

	// 拡大行列:<1,0,0,...>ベクトルを先頭に付け加える
	public CMatrix expanedMat() {
		int theRow = this.rowN;
		int theCol = this.colN;
		int exRow = theRow + 1;
		int exCol = theCol + 1;
		// CMatrx() は対角要素が1で他が0であることを利用する。
		CMatrix r = new CMatrix(exRow, exCol);
		for (int i = 0; i < theRow; i++) {
			for (int j = 0; j < theCol; j++) {
				r.mat[i + 1][j + 1] = this.mat[i][j];
			}
		}
		//
		return r;
	}

	// 拡大行列：次元 n の単位行列を左上に付け加える。
	public CMatrix expanedMat(int n) {
		CMatrix r = new CMatrix(this.rowN + n, this.colN + n);
		for (int i = n; i < r.rowN; i++) {
			for (int j = n; j < r.colN; j++) {
				r.mat[i][j] = this.mat[i - n][j - n];
			}
		}
		return r;
	}

	// 行列の足し算
	public CMatrix addMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] + in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 行列の引き算
	public CMatrix subtractMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] - in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 行列の定数倍
	public CMatrix byScalar(double in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] * in;
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 自分自身を定数倍
	public void byScalarThis(double in) {
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] * in;
				this.mat[i][j] = d;
			}
		}
	}

	// 行列かけベクトル。後ろから列ベクトルをかける。
	// 結果はこの行列の行数を次数とする列ベクトル
	public CVector_Col byVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this.rowN);
		for (int i = 0; i < this.rowN; i++) {
			CVector_Row row = this.getRow(i);
			r.setValue(i, row.byVec(in));
		}
		return r;
	}

	// 行列のかけ算。後ろからかける
	public CMatrix byMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, in.colN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.getRow(i).byVec(in.getCol(j));
			}
		}
		return r;
	}

	// 逆行列計算のため単位置換行列を出力する。
	public CMatrix permut() {
		CMatrix original = new CMatrix(this);
		CMatrix r = new CMatrix(original.rowN, original.colN);
		// int count = 0;
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				r.exchangeRow(pivotRow, maxpos);
				// count++;
			} // end of if(...)
		} // end of for(...)
		return r;
	}

	// 行列式。
	public double det() {
		CMatrix original = new CMatrix(this);
		CMatrix r = new CMatrix(this.rowN, this.colN);
		int count = 0;
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				r.exchangeRow(pivotRow, maxpos);
				count++;
			} // end of if(...)
		} // end of for(...)
			// 以上で置換終わり
			// ここから上三角行列をつくる
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = r.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = r.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base行にすべて ratio をかける
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				r.setRow(targetRow, resultVecUnit);
			} // end of for(int targetRow=baseRow+1
		} // end of for(int baseRow = 0
		double det = 1.0;
		for (int i = 0; i < original.rowN; i++) {
			det *= original.getValue(i, i);
		}
		det = Math.pow(-1.0, count) * det;
		return det;
	}// end of det()
		// 逆行列

	public CMatrix inverse() {
		CMatrix original = new CMatrix(this);
		CMatrix unit = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				unit.exchangeRow(pivotRow, maxpos);
			} // end of if(...)
		} // end of for(...)
			// 以上で置換終わり。ここから上三角行列をつくる
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = unit.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = unit.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base行にすべて ratio をかける
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				unit.setRow(targetRow, resultVecUnit);
			} // end of for(int targetRow=baseRow+1
		} // end of for(int baseRow = 0
			// 以上で original 行列は上三角行列になった。
			// ガウス・ジョルダン法を上三角行列に適用
		for (int pivot = 0; pivot < original.rowN; pivot++) {
			// pivot 行、pivot列成分を1 にする。
			double invPivot = 1.0 / original.getValue(pivot, pivot);
			CVector_Row pivotVec = original.getRow(pivot);
			CVector_Row pivotVecUnit = unit.getRow(pivot);
			pivotVec.byScalarThis(invPivot);
			pivotVecUnit.byScalarThis(invPivot);
			original.setRow(pivot, pivotVec);
			unit.setRow(pivot, pivotVecUnit);
			for (int i = 0; i < pivot; i++) {
				if (i != pivot) {
					CVector_Row targetVec = original.getRow(i);
					CVector_Row targetVecUnit = unit.getRow(i);
					double value = targetVec.getValue(pivot);
					CVector_Row tmpVec = pivotVec.byScalar(value);
					CVector_Row subVec = targetVec.subtractVec(tmpVec);
					CVector_Row tmpVecUnit = pivotVecUnit.byScalar(value);
					CVector_Row subVecUnit = targetVecUnit.subtractVec(tmpVecUnit);
					original.setRow(i, subVec);
					unit.setRow(i, subVecUnit);
				} // end of if()
			} // end of for(int i=0...
		} // end of for( int pivot = 0...
		return unit;
	} // end of inverse()

	// ハウスホルダー行列を返す。
	public static CMatrix householder(CMatrix in) {
		// in の最初の列ベクトルを x とする。
		CVector_Col x = in.getCol(0);
		CVector_Col y = new CVector_Col(x.getDim());
		double norm_x = x.getNorm();
		y.setValue(0, (-1) * norm_x);
		// v = x -y を作る
		CVector_Col v = x.subtractVec(y);
		// この行列の Householder 行列
		CMatrix sourceHH = v.byVec(v.transpose());
		double coef = 2.0 / (v.getNorm() * v.getNorm());
		sourceHH.byScalarThis(coef);
		CMatrix unit = new CMatrix(sourceHH.colN, sourceHH.rowN);
		CMatrix HH = unit.subtractMat(sourceHH);
		return HH;
	}

	// この行列を対象にしたハウスホルダー行列を返す。
	public CMatrix householder() {
		// 最初の列ベクトルを x とする。
		CVector_Col x = this.getCol(0);
		CVector_Col y = new CVector_Col(x.getDim());
		double norm_x = x.getNorm();
		y.setValue(0, (-1) * norm_x);
		// v = x -y を作る
		CVector_Col v = x.subtractVec(y);
		// この行列の Householder 行列
		CMatrix sourceHH = v.byVec(v.transpose());
		double coef = 2.0 / (v.getNorm() * v.getNorm());
		sourceHH.byScalarThis(coef);
		CMatrix unit = new CMatrix(sourceHH.colN, sourceHH.rowN);
		CMatrix HH = unit.subtractMat(sourceHH);
		return HH;
	}

	// この行列のQR 分解 CMatrix の配列として Q,R を返す
	public CMatrix[] QRDecomp() {
		CMatrix R = new CMatrix(this);
		CMatrix Q = new CMatrix(R.rowN, R.colN);
		int loopLimit = R.rowN - 1;
		for (int pivot = 0; pivot < loopLimit; pivot++) {
			// Householder 小行列をつくる。
			// 行列の pivot 行までは単位行列であるとして、その部分を剥ぎ取る
			CMatrix Hr = R.contractMat(pivot);
			CMatrix HH = householder(Hr);
			// HHを元の次数に拡大
			CMatrix exHH = HH.expanedMat(pivot);
			R = exHH.byMat(R);
			Q = Q.byMat(exHH.transpose());
		}
		// 以上で Q,R ができたので、配列をつくってそれを返す。
		CMatrix[] r = { Q, R };
		return r;
	}

	// この行列の固有値を double[] として返し、この行列を固有ベクトル行列に置き換える
	public double[] eigenValueVec() {
		//計算用の行列
		CMatrix origin = new CMatrix(this);
		// 固有ベクトルがはいる、つもりの CVector_Col 配列
		int numOfVariables =origin.colN;
		CVector_Col[] eigenVecArray = new CVector_Col[numOfVariables];
		for (int i = 0; i < eigenVecArray.length; i++) {
			eigenVecArray[i] = new CVector_Col(numOfVariables);
			eigenVecArray[i].setValue(0, 1.0);
		}
		// 固有値が入る予定の double 配列
		double[] eigenValue = new double[numOfVariables];
		// べき乗法
		for (int loop = 0; loop < numOfVariables; loop++) {
			CVector_Col before = eigenVecArray[loop];
			boolean checkFlag = false; // 収束判定フラグ
			while (!checkFlag) {
				CVector_Col after = origin.byVec(before);
				eigenValue[loop] = after.getNorm();
				after.normalizeThis();
				// 収束チェック
				checkFlag = checkVec(after, before,1.0E-5);
				if (checkFlag) {
					eigenVecArray[loop] = after;
				}
				// 入れ替え
				before = after;
			} // end of while(...
			// 差し引く行列をつくる
			CMatrix subMatrix = eigenVecArray[loop].byVec(eigenVecArray[loop].transpose());
			subMatrix.byScalarThis(eigenValue[loop]);
			//
			origin = origin.subtractMat(subMatrix);
		} // end of for(... べき乗法のおわり
		//この行列の中身を eigenVecArray で置き換える
		for(int j=0;j<this.colN;j++) {
			this.setCol(j, eigenVecArray[j]);
		}
		return eigenValue;
	}
	// この行列の固有値を double[] として返し、この行列を固有ベクトル行列に置き換える
		public CVector_Col eigenValueVec_Vec() {
			//計算用の行列
			CMatrix origin = new CMatrix(this);
			// 固有ベクトルがはいる、つもりの CVector_Col 配列
			int numOfVariables =origin.colN;
			CVector_Col[] eigenVecArray = new CVector_Col[numOfVariables];
			for (int i = 0; i < eigenVecArray.length; i++) {
				eigenVecArray[i] = new CVector_Col(numOfVariables);
				eigenVecArray[i].setValue(0, 1.0);
			}
			// 固有値が入る予定の double 配列
			double[] eigenValue = new double[numOfVariables];
			// べき乗法
			for (int loop = 0; loop < numOfVariables; loop++) {
				CVector_Col before = eigenVecArray[loop];
				boolean checkFlag = false; // 収束判定フラグ
				while (!checkFlag) {
					CVector_Col after = origin.byVec(before);
					eigenValue[loop] = after.getNorm();
					after.normalizeThis();
					// 収束チェック
					checkFlag = checkVec(after, before,1.0E-5);
					if (checkFlag) {
						eigenVecArray[loop] = after;
					}
					// 入れ替え
					before = after;
				} // end of while(...
				// 差し引く行列をつくる
				CMatrix subMatrix = eigenVecArray[loop].byVec(eigenVecArray[loop].transpose());
				subMatrix.byScalarThis(eigenValue[loop]);
				//
				origin = origin.subtractMat(subMatrix);
			} // end of for(... べき乗法のおわり
			//この行列の中身を eigenVecArray で置き換える
			for(int j=0;j<this.colN;j++) {
				this.setCol(j, eigenVecArray[j]);
			}
			//
			CVector_Col r = new CVector_Col(eigenValue);
			return r;
		}

	// この行列の第p列と第q列を入れ替える
	public void exchangeCol(int p, int q) {
		CVector_Col tmp = this.getCol(p);
		this.setCol(p, this.getCol(q));
		this.setCol(q, tmp);
	}

	// この行列の第 p 行と第 q 行を入れ替える
	public void exchangeRow(int p, int q) {
		CVector_Row tmp = this.getRow(p);
		this.setRow(p, this.getRow(q));
		this.setRow(q, tmp);
	}

	// getter
	public double[][] getMat() {
		return this.mat;
	}

	// 第(i,j)要素を返す
	public double getValue(int i, int j) {
		return this.mat[i][j];
	}

	// 第j列を列ベクトルとして返す
	public CVector_Col getCol(int j) {
		CVector_Col r = new CVector_Col(getColArray(j));
		return r;
	}

	// 第j列を double[] で返す
	public double[] getColArray(int j) {
		double[] r = new double[this.rowN];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[i][j];
		}
		return r;
	}

	// 第i行を行ベクトルとして返す
	public CVector_Row getRow(int i) {
		CVector_Row r = new CVector_Row(getRowArray(i));
		return r;
	}

	// 第i行を double[] で返す
	public double[] getRowArray(int i) {
		double[] r = new double[this.colN];
		for (int j = 0; j < r.length; j++) {
			r[j] = this.mat[i][j];
		}
		return r;
	}

	// 正方行列かどうか？
	public boolean isSquare() {
		return this.isSquare;
	}

	// setter
	// (i,j)要素に値をセットする
	public void setValue(int i, int j, double v) {
		this.mat[i][j] = v;
	}

	// 第i 行を CVector_Row で置き換える
	public void setRow(int i, CVector_Row in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in.getArray()[j];
		}
	}

	// 第i行を double[] で置き換える。
	public void setRow(int i, double[] in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in[j];
		}
	}

	// 第j列を CVector_Colで置き換える
	public void setCol(int j, CVector_Col in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in.getArray()[i];
		}
	}

	// 第j列を double[] で置き換える
	public void setCol(int j, double[] in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in[i];
		}
	}

	//
	// 収束チェックのために2つのベクトルの差をとり、しきい値未満なら true を返す
	static boolean checkVec(CVector_Col x1, CVector_Col x2, double threshold) {
		boolean r = true;
		CVector_Col check = x1.subtractVec(x2);
		for (int i = 0; i < check.getDim(); i++) {
			if (Math.abs(check.getValue(i)) > threshold)
				r = false;
		}
		return r;
	}

}
