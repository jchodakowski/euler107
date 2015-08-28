import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EdgeDetector {

	public static void main(String[] args) {
		// Making sure we have the input
		String fileName = "p107_network.txt";
		if (args.length > 0)
			fileName = args[0];

		// Really making sure we have the input
		int[][] matrix = readFile(fileName);
		if (matrix == null)
			System.exit(1);

		try {
			sanityCheck(matrix);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		// Off to the races
		bingoCard(matrix);

		int totalWeight = getTotalWeight(matrix);
		System.out.println("Total matrix weight: " + totalWeight);
		int r1 = bestPath(matrix);
		int x = totalWeight - r1;
		System.out.println("New network cost: " + x);
		System.out.println("Total savings: " + r1);
	}

	private static int bestPath(int[][] matrix) {
		int depth = matrix.length;
		boolean[] seen = new boolean[depth];
		// pre-seed the first node in the table
		seen[0] = true;
		int weight = 0;
		for (int i = 1; i < depth; i++) {
			int lwm = -1;
			int current = -1;
			XAxis: for (int x = 0; x < depth; x++) {
				// continue until we've seen it all
				// bear in mind position 0 was pre-seeded
				if (!seen[x])
					continue XAxis;

				YAxis: for (int y = 0; y < depth; y++) {
					// seen this item
					if (seen[y])
						continue YAxis;

					// end of this line
					if (matrix[x][y] == -1)
						continue YAxis;

					// this is where we flag our low water mark
					if (lwm == -1 || matrix[x][y] < lwm) {
						lwm = matrix[x][y];
						current = y;
					}
				}
			}

			seen[current] = true;
			weight += lwm;
		}

		return weight;
	}

	private static int getTotalWeight(int[][] matrix) {
		int weight = 0;
		for (int x = 0; x < matrix.length; x++) {
			YAxis: for (int y = 0; y < matrix.length; y++) {
				int w = matrix[x][y];
				if (w == -1)
					continue YAxis;

				weight = weight + w;
			}
		}
		return weight;
	}

	/**
	 * Intended to be extended in case there's a scenario I forgot
	 * 
	 * @param matrix
	 * @throws Exception
	 */
	private static void sanityCheck(int[][] matrix) throws Exception {
		if (matrix.length != matrix[0].length)
			throw new Exception("The input data does not represent sides of equal length!");

		for (int i = 0; i < matrix.length; i++) {
			// should be a 45 degree line through the middle where everything is 'null'
			// -1 in this case
			if (matrix[i][i] != -1)
				throw new Exception("Matrix data doesn't resolve correctly ( x / y intersection not -1)");
		}
	}

	/**
	 * Put this part of the work in its own method just so it doesn't pollute
	 * the main 'body' of the code. Anything bad that happens here will null the
	 * return so it should get tested for on the receiving end.
	 * 
	 * This function also does the string->int conversion to keep 'conversion'
	 * code all in one place, and that should make 'main' nice and clean and
	 * just the 'main' body of work (or, that's the plan anyway)
	 * 
	 * The goal here is to be nearly production ready which means rejecting (or
	 * altering) data that is otherwise not useful, we'll check the matrix for
	 * squareness in a bit
	 * 
	 * @param fileName
	 * @return int[][]
	 */
	private static int[][] readFile(String fileName) {
		ArrayList<int[]> lines = new ArrayList<int[]>();
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				// Split the line on commas
				String[] q = s.split(",");
				int[] i = new int[q.length];
				L2: for (int x = 0; x < q.length; x++) {
					String n = q[x];
					// go through a quick series of short circuits for
					// bad or non-useful data
					if ((n.length() < 1) // empty
							|| n.equals("-") // dash
							|| !Character.isDigit(n.charAt(0)) // not a number
					) {
						i[x] = -1;
						// exit our inner loop
						continue L2;
					}

					// we're safe to make input an integer
					i[x] = Integer.parseInt(n);
				}
				// put it away
				lines.add(i);
			}
			br.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not locate the input data file '" + fileName + "'");
			lines = null;
		} catch (IOException ioe) {
			System.err.println("There was an error processing the file: " + ioe.getMessage());
			lines = null;
		}

		// trap the exceptions that nulled our output
		if (lines == null)
			return null;

		// or else clean it up and get out
		lines.trimToSize();

		int[][] results = new int[lines.size()][];
		for (int i = 0; i < lines.size(); i++)
			results[i] = lines.get(i);

		return results;
	}

	/**
	 * I wrote this for myself because numbers and algorithmic stuff like this
	 * fills my mind with flying monkeys. I need to see a thing to know how to
	 * work it, so I put in a little extra time, just so I could "see" my data
	 * 
	 * For debug use only. YMMV
	 * 
	 * @param input
	 */
	private static void bingoCard(int[][] input) {
		// iterate quickly so we can figure out a padding
		int pad = 0;
		for (int[] x : input) {
			for (int y : x) {
				String s = new Integer(y).toString();
				if (s.length() > pad)
					pad = s.length();
			}
		}

		// now iterate for output
		for (int[] x : input) {
			StringBuffer sb = new StringBuffer("|");
			for (int y : x) {
				sb.append(" ");
				String s = new Integer(y).toString();
				if (s.length() < pad) {
					int diff = pad - s.length();
					for (int z = 0; z < diff; z++)
						sb.append(" ");
				}
				sb.append(s);
				sb.append(" |");
			}
			System.out.println(sb.toString());
		}
	}
}
