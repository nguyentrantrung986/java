import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
	private Picture pic;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		this.pic = new Picture(picture);
	}

	// current picture
	public Picture picture() {
		return new Picture(pic);
	}

	// width of current picture
	public int width() {
		return pic.width();
	}

	// height of current picture
	public int height() {
		return pic.height();
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
			throw new java.lang.IllegalArgumentException();
		if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
			return 1000;
		double deltaSquaredX = deltaSquared(pic.getRGB(x - 1, y), pic.getRGB(x + 1, y));
		double deltaSquaredY = deltaSquared(pic.getRGB(x, y - 1), pic.getRGB(x, y + 1));
		return Math.sqrt(deltaSquaredX + deltaSquaredY);
	}
	
	public   int[] findHorizontalSeam() {
		return new int[2];
	}

	/**
	 * sequence of indices for vertical seam
	 * 
	 * @return an array of length H such that entry y is the column number of
	 *         the pixel to be removed from row y of the image.
	 */
	public int[] findVerticalSeam() {
		double minSeamEnergy = Double.POSITIVE_INFINITY;
		int seamEndX=0;
		//minimum cumulative energy of the seam to each pixel
		double[][] energyTo = new double[height()][width()];
		//energy of each pixel in the image
		double[][] energy = new double[height()][width()];
		//x coordinate of the pixel in previous row which is selected for minimum total energy
		int[][] pixelTo = new int[height()][width()];
		
		for (int row = 0; row < height(); row++) 
			for (int col = 0; col < width(); col++){
				if (row == 0)
					energyTo[row][col] = 1000;
				else
					energyTo[row][col] = Double.POSITIVE_INFINITY;
				
				energy[row][col] = energy(col, row);
			}
		
		for (int row = 0; row < height()-1; row++) 
			for (int col = 0; col < width(); col++)
				relax(col, row, energyTo, energy, pixelTo);
		
		int lastRow = height() -1;
		for (int col = 0; col < width(); col++){
			if(energyTo[lastRow][col] < minSeamEnergy){
				minSeamEnergy = energyTo[lastRow][col];
				seamEndX = col;
			}
		}
		
		int[] seam = new int[height()];
		for(int row=height()-1; row >=0; row--){
			seam[row] = seamEndX;
			seamEndX = pixelTo[row][seamEndX];
		}
		
		return seam;
	}
	
	//relax 3 pixels below, 2 if the current pixel is in first or last column
	private void relax(int col, int row, double[][] energyTo, double[][] energy, int[][] pixelTo){
		if(col>0 && energyTo[row+1][col-1] > energyTo[row][col] + energy[row+1][col-1]){
			energyTo[row+1][col-1] = energyTo[row][col] + energy[row+1][col-1];
			pixelTo[row+1][col-1] = col;
		}
		
		if(energyTo[row+1][col] > energyTo[row][col] + energy[row+1][col]){
			energyTo[row+1][col] = energyTo[row][col] + energy[row+1][col];
			pixelTo[row+1][col] = col;
		}

		if(col<(width()-1) && energyTo[row+1][col+1] > energyTo[row][col] + energy[row+1][col+1]){
			energyTo[row+1][col+1] = energyTo[row][col] + energy[row+1][col+1];
			pixelTo[row+1][col+1] = col;
		}
	}

	private double deltaSquared(int rgb1, int rgb2) {
		double dr = ((rgb2 >> 16) & 0xFF) - ((rgb1 >> 16) & 0xFF);
		double dg = ((rgb2 >> 8) & 0xFF) - ((rgb1 >> 8) & 0xFF);
		double db = ((rgb2 >> 0) & 0xFF) - ((rgb1 >> 0) & 0xFF);

		return dr * dr + dg * dg + db * db;
	}

	public static void main(String[] args) {
		Picture picture = new Picture(args[0]);
		StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());

		SeamCarver sc = new SeamCarver(picture);

		StdOut.printf("Printing energy calculated for each pixel.\n");

		for (int row = 0; row < sc.height(); row++) {
			for (int col = 0; col < sc.width(); col++)
				StdOut.printf("%9.0f ", sc.energy(col, row));
			StdOut.println();
		}
	}
}
