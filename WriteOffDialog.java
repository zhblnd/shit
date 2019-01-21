package view.dialogs;

import database.models.ExpenditureInvoice;
import database.models.ExpenditureInvoice_Good;
import database.models.Good;
import database.models.WriteOffAct;
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

public class WriteOffDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable goodsTable;
    private JTextField reasonTextField;
    private Session session;
    private List<Good> goods;

    public WriteOffDialog(Session session) {
        this.session = session;
        goods = new ArrayList<>();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        final Query query = this.session.createQuery("FROM Good");
        for(Object o: query.list()){
            goods.add((Good)o);
        }
        this.goodsTable.setModel(new GoodTableModel(query.list()));

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
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (this.goodsTable.getModel().getRowCount() <= 0 || this.goodsTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Товар не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                WriteOffAct writeOffAct = new WriteOffAct();
                writeOffAct.setDate(Date.from(Instant.now()));
                writeOffAct.setTime(Time.valueOf(LocalTime.now()));
                writeOffAct.setReason(reasonTextField.getText());
                BigDecimal price = BigDecimal.valueOf(0);
                for(int selectedrow: this.goodsTable.getSelectedRows()){
                    Query query = session.createQuery("delete Good where id = :id")
                            .setParameter("id", goods.get(selectedrow).getId());
                    price = price.add(goods.get(selectedrow).getPrice().multiply(BigDecimal.valueOf(goods.get(selectedrow).getAmount())));
                    query.executeUpdate();
                }
                writeOffAct.setPrice(price);
                session.saveOrUpdate(writeOffAct);
            }
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
//        WriteOffDialog dialog = new WriteOffDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
