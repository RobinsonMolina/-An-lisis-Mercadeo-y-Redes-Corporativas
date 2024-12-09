package co.edu.uptc.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowData {

    private final StringProperty entity; // Para la entidad (similaridad)
    private final StringProperty similarEntities; // Para entidades similares
    private final StringProperty bundle; // Para paquetes de productos

    // Constructor para entidades similares
    public TableRowData(String entity, String similarEntities) {
        this.entity = new SimpleStringProperty(entity);
        this.similarEntities = new SimpleStringProperty(similarEntities);
        this.bundle = null; // No aplica para este caso
    }

    // Constructor para paquetes de productos
    public TableRowData(String bundle) {
        this.entity = null; // No aplica para este caso
        this.similarEntities = null; // No aplica para este caso
        this.bundle = new SimpleStringProperty(bundle);
    }

    // Getters para propiedades
    public StringProperty entityProperty() {
        return entity;
    }

    public StringProperty similarEntitiesProperty() {
        return similarEntities;
    }

    public StringProperty bundleProperty() {
        return bundle;
    }

    // Métodos para obtener valores directamente
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