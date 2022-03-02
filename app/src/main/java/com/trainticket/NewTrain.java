package com.trainticket;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class NewTrain extends Activity {

    DataBase db;
    EditText dtleavedate,dtleavetime,dtarrivetime,trainname,trainclass,quota,price,tbphoto;
    Spinner cbstart,cbend;
    Bitmap TrainImages;
    String currentpictimagepath = null;
    String ImageName = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtrain);

        load_comp();
    }

    private void load_comp(){
        db = new DataBase(this);

        dtleavedate = findViewById(R.id.dtleavedateedit);
        dtleavetime = findViewById(R.id.dtstartedit);
        dtarrivetime = findViewById(R.id.dtendedit);

        cbstart = findViewById(R.id.cbtrainstartedit);
        cbend = findViewById(R.id.cbtrainendedit);

        imageView = findViewById(R.id.trainphoto);
        tbphoto = findViewById(R.id.tbphoto);

        trainname = findViewById(R.id.tbtrainnameedit);
        trainclass = findViewById(R.id.tbtrainclassedit);
        quota = findViewById(R.id.tbquotaedit);
        price = findViewById(R.id.tbpriceedit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DestArray, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbstart.setAdapter(adapter);
        cbend.setAdapter(adapter);
    }

    public void StartClick(View view){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dtleavetime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void EndClick(View view){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dtarrivetime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void SetLeaveDate(View view){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dtleavedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentpictimagepath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void CommitTrain(View view){
        String start,end,leave;
        leave = dtleavedate.getText().toString();

        String statement = "INSERT INTO train (name,origin,destination,timeleave,timearrive,date,carriagename,quota,price,picture) " +
                "VALUES ('"+ trainname.getText().toString() + "','"+ cbstart.getSelectedItem().toString() +"','"+ cbend.getSelectedItem().toString() +"'," +
                "'"+ dtleavetime.getText().toString() +"','" + dtarrivetime.getText().toString() +"','" + dtleavedate.getText().toString() +"'," +
                "'"+ trainclass.getText().toString() +"','"+ quota.getText().toString() +"','"+ price.getText().toString() +"', '" + ImageName + "')";

        if (dtleavetime.getText().toString() != "" && cbstart.getSelectedItem().toString() != "" && cbend.getSelectedItem().toString() != ""
                && trainname.getText().toString() != "" && trainclass.getText().toString() != "" && quota.getText().toString() != "" && price.getText().toString() != ""){
            if (db.ExecDDL(statement) == true){
                db.message(this,"Data has Committed");
                Intent intent = new Intent(this, TrainList.class);
                galleryAddPic();

                startActivity(intent);
            }
            else {
                db.message(this,"Error While Executing Statement");
            }
        }
        else {
            db.message(this,"Please Fill All The Data Before Commit");
        }
    }

    private File SaveImage() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String Image_Form = "img_" + timestamp + "_";
        File Dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File ImageFile = File.createTempFile(Image_Form,".jpg",Dir);

        currentpictimagepath = ImageFile.getAbsolutePath();
        ImageName = ImageFile.getName();
        //db.message(this, ImageFile.getName());
        //db.message(this, ImageFile.getAbsolutePath());
        return ImageFile;
    }

    public void UploadPhoto(View view){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imageFiles = null;

            try {
                imageFiles = SaveImage();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            if (imageFiles != null){
                Uri imageUri = FileProvider.getUriForFile(this, "com.trainticket", imageFiles);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra("data", currentpictimagepath);
            }
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                db.message(this, "camera permission granted");

                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

//                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "TrainImages");
//                imagesFolder.mkdirs();
//
//                File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
//                Uri uriSavedImage = Uri.fromFile(image);

                File imageFiles = null;

                try {
                    imageFiles = SaveImage();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                if (imageFiles != null){
                    Uri imageUri = FileProvider.getUriForFile(this, "com.trainticket", imageFiles);

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                }

                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                startActivityForResult(cameraIntent,1);
            }
            else
            {
                db.message(this, "camera permission denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bundle extras = getIntent().getExtras();
            Bitmap photo = (Bitmap)extras.get("data");
            TrainImages = photo;
            imageView.setImageBitmap(photo);
        }
    }
}
