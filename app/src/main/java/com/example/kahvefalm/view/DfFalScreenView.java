package com.example.kahvefalm.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kahvefalm.R;
import com.example.kahvefalm.controllers.DfFalScreenController;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class DfFalScreenView implements View.OnClickListener  {
    //İnitialize recomended fields
    private View rootView;
    private DfFalScreenController dfFalScreenController;

    //View Object
    Button sendBtn;
    MaterialToolbar toolbar;
    ChipGroup chipGroup;
    Chip[] chips;
    ImageView[] photoHolders;
    EditText messageEditText;
    ProgressBar progressBar;

    //Dialog Box
    MaterialAlertDialogBuilder networkDialog;
    MaterialAlertDialogBuilder photoDialog;


    public DfFalScreenView(Context context, ViewGroup viewGroup){

        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fal,viewGroup);
        dfFalScreenController = new DfFalScreenController(this);

    }

    public void initViews() {
        //Setup Views

        sendBtn = (Button)rootView.findViewById(R.id.button);

        progressBar = (ProgressBar)rootView.findViewById(R.id.ProgresBar);
        progressBar.setVisibility(View.GONE);

        messageEditText = (EditText)rootView.findViewById(R.id.editTextMessage);

        photoHolders = new ImageView[3];
        photoHolders[0] = (ImageView)rootView.findViewById(R.id.imageView1);
        photoHolders[0].setOnClickListener(this);
        photoHolders[1] = (ImageView)rootView.findViewById(R.id.imageView2);
        photoHolders[1].setOnClickListener(this);
        photoHolders[2] = (ImageView)rootView.findViewById(R.id.imageView3);
        photoHolders[2].setOnClickListener(this);


        toolbar = (MaterialToolbar)rootView.findViewById(R.id.toolbarFal);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dfFalScreenController.closeScreen();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dfFalScreenController.sendFal();
            }
        });

        setChips();




    }

    private void setChips(){

        chipGroup = (ChipGroup)rootView.findViewById(R.id.chipGroup);

        chips = new Chip[4];

        chips[0] = (Chip)rootView.findViewById(R.id.chipGenel);
        chips[1] = (Chip)rootView.findViewById(R.id.chipAsk);
        chips[2] = (Chip)rootView.findViewById(R.id.chipPara);
        chips[3] = (Chip)rootView.findViewById(R.id.chipSaglık);
        chips[0].setChecked(true);

        dfFalScreenController.setFalTypeEquivalentChip(new int[]{chips[0].getId(),
                chips[1].getId(),
                chips[2].getId(),
                chips[3].getId()
        });

        dfFalScreenController.setFalTipi(chips[0].getId());


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                dfFalScreenController.searchFalTipi(checkedId);
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageView1 :
                dfFalScreenController.fotoYukle(view.getId());
                break;

            case R.id.imageView2:
                dfFalScreenController.fotoYukle(view.getId());
                break;

            case R.id.imageView3:
                dfFalScreenController.fotoYukle(view.getId());
                break;

        }

    }

    public int setPhotoHolderImage(int id, Bitmap bitmap){

        int whichHolder = 0;

        switch (id){

            case R.id.imageView1:
                photoHolders[0].setImageBitmap(bitmap);
                whichHolder = 0;
                break;

            case R.id.imageView2:
                photoHolders[1].setImageBitmap(bitmap);
                whichHolder = 1;
                break;

            case R.id.imageView3:
                photoHolders[2].setImageBitmap(bitmap);
                whichHolder = 2;
                break;

        }
                return whichHolder;
    }

    public void setProgressIndicatorVisible(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressIndicatorInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void networkErrorDialog(){

        networkDialog = new MaterialAlertDialogBuilder(rootView.getContext());
        networkDialog.setTitle("Hata")
                .setMessage("Uygulamayı kullanabilmeniz için ağ bağlantınızın olması gerekir.")
                .setNeutralButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        networkDialog.show();

    }

    public void photoErrorDialog(){

        photoDialog = new MaterialAlertDialogBuilder(rootView.getContext());
        photoDialog.setTitle("Uyarı")
                .setMessage("Lütfen 3 adet fotoğraf çekiniz.")
                .setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        photoDialog.show();


    }

    public void errorToast(String message){

        Toast.makeText(rootView.getContext(),message,Toast.LENGTH_LONG).show();

    }

    public Size getPhotoHolderSize(){

        int width = photoHolders[0].getDrawable().getIntrinsicWidth();
        int heigth = photoHolders[0].getDrawable().getIntrinsicHeight();

        return new Size(width,heigth);

    }

    public DfFalScreenController getDfFalScreenController() {
        return dfFalScreenController;
    }

    public String getMessageText(){
        return messageEditText.getText().toString();
    }

    public View getRootView() {
        return rootView;
    }


}
