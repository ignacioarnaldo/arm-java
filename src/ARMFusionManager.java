/**
 * Copyright (c) 2011-2013 ALFA Group
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
 */

import arm.ARMModelFusion;
import java.io.IOException;

/**
 * wrapper class to parse the command line to fuse the predictions
 * of a set of procedures via Adaptive Regression by Mixing (ARM)
 * 
 * @author Ignacio Arnaldo
 */
public class ARMFusionManager {
    
    /**
     * Print Usage
     * This message appears when the command is not called correctly
     */
    public void printUsage(){
        System.err.println();
        System.err.println("USAGE:");
        System.err.println();
        System.err.println("ARM FUSION:");
        System.err.println("java -jar armfusion.jar -csv path_to_preds -iters num_iters");
        System.err.println();
        System.err.println();

    }
    
    /**
     * Parses the arguments of the command
     * @param args
     * @throws IOException 
     */
    public void parseARMFusion(String args[]) throws IOException{
        String path_to_csv;
        int numIters;
        if(args[0].equals("-csv")){
            path_to_csv = args[1];
            if(args[2].equals("-iters")){
                numIters = Integer.parseInt(args[3]);
                ARMModelFusion armmf = new ARMModelFusion(path_to_csv,numIters);
                armmf.arm_weights();
                armmf.printFusedModel();
            }else{
                System.err.println("Error: expected -iters flag");
                printUsage();
            }
        }else{
            System.err.println("Error: expected -csv flag");
            printUsage();
        }
    }

    /**
     * Entry point of the code. Creates a manager that parses the command
     * line arguments
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        ARMFusionManager armfm = new ARMFusionManager();
        if (args.length == 4) {
            armfm.parseARMFusion(args);
        }else{
            System.err.println("Error: wrong number of arguments");
            armfm.printUsage();
            System.exit(-1);
        }
    }
}
