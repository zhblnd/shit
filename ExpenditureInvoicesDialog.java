package view.dialogs;

import database.models.ExpenditureInvoice;
import database.models.ExpenditureInvoice_Good;
import database.models.Good;
import database.models.GoodType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import view.tables.ExpenditureInvoiceTableModel;
import view.tables.GoodTypeTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ExpenditureInvoicesDialog extends JDialog {
    private JPanel contentPane;
    private JTable expenditureInvoiceTable;
    private JButton addExpenditureInvoiceButton;
    private JButton button3;
    private JButton buttonOK;

    private Session session;
    private BigDecimal price;

    public ExpenditureInvoicesDialog(Session session) {
        this.session = session;
        setTitle("Расходные накладные");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        updateExpenditureInvoicesTable();
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        AddExpenditureInvoice();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void AddExpenditureInvoice(){
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ExpenditureInvoiceDialog expenditureInvoiceDialog = new ExpenditureInvoiceDialog(null,this.session);
            expenditureInvoiceDialog.setSize(350, 150);
            expenditureInvoiceDialog.setLocationRelativeTo(this);
            expenditureInvoiceDialog.setVisible(true);
            if (expenditureInvoiceDialog.isAccept()) {
                HashMap<BigDecimal, ExpenditureInvoice_Good> expenditureInvoice_goodHashMap = expenditureInvoiceDialog.getExpenditureInvoice_goodHashMap();
                //ExpenditureInvoice goodType = expenditureInvoiceDialog.getGoodType();
                ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice();
                expenditureInvoice.setDate(Date.from(Instant.now()));
                expenditureInvoice.setTime(Time.valueOf(LocalTime.now()));
                price = BigDecimal.valueOf(0);
                Collection<ExpenditureInvoice_Good> expenditureInvoice_goods = new ArrayList<>();
                for (Map.Entry<BigDecimal, ExpenditureInvoice_Good> entry : expenditureInvoice_goodHashMap.entrySet()) {
                    price = entry.getKey();
                    expenditureInvoice_goods.add(entry.getValue());
                    Good updatedgood = new Good();
                    this.session.saveOrUpdate(entry.getValue().getGood());
                }
                expenditureInvoice.setPrice(price);
                expenditureInvoice.setExpenditureInvoice_goods(expenditureInvoice_goods);

//                List goodTypes = this.session.createQuery("FROM GoodType WHERE deleted = TRUE AND name = :name")
//                        .setParameter("name", goodType.getName())
//                        .list();
//                if (!goodTypes.isEmpty()) {
//                    goodType = (GoodType) goodTypes.get(0);
//                    goodType.setDeleted(false);
//                }
                this.session.saveOrUpdate(expenditureInvoice);
            }
            transaction.commit();
            this.updateExpenditureInvoicesTable();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateExpenditureInvoicesTable() {
        final Query query = this.session.createQuery("FROM ExpenditureInvoice");
        this.expenditureInvoiceTable.setModel(new ExpenditureInvoiceTableModel(query.list()));
    }

//    public static void main(String[] args) {
//        ExpenditureInvoicesDialog dialog = new ExpenditureInvoicesDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
