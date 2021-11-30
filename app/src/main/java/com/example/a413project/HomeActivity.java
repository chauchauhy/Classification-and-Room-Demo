package com.example.a413project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.a413project.Adapter.HomePageAdapter;
import com.example.a413project.Classifies.Classifier;
import com.example.a413project.Database.DataBase;
import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.DataList;
import com.example.a413project.Database.model.Label;
import com.facebook.stetho.Stetho;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Context context;
    final int TAKE_CARMA_CODE = 0;
    final int TAKE_PICTURE = 1;
    final int PERMISSION_ID = 2;
    Uri image_uri;
    DataList dataList;
    HomePageAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        dataList = new DataList(context);
    }



    private void callClassification(Bitmap bitmap){
        try {
            Classifier classifier = new Classifier(getAssets(), 224);
            List<Label> result = classifier.recognizeImage(bitmap);
            for (Label l : result){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createImageSelection(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final CharSequence[] options = {"New Picture", "Select picture", "Cancel"};
        final String[] option = {"New Picture", "Select picture", "Cancel"};
        builder.setTitle("Select options ...").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals(option[0])) {
                    // dismiss the dialog and open gallery for selecting image
                    dialogInterface.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_CARMA_CODE);
                } else if (options[i].equals(option[1])) {
                    // disappear the dialog and open camera
                    dialogInterface.dismiss();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Please select a image from gallary"), TAKE_PICTURE);
                } else if (options[i].equals(option[2])) {
                    dialogInterface.dismiss();
                }
            }
        }).setCancelable(true).create().show();
    }

    public boolean permissionAvailable(String... permissionIDs){
        // check permission is granted or not;
        boolean result = true;
        for (String id : permissionIDs){
            if (ActivityCompat.checkSelfPermission(context, id)== PackageManager.PERMISSION_DENIED){
                result = false;
            }
        }
        return result;
    }

    public void permissionsAction(String[] permissionIDs){
        // shows dialog to request permission
        ActivityCompat.requestPermissions(this, permissionIDs, PERMISSION_ID);
    }

    private void initUI(){
        context = this;
        recyclerView = findViewById(R.id.recyclerViewHomePage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE && data !=null && data.getData() !=null) {
            image_uri = data.getData();
            Log.i("ACTIVIT", image_uri.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                callClassification(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i("ACTIVIT", "ERROR");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addNewPic:
                if(permissionAvailable(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    createImageSelection();
                }else{
                    permissionsAction(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                }
                break;
            case R.id.about_app:
                Toast.makeText(this, "About this app", Toast.LENGTH_LONG).show();
                break;
            case R.id.clearAll:
                dataList.removeAll();
                Toast.makeText(this, "All data have been remove", Toast.LENGTH_LONG).show();
                
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID ){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // nothing to do
            }
        }
    }
}