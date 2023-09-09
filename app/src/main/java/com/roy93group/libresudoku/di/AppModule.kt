package com.roy93group.libresudoku.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.roy93group.libresudoku.data.database.AppDatabase
import com.roy93group.libresudoku.data.database.dao.BoardDao
import com.roy93group.libresudoku.data.database.dao.FolderDao
import com.roy93group.libresudoku.data.database.dao.RecordDao
import com.roy93group.libresudoku.data.database.dao.SavedGameDao
import com.roy93group.libresudoku.data.database.repository.BoardRepositoryImpl
import com.roy93group.libresudoku.data.database.repository.FolderRepositoryImpl
import com.roy93group.libresudoku.data.database.repository.RecordRepositoryImpl
import com.roy93group.libresudoku.data.database.repository.SavedGameRepositoryImpl
import com.roy93group.libresudoku.data.datastore.AppSettingsManager
import com.roy93group.libresudoku.data.datastore.ThemeSettingsManager
import com.roy93group.libresudoku.domain.repository.BoardRepository
import com.roy93group.libresudoku.domain.repository.FolderRepository
import com.roy93group.libresudoku.domain.repository.RecordRepository
import com.roy93group.libresudoku.domain.repository.SavedGameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val ACRA_SHARED_PREFS_NAME = "acra_shared_pref"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAcraSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(ACRA_SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideFolderRepository(folderDao: FolderDao): FolderRepository =
        FolderRepositoryImpl(folderDao)

    @Provides
    @Singleton
    fun provideFolderDao(appDatabase: AppDatabase): FolderDao = appDatabase.folderDao()

    // records
    @Singleton
    @Provides
    fun provideRecordRepository(recordDao: RecordDao): RecordRepository =
        RecordRepositoryImpl(recordDao)

    @Singleton
    @Provides
    fun provideRecordDao(appDatabase: AppDatabase): RecordDao = appDatabase.recordDao()


    // boards
    @Singleton
    @Provides
    fun provideBoardRepository(boardDao: BoardDao): BoardRepository = BoardRepositoryImpl(boardDao)

    @Singleton
    @Provides
    fun provideBoardDao(appDatabase: AppDatabase): BoardDao = appDatabase.boardDao()


    // saved games
    @Singleton
    @Provides
    fun provideSavedGameRepository(savedGameDao: SavedGameDao): SavedGameRepository =
        SavedGameRepositoryImpl(savedGameDao)

    @Singleton
    @Provides
    fun provideSavedGameDao(appDatabase: AppDatabase): SavedGameDao = appDatabase.savedGameDao()

    // settings datastore
    @Provides
    @Singleton
    fun provideAppSettingsManager(@ApplicationContext context: Context) =
        AppSettingsManager(context)

    // appTheme datastore
    @Provides
    @Singleton
    fun provideThemeSettingsManager(@ApplicationContext context: Context) =
        ThemeSettingsManager(context)

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase = AppDatabase.getInstance(context = app)
}
