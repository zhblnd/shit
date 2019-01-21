package view.dialogs;

import database.models.Good;
import database.models.GoodType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import view.tables.GoodTypeTableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class GoodTypesDialog extends JDialog {
    private JPanel contentPane;
    private JButton addGoodTypeButton;
    private JButton editGoodTypeButton;
    private JButton deleteGoodTYpeButton;
    private JTable goodTypesTable;

    private Session session;

    public GoodTypesDialog(Session session) {
        this.session = session;
        setTitle("Товарные группы");
        setContentPane(contentPane);
        setModal(true);

        updateGoodTypesTable();

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
        addGoodTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGoodType();
            }
        });
        editGoodTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditGoodType();
            }
        });
        deleteGoodTYpeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteGoodType();
            }
        });
    }

    private void AddGoodType(){
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            GoodTypeDialog goodTypeDialog = new GoodTypeDialog(null);
            goodTypeDialog.setSize(350, 150);
            goodTypeDialog.setLocationRelativeTo(this);
            goodTypeDialog.setVisible(true);
            if (goodTypeDialog.isAccept()) {
                GoodType goodType = goodTypeDialog.getGoodType();
                List goodTypes = this.session.createQuery("FROM GoodType WHERE deleted = TRUE AND name = :name")
                        .setParameter("name", goodType.getName())
                        .list();
                if (!goodTypes.isEmpty()) {
                    goodType = (GoodType) goodTypes.get(0);
                    goodType.setDeleted(false);
                }
                this.session.saveOrUpdate(goodType);
            }
            transaction.commit();
            this.updateGoodTypesTable();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void EditGoodType(){
        if (this.goodTypesTable.getModel().getRowCount() <= 0 || this.goodTypesTable.getSelectedRow() == -1) {
            return;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            GoodTypeTableModel goodTypesTableModel = (GoodTypeTableModel) this.goodTypesTable.getModel();
            GoodType goodType = goodTypesTableModel.getGoodTypes().get(this.goodTypesTable.getSelectedRow());
            GoodTypeDialog goodTypeDialog = new GoodTypeDialog(goodType);
            goodTypeDialog.setSize(350, 350);
            goodTypeDialog.setLocationRelativeTo(this);
            goodTypeDialog.setVisible(true);
            if (goodTypeDialog.isAccept()) {
                goodType = goodTypeDialog.getGoodType();
                this.session.saveOrUpdate(goodType);
//                for (GoodType materialTypes : goodType.getMaterialTypes()) {
//                    this.session.saveOrUpdate(materialTypes);
//                }
            }
            transaction.commit();
            this.updateGoodTypesTable();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void DeleteGoodType(){
        if (this.goodTypesTable.getModel().getRowCount() <= 0 || this.goodTypesTable.getSelectedRow() == -1) {
            return;
        }
        int dialogResult = JOptionPane.showConfirmDialog (this, "Удалить?","Подтверждение", JOptionPane.YES_NO_OPTION);
        if(dialogResult != JOptionPane.YES_OPTION){
            return;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            GoodTypeTableModel goodTypesTableModel = (GoodTypeTableModel) this.goodTypesTable.getModel();
            GoodType goodType = goodTypesTableModel.getGoodTypes().get(this.goodTypesTable.getSelectedRow());
            goodType.setDeleted(true);
            this.session.saveOrUpdate(goodType);
            transaction.commit();
            this.updateGoodTypesTable();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                JOptionPane.showMessageDialog(this, ex, "Ошибка при выполнении транзакции", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void updateGoodTypesTable() {
        final Query query = this.session.createQuery("FROM GoodType WHERE deleted = false ORDER BY name");
        this.goodTypesTable.setModel(new GoodTypeTableModel(query.list()));
    }

//    public static void main(String[] args) {
//        GoodTypesDialog dialog = new GoodTypesDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
