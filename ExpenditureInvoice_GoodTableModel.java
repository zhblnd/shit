package view.tables;

import database.models.ExpenditureInvoice_Good;
import database.models.GoodType;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureInvoice_GoodTableModel extends DefaultTableModel {
    private final List<ExpenditureInvoice_Good> expenditureInvoice_goods;

    public ExpenditureInvoice_GoodTableModel(List<ExpenditureInvoice_Good> expenditureInvoice_goods){
        this.expenditureInvoice_goods = expenditureInvoice_goods;
        String[] columnsNames = { "Название товара" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (ExpenditureInvoice_Good expenditureInvoice_good : expenditureInvoice_goods) {
            rows.add(new Object[]{ expenditureInvoice_good.getGood().getName()});
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<ExpenditureInvoice_Good> getExpenditureInvoice_goods() {
        return expenditureInvoice_goods;
    }
}
