#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void multiplyRow(int row, int len, double val, double rowToChange[len][len], double copyOfRow[len], double inverse[len][len], double copyOfInverse[len]);
void divideRow(int row,int len, double val, double rowToChange[len][len], double inverse[len][len]);
void GJElimination(int len, double array[len][len], double inverse[len][len]);
void transposeMatrix(int row, int column, double array[row][column], double copy[column][row]);
void multiplyMatrices(int row1, int column1, int row2, int column2, double multiplicand[row1][column1], double multiplier[row2][column2], double product[row1][column2]);
void createInverse(int row, double inverse[row][row]);


int main(int argsc, char **argsv){
	int i,j,n,k,k2,m;
	double num;
	
	FILE *inputTrain = NULL;
	FILE *inputTest = NULL;
	char *filename = NULL;
	char *filename2 = NULL;
	char testOrTrain[10];
	
	//generic error check To see if the right amount of arguments were added
	if(argsc < 3){
		
		printf("error\n");
		exit(0);
	}
	
	filename = argsv[1];
	filename2 = argsv[2];
	
	inputTrain = fopen(filename, "r");
	inputTest = fopen(filename2, "r");
	
	//Error check to make sure the files arent empty
	if(inputTrain == NULL || inputTest == NULL){
		printf("error\n");
		exit(0);
		}
	
	
	//Check to see if the format of the input is normal or reversed. 
	
	//testOrTrain = (char*) malloc(10);
	fscanf(inputTrain, "%5s", testOrTrain); 
	//If the input is reversed close the files and reopen them in correct order
	if(strcmp(testOrTrain, "train") != 0){
	fclose(inputTrain);
	fclose(inputTest);
	inputTrain = fopen(filename2, "r");
	inputTest = fopen(filename, "r");
	fscanf(inputTrain, "%5s", testOrTrain);
	
	}
	//collect garbage value and free the variable used to check for the input
	fscanf(inputTest, "%5s", testOrTrain);
	
	

	
	//find size of the matrices
	fscanf(inputTrain, "%d", &k);
	fscanf(inputTrain, "%d", &n);
	fscanf(inputTest, "%d", &k2);
	fscanf(inputTest, "%d", &m);
	
	//error check for different values of k
	if(k != k2){
		printf("error???");
		exit(1);
	}

	//all the Matrices needed for the final calculation
	double trainData[n][k+1];
	double priceVector[n][1];
	double testData[m][k+1];
	double trainDataT[k+1][n];
	double resultant[k+1][k+1];
	double inverse[k+1][k+1];
	double psuedoInverse[k+1][n];
	double weightMatrix[k+1][1];
	double finalPrice[m][1];
	
	
	//populate trainData and Price Vector
	for( i = 0; i < n; i++){
		trainData[i][0] = 1.0;
		}
		
	for(i = 0; i < n; i++){
		for(j = 1; j < k + 1; j++){
			fscanf(inputTrain, "%lf", &num);
			trainData[i][j] = num;
		}
		
		fscanf(inputTrain, "%lf", &num);
			priceVector[i][0] = num;
	}	
	
	
//Populate testData

	for( i = 0; i < m; i++){
			testData[i][0] = 1.00;	
	}
		
	for(i = 0; i < m; i++){
		for(j = 1; j < k + 1 ; j++){
			fscanf(inputTest, "%lf", &num);				
				testData[i][j] = num;
			
		}
	}
	

	fclose(inputTrain);
	fclose(inputTest);
	
	createInverse(k + 1, inverse);
	transposeMatrix(n,k + 1,trainData,trainDataT);
	multiplyMatrices(k + 1,n,n,k + 1,trainDataT, trainData,resultant);	
	GJElimination(k + 1, resultant, inverse);	
	multiplyMatrices(k+ 1,k+ 1,k+ 1,n,inverse, trainDataT,psuedoInverse);
	multiplyMatrices(k+1,n,n ,1,psuedoInverse,priceVector,weightMatrix);	
	multiplyMatrices(m,k+1,k+1, 1,testData,weightMatrix, finalPrice);	
	
	
	for(i = 0; i < m; i++){
		for(j = 0;  j < 1; j++){
			printf("%0.0lf", finalPrice[i][j]);
		}
		printf("\n");
	}
	

return 0;
}



//Function that does the Gauss Jordan Elmination
void GJElimination(int len, double array[len][len], double inverse[len][len]){
	int i,j;
	//First Pass Down the matrix to get a lower triangle
	for(i = 0; i < len; i++){
		if(array[i][i] != 1.00){
			divideRow(i,len, array[i][i],array,inverse);
			}		
			
		for(j = i + 1; j < len; j++){
			multiplyRow(j,len, array[j][i],array,array[i], inverse,inverse[i]);
			
		}
	}
	//second pass for the final inverse
	for(i = len - 1; i >= 0; i--){
		for(j = i - 1; j>=0; j--){
			multiplyRow(j,len,array[j][i], array,array[i], inverse,inverse[i]);
		}
	}


}

//Simple transpose function
void transposeMatrix(int row, int column, double array[row][column], double copy[column][row]){

	int i,j;

	for(i = 0; i < row; i++){
			for(j = 0; j < column; j++){	
				copy[j][i] = array[i][j];
			}
	}

}

void multiplyMatrices(int row1, int column1, int row2, int column2, double multiplicand[row1][column1], double multiplier[row2][column2], double product[row1][column2]){

	int i,j,k;
	double sum;
	
	for (i = 0; i < row1; i++){
		for(j = 0; j < column2; j++){
			sum = 0;
			for (k = 0; k < row2; k++){
				sum  += multiplicand[i][k] * multiplier[k][j]; 
					}
			product[i][j] = sum;
			}
		}
}
//Helpder function to multiply a row in the matric
void multiplyRow(int row, int len,double val, double rowToChange[len][len], double copyOfRow[len], double inverse[len][len], double copyOfInverse[len]){
	int i;	
	
	
	for(i = 0; i < len; i++){
		
		rowToChange[row][i] += (-1 * val * copyOfRow[i]);
		inverse[row][i] += (-1 * val * copyOfInverse[i]);
	}

}

//helper function to divide the row if the number isn't 1
void divideRow(int row,int len, double val, double rowToChange[len][len], double inverse[len][len]){
	int i;
	
	
	for(i = 0; i < len; i++){
		
		rowToChange[row][i] /= val;
		inverse[row][i] /= val;
	}

}

//Helper function to instantiate a proper inverse
void createInverse(int row, double array[row][row]){
int i,j;

	for(i = 0; i < row; i++){
		for(j = 0; j < row; j++){
			if(i == j){
				array[i][j] = 1.000;
			} else{
				array[i][j] = 0.000;
			}
		}
	}
}
