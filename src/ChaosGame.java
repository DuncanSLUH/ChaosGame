import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

public class ChaosGame extends JPanel {

    // get the point in between two points
    private Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }


    Stack<Point> stack = new Stack<>();
    Point[] points = new Point[3];

    Random r = new Random();

    // amount of points drawn
    int amount = 0;

    public ChaosGame() {
        // this also controls the size of the triangle!!
        Dimension dim = new Dimension(920, 920);
        setPreferredSize(dim);
        setBackground(Color.white);

        int margin = 80;
        int size = dim.width - 2 * margin;

        points[0] = new Point(dim.width / 2, margin);
        points[1] = new Point(margin, size);
        points[2] = new Point(margin + size, size);

        stack.push(new Point(0, 0));

        new Timer(10, (ActionEvent e) -> {
            // add 1000 at a time to make it faster
            for (int i = 0; i < 1000; i++) addPoint();
            repaint();
        }).start();
    }

    // add a point to be drawn
    private void addPoint() {
        try {
            int towards = r.nextInt(3);
            Point p1 = stack.peek();
            Point p2 = points[towards];
            stack.add(midpoint(p1, p2));
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }

    // draw a circle and print out info to console
    void drawPoints(Graphics2D g) {
        for (Point p : stack) {
            g.fillOval(p.x, p.y, 1, 1);
            amount++;
            System.out.println("Printed point " + amount + " at (" + p.x + ", " + p.y + ")");
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawPoints(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("");
            f.setResizable(false);
            f.add(new ChaosGame(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}