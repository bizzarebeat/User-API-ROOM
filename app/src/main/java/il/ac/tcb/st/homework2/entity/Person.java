package il.ac.tcb.st.homework2.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "persons",
        indices = {@Index(value = {"uuid"}, unique = true)})
public class Person {
    @PrimaryKey()
    @ColumnInfo(name = "uuid")
    @NonNull
    public String uuid;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "age")
    public Integer age;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "image_url")
    public String imageUrl;

    @Override
    public String toString() {
        return "Person{" +
                "uuid='" + uuid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

