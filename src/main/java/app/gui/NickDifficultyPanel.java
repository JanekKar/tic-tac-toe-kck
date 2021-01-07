package app.gui;

import app.Main;
import app.ticTacToe.Player;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NickDifficultyPanel extends JPanel {
    private JRadioButton easy;
    private JRadioButton medium;
    private JRadioButton hard;
    private JRadioButton impossible;

    private JButton startButton;

    private JTextField nickField;


    public NickDifficultyPanel(){
        LayoutManager mainLayout = new FlowLayout();

        this.setLayout(mainLayout);
        Color backGroundColor = new Color(100, 100, 100);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        easy = prepareButton("Easy");
        medium = prepareButton("Medium");
        hard = prepareButton("Hard");
        impossible = prepareButton("Impossible");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easy);
        buttonGroup.add(medium);
        buttonGroup.add(hard);
        buttonGroup.add(impossible);


        nickField = new JTextField();
        nickField.setPreferredSize(new Dimension(200, 25));
        nickField.setFont(new Font(nickField.getFont().getName(), Font.BOLD, 15));
        nickField.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if(str == null)
                    return;
                if(getLength() + str.length() <= 10)
                    super.insertString(offs, str, a);
            }
        });



        startButton = new JButton("Start game");
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

                    CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                    cl.show(Window.rootPanel, "GAME");

                    ((JTextArea)Window.gameView.getSidebar().getComponent(0)).setText("Mode:\n" + buttonGroup.getSelection().getActionCommand());

                    Main.game.setPlayer(new Player(nickField.getName()));
                    (Window.gameView.getSidebar()).setPlayerName();

                }
            }
        });


        this.add(easy);
        this.add(medium);
        this.add(hard);
        this.add(impossible);

        this.add(new JLabel("Enter Nick"));
        this.add(nickField);

        this.add(startButton);

    }

    private JRadioButton prepareButton(String text){
        JRadioButton temp = new JRadioButton(text);
        temp.setFont(new Font(temp.getFont().getName(), Font.BOLD, 15));
        temp.setActionCommand(text.toUpperCase());
        return temp;
    }
}
