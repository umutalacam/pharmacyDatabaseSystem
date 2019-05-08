package Unit;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class Warning {
    int warningId;
    SimpleStringProperty description;

    public Warning(int warningId, String description) {
        this.warningId = warningId;
        this.description = new SimpleStringProperty(description);
    }

    public int getWarningId() {
        return warningId;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}
