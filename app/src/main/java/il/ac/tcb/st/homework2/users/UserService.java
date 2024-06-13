package il.ac.tcb.st.homework2.users;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/api")
    public Call<Results> getUsers();
}
