package com.example.ehsanullah.loginandregistration.Books;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.ReadBookActivity;
import com.example.ehsanullah.loginandregistration.databinding.ActivityBookInfoBinding;

public class BookInfoActivity extends AppCompatActivity {

    Bundle metadata;
    ActivityBookInfoBinding bookInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_info);

        Toolbar toolbar = bookInfoBinding.toolbar;
        setSupportActionBar(toolbar);

        bookInfoBinding.toolbarLayout.setTitleEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        metadata = getIntent().getBundleExtra("metadata");
        updateViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/


                Intent intent = new Intent(BookInfoActivity.this, ReadBookActivity.class);
               intent.putExtra("metadata",metadata);
                startActivity(intent);
            }
        });

    }

    private void updateViews() {

        Glide.with(this).load(metadata.getString(BookMetadata.IMAGE)).into(bookInfoBinding.cover);

        String unknown = getString(R.string.unknown);

        bookInfoBinding.bookTitle.setText(metadata.containsKey(BookMetadata.TITLE) ? metadata.getString(BookMetadata.TITLE) : unknown);
        bookInfoBinding.contentBookInfo.publishedDate.append(metadata.containsKey(BookMetadata.PUBLISHED_DATE) ? metadata.getString(BookMetadata.PUBLISHED_DATE) : unknown);
        bookInfoBinding.contentBookInfo.publisher.append(metadata.containsKey(BookMetadata.PUBLISHER) ? metadata.getString(BookMetadata.PUBLISHER) : unknown);
        if (metadata.containsKey(BookMetadata.AUTHORS)) {
            String author = "\n";
            String[] authors = metadata.getStringArray(BookMetadata.AUTHORS);
            for (int i = 0; i < authors.length; i++) {
                String singleAuthor = authors[i];
                if (TextUtils.isEmpty(singleAuthor)) {
                    continue;
                }
                author += singleAuthor.concat(i == authors.length - 1 ? "" : "\n");
                bookInfoBinding.contentBookInfo.author.append(author);
            }
        }
        if (metadata.containsKey(BookMetadata.SUBTITLE)) {
            bookInfoBinding.subtitle.setText(metadata.getString(BookMetadata.SUBTITLE));
        } else {
            bookInfoBinding.subtitle.setVisibility(View.GONE);
        }
        if (metadata.containsKey(BookMetadata.DESCRIPTION)) {
            bookInfoBinding.contentBookInfo.description.setText(metadata.getString(BookMetadata.DESCRIPTION));
        }
    }

}
