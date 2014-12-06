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
 * @author Dylan Sherry
 */


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class for crudely determining the number of lines and explanatory variables
 * in a data file
 * 
 * @author Dylan Sherry
 * 
 */
public class DataSizeRetreiver {
    /**
     * Set the number of predictors present in the file
     * 
     * @param filepath
     * @return the number of predictors in the file, 
     * it assumes that the last column is the target value
     * @throws java.io.IOException 
     */
    public static int num_predictors(String filepath) throws IOException {
        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), Charset.defaultCharset()));
            if (!f.ready()) {
                System.err.println(String.format("Error: empty file %s", filepath));
            }   String[] tokens = f.readLine().split(",");
            f.close();
            int numPredictors = tokens.length - 1; // assuming one output variable
            return numPredictors;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataSizeRetreiver.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {
                if(f!=null)f.close();
            } catch (IOException ex) {
                Logger.getLogger(DataSizeRetreiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Returns the  number of lines found in the file
     * 
     * @param filepath
     * @return the number of lines/exemplars in the file
     */
    public static int num_exemplars(String filepath) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(filepath));
            try {
                byte[] c = new byte[1024];
                int count = 0;
                int readChars;
                boolean empty = true;
                while ((readChars = is.read(c)) != -1) {
                    empty = false;
                    for (int i = 0; i < readChars; ++i) {
                        if (c[i] == '\n') {
                            ++count;
                        }
                    }
                }
                return (count == 0 && !empty) ? 1 : count;
            } finally {
                is.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Error: file %s not found.", filepath));
        } catch (IOException e) {
            System.err.println(String.format("Error: file %s not found.", filepath));
        }
        return -1;
    }
}
