package view.dialogs;

import database.models.Good;
import database.models.Receipt;
import database.models.Receipt_Good;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import view.tables.GoodTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.*;

public class ReceiptDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable goodsTable;
    private JTextField suppliersInfoTextField;
    private JButton addGoodButton;
    private JButton deleteGoodButton;

    private Session session;
    private ArrayList<Good> goods;

    public ReceiptDialog(Session session) {
        this.session = session;
        goods = new ArrayList<>();
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
        addGoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGood();
            }
        });
    }

    private void AddGood(){
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            final Query goodsQuery = session.createQuery("FROM GoodType");
//            Vector<String> goodTypesVector = new Vector<>();
//            goodTypesVector.add(null);
//            for (Object object : goodsQuery.list()) {
//                goodTypesVector.add((String) object);
//            }
            GoodDialog goodDialog = new GoodDialog(goodsQuery);
            goodDialog.setSize(350, 150);
            goodDialog.setLocationRelativeTo(this);
            goodDialog.setVisible(true);
            if (goodDialog.isAccept()) {
                Good good = goodDialog.getGood();
                List goods = this.session.createQuery("FROM Good WHERE name = :name")
                        .setParameter("name", good.getName())
                        .list();
                if (!goods.isEmpty()) {
                    good = (Good) goods.get(0);
                    //good.setDeleted(false);
                }
                //this.session.saveOrUpdate(good);
                this.goods.add(good);
            }
            transaction.commit();
            this.updateGoodsTable();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateGoodsTable() {
        //final Query query = this.session.createQuery("FROM Good");
        this.goodsTable.setModel(new GoodTableModel(goods));
    }

    private void onOK() {
        System.out.println("fasfafafa");
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Receipt receipt = new Receipt();
            BigDecimal price = BigDecimal.valueOf(0);
            receipt.setDate(Date.from(Instant.now()));
            receipt.setTime(Time.valueOf(LocalTime.now()));
            Collection<Receipt_Good> receipt_goods = new ArrayList<>();
            for(Good good: goods){
//                Receipt_Good receipt_good = new Receipt_Good();
//                receipt_good.setGood(good);
//                receipt_good.setGood_id(good.getId());
//                receipt_good.setReceipt(receipt);
//                receipt_good.setReceipt_id(receipt.getId());
//                System.out.println(receipt.getId()+" fsdfwe DAFAF");
//                receipt_goods.add(receipt_good);
                System.out.println(price);
                price = price.add(good.getPrice().multiply(BigDecimal.valueOf(good.getAmount())));
                this.session.saveOrUpdate(good);
                //this.session.saveOrUpdate(receipt_good);
            }
            receipt.setPrice(price);
            //receipt.setReceipt_goods(receipt_goods);
            receipt.setSupplierinfo(suppliersInfoTextField.getText());
            this.session.saveOrUpdate(receipt);

//            for(Good good: goods){
//                Receipt_Good receipt_good = new Receipt_Good();
//                receipt_good.setGood(good);
//                receipt_good.setGood_id(good.getId());
//                receipt_good.setReceipt(receipt);
//                receipt_good.setReceipt_id(receipt.getId());
//                receipt_goods.add(receipt_good);
//                this.session.saveOrUpdate(receipt_good);
//            }
            //receipt.setReceipt_goods(receipt_goods);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        ReceiptDialog dialog = new ReceiptDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
