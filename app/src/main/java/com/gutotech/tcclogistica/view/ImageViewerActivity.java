package com.gutotech.tcclogistica.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.Storage;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ImageView imageView = findViewById(R.id.imageView);
            Storage.downloadProfile(this, imageView, bundle.getString("image"));
        }
    }
}
