package view.dialogs;

import database.models.ExpenditureInvoice;
import database.models.ExpenditureInvoice_Good;
import database.models.Good;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jdatepicker.JDatePicker;
import view.tables.ExpenditureInvoice_GoodTableModel;
import view.tables.GoodTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ExpenditureInvoiceDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable expenditureInvoicesTable;
    private JButton addGoodButton;
    private JButton removeGoodButton;
    private JTextField priceTextField;
    private JDatePicker datePicker;

    private boolean accept;
    private ExpenditureInvoice expenditureInvoice;
    private List<Good> goods;
    private List<ExpenditureInvoice_Good> expenditureInvoice_goods;

    public HashMap<BigDecimal, ExpenditureInvoice_Good> getExpenditureInvoice_goodHashMap() {
        return expenditureInvoice_goodHashMap;
    }

    private HashMap<BigDecimal,ExpenditureInvoice_Good> expenditureInvoice_goodHashMap;
    private Session session;

    public ExpenditureInvoiceDialog(ExpenditureInvoice expenditureInvoice, Session session) {
        expenditureInvoice_goodHashMap = new HashMap<>();
        goods = new ArrayList<>();
        this.session = session;
        this.accept = false;
        if (expenditureInvoice == null) {
            expenditureInvoice = new ExpenditureInvoice();
        }
        this.expenditureInvoice = expenditureInvoice;

        setTitle("Расходная накладная");
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

        addGoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ddhbvdjgds");
                AddGoodToExpenditureInvoice();
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

        this.accept = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void AddGoodToExpenditureInvoice(){
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
            final Query goodsQuery = session.createQuery("FROM Good");
//                Vector<String> goodsVector = new Vector<>();
//                goodsVector.add(null);
//                for (Object object : goodsQuery.list()) {
//                    goodsVector.add((String) object);
//                }
            AddGoodToExpenditureInvoiceDialog addGoodToExpenditureInvoiceDialog = new AddGoodToExpenditureInvoiceDialog(this.expenditureInvoice, goodsQuery);
            addGoodToExpenditureInvoiceDialog.setSize(350, 150);
            addGoodToExpenditureInvoiceDialog.setLocationRelativeTo(this);
            addGoodToExpenditureInvoiceDialog.setVisible(true);
            if (addGoodToExpenditureInvoiceDialog.isAccept()) {
                ExpenditureInvoice_Good expenditureInvoice_good = addGoodToExpenditureInvoiceDialog.getExpenditureInvoice_Good();
                goods.add(expenditureInvoice_good.getGood());
                //ExpenditureInvoice goodType = addGoodToExpenditureInvoiceDialog.getGoodType();
                List expenditureInvoice_Goods = this.session.createQuery("FROM ExpenditureInvoice_Good WHERE id = :id")
                        .setParameter("id", expenditureInvoice_good.getId())
                        .list();
                if (!expenditureInvoice_Goods.isEmpty()) {
                    expenditureInvoice_good = (ExpenditureInvoice_Good) expenditureInvoice_Goods.get(0);
                    //expenditureInvoice_good.setDeleted(false);
                }
                expenditureInvoice_goodHashMap.put(addGoodToExpenditureInvoiceDialog.getNum(),expenditureInvoice_good);
                //this.session.saveOrUpdate(expenditureInvoice_good);
            }
           // transaction.commit();
            this.updateExpenditureInvoice_GoodTable();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }

    private void updateExpenditureInvoice_GoodTable() {
        //final Query query = this.session.createQuery("FROM ExpenditureInvoice_Good");
        this.expenditureInvoicesTable.setModel(new GoodTableModel(goods));
    }

    public boolean isAccept(){
        return accept;
    }
//    public static void main(String[] args) {
//        ExpenditureInvoiceDialog dialog = new ExpenditureInvoiceDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
