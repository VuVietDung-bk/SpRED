package screens;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import spred.WindowGame;

public class InitialWindow extends Screen {

    private static final long serialVersionUID = 1L;

    public InitialWindow(WindowGame window) {
        super(window);
        setImage("initial.jpg");

        setLayout(new GridBagLayout());

        // Create the play button
        JButton playButton = createButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.startGame();
            }
        });

        // Create the exit button
        JButton exitButton = createButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;

        // Create an invisible spacer panel to push buttons down
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);  // Make sure the spacer is invisible
        gbc.weighty = 1;  // Adjust this weight to control how far down the buttons go
        gbc.gridy = 0;  // Position the spacer at the top
        add(spacer, gbc);

        // Set the play button at the first position in the lower half
        gbc.weighty = 0;  // Reset weight so buttons don't expand
        gbc.gridy = 1;  
        add(playButton, gbc);

        // Set the exit button at the second position in the lower half
        gbc.gridy = 2;  
        add(exitButton, gbc);

        // Add another spacer at the bottom to ensure proper positioning
        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setOpaque(false);  // Make sure the spacer is invisible
        gbc.weighty = 0.3;  // Adjust this weight to balance the layout
        gbc.gridy = 3;  // Position the bottom spacer
        add(bottomSpacer, gbc);
    }

    // Helper method to create a customized button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(36.0f)); 
        button.setFocusPainted(false);
        button.setContentAreaFilled(false); // Make the background transparent
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20)); // Adjust padding as needed
        button.setForeground(Color.WHITE);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }
}
