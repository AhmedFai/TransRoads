package com.example.user.transroads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText txtRegUserName;
    private EditText txtRegEmailAddress;
    private EditText txtRegPassword;
    private EditText txtRegCnfrmPass;
    private FirebaseAuth firebaseAuth;
    private String mUserName, mUserEmail, mEmailPattern, mPassword, mCpassword;
    private ProgressDialog mProgressDialog;
    private ImageButton mImageButton;
    private static  final int GALLERY_REQUEST=1;
    private DatabaseReference mDatabaseUsers;
    private StorageReference mStorage;
    private Uri mImageUri = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mStorage = FirebaseStorage.getInstance().getReference().child("Profile_Photos");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        txtRegUserName = (EditText) findViewById(R.id.regUserName);
        txtRegEmailAddress = (EditText) findViewById(R.id.regEmailAddress);
        txtRegPassword = (EditText) findViewById(R.id.regPassword);
        txtRegCnfrmPass = (EditText) findViewById(R.id.reEnterPass);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent ,GALLERY_REQUEST );
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

       // getSupportActionBar().hide();
        TextView loginText = (TextView)findViewById(R.id.registerLogInText);
        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
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
                mImageButton.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onClick_Registration(View v){

        mUserName = txtRegUserName.getText().toString();
        mUserEmail = txtRegEmailAddress.getText().toString().toLowerCase();
        mEmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        mPassword = txtRegPassword.getText().toString();
        mCpassword = txtRegCnfrmPass.getText().toString();

        if (TextUtils.isEmpty(mUserName)) {
            txtRegUserName.setError("Enter User Name!");
            txtRegUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mUserEmail)) {
            txtRegEmailAddress.setError("Enter Email Address!");
            txtRegEmailAddress.requestFocus();
            return;
        }

        if (!mUserEmail.matches(mEmailPattern))
        {
            txtRegEmailAddress.setError("Invalid Email Address!");
            txtRegEmailAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mPassword)) {
            txtRegPassword.setError("Enter Password!");
            txtRegPassword.requestFocus();
            return;
        }

        if (mPassword.length() < 6) {
            txtRegPassword.setError("Password too short, enter minimum 6 characters!");
            txtRegPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mCpassword)) {
            txtRegCnfrmPass.setError("Enter Password!");
            txtRegCnfrmPass.requestFocus();
            return;
        }

        if(!mPassword.equals(mCpassword)){
            txtRegCnfrmPass.setError("Password is not matching");
            txtRegCnfrmPass.requestFocus();
            return;
        }

        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(mUserEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            final String user_id = firebaseAuth.getCurrentUser().getUid();
                            final DatabaseReference current_user_database = mDatabaseUsers.child(user_id);
                            current_user_database.child("Name").setValue(mUserName);
                            StorageReference filePath = mStorage.child(mImageUri.getLastPathSegment());
                            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String downloadUri = taskSnapshot.getDownloadUrl().toString();
                                    current_user_database.child("Photo").setValue(downloadUri);
                                }
                            });
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
