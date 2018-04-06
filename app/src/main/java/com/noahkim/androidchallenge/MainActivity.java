package com.noahkim.androidchallenge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
    RecyclerView recyclerView;
    @BindView(R.id.server_error)
    TextView errorView;

    private List<User> userList = new ArrayList<>();
    private List<BadgeCounts> badgeCountsList = new ArrayList<>();
    private UserAdapter userAdapter;
    private AlertDialog alertDialog;
    private final static String USERS_KEY = "users_key";
    private final static String BADGES_KEY = "badges_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        // retrieve data from saved instance state
        if (savedInstanceState != null) {
            userList = savedInstanceState.getParcelableArrayList(USERS_KEY);
            badgeCountsList = savedInstanceState.getParcelableArrayList(BADGES_KEY);
        }

        // Initialize adapter
        userAdapter = new UserAdapter(this, userList, badgeCountsList);

        // Set linearlayout manager to recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter to recyclerview
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        if (!isConnected(this)) {
            showInternetDisabledAlertDialog(this);
        } else {
            getUserData();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        super.onStop();
    }

    private void getUserData() {
        // Retrieve data from endpoint
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserAPI service = retrofit.create(UserAPI.class);
        Call<Item> users = service.getItems();
        users.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(@NonNull Call<Item> call, @NonNull Response<Item> response) {
                if (response.isSuccessful()) {
                    Item item = response.body();
                    if (item != null) {
                        userList.clear();
                        userList.addAll(item.getUsers());
                        userAdapter.notifyDataSetChanged();
                        for (User user : userList) {
                            badgeCountsList.add(user.getBadgeCounts());
                        }
                    }
                    errorView.setVisibility(View.GONE);
                } else {
                    Timber.e("Error: " + response.code());
                    errorView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Item> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save data for saved instance state
        outState.putParcelableArrayList(USERS_KEY, (ArrayList<? extends Parcelable>) userList);
        outState.putParcelableArrayList(BADGES_KEY, (ArrayList<? extends Parcelable>) badgeCountsList);
    }

    // Check network connectivity
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }
            NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    // Show alert dialog if phone is not connected to Internet
    public void showInternetDisabledAlertDialog(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        alertDialogBuilder.setMessage(R.string.alert_message)
                .setTitle(R.string.alert_title)
                .setPositiveButton(R.string.positive_button,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.negative_button,
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
