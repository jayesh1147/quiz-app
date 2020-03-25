package com.example.enrichyourknowlwdge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.enrichyourknowlwdge.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;


    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("........... is the first woman to head a public sector bank."
                , " Arundhati Bhattacharya", " Shikha Sharma ", "Chanda Kochar", 1);
        addQuestion(q1);
        Question q2 = new Question("World Tourism Day is celebrated on",
                "September 12 ", "September 25", "September 27", 3);
        addQuestion(q2);
        Question q3 = new Question("Where is Bose Institute ? ",
                " Dispur ", " New Delhi ", " Kolkata", 3);
        addQuestion(q3);
        Question q4 = new Question(" When is the International Yoga Day celebrated ? ",
                " June 21 ", " March 21 ", "April 22", 1);
        addQuestion(q4);
        Question q5 = new Question(" What is the name of Indira Gandhi's Samadhi? ",
                " Shanti Ghat ", " Shakti Sthal ", "Shanti Van", 2);
        addQuestion(q5);
        Question q6 = new Question(" Which one of the following states of India has made" +
                " unique venture of putting daughter’s nameplate on the front of the house? ",
                " Punjab ", "Uttar Pradesh", "Haryana ", 3);
        addQuestion(q6);
        Question q7 = new Question("The first Indian State to go wholley organic is - ",
                "Meghalaya ", " Manipur ", " Sikkim", 3);
        addQuestion(q7);
        Question q8 = new Question("Who among the following Scientists got two Nobel prizes" +
                " of which one was in Peace? ",
                "Albert Einstein ", " H. G. Khorana ", "Linus Pauling", 3);
        addQuestion(q8);
        Question q9 = new Question(" When Government of India confers the \"Highest Civilian Honor for Women\" by presenting \"Nari Shakti Puraskars\" ?  ",
                " June 5 ", " 8th March, every year, International Women's Day ", "  April 7 ", 2);
        addQuestion(q9);
        Question q10 = new Question("The motif of 'Hampi with Chariot' is printed on the " +
                "reverse of which currency note ? ",
                "Rs. 500 note", " Rs. 50 note", "Rs. 1000 note", 2);
        addQuestion(q10);
    }


    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }


    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}