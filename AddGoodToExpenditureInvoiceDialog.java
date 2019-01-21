package view.dialogs;

import database.models.ExpenditureInvoice;
import database.models.ExpenditureInvoice_Good;
import database.models.Good;
import database.models.GoodType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class AddGoodToExpenditureInvoiceDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField sizeTextField;
    private JComboBox goodСomboBox;

    private ExpenditureInvoice_Good good;
    private ExpenditureInvoice expenditureInvoice;
    private List<Good> goods;
    private BigDecimal num;
    private boolean accept;
    private HashMap<String,Good> goodHashMap;
    private int amount;

    public AddGoodToExpenditureInvoiceDialog(ExpenditureInvoice expenditureInvoice, Query goodsVector) {
        this.expenditureInvoice = expenditureInvoice;
        goodHashMap = new HashMap<>();
        amount = 0;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
//        Vector<String> goodsVector = new Vector<>();
//        goods.add(null);
//        final Query goodsQuery = session.createQuery("FROM Good");
//        for (Object object : goodsQuery.list()) {
//            goods.add((String) object);
//        }
//        this.goodСomboBox.setModel(new DefaultComboBoxModel( goodsVector));
        for (Object object : goodsVector.list()) {
            goodHashMap.put(((Good)object).getName(),(Good)object);
            goodСomboBox.addItem(((Good)object).getName());

        }

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
        ExpenditureInvoice_Good expenditureInvoice_good = new ExpenditureInvoice_Good();
        expenditureInvoice_good.setGood(goodHashMap.get(goodСomboBox.getSelectedItem()));
        expenditureInvoice_good.setExpenditureInvoice(expenditureInvoice);
        this.num = (BigDecimal.valueOf(Integer.parseInt(sizeTextField.getText()))).multiply((goodHashMap.get(goodСomboBox.getSelectedItem()).getPrice()));
        System.out.println("num = "+num);
        this.good = expenditureInvoice_good;
        this.amount = Integer.parseInt(sizeTextField.getText());
        if (this.good == null) {
            JOptionPane.showMessageDialog(this, "Товар не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(sizeTextField.getText() == null){
            return;
        }

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

    public ExpenditureInvoice_Good getExpenditureInvoice_Good() {
        return good;
    }

    public BigDecimal getNum(){
        return this.num;
    }
//    public static void main(String[] args) {
//        AddGoodToExpenditureInvoiceDialog dialog = new AddGoodToExpenditureInvoiceDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
