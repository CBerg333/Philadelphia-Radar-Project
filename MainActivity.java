package com.github.philadelphia_radar_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView txtSubTitle;
    private MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.inflater = getMenuInflater();
        this.inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}