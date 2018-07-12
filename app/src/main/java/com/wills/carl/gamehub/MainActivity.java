package com.wills.carl.gamehub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.carl.gamehub.Util.ParseJson;

import com.android.volley.VolleyError;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.wills.carl.gamehub.model.Game;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 729;
    FirebaseUser user;
    ArrayList<Game> games = new ArrayList<>();

    private final static String API_KEY = BuildConfig.ApiKey;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );


    TextView display;
    EditText edit;
    Button button;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        display = (TextView) findViewById(R.id.display);
        edit = (EditText) findViewById(R.id.edit);
        button= (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Map<String, String> msg = new HashMap<>();
               // msg.put("data", edit.getText().toString());
         //       addMsgToDb(msg);
          //      populateEdit();

                gameSearch(v);

            }
        });






    }

    private void gameSearch(View v) {
        APIWrapper wrapper = new APIWrapper(v.getContext(), API_KEY);


        Parameters params = new Parameters()
                .addFields("id,name,summary,platforms,cover")
                .addFilter("[version_parent][not_exists]=1")
                .addSearch(edit.getText().toString())
                .addExpand("platforms")
                .addLimit("10");
        wrapper.games(params, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                display.setText(jsonArray.toString());
                games =ParseJson.parseGames(jsonArray);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.e("ER", "ERROR "+ volleyError.getMessage());
            }
        });
    }

    public void populateEdit(){
        db.collection("message")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document :task.getResult()) {
                                display.append("\n" + document.getData());
                            }
                        } else {
                            Log.w("DBF", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void addMsgToDb(Map<String, String> msg){
        db.collection("message")
                .add(msg)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DBS ", "DocumentSnapshot added with ID: "+ documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DBF ", "Error adding document", e);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        doAuth();
    }

    private void doAuth() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivityForResult(
                    AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
            }
            else {
                Log.e("AUTH ERROR: ", response.getError().getMessage());
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.log_out:
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                doAuth();
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
