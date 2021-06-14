package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Quiz;

import java.util.List;

@Dao
public interface QuizDAO {

    @Insert()
    void addAllQuizes(List<Quiz> quizzes);

    @Query("SELECT * FROM Quiz")
    List<Quiz> getAllQuizzes();

    @Query("SELECT * FROM Quiz WHERE status = 1")
    Quiz getActiveQuiz();

    @Query("DELETE FROM Quiz")
    void deleteAllQuizzes();
}
