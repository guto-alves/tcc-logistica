package com.gutotech.tcclogistica.view.roteirista.ui.entrega;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.model.Status;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class EntregaDialog extends Dialog {
    private Entrega entrega;
    private Nota newNota;

    private Spinner notaSpinner;
    private ArrayAdapter notasArrayAdapter;
    private List<Nota> notaList = new LinkedList<>();
    private List<String> numerosNotasList = new LinkedList<>();

    private Spinner motoristaSpinner;
    private ArrayAdapter motoristasArrayAdapter;
    private List<Funcionario> motoristasList = new LinkedList<>();
    private List<String> nomesMotoristasList = new LinkedList<>();

    private DatabaseReference notasReference;
    private ValueEventListener notasListener;
    private Query notasQuery;

    private DatabaseReference motoristasReference;
    private Query motoristasQuery;
    private ValueEventListener motoristasListener;

    private EditText dataEditText, horaEditText;

    public EntregaDialog(@NonNull Context context, final Entrega entrega) {
        super(context);
        setContentView(R.layout.dialog_entrega);
        setCancelable(false);

        this.entrega = entrega;
        newNota = entrega.getNota();

        notaSpinner = findViewById(R.id.notaSpinner);
        motoristaSpinner = findViewById(R.id.motoristaSpinner);

        final CalendarView calendarView = findViewById(R.id.calendarView);

        dataEditText = findViewById(R.id.dataEditText);
        horaEditText = findViewById(R.id.horaEditText);

        notasArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, numerosNotasList);
        notasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notaSpinner.setAdapter(notasArrayAdapter);
        notaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newNota = notaList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        motoristasArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nomesMotoristasList);
        motoristasArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motoristaSpinner.setAdapter(motoristasArrayAdapter);
        motoristaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Funcionario motorista = motoristasList.get(position);
                entrega.setMotorista(motorista);
                entrega.setNomeMotorista(motorista.getNome());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dataEditText.setText(DateCustom.getData());
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        dataEditText.setFocusable(false);
        dataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
            }
        });

        calendarView.setVisibility(View.GONE);
        calendarView.setDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                dataEditText.setText(data);
                calendarView.setVisibility(View.GONE);
            }
        });

        ImageButton fecharButton = findViewById(R.id.fecharButton);
        final ImageButton updateImageButton = findViewById(R.id.updateImageButton);
        final ImageButton modeEditImageButton = findViewById(R.id.modoEditImageButton);
        ImageButton excluirButton = findViewById(R.id.excluirButton);

        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        modeEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entrega.getStatus() == Status.PENDENTE) {
                    changeMode(true);
                    modeEditImageButton.setVisibility(View.GONE);
                    updateImageButton.setVisibility(View.VISIBLE);
                }
            }
        });

        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields(dataEditText.getText().toString(), horaEditText.getText().toString())) {
                    updateEntrega();
                    Toasty.success(getContext(), "Entrega atualizada", Toasty.LENGTH_SHORT, true).show();

                    changeMode(false);
                    updateImageButton.setVisibility(View.GONE);
                    modeEditImageButton.setVisibility(View.VISIBLE);
                }
            }
        });

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("EXCLUIR ENTREGA");
                alert.setMessage("Tem certeza que deseja excluir a entrega?");
                alert.create();
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        entrega.getNota().setEstoque(true);
                        entrega.getNota().salvar();
                        entrega.excluir();
                        dismiss();
                    }
                });
                alert.setNegativeButton("Não", null);
                alert.show();
            }
        });

        addMasks();
        setInformations();
        changeMode(false);

        notasReference = ConfigFirebase.getDatabase().child("nota");
        motoristasReference = ConfigFirebase.getDatabase().child("funcionario");
    }

    private void setInformations() {
        dataEditText.setText(entrega.getData());
        horaEditText.setText(entrega.getHora());
    }

    private void updateEntrega() {
        entrega.getNota().setEstoque(true);
        entrega.getNota().salvar();

        newNota.setEstoque(false);
        entrega.setNota(newNota);
        entrega.getNota().salvar();

        entrega.setData(dataEditText.getText().toString());
        entrega.setHora(horaEditText.getText().toString());

        entrega.salvar();
    }

    private void addMasks() {
        dataEditText.addTextChangedListener(new MaskTextWatcher(dataEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        horaEditText.addTextChangedListener(new MaskTextWatcher(horaEditText, new SimpleMaskFormatter("NN:NN")));
    }

    private void changeMode(boolean mode) {
        notaSpinner.setEnabled(mode);
        motoristaSpinner.setEnabled(mode);
        dataEditText.setEnabled(mode);
        horaEditText.setEnabled(mode);
    }

    private boolean isValidFields(String data, String hora) {
        boolean valid = true;

        if (data.isEmpty()) {
            dataEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        if (hora.isEmpty()) {
            horaEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        return valid;
    }

    private void getNotas() {
        notasQuery = notasReference.orderByChild("estoque").equalTo(true);

        notasListener = notasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notaList.clear();
                numerosNotasList.clear();

                notaList.add(entrega.getNota());
                numerosNotasList.add(entrega.getNota().getNumero());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Nota nota = data.getValue(Nota.class);
                    notaList.add(nota);
                    numerosNotasList.add(nota.getNumero());
                }

                notasArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getMotoristas() {
        motoristasQuery = motoristasReference.orderByChild("cargo").equalTo(Funcionario.MOTORISTA);

        motoristasListener = motoristasQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motoristasList.clear();
                nomesMotoristasList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Funcionario funcionario = data.getValue(Funcionario.class);
                    motoristasList.add(funcionario);
                    nomesMotoristasList.add(funcionario.getNome());
                }

                motoristasArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        getMotoristas();
        getNotas();
    }

    @Override
    public void onStop() {
        super.onStop();

        motoristasQuery.removeEventListener(motoristasListener);
        notasQuery.removeEventListener(notasListener);
    }
}
