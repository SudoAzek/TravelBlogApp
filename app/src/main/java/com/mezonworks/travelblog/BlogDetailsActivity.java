package com.mezonworks.travelblog;

import androidx.appcompat.app.AppCompatActivity;

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

        // start loading data
        loadData();

//        ImageView imageMain = findViewById(R.id.imageMain);
////        imageMain.setImageResource(R.drawable.sydney_image);
//        Glide.with(this)
//                .load(IMAGE_URL)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageMain);
//
//        ImageView imageAvatar = findViewById(R.id.imageAvatar);
////        imageAvatar.setImageResource(R.drawable.avatar);
//        Glide.with(this)
//                .load(AVATAR_URL)
//                .transform(new CircleCrop())
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageAvatar);



//        TextView textTitle = findViewById(R.id.textTitle);
//        textTitle.setText("G'day from Sydney");
//
//        TextView textDate = findViewById(R.id.textDate);
//        textDate.setText("August 2, 2022");
//
//        TextView textAuthor = findViewById(R.id.textAuthor);
//        textAuthor.setText("Azamat Ochilov");
//
//        TextView textRating = findViewById(R.id.textRating);
//        textRating.setText("4.4");
//
//        TextView textViews = findViewById(R.id.textViews);
//        textViews.setText("(2687 views)");
//
//        TextView textDescription = findViewById(R.id.textDescription);
//        textDescription.setText("Australia is one of the most popular travel destinations in the world.");
//
//        RatingBar ratingBar = findViewById(R.id.ratingBar);
//        ratingBar.setRating(4.4f);
//
//        ImageView imageBack = findViewById(R.id.imageBack);
//        imageBack.setOnClickListener(v -> finish());

    }

    private void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            @Override
            public void onError() {
                // handle error
                runOnUiThread(() -> showErrorSnackbar());
            }
        });
    }

    private void showErrorSnackbar() {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView,
                "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.orange500));
        snackbar.setAction("Retry", view -> {
            loadData();
            snackbar.dismiss();
        });
        snackbar.show();
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
}