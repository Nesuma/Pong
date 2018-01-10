/**
 * Created by Classdumper, User Peter Heusch
 * Creation Date: 15.12.2017 07:49:44
 */
package simplePong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SimpleMain {

    public SimpleMain() {
        // Roughly 1 lines of implementation
    }

    public static void main(String[] args) {
        // Roughly 7 lines of implementation
        Runnable r = new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame();
                SimplePanel a = new SimplePanel();

                frame.setUndecorated(true);
                Polygon p = new Polygon(new int[]{0, 200, 400}, new int[]{400, 0, 400}, 3);
                frame.setShape(p);

                frame.getContentPane().add(a);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                a.addKeyListener(a);
                a.setFocusable(true);
                a.requestFocusInWindow();

                frame.setResizable(false);
                frame.setSize(new Dimension(400, 400));
                frame.setVisible(true);
            }

        };

        SwingUtilities.invokeLater(r);
//        SimplePanel test = new SimplePanel();
//        JFrame jp1 = new JFrame();
//        
//        
//        jp1.getContentPane().add(a, BorderLayout.CENTER);
//        jp1.setSize(new Dimension(500, 500));
//        jp1.setResizable(false);
//        jp1.setVisible(true);  

    }
}
