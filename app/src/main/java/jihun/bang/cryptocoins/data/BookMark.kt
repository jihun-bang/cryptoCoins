package jihun.bang.cryptocoins.data

import android.content.Context
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Entity
data class BookMark(
    @PrimaryKey val coinName: String
)

@Dao
interface BookMarkDao {
    @Query("SELECT * FROM bookMark")
    fun getAll(): List<BookMark>

    @Insert(onConflict = REPLACE)
    fun insert(bookMark: BookMark)

    @Delete
    fun delete(bookMark: BookMark)
}

@Database(entities = [BookMark::class], version = 1)
abstract class BookMarkDB: RoomDatabase() {
    abstract fun bookMarkDao(): BookMarkDao

    companion object {
        private var instance: BookMarkDB? = null

        fun getInstance(context: Context): BookMarkDB? {
            if (instance == null) {
                synchronized(BookMarkDB::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        BookMarkDB::class.java, "BookMark.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}

object DB {
    var db: BookMarkDB? = null
}