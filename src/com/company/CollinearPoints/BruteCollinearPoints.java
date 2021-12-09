package com.company.CollinearPoints;

import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private final LineSegment[] segmentsArr;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("no input");
        }
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        int length = points.length;
        for (int p = 0; p < length; p++) {
            if (points[p] == null) {
                throw new IllegalArgumentException("null point");
            }
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
        segmentsArr = (LineSegment[]) segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsArr.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segmentsArr;
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
