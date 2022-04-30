package com.example.bbackcodingcon.di

import android.app.Application
import androidx.room.Room
import com.example.bbackcodingcon.feature_memo.data.repository.MemoRepositoryImpl
import com.example.bbackcodingcon.feature_memo.data.source.MemoDatabase
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository
import com.example.bbackcodingcon.feature_memo.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMemoDatabase(app: Application): MemoDatabase {
        return Room.databaseBuilder(
            app,
            MemoDatabase::class.java,
            MemoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMemoRepository(db: MemoDatabase): MemoRepository {
        return MemoRepositoryImpl(db.memoDao)
    }

    @Provides
    @Singleton
    fun provideMemoUseCases(repository: MemoRepository): MemoUseCases {
        return MemoUseCases(
            getMemos = GetMemos(repository),
            delMemo = DelMemo(repository),
            addMemo = AddMemo(repository),
            getMemo = GetMemo(repository)
        )
    }
}