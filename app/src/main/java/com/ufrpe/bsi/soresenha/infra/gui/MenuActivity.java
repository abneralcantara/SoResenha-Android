package com.ufrpe.bsi.soresenha.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.gui.DeletarFestaActivity;
import com.ufrpe.bsi.soresenha.eventos.gui.ListaFestaActivity;
import com.ufrpe.bsi.soresenha.usuario.gui.LoginActivity;
import com.ufrpe.bsi.soresenha.usuario.gui.RegisterActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);

        Button btnconfig = (Button)findViewById(R.id.btnConfigMenu);
        getSupportActionBar().hide();

        btnconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(MenuActivity.this, ConfigurationActivity.class);
                startActivity(configIntent);
            }
        });

        Button btndeletar = (Button)findViewById(R.id.deletePartybutton);

        btndeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delIntent = new Intent(MenuActivity.this, DeletarFestaActivity.class);
                startActivity(delIntent);
            }
        });

        Button btnlist = (Button)findViewById(R.id.consultPartybutton);

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conIntent = new Intent(MenuActivity.this, ListaFestaActivity.class);
                startActivity(conIntent);
            }
        });
    }
}
