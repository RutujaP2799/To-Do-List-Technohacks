
    /* TODO LIST BY RUTUJA PATIL */

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TodoList extends JPanel {
    JLabel index;
    JTextField taskName;
    JButton done;

    Color pink = new Color(111, 222, 55);
    Color green = new Color(222, 111, 55);
    Color doneColor = new Color(235, 120, 120);

    private boolean checked;

    TodoList() {
        this.setPreferredSize(new Dimension(400, 20));
        this.setBackground(Color.magenta);

        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write Your Task Here...");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(pink);
        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);
        done.setFocusPainted(false);
        this.add(done, BorderLayout.EAST);
    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(green);
        taskName.setBackground(green);
        checked = true;
        revalidate();
    }
}

class List extends JPanel {
    Color lightColor = new Color(225, 212, 180);

    List() {
        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(lightColor);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();
        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof TodoList) {
                ((TodoList) listItems[i]).changeIndex(i + 1);
            }
        }
    }

    public void removeCompletedTasks() {
        for (Component c : getComponents()) {
            if (c instanceof TodoList) {
                if (((TodoList) c).getState()) {
                    remove(c);
                    updateNumbers();
                }
            }
        }
    }
}

class Footer extends JPanel {
    JButton addTask;
    JButton clear;

    Color Background = new Color(222, 111, 99);
    Color lightColor = new Color(252, 221, 176);
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(lightColor);

        addTask = new JButton("Add Task");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Serif", Font.ITALIC, 20));
        addTask.setVerticalAlignment(JButton.BOTTOM);
        addTask.setBackground(Color.darkGray);
        addTask.setForeground(Color.white);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20));

        clear = new JButton("Clear Finished Tasks");
        clear.setFont(new Font("Serif", Font.ITALIC, 20));
        clear.setBorder(emptyBorder);
        clear.setBackground(Color.darkGray);
        clear.setForeground(Color.white);
        this.add(clear);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {
    Color lightColor = new Color(252, 221, 176);

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(lightColor);
        JLabel titleText = new JLabel("To Do List By Rutuja Patil");
        titleText.setPreferredSize(new Dimension(300, 60));
        titleText.setFont(new Font("Serif", Font.BOLD, 20));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class AppFrame extends JFrame {
    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;
    private JButton clear;

    AppFrame() {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("To do List By Rutuja Patil");

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(list, BorderLayout.CENTER);

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TodoList task = new TodoList();
                list.add(task);
                list.updateNumbers();

                task.getDone().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        task.changeState();
                        list.updateNumbers();
                        revalidate();
                    }
                });
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                list.removeCompletedTasks();
                repaint();
            }
        });
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
        });
    }
}
