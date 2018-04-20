package com.zhiliang.counter.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.PermissionUtils;
import com.zhiliang.counter.R;

public class MyInfoActivity extends BaseActivity {
    private ImageView mImageView;
    private Button mButton;
    private final int REQUEST_GALLERY = 1;
    private final int mPermissionRequestFotIntenet = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        mImageView = findViewById(R.id.ic_header);
        mButton = findViewById(R.id.tv_change_header);

        mButton.setOnClickListener(getChangeHeadOnClickListener());
    }

    private View.OnClickListener getChangeHeadOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startImagePickActivity();
                } else {
                    ActivityCompat.requestPermissions(MyInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, mPermissionRequestFotIntenet);
                }
            }
        };
    }

    private void startImagePickActivity() {
        Intent toGallery = new Intent(Intent.ACTION_GET_CONTENT);
        toGallery.setType("image/*");
        toGallery.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(toGallery, REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mPermissionRequestFotIntenet) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImagePickActivity();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY) {
            if (resultCode == RESULT_OK && !TextUtils.isEmpty(data.getDataString())) {

            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
