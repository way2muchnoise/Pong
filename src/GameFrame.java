import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameFrame extends JFrame
{
    private int windowWidth = 500;
    private int windowHeight = 500;

    private BufferedImage backBuffer;
    private Insets insets;
    private InputHandler input;

    private Beam player;
    private BeamAI ai;
    private Ball ball;

    public static void main(String[] args)
    {
        GameFrame game = new GameFrame();
        game.run();
        System.exit(0);
    }

    public void run()
    {
        initialize();

        while(true)
        {
            long time = System.currentTimeMillis();

            update();
            if (ball.outOfBounds(0, windowWidth))
            {
                JOptionPane.showMessageDialog(this, (ball.x < 0 ? "Computer" : "Player") +" scores");
                resetGame();
            }
            draw();

            int fps = 60;
            time = (1000 / fps) - (System.currentTimeMillis() - time);

            if (time > 0)
            {
                try
                {
                    Thread.sleep(time);
                }
                catch(Exception ignored)
                {
                    break;
                }
            }
        }

        setVisible(false);
    }

    void initialize()
    {
        setTitle("Pong");
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);

        backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        input = new InputHandler(this);
        resetGame();
    }

    public void resetGame()
    {
        ball = new Ball(250, 250, 5, 0, windowHeight);
        ball.spawn();
        int rectHeight = 50;
        player = new Beam(0, 200, 5, rectHeight, 0, windowHeight);
        player.spawn();
        ai = new BeamAI(windowWidth-5, 200, 5, rectHeight, 0, windowHeight);
        ai.spawn();
    }

    void update()
    {
        int move = 5;
        if (input.isKeyDown(KeyEvent.VK_UP)) player.up(move);
        if (input.isKeyDown(KeyEvent.VK_DOWN)) player.down(move);
        if (input.isKeyDown(KeyEvent.VK_R))
        {
            player.spawn();
            ai.spawn();
            ball.spawn();
        }
        ai.update(ball, move);
        ball.update(player, ai);
    }

    void draw()
    {
        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();

        bbg.setColor(Color.WHITE);
        bbg.fillRect(0, 0, windowWidth, windowHeight);

        player.draw(bbg);
        ai.draw(bbg);
        ball.draw(bbg);

        g.drawImage(backBuffer, insets.left, insets.top, this);
    }
} 