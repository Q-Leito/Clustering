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

    public PointParser(String file) {
        mFile = new File(file);
    }

    /**
     * parsePoints
     *
     * Read file, parse data and store into ArrayList
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
                    lines.add(line.split(","));
                }
            }
        }

        parseLines(lines);

        return mPoints;
    }

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