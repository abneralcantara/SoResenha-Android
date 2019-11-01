package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConsultarEventoActivity extends AppCompatActivity {

    private EventoServices eventoServices = new EventoServices(this);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_festa);
        configurarTela(intent);
    }

    private void configurarTela(Intent intent) {
        Bundle extras = intent.getExtras();
        final Evento eventoOld = eventoServices.get(extras.getLong("EventId"));
        TextView precoFesta = findViewById(R.id.precoFesta);
        TextView nomeFesta = findViewById(R.id.nomeFesta);
        TextView descricaoFesta = findViewById(R.id.descricaoFesta);
        TextView dataFesta = findViewById(R.id.dataFesta);
        nomeFesta.setText(eventoOld.getNome());
        descricaoFesta.setText(eventoOld.getDescricao());
        NumberFormat realFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formatted = realFormat.format(eventoOld.getPreco());
        precoFesta.setText(formatted);
        dataFesta.setText(dateFormat.format(eventoOld.getDate()));
    }
}
