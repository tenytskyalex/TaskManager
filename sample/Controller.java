package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.helpers.DbHandler;
import sample.repositories.TaskRepository;
import sample.entities.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Controller extends View{
    @FXML
    private TextField startFilter;

    private final TaskRepository taskRepository = new TaskRepository(DbHandler.getInstance());

    public Controller() throws SQLException {
        super();
        taskRepository.read();
    }

    public void BtnView(ActionEvent actionEvent) throws IOException, ParseException {
        taskRepository.read();
        view();
    }

    public void BtnAdd(ActionEvent actionEvent) throws IOException {
        showAddDialog();
    }

    public void secondAdd(ActionEvent actionEvent) {
            taskRepository.write(new Task(new_task.getText(),new_start.getText(), new_end.getText()));
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.close();
    }

    public int getCurrentRow() {
        int index = tableView.getSelectionModel().selectedIndexProperty().get();
        return index;
    }

    public void view()
    {
        display(taskRepository.getResult());
    }

    public void BtnDell(ActionEvent actionEvent) throws IOException {
        try {
            taskRepository.delete(getCurrentRow());
        }
        catch (NullPointerException | IndexOutOfBoundsException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка");

            alert.setHeaderText("");
            alert.setContentText("Оберіть задачу!");

            alert.showAndWait();
        }
    }

    public void filter(ActionEvent actionEvent) {
        String fromTime = startFilter.getText();
        taskRepository.getByInterval(fromTime);
        System.out.println(fromTime);
        this.view();
    }

    public void commitEdit(TableColumn.CellEditEvent<Task, String> taskStringCellEditEvent) {
        if(!taskStringCellEditEvent.getOldValue().equals(taskStringCellEditEvent.getNewValue())) {
            String column = taskStringCellEditEvent.getTableColumn().getText();
            Task task = taskStringCellEditEvent.getRowValue();
            this.taskRepository.update(column, task, taskStringCellEditEvent.getNewValue());
            this.view();
        }
    }
}
