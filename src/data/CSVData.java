package data;

/**
 * Copyright (c) 2011-2013 Evolutionary Design and Optimization Group
 * 
 * Licensed under the MIT License.
 * 
 * See the "LICENSE" file for a copy of the license.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.  
 * 
 * @author Ignacio Arnaldo
 *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class stores the predictions of the procedures of the ensemble and
 * the target values. 
 * @author Ignacio Arnaldo
 */
public class CSVData {
	
    // the number of fitness cases
    private int numberOfExemplars;

    // the number of features
    private int numberOfProcedures;
    
    // the matrix composed of n (number of exemplars) * d (numberOfprocedures)
    private final double[][] predictionsMatrix;
    
    /**
     * the true/target values
     */
    private final double[] target;
    
    /**
     * Constructor for the data matrix
     * @param csvPath
     * @throws IOException
     */
    public CSVData(String csvPath) throws IOException {
        numberOfExemplars = DataSizeRetreiver.num_exemplars(csvPath);
        numberOfProcedures = DataSizeRetreiver.num_predictors(csvPath);
        predictionsMatrix = new double[numberOfExemplars][numberOfProcedures];
        target = new double[numberOfExemplars];
        readCSV(csvPath);
    }

    /*
    * read the csv containing the training data
    * the last column contains the target value
    */
    private void readCSV(String csvfile) throws IOException {
        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(csvfile), Charset.defaultCharset()));
            String[] token;
            int exemplarIndex = 0;
            while (f.ready() && exemplarIndex < numberOfExemplars) {
                token = f.readLine().split(",");
                for (int i = 0; i < token.length - 1; i++) {
                    this.predictionsMatrix[exemplarIndex][i] = Double.valueOf(token[i]);
                }
                double val = Double.valueOf(token[token.length - 1]);
                target[exemplarIndex] = val;
                exemplarIndex++;
            }
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(f==null) {
                } else {
                    f.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CSVData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
   
    /**
     * @return the data matrix
     */
    public double[][] getInputValues(){
        return this.predictionsMatrix.clone();
    }

    /**
     * @return the vector of target values
     */
    public double[] getTargetValues(){
        return this.target.clone();
    }

    /**
     * @return the number of exemplars
     */
    public int getNumberOfExemplars() {
        return numberOfExemplars;
    }

    /**
     * @return the number of procedures
     */
    public int getNumberOfProcedures() {
        return numberOfProcedures;
    }
}