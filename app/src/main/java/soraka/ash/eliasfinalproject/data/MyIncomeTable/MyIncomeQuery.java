package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface MyIncomeQuery
{
    @Query("SELECT * FROM MyIncome")
    List<MyIncome> getAll();
    @Query("SELECT * FROM MyIncome WHERE transactionId IN (:userIds)")
    List<MyIncome> loadAllByIds(int[] userIds);
    @Query("SELECT * FROM MyIncome WHERE transactionId = :transactionId LIMIT 1")
    MyIncome loadById(int transactionId);
    @Insert
    void insertAll(MyIncome... users);
    @Delete
    void delete(MyIncome user);
    @Update
    void update(MyIncome... values);
    @Query("Delete From MyIncome WHERE transactionId=:id ")
    void delete(int id);
    @Query("Delete From MyIncome")
    void deleteAll();

}
