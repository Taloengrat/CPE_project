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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CsvImportAdapter extends RecyclerView.Adapter<CsvImportAdapter.CsvViewHolder> {

    private List<Csv> csvList;
    private Activity activity;
    private String[] nextLine;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_import, parent, false);
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


                    progressDialog = ProgressDialog.show(activity, "Import mission", "Loading...", true, false);

                    startThread();


                }

                private void SwitchImport(int numberstep) throws FileNotFoundException {

                    String createFolder = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/" + txName.getText();
                    File file = new File(createFolder);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    Mission mission = new Mission();
                    mission.setMissionName(nextLine[0]);
                    mission.setDetailMission(nextLine[1]);
                    mission.setAge(Integer.valueOf(nextLine[2]));
                    mission.setNumberofMission(Integer.valueOf(nextLine[3]));

                    String path = Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/" + txName.getText();


                    File image1 = new File(path, "picture1.png");
                    File image2 = new File(path, "picture2.png");
                    File image3 = new File(path, "picture3.png");
                    File image4 = new File(path, "picture4.png");
                    File image5 = new File(path, "picture5.png");


                    Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(image1));
                    Bitmap bitmap2 = BitmapFactory.decodeStream(new FileInputStream(image2));
                    Bitmap bitmap3 = BitmapFactory.decodeStream(new FileInputStream(image3));
                    Bitmap bitmap4 = BitmapFactory.decodeStream(new FileInputStream(image4));
                    Bitmap bitmap5 = BitmapFactory.decodeStream(new FileInputStream(image5));


                    switch (numberstep) {

                        case 5:
                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());


                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);


                            mission.setA1(nextLine[9]);
                            mission.setA2(nextLine[10]);
                            mission.setA3(nextLine[11]);
                            mission.setA4(nextLine[12]);
                            mission.setA5(nextLine[13]);

                            mission.setS1(nextLine[14]);
                            mission.setS2(nextLine[15]);
                            mission.setS3(nextLine[16]);
                            mission.setS4(nextLine[17]);
                            mission.setS5(nextLine[18]);

                            mission.setH1(nextLine[19]);
                            mission.setH2(nextLine[20]);
                            mission.setH3(nextLine[21]);
                            mission.setH4(nextLine[22]);
                            mission.setH5(nextLine[23]);


                            mission.setTime(nextLine[24]);
                            mission.setTimeDeduction(nextLine[25]);


                            break;
                        case 6:

                            File image6 = new File(path, "picture6.png");

                            Bitmap bitmap6 = BitmapFactory.decodeStream(new FileInputStream(image6));

                            ////////
                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());
                            saveToInternalStorage(bitmap6, "picture6", txName.getText().toString());

                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);
                            mission.setQ6(nextLine[9]);


                            mission.setA1(nextLine[10]);
                            mission.setA2(nextLine[11]);
                            mission.setA3(nextLine[12]);
                            mission.setA4(nextLine[13]);
                            mission.setA5(nextLine[14]);
                            mission.setA6(nextLine[15]);


                            mission.setS1(nextLine[16]);
                            mission.setS2(nextLine[17]);
                            mission.setS3(nextLine[18]);
                            mission.setS4(nextLine[19]);
                            mission.setS5(nextLine[20]);
                            mission.setS6(nextLine[21]);


                            mission.setH1(nextLine[22]);
                            mission.setH2(nextLine[23]);
                            mission.setH3(nextLine[24]);
                            mission.setH4(nextLine[25]);
                            mission.setH5(nextLine[26]);
                            mission.setH6(nextLine[27]);

                            mission.setTime(nextLine[28]);
                            mission.setTimeDeduction(nextLine[29]);

                            break;
                        case 7:
                            File image6new = new File(path, "picture6.png");
                            File image7 = new File(path, "picture7.png");


                            Bitmap bitmap6new = BitmapFactory.decodeStream(new FileInputStream(image6new));
                            Bitmap bitmap7 = BitmapFactory.decodeStream(new FileInputStream(image7));

                            ////////

                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());
                            saveToInternalStorage(bitmap6new, "picture6", txName.getText().toString());
                            saveToInternalStorage(bitmap7, "picture7", txName.getText().toString());

                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);
                            mission.setQ6(nextLine[9]);
                            mission.setQ7(nextLine[10]);


                            mission.setA1(nextLine[11]);
                            mission.setA2(nextLine[12]);
                            mission.setA3(nextLine[13]);
                            mission.setA4(nextLine[14]);
                            mission.setA5(nextLine[15]);
                            mission.setA6(nextLine[16]);
                            mission.setA7(nextLine[17]);


                            mission.setS1(nextLine[18]);
                            mission.setS2(nextLine[19]);
                            mission.setS3(nextLine[20]);
                            mission.setS4(nextLine[21]);
                            mission.setS5(nextLine[22]);
                            mission.setS6(nextLine[23]);
                            mission.setS7(nextLine[24]);


                            mission.setH1(nextLine[25]);
                            mission.setH2(nextLine[26]);
                            mission.setH3(nextLine[27]);
                            mission.setH4(nextLine[28]);
                            mission.setH5(nextLine[29]);
                            mission.setH6(nextLine[30]);
                            mission.setH7(nextLine[31]);

                            mission.setTime(nextLine[32]);
                            mission.setTimeDeduction(nextLine[33]);

                            break;
                        case 8:

                            File image6new2 = new File(path, "picture6.png");
                            File image7new = new File(path, "picture7.png");
                            File image8 = new File(path, "picture8.png");


                            Bitmap bitmap6new2 = BitmapFactory.decodeStream(new FileInputStream(image6new2));
                            Bitmap bitmap7new = BitmapFactory.decodeStream(new FileInputStream(image7new));
                            Bitmap bitmap8 = BitmapFactory.decodeStream(new FileInputStream(image8));

                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());
                            saveToInternalStorage(bitmap6new2, "picture6", txName.getText().toString());
                            saveToInternalStorage(bitmap7new, "picture7", txName.getText().toString());
                            saveToInternalStorage(bitmap8, "picture8", txName.getText().toString());

                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);
                            mission.setQ6(nextLine[9]);
                            mission.setQ7(nextLine[10]);
                            mission.setQ8(nextLine[11]);


                            mission.setA1(nextLine[12]);
                            mission.setA2(nextLine[13]);
                            mission.setA3(nextLine[14]);
                            mission.setA4(nextLine[15]);
                            mission.setA5(nextLine[16]);
                            mission.setA6(nextLine[17]);
                            mission.setA7(nextLine[18]);
                            mission.setA8(nextLine[19]);


                            mission.setS1(nextLine[20]);
                            mission.setS2(nextLine[21]);
                            mission.setS3(nextLine[22]);
                            mission.setS4(nextLine[23]);
                            mission.setS5(nextLine[24]);
                            mission.setS6(nextLine[25]);
                            mission.setS7(nextLine[26]);
                            mission.setS8(nextLine[27]);


                            mission.setH1(nextLine[28]);
                            mission.setH2(nextLine[29]);
                            mission.setH3(nextLine[30]);
                            mission.setH4(nextLine[31]);
                            mission.setH5(nextLine[32]);
                            mission.setH6(nextLine[33]);
                            mission.setH7(nextLine[34]);
                            mission.setS8(nextLine[35]);

                            mission.setTime(nextLine[36]);
                            mission.setTimeDeduction(nextLine[37]);
                            break;
                        case 9:

                            File image6new3 = new File(path, "picture6.png");
                            File image7new2 = new File(path, "picture7.png");
                            File image8new = new File(path, "picture8.png");
                            File image9 = new File(path, "picture9.png");


                            Bitmap bitmap6new3 = BitmapFactory.decodeStream(new FileInputStream(image6new3));
                            Bitmap bitmap7new2 = BitmapFactory.decodeStream(new FileInputStream(image7new2));
                            Bitmap bitmap8new = BitmapFactory.decodeStream(new FileInputStream(image8new));
                            Bitmap bitmap9 = BitmapFactory.decodeStream(new FileInputStream(image9));
                            /////////////////
                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());
                            saveToInternalStorage(bitmap6new3, "picture6", txName.getText().toString());
                            saveToInternalStorage(bitmap7new2, "picture7", txName.getText().toString());
                            saveToInternalStorage(bitmap8new, "picture8", txName.getText().toString());
                            saveToInternalStorage(bitmap9, "picture9", txName.getText().toString());

                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);
                            mission.setQ6(nextLine[9]);
                            mission.setQ7(nextLine[10]);
                            mission.setQ8(nextLine[11]);
                            mission.setQ9(nextLine[12]);


                            mission.setA1(nextLine[13]);
                            mission.setA2(nextLine[14]);
                            mission.setA3(nextLine[15]);
                            mission.setA4(nextLine[16]);
                            mission.setA5(nextLine[17]);
                            mission.setA6(nextLine[18]);
                            mission.setA7(nextLine[19]);
                            mission.setA8(nextLine[20]);
                            mission.setA9(nextLine[21]);


                            mission.setS1(nextLine[22]);
                            mission.setS2(nextLine[23]);
                            mission.setS3(nextLine[24]);
                            mission.setS4(nextLine[25]);
                            mission.setS5(nextLine[26]);
                            mission.setS6(nextLine[27]);
                            mission.setS7(nextLine[28]);
                            mission.setS8(nextLine[29]);
                            mission.setS9(nextLine[30]);


                            mission.setH1(nextLine[31]);
                            mission.setH2(nextLine[32]);
                            mission.setH3(nextLine[33]);
                            mission.setH4(nextLine[34]);
                            mission.setH5(nextLine[35]);
                            mission.setH6(nextLine[36]);
                            mission.setH7(nextLine[37]);
                            mission.setS8(nextLine[38]);
                            mission.setH9(nextLine[39]);

                            mission.setTime(nextLine[40]);
                            mission.setTimeDeduction(nextLine[41]);
                            break;
                        case 10:

                            File image6new4 = new File(path, "picture6.png");
                            File image7new3 = new File(path, "picture7.png");
                            File image8new2 = new File(path, "picture8.png");
                            File image9new = new File(path, "picture9.png");
                            File image10 = new File(path, "picture10.png");


                            Bitmap bitmap6new4 = BitmapFactory.decodeStream(new FileInputStream(image6new4));
                            Bitmap bitmap7new3 = BitmapFactory.decodeStream(new FileInputStream(image7new3));
                            Bitmap bitmap8new2 = BitmapFactory.decodeStream(new FileInputStream(image8new2));
                            Bitmap bitmap9new = BitmapFactory.decodeStream(new FileInputStream(image9new));
                            Bitmap bitmap10 = BitmapFactory.decodeStream(new FileInputStream(image10));

                            saveToInternalStorage(bitmap1, "picture1", txName.getText().toString());
                            saveToInternalStorage(bitmap2, "picture2", txName.getText().toString());
                            saveToInternalStorage(bitmap3, "picture3", txName.getText().toString());
                            saveToInternalStorage(bitmap4, "picture4", txName.getText().toString());
                            saveToInternalStorage(bitmap5, "picture5", txName.getText().toString());
                            saveToInternalStorage(bitmap6new4, "picture6", txName.getText().toString());
                            saveToInternalStorage(bitmap7new3, "picture7", txName.getText().toString());
                            saveToInternalStorage(bitmap8new2, "picture8", txName.getText().toString());
                            saveToInternalStorage(bitmap9new, "picture9", txName.getText().toString());
                            saveToInternalStorage(bitmap10, "picture10", txName.getText().toString());

                            mission.setQ1(nextLine[4]);
                            mission.setQ2(nextLine[5]);
                            mission.setQ3(nextLine[6]);
                            mission.setQ4(nextLine[7]);
                            mission.setQ5(nextLine[8]);
                            mission.setQ6(nextLine[9]);
                            mission.setQ7(nextLine[10]);
                            mission.setQ8(nextLine[11]);
                            mission.setQ9(nextLine[12]);
                            mission.setQ10(nextLine[13]);


                            mission.setA1(nextLine[14]);
                            mission.setA2(nextLine[15]);
                            mission.setA3(nextLine[16]);
                            mission.setA4(nextLine[17]);
                            mission.setA5(nextLine[18]);
                            mission.setA6(nextLine[19]);
                            mission.setA7(nextLine[20]);
                            mission.setA8(nextLine[21]);
                            mission.setA9(nextLine[22]);
                            mission.setA10(nextLine[23]);

                            mission.setS1(nextLine[24]);
                            mission.setS2(nextLine[25]);
                            mission.setS3(nextLine[26]);
                            mission.setS4(nextLine[27]);
                            mission.setS5(nextLine[28]);
                            mission.setS6(nextLine[29]);
                            mission.setS7(nextLine[30]);
                            mission.setS8(nextLine[31]);
                            mission.setS9(nextLine[32]);
                            mission.setS10(nextLine[33]);

                            mission.setH1(nextLine[34]);
                            mission.setH2(nextLine[35]);
                            mission.setH3(nextLine[36]);
                            mission.setH4(nextLine[37]);
                            mission.setH5(nextLine[38]);
                            mission.setH6(nextLine[39]);
                            mission.setH7(nextLine[40]);
                            mission.setH8(nextLine[41]);
                            mission.setH9(nextLine[42]);
                            mission.setH10(nextLine[43]);

                            mission.setTime(nextLine[44]);
                            mission.setTimeDeduction(nextLine[45]);

                            break;
                    }

                    MissionDATABASE.getInstance(activity).missionDAO().create(mission);
                    stopThread = true;
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

                        String path = Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/" + txName.getText() + "/" + txName.getText() + "Data.csv";


                        try {
                            File csvfile = new File(path);
                            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));

                            while ((nextLine = reader.readNext()) != null) {
                                // nextLine[] is an array of values from the line

                                if (reader.getLinesRead() == 2) {
                                    Log.v("Lineeeeeeeee", String.valueOf(reader.getLinesRead()));

                                    SwitchImport(Integer.valueOf(nextLine[3]));
                                }


                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        }


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
        File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionName + "/");
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
