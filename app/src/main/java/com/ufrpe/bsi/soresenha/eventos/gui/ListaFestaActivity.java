package com.ufrpe.bsi.soresenha.eventos.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.eventos.negocio.RecyclingAdapterfesta;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaFestaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_festa);

        getSupportActionBar().hide();
        RecyclerView recyclerView = findViewById(R.id.recyclerfestas);

        RecyclerView.LayoutManager LinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayoutManager);

        recyclerView.setAdapter(new RecyclingAdapterfesta(createList()));
    }

    private List<Evento> createList() {
        EventoServices eventoServices = new EventoServices(this);
        ArrayList<Evento> listadeEventos = new ArrayList<Evento>();
//essa função coloca uma lista em forma de array list no recycling mas a gente precisa de uma forma de transformar o bd em array

 //forma de entrada no recycling para testar mesmo por enquanto o card so precisa do titulo e da desc
        return Arrays.asList(
                new Evento("Villa Mix","festas em olinda dia 25 de março"),
                new Evento("Rock in RIO","Prefeitura do rio cria evento de rock"),
                new Evento("carb day","carboidratos não são tops sempre"),
                new Evento("Rec'in play","recife de progamão")
        );
    }

}
