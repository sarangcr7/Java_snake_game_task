//snake game using java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 600, HEIGHT = 400, BLOCK_SIZE = 20;
    private LinkedList<Point> snake;
    private Point food;
    private int direction = KeyEvent.VK_RIGHT;
    private boolean gameOver = false;
    private Timer timer;
    
    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new Point(100, 100));
        spawnFood();
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        
        timer = new Timer(100, this);
        timer.start();
    }
    
    private void spawnFood() {
        Random rand = new Random();
        food = new Point(rand.nextInt(WIDTH / BLOCK_SIZE) * BLOCK_SIZE, rand.nextInt(HEIGHT / BLOCK_SIZE) * BLOCK_SIZE);
    }
    
    private void moveSnake() {
        if (gameOver) return;
        
        Point head = snake.getFirst();
        Point newHead = new Point(head);
        
        switch (direction) {
            case KeyEvent.VK_UP: newHead.translate(0, -BLOCK_SIZE); break;
            case KeyEvent.VK_DOWN: newHead.translate(0, BLOCK_SIZE); break;
            case KeyEvent.VK_LEFT: newHead.translate(-BLOCK_SIZE, 0); break;
            case KeyEvent.VK_RIGHT: newHead.translate(BLOCK_SIZE, 0); break;
        }
        
        if (newHead.equals(food)) {
            snake.addFirst(food);
            spawnFood();
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }
        
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT || snake.contains(newHead)) {
            gameOver = true;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over", WIDTH / 2 - 40, HEIGHT / 2);
        } else {
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x, p.y, BLOCK_SIZE, BLOCK_SIZE);
            }
            
            g.setColor(Color.RED);
            g.fillRect(food.x, food.y, BLOCK_SIZE, BLOCK_SIZE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        moveSnake();
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int newDirection = e.getKeyCode();
        if ((newDirection == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) ||
            (newDirection == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) ||
            (newDirection == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) ||
            (newDirection == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT)) {
            direction = newDirection;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
