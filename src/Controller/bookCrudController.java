package Controller;

import db.CrudUtil;
import model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class bookCrudController {

    public static ArrayList<String> getBookId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM Book");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Book getBook(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Book WHERE id=?", id);
        if (result.next()){
            return new Book(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }
}
