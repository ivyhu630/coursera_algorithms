package com.company.collinear;

import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segmentsArr;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsParam) {
        if (pointsParam == null) {
            throw new IllegalArgumentException("no input");
        }
        for (Point p : pointsParam) {
            if (p == null) {
                throw new IllegalArgumentException("missing input");
            }
        }
        Point[] points = pointsParam.clone();

        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        int length = points.length;
        Arrays.sort(points);
        for (int p = 0; p < length; p++) {
            for (int q = p + 1; q < length; q++) {
                if (points[p].compareTo(points[q]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
                for (int r = q + 1; r < length; r++) {
                    for (int s = r + 1; s < length; s++) {
                        double pqSlope = points[p].slopeTo(points[q]);
                        double prSlope = points[p].slopeTo(points[r]);
                        double psSlope = points[p].slopeTo(points[s]);
                        if (pqSlope == prSlope && pqSlope == psSlope) {
                            segmentsList.add(new LineSegment(points[p], points[s]));
                        }

                    }
                }
            }
        }
        segmentsArr = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsArr.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segmentsArr, segmentsArr.length);
    }

    public static void main(String[] args) {
        int ct = StdIn.readInt();
        Point[] points = new Point[ct];
        int i = 0;
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            i++;
        }
        BruteCollinearPoints bb = new BruteCollinearPoints(points);
        System.out.println(bb.segments());
        System.out.println(bb.numberOfSegments());

    }
}
