package view.dialogs;

import org.hibernate.Session;
import org.hibernate.query.Query;
import view.tables.GoodTableModel;
import view.tables.WriteOffActTableModel;

import javax.swing.*;
import java.awt.event.*;

public class WriteOffDialogs extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private Session session;

    public WriteOffDialogs(Session session) {
        this.session = session;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        updateWriteOffActsTable();
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

    private void updateWriteOffActsTable() {
        final Query query = this.session.createQuery("FROM WriteOffAct");
        this.table1.setModel(new WriteOffActTableModel(query.list()));
    }
    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        WriteOffDialogs dialog = new WriteOffDialogs();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
