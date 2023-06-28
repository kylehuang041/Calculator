import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Java GUI Calculator: calculates from left to right with no order of
 * operations
 * 
 * @author Kyle Huang
 * @version 5.8
 * @since 12/23/2021
 */
public class Calculator extends JFrame {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }

    private JFrame frame;
    private JTextArea text;
    private JButton zero, one, two, three, four, five, six, seven, eight,
            nine, add, subtract, multiply, divide, clear, delete, equal,
            negative, power, factorial, decimal, pi;

    /**
     * PRE: requires coordinates and name for frame,
     * coordinates and size for text area, and
     * size for buttons.
     * POST: creates the frame, textarea, and buttons.
     */
    public Calculator() {
        createFrame(330, 440, "Calculator");
        createTextArea(10, 10, 315, 40);
        createButtons(60, 40);
    }

    /**
     * PRE: requires size and name
     * POST: set the size and name of frame
     * 
     * @param width  width of frame
     * @param height height of frame
     * @param name   name of frame
     */
    private void createFrame(int width, int height, String name) {
        this.frame = new JFrame(name);
        this.frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLayout(null);
    }

    /**
     * PRE: requires coordinates and size
     * POST: set the coordinates and size of text area
     * 
     * @param x starting x-coordinate of text area
     * @param y starting y-coordinate of text area
     * @param w width of text area
     * @param h height of text area
     */
    private void createTextArea(int x, int y, int w, int h) {
        this.text = new JTextArea();
        this.text.setLocation(x, y);
        this.text.setSize(w - (2 * x), h);
        Font font = new Font("SansSerif", Font.BOLD, 22);
        this.text.setFont(font);
        this.text.setEditable(false);
        this.frame.add(text);
    }

    /**
     * PRE: requires size of buttons
     * POST: creates the buttons and its functionalities
     * 
     * @param w width of buttons
     * @param h height of buttons
     */
    private void createButtons(int w, int h) {
        // create buttons
        zero = new JButton("0");
        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        add = new JButton("+");
        subtract = new JButton("-");
        multiply = new JButton("*");
        divide = new JButton("/");
        clear = new JButton("C");
        delete = new JButton("\u232B");
        equal = new JButton("=");
        negative = new JButton("(-)");
        power = new JButton("^");
        factorial = new JButton("!");
        decimal = new JButton(".");
        pi = new JButton("\u03c0");

        // variables
        int gap = (this.frame.getWidth() - (4 * w)) / 5, startX = 10,
                startY = 60;
        ArrayList<ArrayList<JButton>> list = new ArrayList<>();

        // add buttons to arraylist
        list.add(new ArrayList<>(Arrays.asList(one, two, three, delete)));
        list.add(new ArrayList<>(Arrays.asList(four, five, six, clear)));
        list.add(new ArrayList<>(Arrays.asList(seven, eight, nine, add)));
        list.add(new ArrayList<>(Arrays.asList(subtract, zero, multiply,
                divide)));
        list.add(new ArrayList<>(Arrays.asList(negative, power, factorial,
                equal)));
        list.add(new ArrayList<>(Arrays.asList(decimal, pi)));

        // place and add buttons onto screen
        for (int i = 0; i < list.size(); i++) {
            ArrayList<JButton> temp = list.get(i);
            for (int j = 0; j < list.get(i).size(); j++) {
                JButton tempBtn = temp.get(j);
                Font font = new Font("SansSerif", Font.BOLD, 16);
                tempBtn.setFont(font);
                tempBtn.setBounds(startX + (w * j) + (gap * j),
                        startY + (i * h) + (i * gap), w, h);

                // create functionalities
                tempBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String BtnText = tempBtn.getText();
                        if (BtnText.equalsIgnoreCase("="))
                            text.setText(String.valueOf(Calculation.solve(text.getText())));
                        else if (BtnText.equalsIgnoreCase("C"))
                            text.setText("");
                        else if (BtnText.equalsIgnoreCase("\u232B")) {
                            if (text.getText().length() > 0)
                                text.setText(text.getText().substring(0,
                                        text.getText().length() - 1));
                        } else if (BtnText.equalsIgnoreCase("+")
                                || BtnText.equalsIgnoreCase("-")
                                || BtnText.equalsIgnoreCase("*")
                                || BtnText.equalsIgnoreCase("/"))
                            text.append(' ' + BtnText + ' ');
                        else if (BtnText.equalsIgnoreCase("(-)"))
                            text.append("-");
                        else
                            text.append(BtnText);
                    }
                });
                this.frame.add(temp.get(j));
            }
        }
        this.frame.setVisible(true);
    }
}