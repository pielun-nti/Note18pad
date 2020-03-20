package pierre.core;
//Notepad in java.
//Jag hade kunnat gjort den mer avancerad om jag la ner mer tid.
//2020-02-28
//Pierre

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad extends javax.swing.JFrame {
    static JFrame frame;
    static JMenuBar menuBar;
    static JMenu fileMenu;
    static JMenu editMenu;
    static JMenu settingsMenu;
    static JMenu aboutMenu;
    static JMenuItem menuItemNewFile;
    static JMenuItem menuItemExit;
    static JMenuItem menuItemImportFile;
    static JMenuItem menuItemSaveAs;
    static JMenuItem menuItemSave;
    static JMenuItem menuItemClear;
    static JMenuItem menuItemChangeFontSize;
    static JCheckBoxMenuItem menuItemAllowResizing;
    static JMenuItem menuItemAbout;
    static JCheckBoxMenuItem menuItemAutomaticRowChange;
    static JMenuItem menuItemCopy;
    static JMenuItem menuItemPaste;
    private JPanel mainPanel;
    private static JTextArea txtEditText;
    private static String MessageBoxTitle = "Notepad GUI";
    private static String openedFile = null;
    private static Font mainFont;
    static int fontSize = 12;



    public static void main(String[] args) {
        frame = new JFrame("Notepad GUI - Pierre");
        BufferedImage image = null;
        try {
            image = ImageIO.read(
                    Notepad.class.getResource("../img/notepadicon.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(image);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        Dimension res = new Dimension(800, 500);
        frame.setPreferredSize(res);
        txtEditText = new JTextArea();
        txtEditText.setEditable(true);
        mainFont = new Font("Verdana", Font.BOLD, fontSize);
        txtEditText.setFont(mainFont);
        frame.setFont(mainFont);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        settingsMenu = new JMenu("Settings");
        aboutMenu = new JMenu("About");
            menuItemNewFile = new JMenuItem("Create New File");
        menuItemNewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String filepath = showCreateFileDialog();
                if (filepath != null & !filepath.trim().equals("")) {
                    openedFile = filepath;
                }

            }
        });
        menuItemExit = new JMenuItem("Exit application");
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                confirmExit();
            }
        });
        menuItemImportFile = new JMenuItem("Import file");
        menuItemImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openFileDialog();
            }
        });
        menuItemSaveAs = new JMenuItem("Save as");
        menuItemSave = new JMenuItem("Save");
        menuItemClear = new JMenuItem("Clear");
        //menuItemChangeTextColor = new JMenuItem("Change Text Color");
        menuItemChangeFontSize = new JMenuItem("Change Font Size");
        menuItemChangeFontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fontSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new font size ( current = " + fontSize + ")", MessageBoxTitle, JOptionPane.INFORMATION_MESSAGE));
                mainFont = new Font("Verdana", Font.BOLD, fontSize);
                txtEditText.setFont(mainFont);
                frame.setFont(mainFont);
            }
        });
        menuItemAllowResizing = new JCheckBoxMenuItem("Allow resizing of window");
        menuItemAllowResizing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (menuItemAllowResizing.isSelected()) {
                    frame.setResizable(true);
                } else {
                    frame.setResizable(false);
                }
            }
        });
        menuItemAbout = new JMenuItem("Credits");
        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 JOptionPane.showMessageDialog(null, "Simple notepad in java.\nDevelopment: Pierre Lundström", MessageBoxTitle, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuItemAutomaticRowChange = new JCheckBoxMenuItem("Automatic Row Changing");
        menuItemAutomaticRowChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (menuItemAutomaticRowChange.isSelected()) {
                    txtEditText.setLineWrap(true);
                } else {
                    txtEditText.setLineWrap(false);
                }
            }
        });


        //menuItemseven = new MenuItem("Change text color");
        //menuItemeight = new MenuItem("Change font size");
        //menuItemnine = new MenuItem("Change background color");
        //menuItemten = new MenuItem("Select all text");
        //menuItemeleven = new MenuItem("Copy selected text");
        //menuItemtwelve = new MenuItem("Paste copied text to cursor location");
        menuItemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFileDialog();
            }
        });
        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (openedFile != null) {
                    if (!txtEditText.getText().trim().equals("") & txtEditText.getText() != null) {
                        saveToOpenedFile();
                    }
                }
            }
        });
        menuItemClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                txtEditText.setText("");
            }
        });
        settingsMenu.add(menuItemAllowResizing);
        settingsMenu.add(menuItemAutomaticRowChange);
        aboutMenu.add(menuItemAbout);
        editMenu.add(menuItemChangeFontSize);
        fileMenu.add(menuItemNewFile);
        fileMenu.add(menuItemImportFile);
        fileMenu.add(menuItemSave);
        fileMenu.add(menuItemSaveAs);
        fileMenu.add(menuItemClear);
        fileMenu.add(menuItemExit);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingsMenu);
        menuBar.add(aboutMenu);
        frame.setJMenuBar(menuBar);
        frame.add(txtEditText);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private static String showCreateFileDialog() {
        final JFileChooser saveAsFileChooser = new JFileChooser();
        saveAsFileChooser.setApproveButtonText("Create File");
        saveAsFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        saveAsFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HyperText Markup Language", "html"));
        int actionDialog = saveAsFileChooser.showOpenDialog(null);
        if (actionDialog != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        // !! File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        File file = saveAsFileChooser.getSelectedFile();
        if (!file.getName().endsWith(".html") & (!file.getName().endsWith(".txt"))) {
            file = new File(file.getAbsolutePath() + ".txt");
        }
        try {
            FileWriter fw = new FileWriter(file,false);
            fw.close();
            //fw = null;
        } catch (Exception ex) {
            return null;
        }
        return file.getAbsolutePath();
            }

    private static void confirmExit() {
        int dialogResult = JOptionPane.showConfirmDialog (null, "ARE YOU SURE YOU WANT TO EXIT THIS APPLICATION? ", MessageBoxTitle, JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            frame.dispose();
        }
    }

    private static void saveFileDialog() {
        final JFileChooser saveAsFileChooser = new JFileChooser();
        saveAsFileChooser.setApproveButtonText("Save");
        saveAsFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        saveAsFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HyperText Markup Language", "html"));
        int actionDialog = saveAsFileChooser.showOpenDialog(null);
        if (actionDialog != JFileChooser.APPROVE_OPTION) {
            return;
        }

        // !! File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        File file = saveAsFileChooser.getSelectedFile();
        if (!file.getName().endsWith(".html") & (!file.getName().endsWith(".txt"))) {
            file = new File(file.getAbsolutePath() + ".txt");
        }

        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(file));
            outFile.write(txtEditText.getText());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error writing to saved file", MessageBoxTitle, JOptionPane.WARNING_MESSAGE);
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (IOException e) {}
            }
        }
    }

    private static void saveToOpenedFile() {
        File file = new File(openedFile);
        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(file));
            outFile.write(txtEditText.getText());

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Couldnt save to opened file!", MessageBoxTitle, JOptionPane.WARNING_MESSAGE);


                }
            }
        }
    }

    private static void openFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try{txtEditText.read(new FileReader(selectedFile),null);
            openedFile = selectedFile.getAbsolutePath();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Couldnt read file " + selectedFile.getAbsolutePath() + "!", MessageBoxTitle, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}