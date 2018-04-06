package com.noahkim.androidchallenge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.noahkim.androidchallenge.data.BadgeCounts;
import com.noahkim.androidchallenge.data.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    private Context context;
    private List<BadgeCounts> badgeCountsList;

    UserAdapter(Context context, List<User> users, List<BadgeCounts> badgeCountsList) {
        this.context = context;
        this.users = users;
        this.badgeCountsList = badgeCountsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        User user = users.get(position);
        BadgeCounts badgeCounts = badgeCountsList.get(position);

        // Set display field for username
        holder.usernameText.setText(user.getUsername());

        // Set display field for profile image
        GlideApp.with(context)
                .load(user.getProfileImage())
                .error(R.drawable.ic_account_circle)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.profileImage);

        // Set display fields for badges
        holder.goldText.setText(String.valueOf(badgeCounts.getGold()));
        holder.silverText.setText(String.valueOf(badgeCounts.getSilver()));
        holder.bronzeText.setText(String.valueOf(badgeCounts.getBronze()));
    }

    @Override
    public int getItemCount() {
        if (users == null) return 0;
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.username_text)
        TextView usernameText;
        @BindView(R.id.profile_image)
        ImageView profileImage;
        @BindView(R.id.gold_text)
        TextView goldText;
        @BindView(R.id.silver_text)
        TextView silverText;
        @BindView(R.id.bronze_text)
        TextView bronzeText;
        @BindView(R.id.progress)
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
