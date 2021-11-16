package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.AuthViewModel;

public class MainActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authViewModel= new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.sinOut)
        {
            authViewModel.signOut();
        }
        return super.onOptionsItemSelected(item);


    }

}
