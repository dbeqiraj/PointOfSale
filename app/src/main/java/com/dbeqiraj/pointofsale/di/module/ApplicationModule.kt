package com.dbeqiraj.pointofsale.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.dbeqiraj.pointofsale.database.PosDatabase
import com.dbeqiraj.pointofsale.database.helper.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule constructor(val mContext: Context){

    @Singleton
    @Provides
    fun provideContext(): Context = mContext

    @Singleton
    @Provides
    fun provideDatabase(): PosDatabase {
        val posDatabaseHelper = DatabaseHelper(mContext)
        return Room
                .databaseBuilder(mContext, PosDatabase::class.java, "pos_db")
                //.allowMainThreadQueries()
                //.fallbackToDestructiveMigration()
                .addCallback(posDatabaseHelper.roomDatabaseCallback)
                .build()
    }
}