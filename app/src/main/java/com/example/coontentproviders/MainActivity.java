package com.example.coontentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<User> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);

        permissioncheck();

        inflateLayout();
    }

    private void inflateLayout() {


        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER};

        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(uri,projection,null,null,null);

        arrayList.clear();
        while(cursor.moveToNext())
        {
            String Name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(new User(Name,phone));

        }

        MyAdapter adapter=new MyAdapter(this,arrayList);
        listView.setAdapter(adapter);
    }

    private void permissioncheck() {

        if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.M)
        {
            return ;
        }
        else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
            }
            else
            {
                inflateLayout();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                inflateLayout();
            }
            else
            {
                Toast.makeText(this, "permission denied... please grant permissions for the smooth running of the application", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
