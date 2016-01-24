package mira.jeugdkern.cluedo_jeugdkern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends Activity {

    private HashMap<String, Question> questions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject reader = new JSONObject(json);
            /*
            Structure of JSON file
            {
            "questions" : [
                {
                    "code" : "QR-Code-Value",
                    "question" : "Question",
                    "answers" : [ "Answer1", "Answer2", ... ],
                    "correctAnswer" : 1
                },
                ...
            ]
            }
            */

            JSONArray questionsJSON = reader.getJSONArray("questions");
            for(int i = 0; i < questionsJSON.length(); i++){
                JSONObject question = questionsJSON.getJSONObject(i);
                String code = question.getString("code");
                String questionString = question.getString("question");

                JSONArray answersArray = question.getJSONArray("answers");
                String[] answers = new String[answersArray.length()];
                for(int j = 0; j < answersArray.length(); j++){
                    answers[j] = answersArray.getString(j);
                }

                int correctAnswer = question.getInt("correctAnswer");

                Question parsedQuestion = new Question(
                        questionString,
                        answers,
                        correctAnswer
                );

                questions.put(code, parsedQuestion);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void scanQRCode(View view){
        try {
            //Make new Intent to scan QR-Code
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);
        } catch (Exception e) {
            //Install correct application for scanning QR-codes.
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String questionID = data.getStringExtra("SCAN_RESULT");
                if(questions.containsKey(questionID)){
                    Question question = questions.get(questionID);
                    Intent intent = new Intent(this, QuestionActivity.class);
                    intent.putExtra("question", question);
                    startActivityForResult(intent, 1);
                }
            }
        }
    }
}
