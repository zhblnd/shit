package view.dialogs;

import database.models.Good;
import database.models.GoodType;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

public class GoodDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField manufacturerTextField;
    private JTextField nomenTextField;
    private JTextField priceTextField;
    private JTextField amountTextField;
    private JTextField adInfoTextField;
    private JComboBox goodTypeComboBox;
    private HashMap<String,GoodType> stringGoodTypeHashMap;
    private boolean accept;

    public Good getGood() {
        return good;
    }

    private Good good;

    public GoodDialog( Query goodTypes) {
        stringGoodTypeHashMap = new HashMap<>();
        this.accept = false;
        good = new Good();

        //this.goodTypeComboBox.setModel(new DefaultComboBoxModel( goodTypes));
        for (Object object : goodTypes.list()) {
            stringGoodTypeHashMap.put(((GoodType)object).getName(),((GoodType)object));
            goodTypeComboBox.addItem(((GoodType)object).getName());

        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String name = this.nameTextField.getText();
        String manufacturer = this.manufacturerTextField.getText();
        int nomen = (Integer.parseInt(this.nomenTextField.getText()));
        BigDecimal price = (BigDecimal.valueOf(Double.parseDouble(priceTextField.getText())));
        int amount = (Integer.parseInt(this.amountTextField.getText()));
        String adInfo = this.adInfoTextField.getText();

//        if (name.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Название товарной группы не заполнено", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (!name.replaceAll("[a-zA-Zа-яА-Я ]", "").isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Название товарной группы не корректно", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (norm.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Норма товарной группы не заполнена", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if(!norm.replaceAll("[0-9]","").isEmpty()){
//            JOptionPane.showMessageDialog(this, "Норма товарной группы не корректна", "Ошибка", JOptionPane.ERROR_MESSAGE);
//        }

        this.good.setName(name);
        this.good.setManufacturer(manufacturer);
        this.good.setAdvertisinginformation(adInfo);
        this.good.setPrice(price);
        this.good.setNomenclaturenumber(nomen);
        this.good.setAmount(amount);
        this.good.setGoodType((stringGoodTypeHashMap.get(goodTypeComboBox.getSelectedItem())));

        this.accept = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean isAccept() {
        return accept;
    }

//    public static void main(String[] args) {
//        GoodDialog dialog = new GoodDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
