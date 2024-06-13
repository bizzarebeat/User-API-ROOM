package il.ac.tcb.st.homework2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import il.ac.tcb.st.homework2.db.PersonDatabase;
import il.ac.tcb.st.homework2.entity.Person;
import il.ac.tcb.st.homework2.users.Results;
import il.ac.tcb.st.homework2.users.User;
import il.ac.tcb.st.homework2.users.UserAPIClient;
import il.ac.tcb.st.homework2.users.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public PersonDatabase db;
    private Button button;
    private TextView textView;
    private Results results;
    private Context context;
    private String fName = "fName";
    private String lName = "lName";
    private int age = 30;
    private String email = "em@ail.com";
    private String city = "Venice";
    private String county = "Spain";
    private String err = "error";
    private boolean isCorrect;

    private User user;
    private String uuid;
    private String imageURL;

    Button buttonCol;
    Button buttonNext;
    Button buttonAdd;

    public void LoadData(TextView tfname, TextView tlname, TextView tage, TextView temail, TextView tcity, TextView tcountry, ImageView imgPhoto) {

        Retrofit retrofit = UserAPIClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Results> callAsync = service.getUsers();

        callAsync.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull Response<Results> response) {
                isCorrect = true;
                results = response.body();
                assert results != null;
                ArrayList<User> resultsList = results.getResults();
                user = resultsList.get(0);
                fName = user.name.getFirst();
                lName = user.name.getLast();
                age = user.dob.age;
                email = user.email;
                city = user.location.city;
                county = user.location.country;
                imageURL = user.picture.large;
                uuid = user.login.uuid;

                buttonCol.setEnabled(true);
                buttonNext.setEnabled(true);
                buttonAdd.setEnabled(true);

                tfname.setText(fName);
                tlname.setText(lName);
                tage.setText("Age: " + Integer.toString(age));
                temail.setText("Email: " + email);
                tcity.setText("City: " + city);
                tcountry.setText("Country: " + county);
                Glide.with(context)
                        .load(imageURL)
                        .into(imgPhoto);
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable throwable) {
                isCorrect = false;
                buttonCol.setEnabled(true);
                buttonNext.setEnabled(true);
                buttonAdd.setEnabled(true);

                tfname.setText(err);
                tlname.setText(err);
                tage.setText("Age: " + err);
                temail.setText("Email: " + err);
                tcity.setText("City: " + err);
                tcountry.setText("Country: " + err);
                Glide.with(context)
                        .load(R.drawable.error_img)
                        .into(imgPhoto);

//                new AlertDialog.Builder(context)
//                        .setTitle("Error")
//                        .setMessage("Request failed " + throwable.getMessage())
//                        .setPositiveButton(android.R.string.yes, null)
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = PersonDatabase.getInstance(this);
        db.personDao().deleteAll();

        context = this;
        TextView tvFname = findViewById(R.id.txtFname);
        TextView tvLname = findViewById(R.id.txtLname);
        TextView tvAge = findViewById(R.id.txtAge);
        TextView tvEmail = findViewById(R.id.txtEmail);
        TextView tvCity = findViewById(R.id.txtCity);
        TextView tvCountry = findViewById(R.id.txtCountry);
        ImageView img = findViewById(R.id.imgPhoto);

        buttonCol = findViewById(R.id.btnCol);
        buttonNext = findViewById(R.id.btnNext);
        buttonAdd = findViewById(R.id.btnAdd);

        buttonCol.setEnabled(false);
        buttonNext.setEnabled(false);
        buttonAdd.setEnabled(false);

        LoadData(tvFname, tvLname, tvAge, tvEmail, tvCity, tvCountry, img);

        buttonNext.setOnClickListener(v -> {
            buttonCol.setEnabled(false);
            buttonNext.setEnabled(false);
            buttonAdd.setEnabled(false);
            LoadData(tvFname, tvLname, tvAge, tvEmail, tvCity, tvCountry, img);
        });

        buttonAdd.setOnClickListener(v -> {
            if (isCorrect)
                AddEntry(user);
            else
                Toast.makeText(this, "Cannot add user to collection!", Toast.LENGTH_LONG).show();
        });

        buttonCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
//                List<Person> persons = db.personDao().getAll();
//                for (Person person : persons) {
//                    Log.d("Database data", person.toString());
//                }
            }
        });
    }

    private void AddEntry(User user) {
        Person person = new Person();
        person.firstName = user.name.getFirst();
        person.lastName = user.name.getLast();
        person.age = user.dob.age;
        person.email = user.email;
        person.city = user.location.city;
        person.country = user.location.country;
        person.imageUrl = user.picture.large;
        person.uuid = user.login.uuid;
        db.personDao().insertPerson(person);
    }
}