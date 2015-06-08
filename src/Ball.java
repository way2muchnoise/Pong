import java.awt.*;

public class Ball
{
    int sX, sY, v, x, y, vX, vY, top, bottom;
    int h = 10;
    int w = 10;
    double angle;

    public Ball(int x, int y, int v, int top, int bottom)
    {
        this.sX = x;
        this.sY = y;
        this.v = v;
        this.top = top;
        this.bottom = bottom;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, w, h);
    }

    public void spawn()
    {
        this.angle = Math.random() * 45;
        this.angle = Math.toRadians(275);
        this.x = this.sX;
        this.y = this.sY;
        this.vX = (int)Math.round(this.v * Math.sin(this.angle));
        this.vY = (int)Math.round(this.v * Math.cos(this.angle));
    }

    public void update(Beam left, Beam right)
    {
        this.x += this.vX;
        this.y += this.vY;
        if (this.y < top)
        {
            this.y = top;
            this.vY = -this.vY;
        }
        if (this.y > bottom)
        {
            this.y = bottom;
            this.vY = -this.vY;
        }
        if (left.impactR(this.x, this.y))
        {
            this.x = left.x + left.w;
            this.vX = -this.vX;
        }
        if (right.impactL(this.x, this.y))
        {
            this.x = right.x - right.w;
            this.vX = -this.vX;
        }
    }

    public boolean outOfBounds(int left, int right)
    {
        return this.x > right || this.x < left;
    }
}
