import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A simple Swing application that serves as a demostration of graphics and
 * thread pools. A user can launch bouncing balls by clicking on a button. Each
 * ball is run on a thread and these threads come from a fixed-size thread pool.
 * Each ball lives for a limited but not too short of a time, so repeatedly
 * clicking the launch button can cause the pool to fill up and require new
 * balls to wait.
 *
 * The main frame window includes a status line with information about the
 * application's state.
 */
public class ThreadPoolBouncer extends JFrame implements Runnable {
    private Random random = new Random();

    private static final int POOL_SIZE = 8;
    private JPanel canvas = new JPanel();
    private JLabel statusLabel = new JLabel();
    private int activeCount = 0;
    private int waitCount = 0;

    /**
     * A thread to do the rendering on, since we don't want to render on the user
     * interface thread.
     */
    private Thread renderThread = new Thread(this);

    /**
     * Stores all the ball tasks. Note balls are not threads -- they are runnables
     * which will be run on threads from a thread pool.
     */
    private List<Ball> balls = Collections.synchronizedList(new ArrayList<Ball>());

    private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

    /**
     * An action to launch a new ball.
     */
    private Action launchAction = new AbstractAction("Launch") {
        public void actionPerformed(ActionEvent e) {
            Ball ball = new Ball();
            balls.add(ball);
            synchronized (this) {
                waitCount++;
                updateStatus();
            }
            pool.execute(ball);
        }
    };

    /**
     * Constructs and lays out the application frame.
     */
    public ThreadPoolBouncer() {
        JPanel statusPanel = new JPanel();
        statusPanel.add(new JButton(launchAction));
        statusPanel.add(statusLabel);
        getContentPane().add(statusPanel, BorderLayout.SOUTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
        setResizable(false);
    }

    /**
     * Renders the canvas with all the balls. First it creates a back buffer and
     * graphics objects for the back buffer and the canvas. Then it loops around
     * drawing all the live balls to the back buffer then blitting the back buffer
     * to the canvas.
     */
    public void run() {
        Image buffer = createImage(canvas.getWidth(), canvas.getHeight());
        Graphics g = buffer.getGraphics();
        Graphics gc = canvas.getGraphics();
        while (true) {
            g.setColor(Color.white);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            synchronized (balls) {
                for (Ball b : balls) {
                    b.draw(g);
                }
            }
            gc.drawImage(buffer, 0, 0, canvas);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
    }

    private void updateStatus() {
        final String text;
        synchronized (this) {
            text = "Waiting: " + waitCount + " Active: " + activeCount;
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                statusLabel.setText(text);
            }
        });
    }

    /**
     * Runs the demonstration as an application.
     */
    public static void main(String[] args) {
        ThreadPoolBouncer bouncer = new ThreadPoolBouncer();
        bouncer.setTitle("Bouncing Balls");
        bouncer.setSize(800, 600);
        bouncer.setVisible(true);
        bouncer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bouncer.renderThread.setPriority(Thread.MAX_PRIORITY);
        bouncer.renderThread.start();
    }

    /**
     * A ball is something that bounces around on a JPanel. Balls are created with a
     * random color not too close to white, initially at a random position on the
     * canvas and oriented in an random direction (neither horizontal or vertical).
     */
    private class Ball implements Runnable {
        private Color color = new Color(random.nextInt(200), random.nextInt(200), random.nextInt(255));
        private int x = random.nextInt(getWidth());
        private int y = random.nextInt(getWidth());
        private int dx = 1 + random.nextInt(5);
        private int dy = 1 + random.nextInt(3);
        private static final int RADIUS = 30;

        /**
         * Draws the ball as a filled in circle.
         */
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, RADIUS, RADIUS);
        }

        /**
         * Updates the position of the ball, taking care of bounces.
         */
        protected void move() {
            x += dx;
            y += dy;
            if (x < 0) {
                // Bounce off left wall.
                x = 0;
                dx = -dx;
            }
            if (x + RADIUS >= canvas.getWidth()) {
                // Bounce off right wall.
                x = canvas.getWidth() - RADIUS;
                dx = -dx;
            }
            if (y < 0) {
                // Bounce off top wall.
                y = 0;
                dy = -dy;
            }
            if (y + RADIUS >= canvas.getHeight()) {
                // Bounce off bottom wall.
                y = canvas.getHeight() - RADIUS;
                dy = -dy;
            }
        }

        /**
         * A ball basically just moves 1000 times. After each position update, it sleeps
         * for 5 milliseconds.
         */
        public void run() {
            synchronized (this) {
                waitCount--;
                activeCount++;
                updateStatus();
            }
            for (int i = 1; i <= 1000; i++) {
                move();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            }
            balls.remove(this);
            synchronized (this) {
                activeCount--;
                updateStatus();
            }
        }
    }
}

