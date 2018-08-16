package com.example.user.transroads;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class AddNewPost extends AppCompatActivity  implements View.OnClickListener{

    private ImageView imageView;
    private Bitmap bitmap;
    TextView cancel, camera, gallery;
    ImageView bus, motor, car,bike,train,plane, addImage;
    private static  final int GALLERY_REQUEST=1;
    private Uri mImageUri = null;
    protected static final int CAMERA_REQUEST = 0;
    String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);
         imageView = findViewById(R.id.ss);
         cancel = (TextView)findViewById(R.id.cancel);

         bus = (ImageView)findViewById(R.id.bus);
         motor = (ImageView)findViewById(R.id.motor);
         car = (ImageView)findViewById(R.id.car);
         bike = (ImageView)findViewById(R.id.bike);
         train = (ImageView)findViewById(R.id.train);
         plane = (ImageView)findViewById(R.id.plane);
         bus.setOnClickListener(this);
         motor.setOnClickListener(this);
         car.setOnClickListener(this);
         train.setOnClickListener(this);
         bike.setOnClickListener(this);
         plane.setOnClickListener(this);
         addImage = (ImageView)findViewById(R.id.addImage);

         addImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                /* Intent galleryIntent = new Intent(
                         Intent.ACTION_PICK,
                         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(galleryIntent ,GALLERY_REQUEST );*/

                 final Dialog dialog = new Dialog(AddNewPost.this);
                 dialog.setContentView(R.layout.add_image_dialog);
                 dialog.setCancelable(true);
                 dialog.show();

                 camera = (TextView)dialog.findViewById(R.id.camera);
                 gallery = (TextView)dialog.findViewById(R.id.gallery);

                 camera.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent = new Intent(
                                 MediaStore.ACTION_IMAGE_CAPTURE);
                         File f = new File(android.os.Environment
                                 .getExternalStorageDirectory(), "temp.jpg");
                         intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                 Uri.fromFile(f));

                         startActivityForResult(intent,
                                 CAMERA_REQUEST);
                         dialog.dismiss();
                     }

                 });

                 gallery.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent pictureActionIntent = null;

                         pictureActionIntent = new Intent(
                                 Intent.ACTION_PICK,
                                 android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(
                                 pictureActionIntent,
                                 GALLERY_REQUEST);
                         dialog.dismiss();
                     }
                 });

             }
         });


         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     /*   if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();
                addImage.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/


        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(getBaseContext(),

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                addImage.setImageBitmap(bitmap);
                storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

               /* if (selectedImagePath != null) {
                    txt_image_path.setText(selectedImagePath);
                }*/

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);



                addImage.setImageBitmap(bitmap);
                storeImageTosdCard(bitmap);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void storeImageTosdCard(Bitmap processedBitmap) {
        try {
            // TODO Auto-generated method stub

            OutputStream output;
            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();
            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath() + "/Transroads/");
            dir.mkdirs();

            String imge_name = "Transroads" + System.currentTimeMillis()
                    + ".jpg";
            // Create a name for the saved image
            File file = new File(dir, imge_name);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();

            }

            try {

                output = new FileOutputStream(file);

                // Compress into png format image from 0% - 100%
                processedBitmap
                        .compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                int file_size = Integer
                        .parseInt(String.valueOf(file.length() / 1024));
                System.out.println("size ===>>> " + file_size);
                System.out.println("file.length() ===>>> " + file.length());

                selectedImagePath = file.getAbsolutePath();



            }

            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bus: {

                bus.setBackgroundResource(R.drawable.icon_background_grey);
                bus.setImageResource(R.drawable.ic_bus_white);
                motor.setBackgroundResource(R.drawable.icon_background);
                motor.setImageResource(R.drawable.ic_motorbike);
                car.setBackgroundResource(R.drawable.icon_background);
                car.setImageResource(R.drawable.ic_car);
                bike.setBackgroundResource(R.drawable.icon_background);
                bike.setImageResource(R.drawable.ic_bike);
                train.setBackgroundResource(R.drawable.icon_background);
                train.setImageResource(R.drawable.ic_train);
                plane.setBackgroundResource(R.drawable.icon_background);
                plane.setImageResource(R.drawable.ic_plane);

                break;
            }
            case R.id.motor: {
                bus.setBackgroundResource(R.drawable.icon_background);
                bus.setImageResource(R.drawable.ic_bus);
                motor.setBackgroundResource(R.drawable.icon_background_grey);
                motor.setImageResource(R.drawable.ic_motorbike_white);
                car.setBackgroundResource(R.drawable.icon_background);
                car.setImageResource(R.drawable.ic_car);
                bike.setBackgroundResource(R.drawable.icon_background);
                bike.setImageResource(R.drawable.ic_bike);
                train.setBackgroundResource(R.drawable.icon_background);
                train.setImageResource(R.drawable.ic_train);
                plane.setBackgroundResource(R.drawable.icon_background);
                plane.setImageResource(R.drawable.ic_plane);

                break;
            }
            case R.id.car: {
                bus.setBackgroundResource(R.drawable.icon_background);
                bus.setImageResource(R.drawable.ic_bus);
                motor.setBackgroundResource(R.drawable.icon_background);
                motor.setImageResource(R.drawable.ic_motorbike);
                car.setBackgroundResource(R.drawable.icon_background_grey);
                car.setImageResource(R.drawable.ic_car_white);
                bike.setBackgroundResource(R.drawable.icon_background);
                bike.setImageResource(R.drawable.ic_bike);
                train.setBackgroundResource(R.drawable.icon_background);
                train.setImageResource(R.drawable.ic_train);
                plane.setBackgroundResource(R.drawable.icon_background);
                plane.setImageResource(R.drawable.ic_plane);

                break;
            }
            case R.id.bike: {

                bus.setBackgroundResource(R.drawable.icon_background);
                bus.setImageResource(R.drawable.ic_bus);
                motor.setBackgroundResource(R.drawable.icon_background);
                motor.setImageResource(R.drawable.ic_motorbike);
                car.setBackgroundResource(R.drawable.icon_background);
                car.setImageResource(R.drawable.ic_car);
                bike.setBackgroundResource(R.drawable.icon_background_grey);
                bike.setImageResource(R.drawable.ic_bike_white);
                train.setBackgroundResource(R.drawable.icon_background);
                train.setImageResource(R.drawable.ic_train);
                plane.setBackgroundResource(R.drawable.icon_background);
                plane.setImageResource(R.drawable.ic_plane);

                break;

            }
            case R.id.train: {
                bus.setBackgroundResource(R.drawable.icon_background);
                bus.setImageResource(R.drawable.ic_bus);
                motor.setBackgroundResource(R.drawable.icon_background);
                motor.setImageResource(R.drawable.ic_motorbike);
                car.setBackgroundResource(R.drawable.icon_background);
                car.setImageResource(R.drawable.ic_car);
                bike.setBackgroundResource(R.drawable.icon_background);
                bike.setImageResource(R.drawable.ic_bike);
                train.setBackgroundResource(R.drawable.icon_background_grey);
                train.setImageResource(R.drawable.ic_train_white);
                plane.setBackgroundResource(R.drawable.icon_background);
                plane.setImageResource(R.drawable.ic_plane);

                break;
            }

            case R.id.plane: {
                bus.setBackgroundResource(R.drawable.icon_background);
                bus.setImageResource(R.drawable.ic_bus);
                motor.setBackgroundResource(R.drawable.icon_background);
                motor.setImageResource(R.drawable.ic_motorbike);
                car.setBackgroundResource(R.drawable.icon_background);
                car.setImageResource(R.drawable.ic_car);
                bike.setBackgroundResource(R.drawable.icon_background);
                bike.setImageResource(R.drawable.ic_bike);
                train.setBackgroundResource(R.drawable.icon_background);
                train.setImageResource(R.drawable.ic_train);
                plane.setBackgroundResource(R.drawable.icon_background_grey);
                plane.setImageResource(R.drawable.ic_plane_white);

                break;
            }
        }

    }
}
