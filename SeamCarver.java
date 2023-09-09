import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    // creates Picture object
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > this.width() || y > this.height()) {
            throw new IllegalArgumentException();
        }
        int up;
        if (y + 1 == this.height()) {
            up = 0;
        }
        else {
            up = y + 1;
        }
        int down;
        if (y - 1 == -1) {
            down = this.height() - 1;
        }
        else {
            down = y - 1;
        }
        int left;
        if (x - 1 == -1) {
            left = this.width() - 1;
        }
        else {
            left = x - 1;
        }
        int right;
        if (x + 1 == this.width()) {
            right = 0;
        }
        else {
            right = x + 1;
        }
        int rgbUp = picture.getRGB(x, up);
        int rgbDown = picture.getRGB(x, down);
        int rgbLeft = picture.getRGB(left, y);
        int rgbRight = picture.getRGB(right, y);
        int redY = ((rgbUp >> 16) & 0xFF) - ((rgbDown >> 16) & 0xFF);
        int greenY = ((rgbUp >> 8) & 0xFF) - ((rgbDown >> 8) & 0xFF);
        int blueY = ((rgbUp >> 0) & 0xFF) - ((rgbDown >> 0) & 0xFF);
        int redX = ((rgbLeft >> 16) & 0xFF) - ((rgbRight >> 16) & 0xFF);
        int greenX = ((rgbLeft >> 8) & 0xFF) - ((rgbRight >> 8) & 0xFF);
        int blueX = ((rgbLeft >> 0) & 0xFF) - ((rgbRight >> 0) & 0xFF);
        double yGradientSq = (redY * redY) + (greenY * greenY) + (blueY * blueY);
        double xGradientSq = (redX * redX) + (greenX * greenX) + (blueX * blueX);
        return Math.sqrt((xGradientSq + yGradientSq));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transposePic();
        int[] seam = findVerticalSeam();
        transposePic();
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // creates energy array
        double[][] energy = new double[picture.height()][picture.width()];
        // fills in energy array
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energy[row][col] = energy(col, row);
            }
        }
        // creates edgeTo array
        int[][] edgeTo = new int[picture.height()][picture.width()];
        // creates distTo array
        double[][] distTo = new double[picture.height()][picture.width()];
        // fills in row 0 of distTo array
        for (int col = 0; col < this.width(); col++) {
            distTo[0][col] = energy[0][col];
        }
        // we now loop through and check the edges, and update the edgeTo and
        // distTo arrays accordingly
        for (int row = 1; row < this.height(); row++) {
            for (int col = 0; col < this.width(); col++) {
                double middle;
                double left;
                double right;
                // check left
                if (col != 0) {
                    left = distTo[row - 1][col - 1];
                }
                else {
                    left = Double.POSITIVE_INFINITY;
                }
                // check right
                if (col != this.width() - 1) {
                    right = distTo[row - 1][col + 1];
                }
                else {
                    right = Double.POSITIVE_INFINITY;
                }
                // there will always be a middle
                middle = distTo[row - 1][col];
                double lrShortest = Math.min(left, right);
                double shortest = Math.min(lrShortest, middle);
                distTo[row][col] = shortest + energy(col, row);
                // set edgeTo[] to the closest pixel
                if (shortest == left) {
                    edgeTo[row][col] = col - 1;
                }
                else if ((shortest == right)) {
                    edgeTo[row][col] = col + 1;
                }
                else {
                    edgeTo[row][col] = col;
                }
            }
        }
        // find the min energy seam
        double bottomMin = Double.POSITIVE_INFINITY;
        int minIndex = 0;
        for (int i = 0; i < this.width(); i++) {
            if (distTo[this.height() - 1][i] < bottomMin) {
                bottomMin = distTo[this.height() - 1][i];
                minIndex = i;
            }
        }
        int[] verticalSeam = new int[this.height()];
        // start from bottom row and loop up to top, updating the vertical
        // seam as it goes
        for (int i = this.height() - 1; i >= 0; i--) {
            verticalSeam[i] = minIndex;
            minIndex = edgeTo[i][minIndex];
        }
        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != this.width()) {
            throw new IllegalArgumentException();
        }
        if (height() == 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        transposePic();
        removeVerticalSeam(seam);
        transposePic();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != this.height()) {
            throw new IllegalArgumentException();
        }
        if (width() == 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        Picture copy = new Picture(picture.width() - 1, picture.height());
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width() - 1; col++) {
                if (col < seam[row]) {
                    copy.setRGB(col, row, picture.getRGB(col, row));
                }
                else {
                    copy.setRGB(col, row, picture.getRGB(col + 1, row));
                }
            }
        }
        this.picture = new Picture(copy);
    }

    // transposes the current picture
    private void transposePic() {
        Picture copy = new Picture(picture.height(), picture.width());
        for (int row = 0; row < picture.width(); row++) {
            for (int col = 0; col < picture.height(); col++) {
                copy.setRGB(col, row, picture.getRGB(row, col));
            }
        }
        this.picture = copy;
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver seamTest = new SeamCarver(picture);
        StdOut.println(seamTest.energy(0, 0));
        StdOut.println(seamTest.height());
        StdOut.println(seamTest.width());
        StdOut.println(seamTest.picture());
        int[] testArr = seamTest.findVerticalSeam();
        for (int i = 0; i < testArr.length; i++) {
            StdOut.println(testArr[i]);
        }
        StdOut.println();
        int[] testArr1 = seamTest.findHorizontalSeam();
        for (int i = 0; i < testArr1.length; i++) {
            StdOut.println(testArr1[i]);
        }
        seamTest.removeVerticalSeam(testArr);
        StdOut.println(seamTest.picture());
        seamTest.removeHorizontalSeam(testArr);
    }
}
