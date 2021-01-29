/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

import constants.Constants;
import core.Codemaker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kwhiting
 */
public class CodemakerUi 
{
    private JPanel codemakerResponse;
    private JPanel secretCode;
    private JLabel[] secretLabels;
    private JLabel[][] responseLabels;
    private ImageIcon question;
    private JButton check;    
    private Codemaker codemaker;
    private boolean checkClicked;
    
    public CodemakerUi(Codemaker codemaker)
    {
        this.codemaker = codemaker;
        initComponents();
    }
    
    private void initComponents()
    {
        initCodemakerResponse();
        initSecretCode();
    }
    
    private void initCodemakerResponse()
    {
        codemakerResponse = new JPanel();
        codemakerResponse.setBorder(BorderFactory.createTitledBorder("Codemaker Response"));
        codemakerResponse.setMinimumSize(new Dimension(150, 100));
        codemakerResponse.setPreferredSize(new Dimension(150,100));
        codemakerResponse.setLayout(new GridLayout(Constants.MAX_ATTEMPTS, Constants.MAX_PEGS));
        
        // instantiate the Array with the size
        responseLabels = new JLabel[Constants.MAX_ATTEMPTS][Constants.MAX_PEGS];
        
        // create the array of JLabels for the code maker's response
        for (int row = 0; row < Constants.MAX_ATTEMPTS; row ++) 
        {			
            for(int col = 0; col < Constants.MAX_PEGS; col++)
            {
                // create the buttons
                responseLabels[row][col] = new JLabel();
                responseLabels[row][col].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                // add the button to the UI
                codemakerResponse.add(responseLabels[row][col]);
            }
        }
    }
    
    private void initSecretCode()
    {
        secretCode = new JPanel();
        secretCode.setBorder(BorderFactory.createTitledBorder("Secret Code"));
        secretCode.setMinimumSize(new Dimension(200, 60));
        secretCode.setPreferredSize(new Dimension(200,60));
        secretCode.setAlignmentY(JPanel.TOP_ALIGNMENT);
        
        // instantiate the Array with the size
        secretLabels = new JLabel[Constants.MAX_PEGS];
        
//        question = new ImageIcon( getClass().getResource("../images/question.jpg"));

        String fileName = "../images/question.jpg";
        URL imgURL = getClass().getResource(fileName);
        
        if(imgURL != null)
        {
            question = new ImageIcon(imgURL);
            question = imageResize(question);
        }
        else
        {
            System.err.println("Couldn't find file: " + fileName);
            question = null;
        }
        
        // counter for enhanced for loop
        int counter = 0;
        
        for (JLabel label : secretLabels) 
        {			
            label = new JLabel();
            label.setBackground(Color.LIGHT_GRAY);
            label.setIcon(imageResize(question));
            
            // add button to JPanel using FlowLayout
            secretCode.add(label);
            
            // increment the counter
            counter++;
        }
        
        // ghetto spacing
        JLabel space = new JLabel();
        space.setMinimumSize(new Dimension(100, 35));
        space.setPreferredSize(new Dimension(100, 35));
        secretCode.add(space);
        
        // add the check button
        check = new JButton("Check");
        check.addActionListener(new CheckAction());
        secretCode.add(check);
    }

    /**
     * @return the codemakerResponse
     */
    public JPanel getCodemakerResponse() 
    {
        return codemakerResponse;
    }

    /**
     * @return the secretCode
     */
    public JPanel getSecretCode() 
    {
        return secretCode;
    }
    
    public boolean getCheckClicked()
    {
        return checkClicked;
    }
    
    public void setCheckClicked(boolean x)
    {
        checkClicked = x;
    }
    
    public JButton getCheck()
    {
        return check;
    }
    
    public void setCheck(JButton x)
    {
        check = x;
    }
    
    private ImageIcon imageResize(ImageIcon icon)
    {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }
    
    // this method updates the secret code jpanel once the game is completed
    public void displaySecretCode()
    {
        secretCode.removeAll();
        JLabel codeDisplay = new JLabel("The secret code was");
        secretCode.add(codeDisplay);
        List<Color> temp = new ArrayList<>(codemaker.getSecretCode());
        
        // for every color in the secret code, this loop adds it to the JPanel
        for(Color color : temp)
        {
            Color buttonColor = color;
            RoundButton button = new RoundButton();
            button.setBackground(buttonColor);
            secretCode.add(button);
        }
        
        secretCode.revalidate();
        secretCode.repaint();
    }
    
    // this method displays the codemaker response after the attempt is checked
    public void displayCodemakerResponse(int row)
    {
        ArrayList<Color> codeResponse = codemaker.getCodemakerResponse();
        int i;
        // this loop displays the codemaker response on the codemaker jpanel
        for(i = 0;i<codeResponse.size();i++)
        {
            Color temp = codeResponse.get(i);
            if(temp != null)
            {
                responseLabels[row][i].setBackground(temp);
                responseLabels[row][i].setOpaque(true);
            }
        }
        codeResponse.clear();
    }
    
    //if the check button is clicked, checkClicked is set to true
    private class CheckAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            checkClicked = true;
        }
    }
}
