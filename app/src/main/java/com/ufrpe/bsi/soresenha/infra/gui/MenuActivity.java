package com.ufrpe.bsi.soresenha.infra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.gui.ListaEventoActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        setMenuButtonClickListeners();
    }

    private void setMenuButtonClickListeners() {
        ImageButton btnConfig = (ImageButton)findViewById(R.id.btnConfigMenu);
        Button btnList = (Button)findViewById(R.id.consultPartybutton);
        getSupportActionBar().hide();
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(MenuActivity.this, ConfigurationActivity.class);
                startActivity(configIntent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conIntent = new Intent(MenuActivity.this, ListaEventoActivity.class);
                startActivity(conIntent);
            }
        });
    }
}
