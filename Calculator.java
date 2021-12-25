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
    private JFrame frame;
    private JTextArea text;
    private JButton zero, one, two, three, four, five, six, seven, eight,
            nine, add, subtract, multiply, divide, clear, delete, equal,
            negative, power, factorial;

    /**
     * PRE: requires coordinates and name for frame,
     * coordinates and size for text area, and
     * size for buttons.
     * POST: creates the frame, textarea, and buttons.
     */
    public Calculator() {
        createFrame(300, 400, "Calculator");
        createTextArea(10, 10, 280, 40);
        createButtons(52, 40);
    }

    /**
     * PRE: require string from textarea block
     * POST: calculates the expression from the string
     * 
     * @param equ equation or expression
     * @return result of expression
     */
    private long calculate(String equ) {
        Scanner reader = new Scanner(equ);
        long res = 0;

        try {
            while (reader.hasNext()) {
                String tmp = reader.next();
                if (tmp.equalsIgnoreCase("+"))
                    res += Long.parseLong(reader.next());
                else if (tmp.equalsIgnoreCase("-"))
                    res -= Long.parseLong(reader.next());
                else if (tmp.contains("-")) {
                    tmp = tmp.replace("(", "").replace(")", "");
                    res += Long.parseLong(tmp);
                } else if (tmp.equalsIgnoreCase("*"))
                    res *= Long.parseLong(reader.next());
                else if (tmp.equalsIgnoreCase("/"))
                    res /= Long.parseLong(reader.next());
                else if (tmp.contains("^")) {
                    String[] parts = tmp.replace("^", " ").split(" ");
                    res += power(Long.parseLong(parts[0]),
                            Long.parseLong(parts[1]));
                } else if (tmp.contains("!")) {
                    String[] parts = tmp.replace("!", " ").split(" ");
                    System.out.println("!" + parts[0]);
                    res += factorial(Long.parseLong(parts[0]));
                } else
                    res += Double.parseDouble(tmp);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Numbers split");
        } catch (NumberFormatException e) {
            System.out.println("Error: Number formating");
        } catch (Exception e) {
            System.out.println("Error: finish the expression");
        } finally {
            reader.close();
        }

        return res;
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
        this.frame.setVisible(true);
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

        // place and add buttons onto screen
        for (int i = 0; i < list.size(); i++) {
            ArrayList<JButton> temp = list.get(i);
            for (int j = 0; j < list.get(i).size(); j++) {
                JButton tempBtn = temp.get(j);
                Font font = new Font("SansSerif", Font.BOLD, 22);
                tempBtn.setFont(font);
                tempBtn.setBounds(startX + (w * j) + (gap * j),
                        startY + (i * h) + (i * gap), w, h);

                // create functionalities
                tempBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String BtnText = tempBtn.getText();
                        if (BtnText.equalsIgnoreCase("="))
                            text.setText(String.valueOf(calculate(
                                    text.getText())));
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
                            text.append(" " + BtnText + " ");
                        else {
                            if (BtnText.equalsIgnoreCase("(-)")) {
                                text.append(BtnText.replace("(", "").replace(")",
                                ""));
                            } else text.append(BtnText);
                        }
                    }
                });
                this.frame.add(temp.get(j));
            }
        }
    }

    /**
     * PRE: requires a base and exponent
     * POST: return the result of a exponential expression
     * @param base base number
     * @param exponent exponent number
     * @return result -> result of a exponential expression
     */
    private long power(long base, long exponent) {
        long res = 1;
        for (int i = 1; i <= exponent; i++) {
            res *= base;
        }
        return res;
    }

    /**
     * PRE: requires a starting value
     * POST: return the factorial of the value
     * @param num starting value or value
     * @return result -> factorial of the value
     */
    private long factorial(long num) {
        if (num == 0) return 1;
        return num * factorial(num - 1);
    }
}