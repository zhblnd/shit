package view.tables;

import database.models.GoodType;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class GoodTypeTableModel extends DefaultTableModel {
    private final List<GoodType> goodTypes;

    public GoodTypeTableModel(List<GoodType> goodTypes){
        this.goodTypes = goodTypes;
        String[] columnsNames = { "Название", "Норматив запаса" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (GoodType goodType : goodTypes) {
            rows.add(new Object[]{ goodType.getName(), goodType.getNorm() });
        }
        this.setDataVector(rows.toArray(new Object[0][]), columnsNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public List<GoodType> getGoodTypes() {
        return goodTypes;
    }
}
