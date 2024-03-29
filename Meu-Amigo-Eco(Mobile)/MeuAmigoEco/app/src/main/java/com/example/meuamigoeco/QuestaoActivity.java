package com.example.meuamigoeco;

import static com.example.meuamigoeco.SetsActivity.idCategoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestaoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questao, countQuestao, temporizador;
    private Button questaoUm, questaoDois, questaoTres, questaoQuatro;
    private List<Questao> listaQuestoes;
    private int numeroQuestao;
    private CountDownTimer countDown;
    private int pontuacao;
    private FirebaseFirestore firestore;
    private int numSet;
    private Dialog carregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);

        questao = findViewById(R.id.questao);
        countQuestao = findViewById(R.id.questao_number);
        temporizador = findViewById(R.id.contador);

        // Botões das questões do Quiz
        questaoUm = findViewById(R.id.buttonUm);
        questaoDois = findViewById(R.id.buttonDois);
        questaoTres = findViewById(R.id.buttonTres);
        questaoQuatro = findViewById(R.id.buttonQuatro);

        questaoUm.setOnClickListener(this);
        questaoDois.setOnClickListener(this);
        questaoTres.setOnClickListener(this);
        questaoQuatro.setOnClickListener(this);

        carregando = new Dialog(QuestaoActivity.this);
        carregando.setContentView(R.layout.barra_progresso_carregamento);
        carregando.setCancelable(false);
        carregando.getWindow().setBackgroundDrawableResource(R.drawable.background_progresso);
        carregando.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        carregando.show();

        numSet = getIntent().getIntExtra("SETNUMB", 1);
        firestore = FirebaseFirestore.getInstance();

        getQuestaosList();

        pontuacao = 0;
    }

    private void getQuestaosList(){
        listaQuestoes = new ArrayList<>();

        firestore.collection("MeuAmigoEco").document("Categoria" +
                String.valueOf(idCategoria)).collection("SET" + String.valueOf(numSet))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot questoes = task.getResult();

                    for (QueryDocumentSnapshot doc : questoes){
                        listaQuestoes.add(new Questao(doc.getString("Questao"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("Resposta"))
                        ));
                    }
                    setQuestao();
                }
                else {
                    Toast.makeText(QuestaoActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                carregando.cancel();
            }
        });
    }

    private void setQuestao(){
        temporizador.setText(String.valueOf(10));
        questao.setText(listaQuestoes.get(0).getQuestao());
        questaoUm.setText(listaQuestoes.get(0).getOpcaoA());
        questaoDois.setText(listaQuestoes.get(0).getOpcaoB());
        questaoTres.setText(listaQuestoes.get(0).getOpcaoC());
        questaoQuatro.setText(listaQuestoes.get(0).getOpcaoD());

        countQuestao.setText(String.valueOf(1) + "/" + String.valueOf(listaQuestoes.size()));

        startTemporizador();

        numeroQuestao = 0;
    }

    private void startTemporizador(){
         countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000)
                    temporizador.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mudarQuestao();
            }
        };
        // Start do temporizador
        countDown.start();
    }


    @Override
    public void onClick(View v) {
        int opcaoSelecionada = 0;

        switch (v.getId()){
            case R.id.buttonUm:
                opcaoSelecionada = 1;
                break;
            case R.id.buttonDois:
                opcaoSelecionada = 2;
                break;
            case R.id.buttonTres:
                opcaoSelecionada = 3;
                break;
            case R.id.buttonQuatro:
                opcaoSelecionada = 4;
                break;
            default:
                break;
        }
        countDown.cancel();
        checkResposta(opcaoSelecionada, v);
    }

    private void checkResposta(int opcaoSelecionada, View v){
        if (opcaoSelecionada == listaQuestoes.get(numeroQuestao).getQuestaoCorreta()){
            // questão coreta
            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            pontuacao++;
        }
        else {
            // Questão incorreta
            ((Button)v).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (listaQuestoes.get(numeroQuestao).getQuestaoCorreta()){
                case 1:
                    questaoUm.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    questaoDois.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    questaoTres.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    questaoQuatro.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mudarQuestao();
            }
        }, 2000);
    }

    private void mudarQuestao(){
        if (numeroQuestao < listaQuestoes.size() - 1){
            numeroQuestao++;

            playAnim(questao, 0, 0);
            playAnim(questaoUm, 0, 1);
            playAnim(questaoDois, 0, 2);
            playAnim(questaoTres, 0, 3);
            playAnim(questaoQuatro, 0, 4);

            countQuestao.setText(String.valueOf(numeroQuestao + 1) + "/" + String.valueOf(listaQuestoes.size()));
            temporizador.setText(String.valueOf(10));
            startTemporizador();
        } else {
            // Vá para a Activity de pontuação
            Intent intent = new Intent(QuestaoActivity.this, PontuacaoActivity.class);
            intent.putExtra("PONTUAÇÃO", String.valueOf(pontuacao) + "/" + String.valueOf(listaQuestoes.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void playAnim(View v, final int value, int numeroView) {
        v.animate().alpha(value)
                .scaleX(value).setDuration(500)
                .setDuration(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0) {
                            switch (numeroView) {
                                case 0:
                                    ((TextView) v).setText(listaQuestoes.get(numeroQuestao).getQuestao());
                                    break;
                                case 1:
                                    ((Button) v).setText(listaQuestoes.get(numeroQuestao).getOpcaoA());
                                    break;
                                case 2:
                                    ((Button) v).setText(listaQuestoes.get(numeroQuestao).getOpcaoB());
                                    break;
                                case 3:
                                    ((Button) v).setText(listaQuestoes.get(numeroQuestao).getOpcaoC());
                                    break;
                                case 4:
                                    ((Button) v).setText(listaQuestoes.get(numeroQuestao).getOpcaoD());
                                    break;
                            }

                            if (numeroView != 0)
                                ((Button) v).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5b92e5")));

                            playAnim(v, 1, numeroView);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDown.cancel();
    }
}