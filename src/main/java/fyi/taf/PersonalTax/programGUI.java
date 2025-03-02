package fyi.taf.PersonalTax;

import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 * GUI program
 * @author fuuzen
 * @version 1.0.0
 * @since 2025-2-28
 */
public class programGUI {

    /**
     * GUI program constructor
     */
    public programGUI() {};

    /**
     * GUI program main function
     * @param args 参数无效,不需要
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        JFrame frame = new JFrame("个人所得税计算器");
        frame.setSize(650, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();    
        frame.add(panel);
        panel.setLayout(null);

        try {
            String os = System.getProperty("os.name").toLowerCase();
            Image icon;
            if (os.contains("win")) {
                ImageIcon ico = new ImageIcon("images/icons/icon.ico");
                icon = ico.getImage();
            } else if (os.contains("mac")) {
                ImageIcon icns = new ImageIcon("images/icons/icon.icns");
                icon = icns.getImage();
            } else {
                InputStream inputStream16 = programGUI.class.getClassLoader().getResourceAsStream("images/icons/icon16.png");
                InputStream inputStream32 = programGUI.class.getClassLoader().getResourceAsStream("images/icons/icon32.png");
                InputStream inputStream64 = programGUI.class.getClassLoader().getResourceAsStream("images/icons/icon64.png");
                InputStream inputStream128 = programGUI.class.getClassLoader().getResourceAsStream("images/icons/icon128.png");
                InputStream inputStream256 = programGUI.class.getClassLoader().getResourceAsStream("images/icons/icon256.png");

                if (inputStream16 == null || inputStream32 == null || inputStream64 == null || inputStream128 == null || inputStream256 == null) {
                    throw new IOException("Icon not found!");
                }

                BufferedImage icon16 = ImageIO.read(inputStream16);
                BufferedImage icon32 = ImageIO.read(inputStream32);
                BufferedImage icon64 = ImageIO.read(inputStream64);
                BufferedImage icon128 = ImageIO.read(inputStream128);
                BufferedImage icon256 = ImageIO.read(inputStream256);
    
                icon = new BaseMultiResolutionImage(icon16, icon32, icon64, icon128, icon256);
            }
            frame.setIconImage(icon);

            InputStream inputStream = programGUI.class.getClassLoader().getResourceAsStream("images/taffy.png");
            if (inputStream == null) {
                throw new IOException("Image not found!");
            }
            JLabel taffy = new JLabel(
                new ImageIcon(
                    ImageIO.read(inputStream).getScaledInstance(250, 250, Image.SCALE_SMOOTH)
                )
            );
            taffy.setBounds(25, 5, 250, 250);
            panel.add(taffy);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load image!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel inputLabel = new JLabel("您的收入: ");
        inputLabel.setBounds(300, 20, 100, 25);
        inputLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(inputLabel);

        SpinnerNumberModel input = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(input);
        spinner.setBounds(400, 20, 150, 25);
        panel.add(spinner);

        JButton calButton = new JButton("计算");
        calButton.setBounds(400, 50, 100, 25);
        panel.add(calButton);

        JLabel texLabelText = new JLabel("您的个人所得税为: ");
        texLabelText.setBounds(350, 80, 200, 25);
        texLabelText.setHorizontalAlignment(JLabel.CENTER);
        panel.add(texLabelText);

        String[] columnNames = {"税级", "起征点", "税率", "您应缴税"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 不可编辑
                return column != 0 && column != 3 && row != 0 && row != 6;
            }

            @Override
            public void setValueAt(Object value, int row, int column) {
                super.setValueAt(value, row, column);
                if (column == 1) { // 修改起征点
                    calculator.adjustTaxThreshold(row - 1, Double.parseDouble(value.toString()));
                }
                if (column == 2) { // 修改税率
                    calculator.adjustTaxRate(row - 1, Double.parseDouble(value.toString()));
                }
            }
        };
        model.addRow(new Object[]{"税级", "起征点", "税率", "您应缴税"});
        for(int i = 0; i < calculator.getTaxLevelCount(); ++i) {
            TaxLevel taxLevel = calculator.getTaxLevel(i);
            model.addRow(new Object[]{i + 1, taxLevel.getThreshold(), taxLevel.getRate(), 0});
        }
        model.addRow(new Object[]{"总共", null, null, 0});
        JTable table = new JTable(model);
        table.setBounds(300, 120, 300, 120);
        panel.add(table);

        calButton.addActionListener(e -> {
            ArrayList<Double> res = calculator.calculate(input.getNumber().doubleValue());
            for(int i = 0; i < 6; ++i) {
                model.setValueAt(String.format("%,.2f", res.get(i)), i + 1, 3);
            }
        });

        frame.setVisible(true);
    }
}
