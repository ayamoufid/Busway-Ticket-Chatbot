package ma.fstm.ilisi.projectbusway.presentation;

import ma.fstm.ilisi.projectbusway.metier.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userInput;
    private JTextArea chatArea;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private ReservationBot bot;

    public ChatBotUI() {
        bot = new ReservationBot();

        setTitle("ChatBot");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        scrollPane = new JScrollPane(chatArea);

        userInput = new JTextField();
        sendButton = new JButton("Envoyer");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                String response = bot.processInput(input);
                chatArea.append("Vous: " + input + "\n");
                chatArea.append("ChatBot: " + response + "\n");
                userInput.setText("");
            }
        });

        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, userInput);
        this.getContentPane().add(BorderLayout.EAST, sendButton);
    }

    public static void main(String[] args) {
        ChatBotUI chatBotUI = new ChatBotUI();
        chatBotUI.setVisible(true);
    }
}

