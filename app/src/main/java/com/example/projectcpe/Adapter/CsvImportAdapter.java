package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.CreateMission.Export.ExportOnDevice;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Csv;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CsvImportAdapter extends RecyclerView.Adapter<CsvImportAdapter.CsvViewHolder>{

    private List<Csv> csvList;
    private Activity activity;
    private String[] nextLine ;
    private Mission missionImport;
    private ProgressDialog progressDialog;
    private volatile boolean stopThread = false;

    public CsvImportAdapter(List<Csv> list, Activity activity) {
        this.csvList = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CsvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_import, parent,false);
        return new CsvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CsvViewHolder holder, int position) {
        final Csv Document = csvList.get(position);

        holder.txName.setText(Document.getCsvName());

    }

    @Override
    public int getItemCount() {
        return csvList.size();
    }

    public class CsvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txName, txDate;
        public CsvViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txName = itemView.findViewById(R.id.csvName);
            txDate = itemView.findViewById(R.id.csvDate);
        }

        @Override
        public void onClick(View view) {

//            Toast.makeText(activity, txName.getText(),Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle("Import Mission On Device");
            dialog.setCancelable(true);
            dialog.setMessage("Do you want to import this mission?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    String path = Environment.getExternalStorageDirectory().getPath()+"/MyMissionExport/"+txName.getText()+"/"+txName.getText()+"Data.csv";


                    try {
                        File csvfile = new File(path);
                        CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));

                        while ((nextLine = reader.readNext()) != null) {
                            // nextLine[] is an array of values from the line

                            if (reader.getLinesRead() == 2 ){
                                Log.v("Lineeeeeeeee",String.valueOf(reader.getLinesRead()));

                                SwitchImport(Integer.valueOf(nextLine[3]), path);
                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "The specified file was not found", Toast.LENGTH_SHORT).show();
                    }


//                    progressDialog  = ProgressDialog.show(activity, "Import mission", "Loading...", true, false);

//                    startThread();


                }

                private void SwitchImport(int numberstep, String filePath) {
                    String path = Environment.getExternalStorageDirectory().getPath()+"/MyMissionExport/"+txName.getText();

                    File image1 = new File(filePath, "picture1.png");
                    File image2 = new File(filePath, "picture2.png");
                    File image3 = new File(filePath, "picture3.png");
                    File image4 = new File(filePath, "picture4.png");
                    File image5 = new File(filePath, "picture5.png");
                    File image6 = new File(filePath, "picture6.png");
                    File image7 = new File(filePath, "picture7.png");
                    File image8 = new File(filePath, "picture8.png");
                    File image9 = new File(filePath, "picture9.png");
                    File image10 = new File(filePath, "picture10.png");

                    BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions2 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions3 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions4 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions5 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions6 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions7 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions8 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions9 = new BitmapFactory.Options();
                    BitmapFactory.Options bmOptions10 = new BitmapFactory.Options();



                    Bitmap bitmap1 = BitmapFactory.decodeFile(image1.getAbsolutePath(),bmOptions1);
                    Bitmap bitmap2 = BitmapFactory.decodeFile(image2.getAbsolutePath(),bmOptions2);
                    Bitmap bitmap3 = BitmapFactory.decodeFile(image3.getAbsolutePath(),bmOptions3);
                    Bitmap bitmap4 = BitmapFactory.decodeFile(image4.getAbsolutePath(),bmOptions4);
                    Bitmap bitmap5 = BitmapFactory.decodeFile(image5.getAbsolutePath(),bmOptions5);
                    Bitmap bitmap6 = BitmapFactory.decodeFile(image6.getAbsolutePath(),bmOptions6);
                    Bitmap bitmap7 = BitmapFactory.decodeFile(image7.getAbsolutePath(),bmOptions7);
                    Bitmap bitmap8 = BitmapFactory.decodeFile(image8.getAbsolutePath(),bmOptions8);
                    Bitmap bitmap9 = BitmapFactory.decodeFile(image9.getAbsolutePath(),bmOptions9);
                    Bitmap bitmap10 = BitmapFactory.decodeFile(image10.getAbsolutePath(),bmOptions10);

                    switch (numberstep){
                        case 5 :
                            break;
                        case 6 :
                            break;
                        case 7 :
                            break;
                        case 8 :
                            break;
                        case 9 :
                            break;
                        case 10 :




                            saveToInternalStorage(bitmap1, "picture1", "Animal");
                            saveToInternalStorage(bitmap2, "picture2", "Animal");
                            saveToInternalStorage(bitmap3, "picture3", "Animal");
                            saveToInternalStorage(bitmap4, "picture4", "Animal");
                            saveToInternalStorage(bitmap5, "picture5", "Animal");
                            saveToInternalStorage(bitmap6, "picture 6", "Animal");
                            saveToInternalStorage(bitmap7, "picture7", "Animal");
                            saveToInternalStorage(bitmap8, "picture8", "Animal");
                            saveToInternalStorage(bitmap9, "picture9", "Animal");
                            saveToInternalStorage(bitmap10, "picture10", "Animal");
                            break;
                    }
                }

                private void startThread() {

                        stopThread = false;
                        ExampleRunnable runnable = new ExampleRunnable();
                        new Thread(runnable).start();


                }

                class ExampleRunnable implements Runnable {



                    ExampleRunnable() {

                    }

                    @Override
                    public void run() {


//                        SwitchAddListString(missionList.get(0).getNumberofMission());

                        if (stopThread) {


                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {




                                    progressDialog.dismiss();
//                                    recreate();



                                }
                            });
                        }


                    }



                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            dialog.show();


        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String picturename, String missionName) {
        ContextWrapper cw = new ContextWrapper(activity);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(Environment.getExternalStorageDirectory() + "/MyMission/" + missionName + "/");
        // Create imageDir
        File mypath = new File(directory, picturename + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 30, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
