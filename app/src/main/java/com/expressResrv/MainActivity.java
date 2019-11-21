package com.expressResrv;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String jsonURL = "https://gist.github.com/thomashaldorsen/346f3106a3ac57ceb6121373a34ce989/raw/358002714d096fa6a4102f6c0644b9f98df9a279/rom.json";
    private final int jsoncode = 1;
    private ListView listView;
    private RoomAdapter roomAdapter;
    ArrayList<RoomModel> roomModelArrayList;


    private static ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.romListView);


        fetchJSON();

    }

    // Åpner TimeActivity              
    private void OpenTimeActivity() {
        Intent intent = new Intent(this, TimeActivity.class);
        startActivity(intent);
    }




    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        //Melding som vises mens HttpRequest avventer tilbakemelding fra API - linje 180.

        showSimpleProgressDialog(this, "Loading...","Loading..",false);

        // AsyncTask da vi skal kjøre en prosess i bakgrunnen som oppdaterer grensesnittet.
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    //Instansiering av HttpRequest-klasse med jsonURL som parameter.
                    HttpRequest req = new HttpRequest(jsonURL);
                    //Tilbakemelding fra HttpRequest-klasse.
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;

            }

            //resonse fra API motatt og sendes til onPostExecute
            protected void onPostExecute(String result) {
                //kaller på onTaskCompleted.
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }



    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    //Da responsen er motatt lukkes loading-melding som ble opprettet ved linje 46.
                    removeSimpleProgressDialog();
                    //Data er legges i arrayList, se linje 92.
                    roomModelArrayList = getInfo(response);
                    //Instansiering av HttpRequest-klasse med jsonURL som parameter.
                    roomAdapter = new RoomAdapter(this, roomModelArrayList);
                    listView.setAdapter(roomAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                            RoomModel room = (RoomModel) adapter.getItemAtPosition(i);
                            OpenTimeActivity();
                        }
                    });

                }else {
                    //Ved feil sendes feilmelding fra linje 146 - og vises som toast-melding.
                    Toast.makeText(MainActivity.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + serviceCode);
        }
    }

    public ArrayList<RoomModel> getInfo(String response) {
        //ArrayList hvor alle tilgjengelige rom vil legges.
        ArrayList<RoomModel> roomModelArrayList = new ArrayList<>();
        try {
            //Instansiering av JSONObject
            JSONObject jsonObject = new JSONObject(response);

            //Sjekker JSON-etter status == true, som man ser på http://84.208.73.193/API/Test.json
            if (jsonObject.getString("status").equals("true")) {


                JSONArray dataArray = jsonObject.getJSONArray("data");


                //Løkke som oppretter nye objekter fra JasonObject til klasse RoomModel.
                for (int i = 0; i < dataArray.length(); i++) {

                    RoomModel roomsModel = new RoomModel();

                    //JasonObjekt tilknyttes setters i RoomModel-klasse.
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    roomsModel.setName(dataobj.getString("name"));
                    roomsModel.setRoomnumber(dataobj.getString("roomnumber"));
                    roomsModel.setDescription(dataobj.getString("description"));
                    roomModelArrayList.add(roomsModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roomModelArrayList;
    }

    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("true")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    //fjerner loading-dialog.
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Viser loading-dialog.
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
