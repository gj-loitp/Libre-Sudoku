package com.roy93group.libresudoku.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roy93group.libresudoku.data.db.converters.DurationConverter
import com.roy93group.libresudoku.data.db.converters.GameDifficultyConverter
import com.roy93group.libresudoku.data.db.converters.GameTypeConverter
import com.roy93group.libresudoku.data.db.converters.ZonedDateTimeConverter
import com.roy93group.libresudoku.data.db.dao.BoardDao
import com.roy93group.libresudoku.data.db.dao.FolderDao
import com.roy93group.libresudoku.data.db.dao.RecordDao
import com.roy93group.libresudoku.data.db.dao.SavedGameDao
import com.roy93group.libresudoku.data.db.model.Folder
import com.roy93group.libresudoku.data.db.model.Record
import com.roy93group.libresudoku.data.db.model.SavedGame
import com.roy93group.libresudoku.data.db.model.SudokuBoard

@Database(
    entities = [
        Record::class,
        SudokuBoard::class,
        SavedGame::class,
        Folder::class
    ],
    version = 5,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5)
    ]
)
@TypeConverters(
    DurationConverter::class,
    ZonedDateTimeConverter::class,
    GameDifficultyConverter::class,
    GameTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun boardDao(): BoardDao
    abstract fun savedGameDao(): SavedGameDao

    abstract fun folderDao(): FolderDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "main_database"
                ).build()
            }

            return INSTANCE as AppDatabase
        }
    }
}
