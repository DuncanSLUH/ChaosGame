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

    // stacks are perfect for this
    private final Stack<Point> stack = new Stack<>();
    private final Point[] points = new Point[3];
    private final Random r = new Random();
    private int amount = 0;

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
            for (int i = 0; i < 1000; i++) {
                try {
                    Point latestPoint = stack.peek();
                    Point towardPoint = points[r.nextInt(3)];
                    stack.add(midpoint(latestPoint, towardPoint));
                } catch (EmptyStackException ex) {
                    ex.printStackTrace();
                }
            }
            repaint();
        }).start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Point p : stack) {
            graphics2D.fillOval(p.x, p.y, 1, 1);
            amount++;
            System.out.println("Printed point " + amount + " at (" + p.x + ", " + p.y + ")");
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("");
        f.setResizable(false);
        f.add(new ChaosGame(), BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}