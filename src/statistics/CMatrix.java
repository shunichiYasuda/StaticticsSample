package statistics;

public class CMatrix {
	private double[][] mat;
	public int colN, rowN; // �s���A��
	public boolean isSquare; // �����s�񂩁H

	public CMatrix(int m, int n) {
		this.mat = new double[m][n]; // m�s����s��
		this.rowN = m;
		this.colN = n;
		if (m == n) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
		// ��{�͒P�ʍs��
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

	// ��x�N�g���̔z�񂩂�s�������
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

	// �s�x�N�g���̔z�񂩂�s�������B
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

	// �]�u
	public CMatrix transpose() {
		CMatrix r = new CMatrix(this.colN, this.rowN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.mat[j][i];
			}
		}
		return r;
	}

	// �k���s��
	public CMatrix contractMat(int i, int j) {
		// ��i�s�A��j�����苎�����k���s���Ԃ��B
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

	// �������g���k������
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
		// �s��̐擪 n �� n�s���͂��Ƃ�
		double[][] con = new double[this.rowN - n][this.colN - n];
		for (int p = n; p < this.rowN; p++) {
			for (int q = n; q < this.colN; q++) {
				con[p - n][q - n] = this.mat[p][q];
			}
		}
		CMatrix r = new CMatrix(con);
		return r;
	}

	// �g��s��:<1,0,0,...>�x�N�g����擪�ɕt��������
	public CMatrix expanedMat() {
		int theRow = this.rowN;
		int theCol = this.colN;
		int exRow = theRow + 1;
		int exCol = theCol + 1;
		// CMatrx() �͑Ίp�v�f��1�ő���0�ł��邱�Ƃ𗘗p����B
		CMatrix r = new CMatrix(exRow, exCol);
		for (int i = 0; i < theRow; i++) {
			for (int j = 0; j < theCol; j++) {
				r.mat[i + 1][j + 1] = this.mat[i][j];
			}
		}
		//
		return r;
	}

	// �g��s��F���� n �̒P�ʍs�������ɕt��������B
	public CMatrix expanedMat(int n) {
		CMatrix r = new CMatrix(this.rowN + n, this.colN + n);
		for (int i = n; i < r.rowN; i++) {
			for (int j = n; j < r.colN; j++) {
				r.mat[i][j] = this.mat[i - n][j - n];
			}
		}
		return r;
	}

	// �s��̑����Z
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

	// �s��̈����Z
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

	// �s��̒萔�{
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

