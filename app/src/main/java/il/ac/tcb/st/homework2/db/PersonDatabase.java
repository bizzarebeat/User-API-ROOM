package il.ac.tcb.st.homework2.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import il.ac.tcb.st.homework2.dao.PersonDao;
import il.ac.tcb.st.homework2.entity.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    // Database creation singleton
    private static PersonDatabase instance;
    public static synchronized PersonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PersonDatabase.class,
                            "person_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract PersonDao personDao();
}
