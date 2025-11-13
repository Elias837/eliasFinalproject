package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MyIncomeQuery
{
    @Insert
    void insert(MyIncome income);
    @Update
    void update(MyIncome income);
    @Delete
    void delete(MyIncome income);

    //get all incomes
    @Query("SELECT * FROM MyIncome")
    List<MyIncome> getAllIncomes();

    //get income by id
    @Query("SELECT * FROM MyIncome WHERE id = :incomeId LIMIT 1")
    MyIncome getIncomeById(int incomeId);

    //get incomes by user id
    @Query("SELECT * FROM MyIncome WHERE userId = :userId")
    List<MyIncome> getIncomesByUserId(int userId);

    //get incomes by category id
    @Query("SELECT * FROM MyIncome WHERE categoryId = :categoryId")
    List<MyIncome> getIncomesByCategoryId(int categoryId);

    //get completed incomes by user id
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 1")
    List<MyIncome> getCompletedIncomesByUserId(int userId);

    //get non completed incomes by user id
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 0")
    List<MyIncome> getNonCompletedIncomesByUserId(int userId);












}
