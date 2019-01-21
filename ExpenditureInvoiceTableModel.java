package view.tables;

import database.models.ExpenditureInvoice;
import database.models.ExpenditureInvoice_Good;
import database.models.GoodType;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureInvoiceTableModel extends DefaultTableModel {
    private final List<ExpenditureInvoice> expenditureInvoices;

    public ExpenditureInvoiceTableModel(List<ExpenditureInvoice> expenditureInvoices){
        this.expenditureInvoices = expenditureInvoices;
        String[] columnsNames = { "Дата", "Время","Сумма","Товары" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (ExpenditureInvoice expenditureInvoice : expenditureInvoices) {
            StringBuilder goods = new StringBuilder();
            for (ExpenditureInvoice_Good expenditureInvoice_good : expenditureInvoice.getExpenditureInvoice_goods()) {
                goods.append(expenditureInvoice_good.getGood().getName()).append(", ");
            }
            rows.add(new Object[]{
                    expenditureInvoice.getDate(),
                    expenditureInvoice.getTime(),
                    expenditureInvoice.getPrice(),
                    goods.toString()
            });
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<ExpenditureInvoice> getExpenditureInvoices() {
        return expenditureInvoices;
    }
}
