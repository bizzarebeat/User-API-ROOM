package il.ac.tcb.st.homework2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import il.ac.tcb.st.homework2.entity.Person;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM persons")
    List<Person> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerson(Person person);
//    @Delete
//    void deleteStudent(Student student);
    @Query("DELETE FROM persons")
    void deleteAll();
}
