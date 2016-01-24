package mira.jeugdkern.cluedo_jeugdkern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends Activity {

    private Question question;
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Get Question from intent.
        this.question = (Question) getIntent().getSerializableExtra("question");

        //Set question and answers in view.
        TextView txtQuestion = (TextView) findViewById(R.id.txt_question);
        txtQuestion.setText(question.getQuestion());

        group = (RadioGroup) findViewById(R.id.lst_answers);
        RadioButton button;
        for(int i = 0; i < question.getAnswers().length; i++) {
            button = new RadioButton(this);
            button.setText(question.getAnswers()[i]);
            group.addView(button);
        }
    }

    public void onSubmit(View view){
        int selectedAnswer = group.getCheckedRadioButtonId();

        if(selectedAnswer == -1){
            //Nothing selected.
            return;
        }

        String resultaat = "Fout."; //The answers is false until otherwise proven.
        for(int i = 0; i < group.getChildCount();i++ ) {
            RadioButton radioButton = (RadioButton) group.getChildAt(i);
            if(radioButton.isChecked() && i==question.getCorrectAnswer()){
                resultaat = "Juist!";
            }
        }

        //Display the response in a new AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(resultaat)
                .setTitle("Antwoord")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //Return to MainActivity.
                        QuestionActivity.this.finish();
                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
