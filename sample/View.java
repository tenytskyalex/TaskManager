package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.entities.Task;

import java.io.IOException;

public class View {
    @FXML
    protected Button addBtn;
    @FXML
    protected TextField new_task;
    @FXML
    protected TextField new_start;
    @FXML
    protected TextField new_end;

    @FXML
    protected TableView<Task> tableView;
    @FXML
    protected TableColumn<Task, Integer> id;
    @FXML
    protected TableColumn<Task, String> task;
    @FXML
    protected TableColumn<Task, String> start;
    @FXML
    protected TableColumn<Task, String> end;

    @FXML
    private TextArea output;

    public void display(ObservableList<Task> state) {
        tableView.setEditable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        task.setCellValueFactory(new PropertyValueFactory<>("name"));
        task.setCellFactory(TextFieldTableCell.forTableColumn());
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        start.setCellFactory(TextFieldTableCell.forTableColumn());
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        end.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.setItems(state);
        tableView.refresh();
    }

    public void print(String arr)
    {
        output.setText(arr);
    }

    public void showAddDialog() throws IOException {
        Main.getStage().show();
    }
}
