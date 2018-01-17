import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture pic;
	private int[][] rgb;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if(picture == null) throw new java.lang.IllegalArgumentException();
		this.pic = new Picture(picture);
		
		rgb = new int[pic.height()][pic.width()];
		for (int row = 0; row < height(); row++)
			for (int col = 0; col < width(); col++) 
				rgb[row][col] = pic.getRGB(col, row);
	}

	// current picture
	public Picture picture() {
		pic = new Picture(rgb[0].length,rgb.length);
		for (int row = 0; row < height(); row++)
			for (int col = 0; col < width(); col++) 
				pic.setRGB(col, row, rgb[row][col]);
		
		return new Picture(pic);
	}

	// width of current picture
	public int width() {
		return rgb[0].length;
	}

	// height of current picture
	public int height() {
		return rgb.length;
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
			throw new java.lang.IllegalArgumentException();
		if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
			return 1000;
		double deltaSquaredX = deltaSquared(rgb[y][x - 1], rgb[y][x + 1]);
		double deltaSquaredY = deltaSquared(rgb[y - 1][x], rgb[y + 1][x]);
		return Math.sqrt(deltaSquaredX + deltaSquaredY);
	}

	/**
	 * sequence of indices for horizontal seam
	 * 
	 * @return an array of length width such that entry x is the row number of
	 *         the pixel to be removed from column x of the image.
	 */
	public int[] findHorizontalSeam() {
		double minSeamEnergy = Double.POSITIVE_INFINITY;
		int seamEndY = 0;
		// minimum cumulative energy of the seam to each pixel
		double[][] energyTo = new double[height()][width()];
		// energy of each pixel in the image
		double[][] energy = new double[height()][width()];
		// y coordinate of the pixel in previous row which is selected for
		// minimum total energy
		int[][] pixelTo = new int[height()][width()];

		for (int row = 0; row < height(); row++)
			for (int col = 0; col < width(); col++) {
				if (col == 0)
					energyTo[row][col] = 1000;
				else
					energyTo[row][col] = Double.POSITIVE_INFINITY;

				energy[row][col] = energy(col, row);
			}

		for (int col = 0; col < width() -1; col++)
			for (int row = 0; row < height(); row++)
				relaxHorizontal(col, row, energyTo, energy, pixelTo);

		int lastCol = width() - 1;
		for (int row = 0; row < height(); row++) {
			if (energyTo[row][lastCol] < minSeamEnergy) {
				minSeamEnergy = energyTo[row][lastCol];
				seamEndY = row;
			}
		}

		int[] seam = new int[width()];
		for (int col = width() - 1; col >= 0; col--) {
			seam[col] = seamEndY;
			seamEndY = pixelTo[seamEndY][col];
		}

		return seam;
	}

	/**
	 * sequence of indices for vertical seam
	 * 
	 * @return an array of length H such that entry y is the column number of
	 *         the pixel to be removed from row y of the image.
	 */
	public int[] findVerticalSeam() {
		double minSeamEnergy = Double.POSITIVE_INFINITY;
		int seamEndX = 0;
		// minimum cumulative energy of the seam to each pixel
		double[][] energyTo = new double[height()][width()];
		// energy of each pixel in the image
		double[][] energy = new double[height()][width()];
		// x coordinate of the pixel in previous row which is selected for
		// minimum total energy
		int[][] pixelTo = new int[height()][width()];

		for (int row = 0; row < height(); row++)
			for (int col = 0; col < width(); col++) {
				if (row == 0)
					energyTo[row][col] = 1000;
				else
					energyTo[row][col] = Double.POSITIVE_INFINITY;

				energy[row][col] = energy(col, row);
			}

		for (int row = 0; row < height() - 1; row++)
			for (int col = 0; col < width(); col++)
				relaxVertical(col, row, energyTo, energy, pixelTo);

		int lastRow = height() - 1;
		for (int col = 0; col < width(); col++) {
			if (energyTo[lastRow][col] < minSeamEnergy) {
				minSeamEnergy = energyTo[lastRow][col];
				seamEndX = col;
			}
		}

		int[] seam = new int[height()];
		for (int row = height() - 1; row >= 0; row--) {
			seam[row] = seamEndX;
			seamEndX = pixelTo[row][seamEndX];
		}

		return seam;
	}
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		if(seam == null || !validateSeam(seam) || width() < 2) throw new java.lang.IllegalArgumentException();
		int[][] newRGB = new int[height()][width()-1];
		for(int row=0; row < height(); row++){
			if(seam[row]==0)
				System.arraycopy(rgb[row], 1, newRGB[row], 0, newRGB.length);
			else if(seam[row] == width()-1)
				System.arraycopy(rgb[row], 0, newRGB[row], 0, newRGB.length);
			else{
				System.arraycopy(rgb[row], 0, newRGB[row], 0, seam[row]);
				System.arraycopy(rgb[row], seam[row]+1, newRGB[row], seam[row], width()-seam[row]-1);
			}
		}
		
		rgb = newRGB;
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam){
		if(seam == null || !validateSeam(seam) || height() < 2) throw new java.lang.IllegalArgumentException();
		rgb = transpose(rgb);
		removeVerticalSeam(seam);
		rgb = transpose(rgb);
	}
	
	
	//transpose 2d array
	private int[][] transpose(int[][] array){
		int[][] newArray = new int[array[0].length][array.length];
		
		for(int col=0; col<array[0].length;col++)
			for(int row=0; row<array.length;row++)
				newArray[col][row] = array[row][col];
		
		return newArray;
	}
	private boolean validateSeam(int[] seam){
		for(int i=0;i<seam.length-1;i++){
			if(Math.abs(seam[i]-seam[i+1])>1)
				return false;
		}
		
		return true;
	}

	// relax 3 pixels below, 2 if the current pixel is in first or last column
	private void relaxVertical(int col, int row, double[][] energyTo, double[][] energy, int[][] pixelTo) {
		if (col > 0 && energyTo[row + 1][col - 1] > energyTo[row][col] + energy[row + 1][col - 1]) {
			energyTo[row + 1][col - 1] = energyTo[row][col] + energy[row + 1][col - 1];
			pixelTo[row + 1][col - 1] = col;
		}

		if (energyTo[row + 1][col] > energyTo[row][col] + energy[row + 1][col]) {
			energyTo[row + 1][col] = energyTo[row][col] + energy[row + 1][col];
			pixelTo[row + 1][col] = col;
		}

		if (col < (width() - 1) && energyTo[row + 1][col + 1] > energyTo[row][col] + energy[row + 1][col + 1]) {
			energyTo[row + 1][col + 1] = energyTo[row][col] + energy[row + 1][col + 1];
			pixelTo[row + 1][col + 1] = col;
		}
	}

	// relax 3 pixels on the right, 2 if the pixel is in first or last row
	private void relaxHorizontal(int col, int row, double[][] energyTo, double[][] energy, int[][] pixelTo) {
		if (row > 0 && energyTo[row-1][col+1] > energyTo[row][col] + energy[row-1][col+1]){
			energyTo[row-1][col+1] = energyTo[row][col] + energy[row-1][col+1];
			pixelTo[row-1][col+1] = row;
		}
		
		if (energyTo[row][col+1] > energyTo[row][col] + energy[row][col+1]){
			energyTo[row][col+1] = energyTo[row][col] + energy[row][col+1];
			pixelTo[row][col+1] = row;
		}
		
		if(row < height()-1 && energyTo[row+1][col+1] > energyTo[row][col] + energy[row+1][col+1]){
			energyTo[row+1][col+1] = energyTo[row][col] + energy[row+1][col+1];
			pixelTo[row+1][col+1] = row;
		}
	}

	private double deltaSquared(int rgb1, int rgb2) {
		double dr = ((rgb2 >> 16) & 0xFF) - ((rgb1 >> 16) & 0xFF);
		double dg = ((rgb2 >> 8) & 0xFF) - ((rgb1 >> 8) & 0xFF);
		double db = ((rgb2 >> 0) & 0xFF) - ((rgb1 >> 0) & 0xFF);

		return dr * dr + dg * dg + db * db;
	}

	public static void main(String[] args) {
		SeamCarver sc = new SeamCarver(new Picture(args[0]));
		int[][] array = {{1,2},{3,4},{5,6}};
		int[][] transposed = sc.transpose(array);
		
		System.out.println(Arrays.toString(transposed));
	}
}
