package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.gui.MenuActivity;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;

import java.util.List;

public class ListaEventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_festa);
        FloatingActionButton newFesta = (FloatingActionButton) findViewById(R.id.criarEventoFloatingButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerfestas);
        setupRecyclerView(recyclerView);
        configurarTela(newFesta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclingAdapterFesta(createList(), this));
    }

    private void configurarTela(FloatingActionButton newFesta) {
        if (!SessaoUsuario.instance.isParceiro()) {
            ((View) newFesta).setVisibility(View.GONE);
        } else {
            newFesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent insertIntent = new Intent(ListaEventoActivity.this, CriarEventoActivity.class);
                    insertIntent.setFlags(insertIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(insertIntent);
                }
            });
        }
    }

    private List<Evento> createList() {
        EventoServices eventoServices = new EventoServices(this);
        return eventoServices.list();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MenuActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
