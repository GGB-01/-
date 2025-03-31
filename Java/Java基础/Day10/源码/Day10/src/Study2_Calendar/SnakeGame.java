package Study2_Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class SnakeGame extends JFrame {
    private GamePanel gamePanel;
    private int gridSize = 20; // 默认地图大小
    private int speed = 100; // 默认速度（毫秒）

    public SnakeGame() {
        setTitle("贪吃蛇");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showMenu();
    }

    // 主菜单
    private void showMenu() {
        getContentPane().removeAll();
        JPanel menuPanel = new JPanel(new GridLayout(5, 1));

        JButton sizeButton = new JButton("选择地图大小（当前：" + gridSize + "x" + gridSize + "）");
        JButton speedButton = new JButton("选择难度（当前速度：" + speed + "ms）");
        JButton tutorialButton = new JButton("新手教程");
        JButton startButton = new JButton("开始游戏");
        JButton exitButton = new JButton("退出");

        sizeButton.addActionListener(e -> chooseSize());
        speedButton.addActionListener(e -> chooseSpeed());
        tutorialButton.addActionListener(e -> showTutorial());
        startButton.addActionListener(e -> startGame());
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(sizeButton);
        menuPanel.add(speedButton);
        menuPanel.add(tutorialButton);
        menuPanel.add(startButton);
        menuPanel.add(exitButton);

        add(menuPanel);
        pack();
        setLocationRelativeTo(null);
    }

    // 选择地图大小
    private void chooseSize() {
        String[] options = {"20x20", "30x30", "40x40"};
        String choice = (String) JOptionPane.showInputDialog(this,
                "选择地图大小：", "地图大小",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice != null) {
            gridSize = Integer.parseInt(choice.split("x")[0]);
            showMenu();
        }
    }

    // 选择速度
    private void chooseSpeed() {
        String[] options = {"慢速(200ms)", "中速(100ms)", "快速(50ms)"};
        String choice = (String) JOptionPane.showInputDialog(this,
                "选择难度（速度）：", "难度选择",
                JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (choice != null) {
            speed = Integer.parseInt(choice.substring(choice.indexOf("(") + 1, choice.indexOf("ms)")));
            showMenu();
        }
    }

    // 显示新手教程
    private void showTutorial() {
        JOptionPane.showMessageDialog(this,
                "新手教程：\n" +
                        "↑ 上箭头：向上移动\n" +
                        "↓ 下箭头：向下移动\n" +
                        "← 左箭头：向左移动\n" +
                        "→ 右箭头：向右移动\n" +
                        "P键：暂停/继续游戏\n" +
                        "目标：吃掉红色的食物（方块）来增长蛇身\n" +
                        "注意：不要撞到墙壁或自己的身体！",
                "新手教程",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // 开始游戏
    private void startGame() {
        getContentPane().removeAll();
        gamePanel = new GamePanel();
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        gamePanel.requestFocus();
    }

    // 游戏面板
    class GamePanel extends JPanel {
        private ArrayList<Point> snake;
        private Point food;
        private char direction = 'R';
        private boolean running = false;
        private boolean paused = false;
        private Timer timer;
        private Random random;

        public GamePanel() {
            setPreferredSize(new Dimension(gridSize * 20, gridSize * 20));
            setBackground(Color.BLACK);
            random = new Random();
            snake = new ArrayList<>();
            initGame();

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP: if (direction != 'D') direction = 'U'; break;
                        case KeyEvent.VK_DOWN: if (direction != 'U') direction = 'D'; break;
                        case KeyEvent.VK_LEFT: if (direction != 'R') direction = 'L'; break;
                        case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
                        case KeyEvent.VK_P: togglePause(); break;
                    }
                }
            });
            setFocusable(true);
        }

        private void initGame() {
            snake.clear();
            snake.add(new Point(gridSize/2, gridSize/2));
            generateFood();
            running = true;
            paused = false;
            timer = new Timer(speed, e -> {
                if (running && !paused) {
                    move();
                    repaint();
                }
            });
            timer.start();
        }

        private void generateFood() {
            do {
                food = new Point(random.nextInt(gridSize), random.nextInt(gridSize));
            } while (snake.contains(food));
        }

        private void move() {
            Point head = new Point(snake.get(0));
            switch (direction) {
                case 'U': head.y--; break;
                case 'D': head.y++; break;
                case 'L': head.x--; break;
                case 'R': head.x++; break;
            }

            if (head.equals(food)) {
                snake.add(0, head);
                generateFood();
            } else {
                snake.add(0, head);
                snake.remove(snake.size() - 1);
            }

            if (head.x < 0 || head.x >= gridSize || head.y < 0 || head.y >= gridSize ||
                    snake.subList(1, snake.size()).contains(head)) {
                gameOver();
            }
        }

        private void togglePause() {
            paused = !paused;
        }

        private void gameOver() {
            running = false;
            timer.stop();
            int choice = JOptionPane.showOptionDialog(this,
                    "游戏结束！得分：" + (snake.size() - 1) + "\n重新开始还是返回菜单？",
                    "游戏结束",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"重新开始", "返回菜单"},
                    "重新开始");

            if (choice == 0) {
                initGame();
            } else {
                showMenu();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // 画食物
            g.setColor(Color.RED);
            g.fillRect(food.x * 20, food.y * 20, 20, 20);
            // 画蛇
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * 20, p.y * 20, 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame().setVisible(true));
    }
}


