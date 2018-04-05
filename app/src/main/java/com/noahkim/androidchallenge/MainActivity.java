package com.noahkim.androidchallenge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.noahkim.androidchallenge.data.BadgeCounts;
import com.noahkim.androidchallenge.data.Item;
import com.noahkim.androidchallenge.data.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.users_recycler_view)
    RecyclerView mRecyclerView;

    private List<User> userList = new ArrayList<>();
    private List<BadgeCounts> badgeCountsList = new ArrayList<>();
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        userAdapter = new UserAdapter(this, userList, badgeCountsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        getUserData();
        super.onStart();
    }

    private void getUserData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final UserAPI service = retrofit.create(UserAPI.class);
        Call<Item> users = service.getItems();
        users.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(@NonNull Call<Item> call, @NonNull Response<Item> response) {
                Item item = response.body();
                if (item != null) {
                    userList.clear();
                    userList.addAll(item.getUsers());
                    userAdapter.notifyDataSetChanged();
                    for (User user : userList) {
                        badgeCountsList.add(user.getBadgeCounts());
                    }
                }
                Timber.d("size: " + userList.size());
                Timber.d("badges: " + badgeCountsList.size());
            }

            @Override
            public void onFailure(@NonNull Call<Item> call, @NonNull Throwable t) {

            }
        });

    }
}
