package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;

import java.util.List;

public class ListaEventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_festa);

        getSupportActionBar().hide();
        RecyclerView recyclerView = findViewById(R.id.recyclerfestas);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new RecyclingAdapterFesta(createList(), this));

        FloatingActionButton newFesta = (FloatingActionButton) findViewById(R.id.criarEventoFloatingButton);

        newFesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent insertIntent = new Intent(ListaEventoActivity.this, CriarEventoActivity.class);
            startActivity(insertIntent);
            }
        });
    }

    private List<Evento> createList() {
        EventoServices eventoServices = new EventoServices(this);
        return eventoServices.list();
    }
}
