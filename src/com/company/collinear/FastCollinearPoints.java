package com.company.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segmentsArr;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pointsParam) {
        if (pointsParam == null) {
            throw new IllegalArgumentException("no input");
        }
        for (Point p : pointsParam) {
            if (p == null) {
                throw new IllegalArgumentException("missing input");
            }
        }
        Point[] points = pointsParam.clone();
        int length = points.length;
        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        Arrays.sort(points);
        Point priorPoint = points[0];
        for (int i = 0; i < length; i++) {
            Point[] pointsCopy = points.clone();
            Point p = points[i];
            if (i > 0 && priorPoint.compareTo(p) == 0) {
                throw new IllegalArgumentException("repeated point");
            }
            priorPoint = p;
            Arrays.sort(pointsCopy, p.slopeOrder());
            double lastSlope = p.slopeTo(p);
            int start = 0, end;
            for (int j = 1; j < length; j++) {
                Point other = pointsCopy[j];
                double slope = p.slopeTo(other);
                if (slope != lastSlope || j == length - 1) {
                    end = slope != lastSlope ? j - 1 : j;
                    Point startPoint = pointsCopy[start];
                    Point endPoint = pointsCopy[end];
                    if (end - start + 2 >= 4) {
                        if (p.compareTo(startPoint) < 0) {
                            segmentsList.add(new LineSegment(p, endPoint));
                        }
                    }

                    start = j;
                }
                lastSlope = slope;
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

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
