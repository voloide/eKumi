package mz.co.insystems.mobicare.common;

import java.util.List;

/**
 * Created by Voloide Tamele on 11/26/2017.
 */

public class DataRowItem<Localizacao> {

    protected List<Localizacao> list;
    protected Localizacao selected;

    public DataRowItem(List<Localizacao> list) {
        this.list = list;
    }

    public List<Localizacao> getList() {
        return list;
    }

    public void setList(List<Localizacao> list) {
        this.list = list;
    }

    public Localizacao getSelected() {
        return selected;
    }

    public void setSelected(Localizacao selected) {
        this.selected = selected;
    }
}
