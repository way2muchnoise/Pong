import java.awt.*;

public class Ball
{
    int sX, sY, sV, x, y, v, top, bottom;
    int h = 10;
    int w = 10;
    double angle;

    public Ball(int x, int y, int v, int top, int bottom)
    {
        this.sX = x;
        this.sY = y;
        this.sV = v;
        this.top = top;
        this.bottom = bottom;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, 10, 10);
    }

    public void spawn()
    {
        this.angle = Math.random() * 1.57 + 0.78;
        this.x = this.sX;
        this.y = this.sY;
        this.v = this.sV;
    }

    public void update(Beam left, Beam right)
    {
        this.x += (int)Math.round(v * Math.sin(this.angle));
        this.y += (int)Math.round(v * Math.cos(this.angle));
        if (this.y < top)
        {
            this.y = top;
            this.angle -= 0.78;
        }
        if (this.y + this.h > bottom)
        {
            this.y = bottom - h;
            this.angle -= 0.78;
        }
        if (left.impactR(this.x, this.y))
        {
            this.x = left.x + this.w;
            this.angle -= 0.78;
        }
        if (right.impactL(this.x, this.y))
        {
            this.x = right.x - this.w;
            this.angle -= 0.78;
        }
    }

    public boolean outOfBounds(int left, int right)
    {
        return this.x > right || this.x < left;
    }
}