	// �������g��萔�{
	public void byScalarThis(double in) {
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] * in;
				this.mat[i][j] = d;
			}
		}
	}

	// �s�񂩂��x�N�g���B��납���x�N�g����������B
	// ���ʂ͂��̍s��̍s���������Ƃ����x�N�g��
	public CVector_Col byVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this.rowN);
		for (int i = 0; i < this.rowN; i++) {
			CVector_Row row = this.getRow(i);
			r.setValue(i, row.byVec(in));
		}
		return r;
	}

	// �s��̂����Z�B��납�炩����
	public CMatrix byMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, in.colN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.getRow(i).byVec(in.getCol(j));
			}
		}
		return r;
	}

	// �t�s��v�Z�̂��ߒP�ʒu���s����o�͂���B
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

	// �s�񎮁B
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
			// �ȏ�Œu���I���
			// ���������O�p�s�������
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = r.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// ��������s�𔲂��o��
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = r.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base�s�ɂ��ׂ� ratio ��������
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// �s��̒��ڍs�����̌��ʂɒu��������B
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
		// �t�s��

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
			// �ȏ�Œu���I���B���������O�p�s�������
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = unit.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// ��������s�𔲂��o��
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = unit.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base�s�ɂ��ׂ� ratio ��������
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// �s��̒��ڍs�����̌��ʂɒu��������B
				original.setRow(targetRow, resultVec);
				unit.setRow(targetRow, resultVecUnit);
			} // end of for(int targetRow=baseRow+1
		} // end of for(int baseRow = 0
			// �ȏ�� original �s��͏�O�p�s��ɂȂ����B
			// �K�E�X�E�W�����_���@����O�p�s��ɓK�p
		for (int pivot = 0; pivot < original.rowN; pivot++) {
			// pivot �s�Apivot�񐬕���1 �ɂ���B
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

	// �n�E�X�z���_�[�s���Ԃ��B
	public static CMatrix householder(CMatrix in) {
		// in �̍ŏ��̗�x�N�g���� x �Ƃ���B
		CVector_Col x = in.getCol(0);
		CVector_Col y = new CVector_Col(x.getDim());
		double norm_x = x.getNorm();
		y.setValue(0, (-1) * norm_x);
		// v = x -y �����
		CVector_Col v = x.subtractVec(y);
		// ���̍s��� Householder �s��
		CMatrix sourceHH = v.byVec(v.transpose());
		double coef = 2.0 / (v.getNorm() * v.getNorm());
		sourceHH.byScalarThis(coef);
		CMatrix unit = new CMatrix(sourceHH.colN, sourceHH.rowN);
		CMatrix HH = unit.subtractMat(sourceHH);
		return HH;
	}

	// ���̍s���Ώۂɂ����n�E�X�z���_�[�s���Ԃ��B
	public CMatrix householder() {
		// �ŏ��̗�x�N�g���� x �Ƃ���B
		CVector_Col x = this.getCol(0);
		CVector_Col y = new CVector_Col(x.getDim());
		double norm_x = x.getNorm();
		y.setValue(0, (-1) * norm_x);
		// v = x -y �����
		CVector_Col v = x.subtractVec(y);
		// ���̍s��� Householder �s��
		CMatrix sourceHH = v.byVec(v.transpose());
		double coef = 2.0 / (v.getNorm() * v.getNorm());
		sourceHH.byScalarThis(coef);
		CMatrix unit = new CMatrix(sourceHH.colN, sourceHH.rowN);
		CMatrix HH = unit.subtractMat(sourceHH);
		return HH;
	}

	// ���̍s���QR ���� CMatrix �̔z��Ƃ��� Q,R ��Ԃ�
	public CMatrix[] QRDecomp() {
		CMatrix R = new CMatrix(this);
		CMatrix Q = new CMatrix(R.rowN, R.colN);
		int loopLimit = R.rowN - 1;
		for (int pivot = 0; pivot < loopLimit; pivot++) {
			// Householder ���s�������B
			// �s��� pivot �s�܂ł͒P�ʍs��ł���Ƃ��āA���̕����𔍂����
			CMatrix Hr = R.contractMat(pivot);
			CMatrix HH = householder(Hr);
			// HH�����̎����Ɋg��
			CMatrix exHH = HH.expanedMat(pivot);
			R = exHH.byMat(R);
			Q = Q.byMat(exHH.transpose());
		}
		// �ȏ�� Q,R ���ł����̂ŁA�z��������Ă����Ԃ��B
		CMatrix[] r = { Q, R };
		return r;
	}

	// ���̍s��̌ŗL�l�� double[] �Ƃ��ĕԂ��A���̍s����ŗL�x�N�g���s��ɒu��������
	public double[] eigenValueVec() {
		//�v�Z�p�̍s��
		CMatrix origin = new CMatrix(this);
		// �ŗL�x�N�g�����͂���A����� CVector_Col �z��
		int numOfVariables =origin.colN;
		CVector_Col[] eigenVecArray = new CVector_Col[numOfVariables];
		for (int i = 0; i < eigenVecArray.length; i++) {
			eigenVecArray[i] = new CVector_Col(numOfVariables);
			eigenVecArray[i].setValue(0, 1.0);
		}
		// �ŗL�l������\��� double �z��
		double[] eigenValue = new double[numOfVariables];
		// �ׂ���@
		for (int loop = 0; loop < numOfVariables; loop++) {
			CVector_Col before = eigenVecArray[loop];
			boolean checkFlag = false; // ��������t���O
			while (!checkFlag) {
				CVector_Col after = origin.byVec(before);
				eigenValue[loop] = after.getNorm();
				after.normalizeThis();
				// �����`�F�b�N
				checkFlag = checkVec(after, before,1.0E-5);
				if (checkFlag) {
					eigenVecArray[loop] = after;
				}
				// ����ւ�
				before = after;
			} // end of while(...
			// ���������s�������
			CMatrix subMatrix = eigenVecArray[loop].byVec(eigenVecArray[loop].transpose());
			subMatrix.byScalarThis(eigenValue[loop]);
			//
			origin = origin.subtractMat(subMatrix);
		} // end of for(... �ׂ���@�̂����
		//���̍s��̒��g�� eigenVecArray �Œu��������
		for(int j=0;j<this.colN;j++) {
			this.setCol(j, eigenVecArray[j]);
		}
		return eigenValue;
	}
	// ���̍s��̌ŗL�l�� double[] �Ƃ��ĕԂ��A���̍s����ŗL�x�N�g���s��ɒu��������
		public CVector_Col eigenValueVec_Vec() {
			//�v�Z�p�̍s��
			CMatrix origin = new CMatrix(this);
			// �ŗL�x�N�g�����͂���A����� CVector_Col �z��
			int numOfVariables =origin.colN;
			CVector_Col[] eigenVecArray = new CVector_Col[numOfVariables];
			for (int i = 0; i < eigenVecArray.length; i++) {
				eigenVecArray[i] = new CVector_Col(numOfVariables);
				eigenVecArray[i].setValue(0, 1.0);
			}
			// �ŗL�l������\��� double �z��
			double[] eigenValue = new double[numOfVariables];
			// �ׂ���@
			for (int loop = 0; loop < numOfVariables; loop++) {
				CVector_Col before = eigenVecArray[loop];
				boolean checkFlag = false; // ��������t���O
				while (!checkFlag) {
					CVector_Col after = origin.byVec(before);
					eigenValue[loop] = after.getNorm();
					after.normalizeThis();
					// �����`�F�b�N
					checkFlag = checkVec(after, before,1.0E-5);
					if (checkFlag) {
						eigenVecArray[loop] = after;
					}
					// ����ւ�
					before = after;
				} // end of while(...
				// ���������s�������
				CMatrix subMatrix = eigenVecArray[loop].byVec(eigenVecArray[loop].transpose());
				subMatrix.byScalarThis(eigenValue[loop]);
				//
				origin = origin.subtractMat(subMatrix);
			} // end of for(... �ׂ���@�̂����
			//���̍s��̒��g�� eigenVecArray �Œu��������
			for(int j=0;j<this.colN;j++) {
				this.setCol(j, eigenVecArray[j]);
			}
			//
			CVector_Col r = new CVector_Col(eigenValue);
			return r;
		}

	// ���̍s��̑�p��Ƒ�q������ւ���
	public void exchangeCol(int p, int q) {
		CVector_Col tmp = this.getCol(p);
		this.setCol(p, this.getCol(q));
		this.setCol(q, tmp);
	}

	// ���̍s��̑� p �s�Ƒ� q �s�����ւ���
	public void exchangeRow(int p, int q) {
		CVector_Row tmp = this.getRow(p);
		this.setRow(p, this.getRow(q));
		this.setRow(q, tmp);
	}

	// getter
	public double[][] getMat() {
		return this.mat;
	}

	// ��(i,j)�v�f��Ԃ�
	public double getValue(int i, int j) {
		return this.mat[i][j];
	}

	// ��j����x�N�g���Ƃ��ĕԂ�
	public CVector_Col getCol(int j) {
		CVector_Col r = new CVector_Col(getColArray(j));
		return r;
	}

	// ��j��� double[] �ŕԂ�
	public double[] getColArray(int j) {
		double[] r = new double[this.rowN];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[i][j];
		}
		return r;
	}

	// ��i�s���s�x�N�g���Ƃ��ĕԂ�
	public CVector_Row getRow(int i) {
		CVector_Row r = new CVector_Row(getRowArray(i));
		return r;
	}

	// ��i�s�� double[] �ŕԂ�
	public double[] getRowArray(int i) {
		double[] r = new double[this.colN];
		for (int j = 0; j < r.length; j++) {
			r[j] = this.mat[i][j];
		}
		return r;
	}

	// �����s�񂩂ǂ����H
	public boolean isSquare() {
		return this.isSquare;
	}

	// setter
	// (i,j)�v�f�ɒl���Z�b�g����
	public void setValue(int i, int j, double v) {
		this.mat[i][j] = v;
	}

	// ��i �s�� CVector_Row �Œu��������
	public void setRow(int i, CVector_Row in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in.getArray()[j];
		}
	}

	// ��i�s�� double[] �Œu��������B
	public void setRow(int i, double[] in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in[j];
		}
	}

	// ��j��� CVector_Col�Œu��������
	public void setCol(int j, CVector_Col in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in.getArray()[i];
		}
	}

	// ��j��� double[] �Œu��������
	public void setCol(int j, double[] in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in[i];
		}
	}

	//
	// �����`�F�b�N�̂��߂�2�̃x�N�g���̍����Ƃ�A�������l�����Ȃ� true ��Ԃ�
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
