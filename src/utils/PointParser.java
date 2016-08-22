package utils;

import clustering.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PointParser {
    private List<Point> mPoints;
    private File mFile;
    private static final String SEPERATOR = ","; // Charaters that seperates values("," or "\t" or "\n").

    public PointParser(String file) {
        mFile = new File(file);
    }

    /**
     * Reads file, parse data and store into ArrayList.
     *
     * 1)
     * WineData.csv will be parsed, lines contains 100 (clients) String array.
     * Each String array has 32 (offers) String values (0 or 1)
     *
     * 2)
     * The parseLines() will iterate through the lines ArrayList and iterate through each array (and parse) from that line.
     * List<Point> mPoints is filled.
     *
     * @return ArrayList
     * @throws FileNotFoundException FileNotFoundException
     */
    public List<Point> parsePoints() throws FileNotFoundException {
        mPoints = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();

        try(Scanner sc = new Scanner(mFile)) {
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line != null && !line.isEmpty()) {
                    lines.add(line.split(SEPERATOR));
                }
            }
        }

        parseLines(lines);

        return mPoints;
    }

    /**
     * 1) Iterates through lines (offers) List.
     * 2) For each offer insert which client took it or not,
     *      insert 1 or 0 into values array,
     *      add values array to List<Point> mPoints.
     *
     * @param lines List of lines parsed.
     */
    private void parseLines(List<String[]> lines) {
        for(int i = 0; i < lines.get(0).length; i++) {
            double[] values = new double[lines.size()];
            for(int j = 0; j < values.length; j++) {
                values[j] = Double.parseDouble(lines.get(j)[i]);
            }
            mPoints.add(new Point(values));
        }
    }
}