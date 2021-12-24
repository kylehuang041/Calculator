import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextArea text;
    private JButton zero, one, two, three, four, five, six, seven, eight, nine,
            add, subtract, multiply, divide, clear, delete, equal;

    public Calculator() {
        create();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private double calculate(String equ) {
        return -1;
    }

    private void create() {
        createFrame(300, 400, "Calculator");
        createTextArea(10, 10, 280, 40);
        createButtons(50, 40);
    }

    private void createFrame(int width, int height, String name) {
        this.frame = new JFrame(name);
        this.frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
    }

    private void createTextArea(int x, int y, int w, int h) {
        this.text = new JTextArea();
        this.text.setLocation(x, y);
        this.text.setSize(w - (2 * x), h);
        Font font1 = new Font("SansSerif", Font.BOLD, 22);
        this.text.setFont(font1);
        this.text.setEditable(false);
        this.frame.add(text);
    }

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
        multiply = new JButton("x");
        divide = new JButton("/");
        clear = new JButton("C");
        delete = new JButton("del");
        equal = new JButton("=");

        // variables
        int gap = (this.frame.getWidth() - (4 * w)) / 5, startX = 10, startY = 60;
        ArrayList<ArrayList<JButton>> list = new ArrayList<>();

        // add buttons to arraylist
        list.add(new ArrayList<>(Arrays.asList(one, two, three, delete)));
        list.add(new ArrayList<>(Arrays.asList(four, five, six, clear)));
        list.add(new ArrayList<>(Arrays.asList(seven, eight, nine, add)));
        list.add(new ArrayList<>(Arrays.asList(subtract, zero, multiply,
                divide)));
        list.add(new ArrayList<>(Arrays.asList(equal)));

        // place and add buttons onto screen
        for (int i = 0; i < list.size(); i++) {
            ArrayList<JButton> temp = list.get(i);
            for (int j = 0; j < list.get(i).size(); j++) {
                JButton tempBtn = temp.get(j);
                tempBtn.setBounds(startX + (w * j) + (gap * j),
                        startY + (i * h) + (i * gap), w, h);
                tempBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String textTemp = tempBtn.getText();
                        if (textTemp.equalsIgnoreCase("C"))
                            text.setText("");
                        else if (textTemp.equalsIgnoreCase("del")) {
                            if (text.getText().length() > 0)
                            text.setText(text.getText().substring(0, text.getText().length() - 1));
                        } else
                            text.append(textTemp);
                    }
                });
                this.frame.add(temp.get(j));
            }
        }
    }
}