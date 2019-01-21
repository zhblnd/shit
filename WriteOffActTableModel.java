package view.tables;

import database.models.Good;
import database.models.WriteOffAct;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class WriteOffActTableModel extends DefaultTableModel {
    private final List<WriteOffAct> writeOffActs;

    public WriteOffActTableModel(List<WriteOffAct> writeOffActs){
        this.writeOffActs = writeOffActs;
        String[] columnsNames = { "Дата", "ВРемя","Причина","Сумма" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (WriteOffAct writeOffAct : writeOffActs) {
            rows.add(new Object[]{ writeOffAct.getDate(), writeOffAct.getTime(),writeOffAct.getReason(),writeOffAct.getPrice() });
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<WriteOffAct> getWriteOffActs() {
        return writeOffActs;
    }
}
