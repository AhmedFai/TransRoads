package com.example.user.transroads;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddNewPost extends AppCompatActivity  implements View.OnClickListener{

    private ImageView imageView;
    private Bitmap bitmap;
    TextView cancel;
    ImageView bus, motor, car,bike,train,plane, addImage;
    private static  final int GALLERY_REQUEST=1;
    private Uri mImageUri = null;

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
                 Intent galleryIntent = new Intent(
                         Intent.ACTION_PICK,
                         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(galleryIntent ,GALLERY_REQUEST );
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
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

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
