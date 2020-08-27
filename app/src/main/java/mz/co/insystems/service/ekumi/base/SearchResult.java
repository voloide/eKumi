package mz.co.insystems.service.ekumi.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResult<T extends AbstractBaseVO> implements Serializable {

    protected int qtdAllRecords;

    protected double totalValue;

    protected List<T> currentSearchedRecords;

    public SearchResult() {
        this.currentSearchedRecords = new ArrayList<>();
    }

    public int getQtdAllRecords() {
        return qtdAllRecords;
    }

    public void setQtdAllRecords(int qtdAllRecords) {
        this.qtdAllRecords = qtdAllRecords;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public List<T> getCurrentSearchedRecords() {
        return currentSearchedRecords;
    }

    public void setCurrentSearchedRecords(List<T> currentSearchedRecords) {
        this.currentSearchedRecords = currentSearchedRecords;
    }
}

