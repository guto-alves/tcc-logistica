package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.RecyclerItemClickListener;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adapter.FuncionariosAdapter;
import com.gutotech.tcclogistica.view.adapter.LoginFuncionarioExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class FuncionariosCadastradosFragment extends Fragment {
    private RecyclerView funcionariosRecyclerView;
    private FuncionariosAdapter funcionariosAdapter;
    private List<Funcionario> funcionariosList = new ArrayList<>();

    private DatabaseReference funcionariosReference;
    private Query funcionarioQuery;
    private ValueEventListener funcionariosListener;

    private TextView statusFuncionariosTextView;

    private boolean confirmaSenha = true;

    public FuncionariosCadastradosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adm_funcionarios_lista, container, false);

        statusFuncionariosTextView = root.findViewById(R.id.statusFuncionariosTextView);
        statusFuncionariosTextView.setText("Carregando Funcionários ..");

        funcionariosRecyclerView = root.findViewById(R.id.funcionariosRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        funcionariosRecyclerView.setLayoutManager(layoutManager);
        funcionariosRecyclerView.setHasFixedSize(true);

        funcionariosAdapter = new FuncionariosAdapter(getActivity(), funcionariosList);
        funcionariosRecyclerView.setAdapter(funcionariosAdapter);
        funcionariosRecyclerView.addOnItemTouchListener(funcionarioItemTouchListener);

        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarFuncionario(newText);
                return true;
            }
        });

        funcionariosReference = ConfigFirebase.getDatabase().child("funcionario");

        return root;
    }

    private final RecyclerView.OnItemTouchListener funcionarioItemTouchListener = new RecyclerItemClickListener(getActivity(), funcionariosRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            final Funcionario funcionario = funcionariosList.get(position);

            final Dialog funcionarioDialog = new Dialog(getActivity());
            funcionarioDialog.setContentView(R.layout.dialog_funcionario);
            funcionarioDialog.setCancelable(false);

            TextView nomeTextView = funcionarioDialog.findViewById(R.id.nomeTextView);
            ImageView perfilImageView = funcionarioDialog.findViewById(R.id.profileImageView);

            TextView cargoTextView = funcionarioDialog.findViewById(R.id.cargoTextView);
            TextView celularTextView = funcionarioDialog.findViewById(R.id.celularTextView);
            TextView emailTextView = funcionarioDialog.findViewById(R.id.emailTextView);
            TextView enderecoTextView = funcionarioDialog.findViewById(R.id.enderecoTextView);
            TextView rgTextView = funcionarioDialog.findViewById(R.id.rgTextView);
            TextView cpfTextView = funcionarioDialog.findViewById(R.id.cpfTextView);
            TextView dataNascimentoTextView = funcionarioDialog.findViewById(R.id.dataNascimentoTextView);
            TextView ultimoLoginTextView = funcionarioDialog.findViewById(R.id.ultimoLoginTextView);

            TextView veiculoTextView = funcionarioDialog.findViewById(R.id.veiculoTextView);
            TextView cnhTextView = funcionarioDialog.findViewById(R.id.cnhTextView);
            TextView placaTextView = funcionarioDialog.findViewById(R.id.placaTextView);
            TextView categoriaTextView = funcionarioDialog.findViewById(R.id.categoriaTextView);
            TextView anoTextView = funcionarioDialog.findViewById(R.id.anoTextView);
            final ExpandableListView loginExpandableListView = funcionarioDialog.findViewById(R.id.loginFuncionarioExpandable);

            nomeTextView.setText(funcionario.getNome());
            cargoTextView.setText(funcionario.getCargo());
            celularTextView.setText(funcionario.getCelular());
            emailTextView.setText(funcionario.getEmail());
            enderecoTextView.setText(funcionario.getEndereco());
            rgTextView.setText(funcionario.getRg());
            cpfTextView.setText(funcionario.getCpf());
            dataNascimentoTextView.setText(funcionario.getDataNascimento());
            ultimoLoginTextView.setText(funcionario.getLogin().getUltimoLogin());

            if (funcionario.getCargo().equals(Funcionario.MOTORISTA)) {
                cnhTextView.setText(funcionario.getCnh());
                categoriaTextView.setText(funcionario.getVeiculo().getCategoria());
                veiculoTextView.setText(funcionario.getVeiculo().getNome());
                placaTextView.setText(funcionario.getVeiculo().getPlaca());
                anoTextView.setText(funcionario.getVeiculo().getAno());

                LinearLayout motoristaTextViews = funcionarioDialog.findViewById(R.id.motoristaTextViewsLinear);
                motoristaTextViews.setVisibility(View.VISIBLE);
            }

            LoginFuncionarioExpandableAdapter loginAdapter = new LoginFuncionarioExpandableAdapter(getActivity(), funcionario);
            loginExpandableListView.setAdapter(loginAdapter);
            loginExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (confirmaSenha) {
                        loginExpandableListView.collapseGroup(0);
                        final Dialog confirmDialog = new Dialog(getActivity());
                        confirmDialog.setContentView(R.layout.dialog_confirm_password);
                        confirmDialog.setCancelable(false);

                        final EditText passwordEditText = confirmDialog.findViewById(R.id.passwordEditText);
                        Button okButton = confirmDialog.findViewById(R.id.okButton);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String password = passwordEditText.getText().toString();
                                if (password.equals(FuncionarioOn.funcionario.getLogin().getPassword())) {
                                    confirmaSenha = false;
                                    loginExpandableListView.expandGroup(0);
                                } else {
                                    confirmaSenha = true;
                                    confirmDialog.dismiss();
                                    Toasty.error(getActivity(), "Acesso negado, senha inválida!", Toasty.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                        Button cancelButton = confirmDialog.findViewById(R.id.cancelButton);
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmDialog.dismiss();
                            }
                        });

                        confirmDialog.show();
                    }
                }
            });
            loginExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    confirmaSenha = true;
                }
            });

            funcionarioDialog.show();

            Button fecharButton = funcionarioDialog.findViewById(R.id.fecharButton);
            fecharButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    funcionarioDialog.dismiss();
                }
            });

            Button excluirFuncionarioButton = funcionarioDialog.findViewById(R.id.excluirButton);
            excluirFuncionarioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("EXCLUIR FUNCIONÁRIO");
                    alert.setMessage("Tem certeza que deseja excluir o funcionário " + funcionario.getNome().toUpperCase() + "?");
                    alert.create();
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            funcionario.excluir();
                            funcionarioDialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("Não", null);
                    alert.show();
                }
            });
        }

        @Override
        public void onLongItemClick(View view, int position) {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });

    private void buscarFuncionario(String query) {
        funcionarioQuery = funcionariosReference.orderByChild("nome").startAt(query).endAt(query + "\uf8ff");

        funcionariosListener = funcionarioQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                funcionariosList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    funcionariosList.add(data.getValue(Funcionario.class));

                if (funcionariosList.size() == 0)
                    statusFuncionariosTextView.setText("Nenhum funcionário encontrado.");
                else
                    statusFuncionariosTextView.setVisibility(View.GONE);

                funcionariosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarFuncionario("");
    }

    @Override
    public void onStop() {
        super.onStop();
        funcionarioQuery.removeEventListener(funcionariosListener);
    }
}
