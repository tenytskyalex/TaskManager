package sample.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entities.Task;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskRepository {
    private boolean status = false;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    private Connection connection;

    public TaskRepository(Connection connection)
    {
        this.connection = connection;
    }

    public void read() {
        tasks.clear();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                Task task = new Task(resultSet.getString("task"),
                        resultSet.getString("start"),
                        resultSet.getString("end"));
                task.setId(resultSet.getInt("task_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean write(Task task) {
            try (PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO tasks(task, start, end) " + "VALUES(?, ?, ?)")) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                format.setLenient(false);
                Date start = null;
                Date end = null;
                try {
                    start = format.parse(task.getStart());
                    end = format.parse(task.getEnd());
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                    return false;
                }

                statement.setObject(1, task.getName());
                statement.setObject(2, task.getStart());
                statement.setObject(3, task.getEnd());
                statement.execute();
                try (Statement statementForId = this.connection.createStatement()) {
                    ResultSet resultSet = statementForId.executeQuery("SELECT max(task_id) as new_id from tasks");
                    System.out.println(task.getName());
                    while (resultSet.next()) {
                        task.setId(resultSet.getInt("new_id"));
                    }
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    public void delete(int index) throws IOException {
        int id = tasks.get(index).getId();
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM tasks WHERE task_id = ?")) {
            statement.setObject(1, id);
            statement.execute();
            tasks.remove(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getByInterval(String from) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM tasks where start < ? and end > ?")) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            format.setLenient(false);
            Date start = null;
            try {
                start = format.parse(from);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                return false;
            }
            tasks.clear();
            statement.setObject(1, from);
            statement.setObject(2, from);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("task"));
                Task task = new Task(resultSet.getString("task"),
                        resultSet.getString("start"),
                        resultSet.getString("end"));
                task.setId(resultSet.getInt("task_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(String column, Task task, String data) {
        switch (column) {
            case "Назва задачі":
                try (PreparedStatement statement = this.connection.prepareStatement("UPDATE tasks set task = ? where task_id = ?")) {
                    statement.setObject(1, data);
                    statement.setObject(2, task.getId());
                    statement.execute();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
            case "Початок":
                try (PreparedStatement statement = this.connection.prepareStatement("UPDATE tasks set start = ? where task_id = ?")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    format.setLenient(false);
                    Date start = null;
                    try {
                        start = format.parse(data);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                    statement.setObject(1, data);
                    statement.setObject(2, task.getId());
                    statement.execute();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
            case "Кінець":
                try (PreparedStatement statement = this.connection.prepareStatement("UPDATE tasks set end = ? where task_id = ?")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    format.setLenient(false);
                    Date end = null;
                    try {
                        end = format.parse(data);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                    statement.setObject(1, data);
                    statement.setObject(2, task.getId());
                    statement.execute();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }
        this.read();
        return true;
    }

    public ObservableList<Task> getResult() {
        return tasks;
    }
}
