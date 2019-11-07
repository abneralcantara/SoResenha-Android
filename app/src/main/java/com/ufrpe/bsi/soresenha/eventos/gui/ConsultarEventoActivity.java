package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.dominio.TipoAvaliacao;
import com.ufrpe.bsi.soresenha.eventos.negocio.AvaliacaoServices;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.gui.MenuActivity;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ConsultarEventoActivity extends AppCompatActivity {

    private EventoServices eventoServices = new EventoServices(this);
    private AvaliacaoServices avaliacaoServices = new AvaliacaoServices(this);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_festa);
        configurarTela(intent);
        getListParticipantes(getEventoId(intent));
        RecyclerView recyclerView = findViewById(R.id.participantesFesta);
        setupRecyclerView(recyclerView, intent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnIrfesta = (Button) findViewById(R.id.btnIrfesta);
        btnIrfesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avaliacaoServices.criar(new Avaliacao(String.valueOf(SessaoUsuario.instance.getUsuario().getId()), String.valueOf(getEventoId(intent)) , TipoAvaliacao.NAOLIKE));
            }
        });
    }
    private void setupRecyclerView(RecyclerView recyclerView, Intent intent) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclingAdapterParticipante(avaliacaoServices.list(getEventoId(intent))));
    }

    private List<Usuario> getListParticipantes(Evento evento) {
        EventoServices eventoServices = new EventoServices(this);
        return eventoServices.listParticipantes(evento);
    }


    private void configurarTela(Intent intent) {
        final Evento eventoOld = getEventoId(intent);
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

    private Evento getEventoId(Intent intent) {
        Bundle extras = intent.getExtras();
        return eventoServices.get(extras.getLong("EventId"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListaEventoActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
