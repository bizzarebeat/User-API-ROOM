package il.ac.tcb.st.homework2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import il.ac.tcb.st.homework2.adapters.PersonAdapter;
import il.ac.tcb.st.homework2.dao.PersonDao;
import il.ac.tcb.st.homework2.db.PersonDatabase;
import il.ac.tcb.st.homework2.entity.Person;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<String> dataList;

    PersonDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        db = PersonDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Person> persons = db.personDao().getAll();

        adapter = new PersonAdapter(persons);
        recyclerView.setAdapter(adapter);
    }
}