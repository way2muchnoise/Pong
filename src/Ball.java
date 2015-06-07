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
        g.fillOval(x, y, w, h);
    }

    public void spawn()
    {
        this.angle = Math.random() * 45;
        if (angle > 43 && angle < 47)
            this.angle += Math.random() * 2 - 1;
        this.angle = Math.toRadians(angle +250);
        this.x = this.sX;
        this.y = this.sY;
        this.v = this.sV;
    }

    public void update(Beam left, Beam right)
    {
        this.x += (int)Math.round(v * Math.sin(this.angle));
        this.y += (int)Math.round(v * Math.cos(this.angle));
        double angleMod = this.angle%(Math.PI*2);
        if (angleMod < 0) angleMod += (Math.PI*2);
        double changeAngle = Math.PI - angleMod;
        if (changeAngle == 0 || changeAngle == Math.PI)
            changeAngle += (Math.random() *2 / 10)-1;
        if (this.y < top)
        {
            this.y = top;
            if (angleMod < Math.PI) this.angle -= changeAngle;
            else this.angle += changeAngle;
        }
        if (this.y > bottom)
        {
            this.y = bottom;
            if (angleMod > Math.PI/2*3) this.angle += changeAngle-Math.PI;
            else this.angle -= changeAngle-Math.PI;
        }
        if (left.impactR(this.x, this.y))
        {
            this.x = left.x + left.w;
            this.angle = -angle;
        }
        if (right.impactL(this.x, this.y))
        {
            this.x = right.x - right.w;
            this.angle = -angle;
        }
    }

    public boolean outOfBounds(int left, int right)
    {
        return this.x > right || this.x < left;
    }
}
