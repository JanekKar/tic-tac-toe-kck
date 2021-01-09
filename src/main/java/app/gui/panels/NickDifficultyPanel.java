package app.gui.panels;

import app.Main;
import app.gui.buttons.MenuButton;
import app.gui.MainPanel;
import app.ticTacToe.Player;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class NickDifficultyPanel extends JPanel {
    private JLabel messageBox;
    private JRadioButton easy;
    private JRadioButton medium;
    private JRadioButton hard;
    private JRadioButton impossible;

    private JButton startButton;
    private JButton backButton;

    private JTextField nickField;

    private ButtonGroup buttonGroup;

    private Color selected = new Color(119, 181, 181);

    private Color backGroundColor = new Color(100, 100, 100);
    private Color lighterBackground = new Color(130, 130, 130);

    public NickDifficultyPanel(){
        LayoutManager mainLayout = new GridLayout(2, 1, 10, 10);

        this.setLayout(mainLayout);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(80, 20, 20, 20));

        easy = prepareButton("Easy");
        medium = prepareButton("Medium");
        hard = prepareButton("Hard");
        impossible = prepareButton("Impossible");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(easy);
        buttonGroup.add(medium);
        buttonGroup.add(hard);
        buttonGroup.add(impossible);


        nickField = new JTextField();
        nickField.setPreferredSize(new Dimension(200, 30));
        nickField.setFont(new Font(nickField.getFont().getName(), Font.BOLD, 18));
        nickField.setBackground(lighterBackground);
        nickField.setForeground(Color.WHITE);
        nickField.setBorder(new EmptyBorder(2, 2, 2, 2));
        nickField.setHorizontalAlignment(0);
        nickField.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if(str == null)
                    return;
                if(getLength() + str.length() <= 10)
                    super.insertString(offs, str, a);
            }
        });



        startButton = new MenuButton("Start game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(buttonGroup.getSelection() != null && nickField.getText().length() > 0){
                    switch (buttonGroup.getSelection().getActionCommand()){
                        case "EASY":
                            Main.logic = new EasyLogic();
                            break;
                        case "MEDIUM":
                            Main.logic = new MediumLogic();
                            break;
                        case "HARD":
                            Main.logic = new HardLogic();
                            break;
                        case "IMPOSSIBLE":
                            Main.logic = new ImpossibleLogic();
                            break;
                    }

                    CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                    cl.show(MainPanel.rootPanel, "GAME");

                    ((JTextPane) MainPanel.gameView.getSidebar().getComponent(0)).setText("Mode:\n" + buttonGroup.getSelection().getActionCommand());

                    Main.game.setPlayer(new Player(nickField.getText()));
                    (MainPanel.gameView.getSidebar()).setupPanel();

                }else{
                    messageBox.setText("Set desired difficulty level and enter your nick.");
                    messageBox.setOpaque(true);
                }
            }
        });

        backButton = new MenuButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                cl.show(MainPanel.rootPanel, "MAINMENU");

            }
        });

        messageBox = new JLabel("", SwingConstants.CENTER);
        messageBox.setForeground(new Color(255, 50, 50));
        messageBox.setFont(new Font(messageBox.getFont().getName(), Font.BOLD, 15));
        messageBox.setBackground(lighterBackground);



        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new GridLayout(5,1,5, 5));
        difficultyPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        difficultyPanel.setBackground(backGroundColor);


        JLabel difficultyLabel = new JLabel("Choose Difficulty: ");
        difficultyLabel.setFont(new Font(difficultyLabel.getFont().getName(), Font.BOLD, 20));
        difficultyLabel.setForeground(new Color(119, 181, 181));


        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);
        difficultyPanel.add(impossible);


        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new GridLayout(2,1,5,5));
        nickPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        nickPanel.setBackground(backGroundColor);

        JLabel nickLabel = new JLabel("Enter Nick:");
        nickLabel.setFont(new Font(nickLabel.getFont().getName(), Font.BOLD, 20));
        nickLabel.setForeground(new Color(119, 181, 181));
        nickPanel.add(nickLabel);
        nickPanel.add(nickField);

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(backGroundColor);
        innerPanel.setLayout(new FlowLayout());
        innerPanel.add(difficultyPanel);
        innerPanel.add(nickPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(backGroundColor);
        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        buttonPanel.add(messageBox);

        JPanel buttonPanelWrapper = new JPanel();
        buttonPanelWrapper.setLayout(new FlowLayout());
        buttonPanelWrapper.setBackground(backGroundColor);
        buttonPanelWrapper.add(buttonPanel);

        this.add(innerPanel);
        this.add(buttonPanelWrapper);

    }

    public void clear(){
        buttonGroup.clearSelection();
        nickField.setText("");
    }

    private JRadioButton prepareButton(String text){
        JRadioButton temp = new JRadioButton(text);
        temp.setFont(new Font(temp.getFont().getName(), Font.BOLD, 18));
        temp.setActionCommand(text.toUpperCase());
        temp.setBackground(lighterBackground);
        temp.setIcon(new ImageIcon());
        Color foreground = temp.getForeground();

        temp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!temp.isSelected())
                    temp.setForeground(Color.WHITE);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!temp.isSelected())
                    temp.setForeground(foreground);
                super.mouseExited(e);
            }
        });

        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Enumeration<AbstractButton> buttons = buttonGroup.getElements();

                while(buttons.hasMoreElements()){
                    JRadioButton button = (JRadioButton)buttons.nextElement();
                    if(button.isSelected())
                        button.setForeground(selected);
                    else
                        button.setForeground(foreground);

                }
            }
        });
        return temp;
    }
}
