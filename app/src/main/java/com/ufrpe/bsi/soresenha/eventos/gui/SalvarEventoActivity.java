package com.ufrpe.bsi.soresenha.eventos.gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.negocio.EventoServices;
import com.ufrpe.bsi.soresenha.infra.helper.BigDecimalUtil;
import com.ufrpe.bsi.soresenha.infra.helper.MoneyTextMask;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SalvarEventoActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editDesc;
    private EditText editPreco;
    private EditText editDate;
    private EditText editHora;
    private EventoServices eventoServices = new EventoServices(this);
    private Calendar eventoDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat horaFormat = new SimpleDateFormat("kk:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);
        editNome = findViewById(R.id.nomeFestaedit);
        editPreco = findViewById(R.id.precoFestaedit);
        editDesc = findViewById(R.id.descFestaEdit);
        editDate = findViewById(R.id.dataFestaEdit);
        editHora = findViewById(R.id.horaFestaEdit);
        Button criarBtn = findViewById(R.id.criarFestabutton);
        criarListeners(criarBtn);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            long id = extras.getLong("EventId");
            final Evento eventoOld = eventoServices.get(id);
            preencherValores(eventoOld);
        }
    }

    private void preencherValores(Evento eventoOld) {
        eventoDate.setTime(eventoOld.getDate());
        editNome = findViewById(R.id.nomeFestaedit);
        editDesc = findViewById(R.id.descFestaEdit);
        editPreco = findViewById(R.id.precoFestaedit);
        editDate = findViewById(R.id.dataFestaEdit);
        editHora = findViewById(R.id.horaFestaEdit);
        editNome.setText(eventoOld.getNome());
        editDesc.setText(eventoOld.getDescricao());
        NumberFormat realFormat = NumberFormat.getCurrencyInstance(new Locale( "pt", "BR" ));
        String formatted = realFormat.format(eventoOld.getPreco());
        editPreco.setText(formatted);
        editDate.setText(dateFormat.format(eventoDate.getTime()));
        editHora.setText(horaFormat.format(eventoDate.getTime()));
    }

    private void criarListeners(Button criarBtn) {
        criarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) return;
                String nome = editNome.getText().toString();
                String descricao = editDesc.getText().toString();
                String preco = editPreco.getText().toString();
                BigDecimal parsed = BigDecimalUtil.fromBRLString(preco);
                Evento evento = new Evento(nome, descricao, parsed, eventoDate.getTime());
                evento.setCriador(SessaoUsuario.instance.getUsuario());
                try {
                    if (getIntent().getExtras() != null) {
                        evento.setId(getIntent().getExtras().getLong("EventId"));
                        eventoServices.update(evento);
                    } else {
                        eventoServices.criar(evento);
                    }
                } catch (SoresenhaAppException e) {
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("UpdateEvento", e.getMessage());
                    return;
                }
                Intent backMenu = new Intent(SalvarEventoActivity.this, ListaEventoActivity.class);
                backMenu.setFlags(backMenu.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backMenu);
            }
        });
        listenersData();
        editPreco.addTextChangedListener(new MoneyTextMask(editPreco));
    }

    private void listenersData() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editDate.setText(dateFormat.format(eventoDate.getTime()));
                eventoDate.set(year, monthOfYear, dayOfMonth);
                editDate.setError(null);
            }
        };
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                eventoDate.set(
                        eventoDate.get(Calendar.YEAR),
                        eventoDate.get(Calendar.MONTH),
                        eventoDate.get(Calendar.DAY_OF_MONTH),
                        i,
                        i1
                );
                String hora = horaFormat.format(eventoDate.getTime());
                editHora.setText(hora);
                editHora.setError(null);
            }
        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, eventoDate
                        .get(Calendar.YEAR), eventoDate.get(Calendar.MONTH),
                        eventoDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(
                        v.getContext(),
                        time,
                        eventoDate.get(Calendar.HOUR_OF_DAY),
                        eventoDate.get(Calendar.MINUTE),
                        true)
                .show();
            }
        });
    }

    private boolean validate() {
        limparCampos();
        if (!validarCampos()) return false;
        return true;
    }

    private boolean validarCampos() {
        boolean res = true;
        if (!validarPreco()) res =  false;
        if (!validarNome()) res = false;
        if (!validarDescricao()) res = false;
        if (!validarData()) res = false;
        if (!validarHora()) res = false;
        return res;
    }

    private boolean validarNome() {
        String nome = editNome.getText().toString();
        if (nome.isEmpty()) {
            editNome.setError("Nome não pode ser vazio");
            return false;
        }
        return true;
    }

    private boolean validarDescricao() {
        String desc = editDesc.getText().toString();
        if (desc.isEmpty()) {
            editDesc.setError("Descrição não pode ser vazia");
            return false;
        }
        return true;
    }

    private boolean validarPreco() {
        String preco = editPreco.getText().toString();
        if (preco.isEmpty()) {
            editPreco.setError("Preço não pode ser vazio");
            return false;
        }
        try {
            BigDecimalUtil.fromBRLString(preco);
        } catch (Exception e) {
            editPreco.setError("Preço não é conversível em número");
            return false;
        }
        return true;
    }

    private boolean validarData() {
        String data = editDate.getText().toString();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        if (data.isEmpty()) {
            editDate.setError("Data não pode ser vazia");
            return false;
        }
        return true;
    }

    private boolean validarHora() {
        String hora = editHora.getText().toString();
        if (hora.isEmpty()) {
            editHora.setError("Hora não pode ser vazia");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        editPreco.setError(null);
        editDesc.setError(null);
        editNome.setError(null);
        editDate.setError(null);
        editHora.setError(null);
    }
}
