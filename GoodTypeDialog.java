package view.dialogs;

import database.models.GoodType;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;

public class GoodTypeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField normTextField;
    private JTextField nameTextField;

    private boolean accept;
    private GoodType goodType;

    public GoodTypeDialog(GoodType goodType) {
        this.accept = false;
        if (goodType == null) {
            goodType = new GoodType();
        }
        this.goodType = goodType;

        setTitle("Товарная группа");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        if (this.goodType.getName() != null) {
            this.nameTextField.setText(this.goodType.getName());
            this.normTextField.setText(String.valueOf(this.goodType.getNorm()));
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
        String name = this.nameTextField.getText();
        String norm = this.normTextField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Название товарной группы не заполнено", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!name.replaceAll("[a-zA-Zа-яА-Я ]", "").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Название товарной группы не корректно", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (norm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Норма товарной группы не заполнена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!norm.replaceAll("[0-9]","").isEmpty()){
            JOptionPane.showMessageDialog(this, "Норма товарной группы не корректна", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        this.goodType.setName(name);
        this.goodType.setNorm(Integer.parseInt(norm));

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

    public GoodType getGoodType() {
        return goodType;
    }
//    public static void main(String[] args) {
//        GoodTypeDialog dialog = new GoodTypeDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
