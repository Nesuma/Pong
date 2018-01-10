/**
 * Created by Classdumper, User Peter Heusch
 * Creation Date: 15.12.2017 07:49:44
 */
package simplePong;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class SimpleEngine {

    public class Game {

        private Point2D.Float puckPoint;
        private float puckSpeed;
        private float puckAngle;
        private float paddlePosition;
        private long paddleTimestamp;
        private long oldTimestamp;
        private float oldPosition;
        private float speedUp;

        public Game(Point2D.Float polyCenter) {
            // Roughly 8 lines of implementation
            this.puckPoint = polyCenter;
            this.puckSpeed = 1;
            speedUp = 1;
            this.paddlePosition = 165;
        }
    }

    public class Court {

        private final Point2D.Float polyCenter;
        private final Point2D.Float rectCenter;
        private final Path2D.Float gamePath;
        private final Rectangle2D.Float drawRectangle;

        private Court() {

            // Roughly 18 lines of implementation
            this.polyCenter = new Point2D.Float(200, 200);
            this.drawRectangle = new Rectangle2D.Float(0, 0, 200, 200);
            this.rectCenter = new Point2D.Float(drawRectangle.width / 2, drawRectangle.height / 2);
            this.gamePath = new Path2D.Float(drawRectangle);

        }
    }

    public static final int BALLSIZE = 10;
    public static final int RACKETQUOTE = 70;
    public static final int SQUARE_SIZE = 400;
    public static final int BORDER = 4;
    public static final int LINEWIDTH = 7;
    public static final float PI = 3.141593f;
    public static final int RACKETSPEED = 2;
    public static boolean first = true;
    public int xTemp = (int) (Math.random() * 5) * ((Math.random() > 0.49) ? 1 : -1);
    public int yTemp = (int) (Math.random() * 5) * ((Math.random() > 0.49) ? 1 : -1);
    public int leben;
    public float velX = xTemp / sqrt((xTemp * xTemp) + (yTemp * yTemp));
    public float velY = yTemp / sqrt((xTemp * xTemp) + (yTemp * yTemp));
    public int betrag = (int) ((velX * velX) + (velY * velY));
    private SimpleEngine.Court court;
    private SimpleEngine.Game game;

    public SimpleEngine() {
        // Roughly 2 lines of implementation
        this.court = new Court();
        this.game = new Game(this.court.polyCenter);
        this.leben = 3;
    }

    private float sin(float angle) {
        // Roughly 1 lines of implementation
        return (float) Math.sin((2 * PI / 360) * angle);
    }

    private float cos(float angle) {
        // Roughly 1 lines of implementation
        return (float) Math.cos((2 * PI / 360) * angle);
    }

    private float atan2(float x, float y) {
        // Roughly 1 lines of implementation
        return (float) Math.atan2(x, y);
    }

    private float sqrt(float x) {
        // Roughly 1 lines of implementation
        return (float) Math.sqrt(x);
    }

    private float random() {
        // Roughly 1 lines of implementation
        return (float) Math.random() * 3 - 2;
    }

    private float round(float angle) {
        // Roughly 1 lines of implementation
        return Math.round(angle);
    }

    private float toDegrees(float angle) {
        // Roughly 1 lines of implementation
        return (float) Math.toDegrees(angle);
    }

    public void speedUp(float speedUp) {
        // Roughly 1 lines of implementation
        game.puckSpeed = game.puckSpeed + game.speedUp;
    }

    public void step(int id) {
        // Roughly 45 lines of implementation
        //initialize again if Lifes are left
        if (first) {
            game.puckPoint.x = 200;
            game.puckPoint.y = 200;
            xTemp = (int) random();
            yTemp = (int) random();
            first = false;
        }
        float puckY = getPuck().y;
        float puckX = getPuck().x;
        Point[] racket = getRacket();
        //turn Polygon int 2 Border Lines, Left and Right
        int minX = getPolygon().getBounds().x;
        int minY = getPolygon().getBounds().y;
        int height = getPolygon().getBounds().height + minX;
        int width = getPolygon().getBounds().width + minY;
        Point rightU = new Point(width, height);
        Point midO = new Point(width / 2, minY);
        Point leftU = new Point(minX, height);
        Line2D left = new Line2D.Float(midO, leftU);
        Line2D right = new Line2D.Float(midO, rightU);

        Line2D racketline = new Line2D.Float(new Point(racket[0].x - RACKETSPEED, racket[0].y), new Point(racket[racket.length - 1].x + RACKETSPEED, racket[racket.length - 1].y));
        Line2D puckLine = new Line2D.Float(getPuck(), new Point.Float(puckX + velX + BALLSIZE, puckY + velY + BALLSIZE));

        //if puck on Racket then it is reflected
        if (puckY + BALLSIZE >= racket[0].y) {
            if (puckX + BALLSIZE >= racket[0].x && puckX <= racket[0].x + RACKETQUOTE) {
                velY = -velY;
            }
        }

        //if puckpoint under the racket than loses one life or Game Over
        if (puckY + BALLSIZE >= racket[0].y + LINEWIDTH) {
            if (leben > 0) {
                leben--;
                first = true;
            } else {
                JOptionPane.showMessageDialog(null, "GAME OVER!");
                System.exit(0);
            }
        }

        //if intersects right Bound of Polygon
        if (puckLine.intersectsLine(right)) {
            float xDifferenz = (float) (right.getX2() - right.getX1());
            float yDifferenz = (float) (right.getY2() - right.getY1());

            float[] vectorRight = {xDifferenz, yDifferenz};
            float wallRadian = atan2(yDifferenz, xDifferenz);
            float wallDegree = (float) ((wallRadian * 360) / (2 * Math.PI));

            float puckXMovement = velX;
            float puckYMovement = velY;
            float[] vectorPuck = {puckXMovement, puckYMovement};
            float puckRadian = atan2(puckYMovement, puckXMovement);
            if (puckRadian < 0) {
                puckRadian += 2 * PI;
            }
            float puckDegree = (float) ((puckRadian * 360) / (2 * Math.PI));

            float dotProduct = vectorRight[0] * vectorPuck[0] + vectorRight[1] * vectorPuck[1];
            float normRight = sqrt(vectorRight[0] * vectorRight[0] + vectorRight[1] * vectorRight[1]);
            float normPuck = sqrt(vectorPuck[0] * vectorPuck[0] + vectorPuck[1] * vectorPuck[1]);
            float intersectDegree = toDegrees((float) Math.acos((dotProduct) / (normRight * normPuck)));

            float newDegree;

            if (intersectDegree < 90) {
                newDegree = puckDegree + 2 * intersectDegree;
            } else if (intersectDegree > 90) {
                newDegree = puckDegree - 2 * (180 - intersectDegree);
            } else {
                newDegree = intersectDegree + 180;
            }

            if (newDegree < 0) {
                newDegree = newDegree + 360;
            }

            float newY;
            float outputIncline = (float) Math.tan((newDegree * Math.PI) / 180);
            if ((newDegree > 0 && newDegree < 90) || (newDegree > 270 && newDegree < 360)) {
                newY = outputIncline;
            } else {
                newY = -outputIncline;
            }

            float newX = -1f;
            float betrag = sqrt((newY * newY) + (newX * newX));
            velX = newX / betrag;
            velY = newY / betrag;
        }

        //if intersects Left Bound of Polygon 
        if (puckLine.intersectsLine(left)) {
            float xDifferenz = (float) (left.getX2() - left.getX1());
            float yDifferenz = (float) (left.getY2() - left.getY1());
            float[] vectorLeft = {xDifferenz, yDifferenz};
            float wallRadian = atan2(yDifferenz, xDifferenz);
            float wallDegree = (float) ((wallRadian * 360) / (2 * Math.PI));

            float puckXMovement = velX;
            float puckYMovement = velY;
            float[] vectorPuck = {puckXMovement, puckYMovement};
            float puckRadian = atan2(puckYMovement, puckXMovement);
            if (puckRadian < 0) {
                puckRadian += 2 * PI;
            }
            float puckDegree = (float) ((puckRadian * 360) / (2 * Math.PI));

            float dotProduct = vectorLeft[0] * vectorPuck[0] + vectorLeft[1] * vectorPuck[1];
            float normLeft = sqrt(vectorLeft[0] * vectorLeft[0] + vectorLeft[1] * vectorLeft[1]);
            float normPuck = sqrt(vectorPuck[0] * vectorPuck[0] + vectorPuck[1] * vectorPuck[1]);
            float intersectDegree = toDegrees((float) Math.acos((dotProduct) / (normLeft * normPuck)));

            float newDegree;
//            float outputIncline = 1f;

            if (intersectDegree < 90) {
                newDegree = puckDegree - 2 * intersectDegree;
            } else if (intersectDegree > 90) {
                newDegree = puckDegree + 2 * (180 - intersectDegree);
            } else {
                newDegree = intersectDegree + 180;
            }

            if (newDegree < 0) {
                newDegree = newDegree + 360;
            }

            float newY;
            float outputIncline = (float) Math.tan((newDegree * Math.PI) / 180);
            if ((newDegree > 0 && newDegree < 90) || (newDegree > 270 && newDegree < 360)) {
                newY = outputIncline;
            } else {
                newY = -outputIncline;
            }

            float newX = 1f;
            float betrag = sqrt((newY * newY) + (newX * newX));
            velX = newX / betrag;
            velY = newY / betrag;
        }

        //change Puck in Game
        game.puckPoint.x += velX;
        game.puckPoint.y += velY;

        //if KeyListener gets Left or Right Key than Racket moves 
        if (id == KeyEvent.VK_RIGHT) {
            if (!racketline.intersectsLine(right)) {
                game.paddlePosition += RACKETSPEED;
            }
        } else if (id == KeyEvent.VK_LEFT) {
            if (!racketline.intersectsLine(left)) {
                game.paddlePosition -= RACKETSPEED;
            }
        }
    }

    public Point[] getRacket() {
        // Roughly 12 lines of implementation
        int racketplace = 360;          //y-Koordinate
        Point[] p2 = new Point[70];   // use Racketquote
        for (int i = 0; i < p2.length; i++) {
            p2[i] = new Point((int) game.paddlePosition + i, racketplace);   //need Bound for x-Koordinate
        }
        return p2;
    }

    private Point getNormal(Point lineStart, Point lineEnd) {
        // Roughly 1 lines of implementation
        return new Point((lineStart.x + lineEnd.x) / 2, lineStart.y);
    }

    Polygon getPolygon() {
        // Roughly 7 lines of implementation
        Polygon poly = new Polygon();
        poly.addPoint(400 / 2, BORDER);
        poly.addPoint(BORDER, 400 - BORDER);
        poly.addPoint(400 - BORDER, 400 - BORDER);
        return poly;
    }

    public Point.Float getPuck() {
        // Roughly 1 lines of implementation
        return game.puckPoint;
    }

    public Point[] getBorderLine(int i) {
        // Roughly 2 lines of implementation
        Polygon p = getPolygon();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Point2D.Float getIntersection(Line2D.Float l, Line2D.Float m) {
        // Roughly 2 lines of implementation
        double d = (l.x1 - l.x2) * (m.y1 - m.y2) - (l.y1 - l.y2) * (m.x1 - m.x2);
        double xi = ((m.x1 - m.x2) * (l.x1 * l.y2 - l.y1 * l.x2) - (l.x1 - l.x2) * (m.x1 * m.y2 - m.y1 * m.x2)) / d;
        double yi = ((m.y1 - m.y2) * (l.x1 * l.y2 - l.y1 * l.x2) - (l.y1 - l.y2) * (m.x1 * m.y2 - m.y1 * m.x2)) / d;
        return new Point2D.Float((float) xi, (float) yi);

    }

    private float getAngle(Line2D.Float l, Line2D.Float m) {
        // Roughly 1 lines of implementation
        return atan2(l.x1, m.y1);
    }

    public void setPosition(float position) {
        // Roughly 3 lines of implementation
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Point getBorderPoint(int i) {
        // Roughly 5 lines of implementation
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Rectangle getDrawRectangle() {
        // Roughly 1 lines of implementation
        return new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
    }

    public Point getRectCenter() {
        // Roughly 1 lines of implementation
        return new Point((int) court.rectCenter.x, (int) court.rectCenter.y);
    }
}
