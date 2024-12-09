package co.edu.uptc.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowData {

    private final StringProperty entity;
    private final StringProperty similarEntities;
    private final StringProperty bundle;


    public TableRowData(String entity, String similarEntities) {
        this.entity = new SimpleStringProperty(entity);
        this.similarEntities = new SimpleStringProperty(similarEntities);
        this.bundle = null;
    }

    public TableRowData(String bundle) {
        this.entity = null;
        this.similarEntities = null;
        this.bundle = new SimpleStringProperty(bundle);
    }


    public StringProperty entityProperty() {
        return entity;
    }

    public StringProperty similarEntitiesProperty() {
        return similarEntities;
    }

    public StringProperty bundleProperty() {
        return bundle;
    }
    public String getEntity() {
        return entity != null ? entity.get() : null;
    }

    public String getSimilarEntities() {
        return similarEntities != null ? similarEntities.get() : null;
    }

    public String getBundle() {
        return bundle != null ? bundle.get() : null;
    }
}