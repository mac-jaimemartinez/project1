package Tpfahp;

public class ArrayFloatPrimitivePlate {
	// Create arrays oldPlate and newPlate with linear dimension d
    //   plus two extra rows and columns to hold edge temperatures
    private static int d;
    private static int maxIterations= 400;
    private static float relChgStopCriteria = 0.005f;
    private static float[][] oldPlate;
    private static float[][] newPlate;
    private static float t, b, l, r;
    
    // Run the simulation
    public void runSimulation(int dim, float top, float bottom, float left,
    		float right, MetaData metaData) {
        //Process the command line options
        d = dim;
        t = top;
        b = bottom;
        l = left;
        r = right;
        
        // Initialize plates before simulation		
        oldPlate= new float[d + 2][d + 2];
        initialize(oldPlate, t, b, l, r);
        newPlate= new float[d + 2][d + 2];
        initialize(newPlate, t, b, l, r);
        
        // Simulate Diffusion by averaging the temperatures
        float maxChange = 100;
        int i = 0;
        for ( ; i < maxIterations && maxChange > relChgStopCriteria; i++) {
            //System.out.println("\n\n");
            maxChange = diffuse(oldPlate, newPlate);
            swap(oldPlate, newPlate);
        }
        metaData.setNumIterations(i);
        // Display the results
        print(newPlate);
    }

    // Initialize the temperatures of the edge values and the plate itself
    public static void initialize(float[][] plate, float top, float bot, float left, float right) {
        for(int i=0; i<plate.length; i++){
            for(int j=0; j<plate.length; j++){
                if(i==0){
                    plate[i][j]=top;
                }
                else if(i==plate.length-1){
                    plate[i][j]=bot;
                }
                else if(i>0 && i<plate.length && j==0){
                    plate[i][j]=left;
                }
                else if (i>0 && i<plate.length && (j==plate.length-1)){
                    plate[i][j]=right;
                }
            }
        }
    }
    
    // Loop until exit criteria are met, updating each newPlate cell from the
    //   average temperatures of the corresponding neighbors in oldPlate
    // Returns the maximum change between oldPlate and newPlate cells
    public static float diffuse(float[][] oldPlate, float[][] newPlate) {
        // diffuse the oldPlate into the newPlate
    	float maxChange = 0;
        for (int i = 1; i <= d; i++) {
            for (int j = 1; j <= d; j++) {
                newPlate[i][j] = (oldPlate[i + 1][j] + oldPlate[i - 1][j] +
                                  oldPlate[i][j + 1] + oldPlate[i][j - 1]) / 4.0f;
                maxChange = Math.max(maxChange, Math.abs(newPlate[i][j] - oldPlate[i][j]));
            }
        }
        return maxChange;
    }
    
    
    // Swap the plates and continue ...
    public static void swap(float[][] oldPlate, float[][] newPlate) {
        // put the values of newPlate into oldPlate
        for(int i= 0; i < oldPlate.length; i++) {
            for(int j= 0; j < oldPlate.length; j++) {
                oldPlate[i][j]= newPlate[i][j];
            }
        }
    }
    
    // Print out the values of the array called for in main
    public static void print(float[][] grid) {
        
        for(int i= 0; i < grid.length; i++) {
            for(int j= 0; j < grid.length; j++) {
                grid[i][j] = (float)Math.round(grid[i][j] * 100) / 100;
                if(i!=0 && i!=grid.length-1 && j!=0 && j!=grid.length-1){
                    System.out.print(grid[i][j]+" ");
                    
                }
                
            }
            System.out.print("\n");
        }
    }   
}
