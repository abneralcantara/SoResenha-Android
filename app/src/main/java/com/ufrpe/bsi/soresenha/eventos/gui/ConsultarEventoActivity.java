package com.ufrpe.bsi.soresenha.eventos.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultarEventoActivity extends AppCompatActivity {

    private EventoServices eventoServices = new EventoServices(this);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_festa);
        configurarTela(intent);
        getListParticipantes(getEventoId(intent));

        Button darLikebtn = (Button)findViewById(R.id.darLikebutton);
        darLikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPassed(intent)) {
                    final Evento eventoOld = getEventoId(intent);
                    eventoOld.plusLike();
                    Log.d("ad", String.valueOf(eventoOld.getQntLikes()));
                    eventoServices.likeupdate(eventoOld);
                    Intent backMenu = new Intent(ConsultarEventoActivity.this, ListaEventoActivity.class);
                    startActivity(backMenu);
                } else {
                    Toast.makeText(getApplicationContext(),"O Evento ainda não ocorreu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private List<Usuario> getListParticipantes(Evento evento) {
        EventoServices eventoServices = new EventoServices(this);
        return eventoServices.listParticipantes(evento);
    }

    private boolean isPassed(Intent intent){
        final Evento eventoOld = getEventoId(intent);
        Date datafesta =  eventoOld.getDate();

        //trocar para before depois dos testes
        if(datafesta.after(new Date())) { return true; }
        return false;
    }


    private void configurarTela(Intent intent) {
        final Evento eventoOld = getEventoId(intent);
        TextView precoFesta = findViewById(R.id.precoFesta);
        TextView nomeFesta = findViewById(R.id.nomeFesta);
        TextView descricaoFesta = findViewById(R.id.descricaoFesta);
        TextView dataFesta = findViewById(R.id.dataFesta);
        TextView likeFesta = findViewById(R.id.qntLike);
        nomeFesta.setText(eventoOld.getNome());
        descricaoFesta.setText(eventoOld.getDescricao());
        likeFesta.setText(String.valueOf(eventoOld.getQntLikes()) + " likes");
        NumberFormat realFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formatted = realFormat.format(eventoOld.getPreco());
        precoFesta.setText(formatted);
        dataFesta.setText(dateFormat.format(eventoOld.getDate()));
    }

    private Evento getEventoId(Intent intent) {
        Bundle extras = intent.getExtras();
        return eventoServices.get(extras.getLong("EventId"));
    }


    private void entrarFesta(Usuario usuario){
    }
}
