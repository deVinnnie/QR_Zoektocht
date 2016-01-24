package mira.jeugdkern.cluedo_jeugdkern;

import java.io.Serializable;

public class Question implements Serializable{
    private String question;
    private String[] answers;

    /**
     * Index of the correct answer. (First answer = 0);
     */
    private int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer){
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
