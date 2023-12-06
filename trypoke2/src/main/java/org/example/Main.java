package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Pokédex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));

        final JTextField pokemonIdField = new JTextField(20);
        pokemonIdField.setMaximumSize(pokemonIdField.getPreferredSize());
        JButton fetchButton = new JButton("Fetch Pokémon");
        JButton toggleButton = new JButton("Show Pokémon");
        final JTextArea displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);

        final JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVisible(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        fetchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(pokemonIdField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(fetchButton);
        panel.add(Box.createVerticalStrut(10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(10));
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(toggleButton);

        // Set the icon image
        URL iconURL = Main.class.getResource("/pokeball.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        } else {
            System.err.println("Icon file not found.");
        }

        // Set the background color for the frame and panel
        Color mediumGray = new Color(128, 128, 128);
        frame.getContentPane().setBackground(mediumGray);
        panel.setBackground(mediumGray);  // Set the background color for the panel
        displayArea.setBackground(mediumGray.darker()); // A slightly darker gray for the text area
        displayArea.setForeground(Color.WHITE); // Set the text color to white for better contrast

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = pokemonIdField.getText();
                try {
                    int pokemonId = Integer.parseInt(id);
                    PokemonService.Pokemon pokemon = PokemonService.fetchPokemonData(pokemonId);
                    displayArea.setText(pokemon.toString());
                    ImageIcon image = new ImageIcon(new URL(pokemon.getSpriteUrl()));
                    imageLabel.setIcon(image);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error fetching Pokémon data");
                }
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTextVisible = scrollPane.isVisible();
                scrollPane.setVisible(!isTextVisible);
                imageLabel.setVisible(isTextVisible);
                frame.pack(); // Adjust the frame size after toggling
            }
        });

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}