import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonSample {
    public static void main(String[] args) {
        Runnable runner = new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Button Sample");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JButton button = new JButton("Select Me");
                // Define ActionListener
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        System.out.println("I was selected.");
                    }
                };

                //Define MouseListener
                MouseListener mouseListener = new MouseAdapter() {
                    public void mousePressed(MouseEvent mouseEvent) {
                        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                            System.out.println("Left Button Pressed");
                        }
                        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                            System.out.println("Right Button Pressed");
                        }
                        if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
                            System.out.println("Middle Button Pressed");
                        }
                    }
                    public void mouseReleased(MouseEvent mouseEvent) {
                        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                            System.out.println("Left button released.");
                        }
                        if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
                            System.out.println("Middle button released.");
                        }
                        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                            System.out.println("Right button released.");
                        }
                        System.out.println();
                    }
                };

                // Attach listeners
                button.addActionListener(actionListener);
                button.addMouseListener(mouseListener);
                frame.add(button, BorderLayout.SOUTH);
                frame.setSize(300, 100);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }
}