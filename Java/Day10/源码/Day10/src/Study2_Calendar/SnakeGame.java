package Study2_Calendar
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Test extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 600, HEIGHT = 600;
    private final int SIZE = 20;  // 每个方块的大小
    private final int NUM_COLUMNS = WIDTH / SIZE;
    private final int NUM_ROWS = HEIGHT / SIZE;

    private LinkedList<Point> snake;
    private Point food;
    private boolean isGameOver = false;
    private int direction = KeyEvent.VK_RIGHT;  // 初始方向
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snake = new LinkedList<>();
        snake.add(new Point(NUM_COLUMNS / 2, NUM_ROWS / 2));  // 初始蛇头位置

        spawnFood();

        timer = new Timer(100, this);  // 每100毫秒更新一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", WIDTH / 4, HEIGHT / 2);
            return;
        }

        // 绘制蛇
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * SIZE, p.y * SIZE, SIZE, SIZE);
        }

        // 绘制食物
        g.setColor(Color.RED);
        g.fillRect(food.x * SIZE, food.y * SIZE, SIZE, SIZE);
    }

    public void actionPerformed(ActionEvent e) {
        if (isGameOver) {
            return;
        }

        // 获取蛇头的位置
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        // 根据方向移动蛇头
        switch (direction) {
            case KeyEvent.VK_UP:
                newHead.y--;
                break;
            case KeyEvent.VK_DOWN:
                newHead.y++;
                break;
            case KeyEvent.VK_LEFT:
                newHead.x--;
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x++;
                break;
        }

        // 检查蛇是否撞到墙壁
        if (newHead.x < 0 || newHead.x >= NUM_COLUMNS || newHead.y < 0 || newHead.y >= NUM_ROWS) {
            isGameOver = true;
            repaint();
            return;
        }

        // 检查蛇是否撞到自己
        if (snake.contains(newHead)) {
            isGameOver = true;
            repaint();
            return;
        }

        // 吃到食物
        if (newHead.equals(food)) {
            snake.addFirst(newHead);
            spawnFood();  // 生成新的食物
        } else {
            snake.addFirst(newHead);
            snake.removeLast();  // 删除蛇尾
        }

        repaint();
    }

    private void spawnFood() {
        Random rand = new Random();
        food = new Point(rand.nextInt(NUM_COLUMNS), rand.nextInt(NUM_ROWS));

        // 确保食物不生成在蛇身上
        while (snake.contains(food)) {
            food = new Point(rand.nextInt(NUM_COLUMNS), rand.nextInt(NUM_ROWS));
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // 防止蛇反方向移动
        if (key == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) {
            direction = key;
        } else if (key == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) {
            direction = key;
        } else if (key == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) {
            direction = key;
        } else if (key == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) {
            direction = key;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


