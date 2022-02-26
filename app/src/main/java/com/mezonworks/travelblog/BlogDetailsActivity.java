package com.mezonworks.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.snackbar.Snackbar;
import com.mezonworks.travelblog.http.Blog;
import com.mezonworks.travelblog.http.BlogArticlesCallback;
import com.mezonworks.travelblog.http.BlogHttpClient;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {

//    public static final String IMAGE_URL = "https://github.com/SudoAzek/TravelBlogApp/blob/main/app/src/main/res/drawable/sydney_image.jpg?raw=true";
//    public static final String AVATAR_URL = "https://github.com/SudoAzek/TravelBlogApp/blob/main/app/src/main/res/drawable/avatar.jpg?raw=true";

    private TextView textTitle;
    private TextView textDate;
    private TextView textAuthor;
    private TextView textRating;
    private TextView textDescription;
    private TextView textViews;
    private RatingBar ratingBar;
    private ImageView imageAvatar;
    private ImageView imageMain;

    private ProgressBar progressBar;

    private static final String EXTRAS_BLOG = "EXTRA_BLOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        imageAvatar = findViewById(R.id.imageAvatar);
        imageMain = findViewById(R.id.imageMain);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> finish());

        textTitle = findViewById(R.id.textTitle);
        textDate = findViewById(R.id.textDate);
        textAuthor = findViewById(R.id.textAuthor);
        textRating = findViewById(R.id.textRating);
        textViews = findViewById(R.id.textViews);
        textDescription = findViewById(R.id.textDescription);
        ratingBar = findViewById(R.id.ratingBar);

        progressBar = findViewById(R.id.progressBar);

        showData(getIntent().getExtras().getParcelable(EXTRAS_BLOG));
    }

    private void showData(Blog blogList) {
        progressBar.setVisibility(View.GONE);
        textTitle.setText(blogList.getTitle());
        textDate.setText(blogList.getDate());
        textAuthor.setText(blogList.getAuthor().getName());
        textRating.setText(String.valueOf(blogList.getRating()));
        textViews.setText(String.format("(%d views)", blogList.getViews()));
        textDescription.setText(Html.fromHtml(blogList.getDescription()));
        ratingBar.setRating(blogList.getRating());


        Glide.with(this)
                .load(blogList.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blogList.getAuthor().getAvatar())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }
    public static void startBlogDetailsActivity(Activity activity, Blog blog) {
        Intent intent = new Intent(activity, BlogDetailsActivity.class);
        intent.putExtra(EXTRAS_BLOG, blog);
        activity.startActivity(intent);
    }
}