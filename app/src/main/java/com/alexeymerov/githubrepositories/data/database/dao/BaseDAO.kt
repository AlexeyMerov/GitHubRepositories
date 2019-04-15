package com.alexeymerov.githubrepositories.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDAO<T> {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun add(item: T)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun add(items: List<T>)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	abstract fun update(item: T)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	abstract fun update(items: List<T>)

	@Delete
	abstract fun delete(item: T)

	@Delete
	abstract fun delete(items: List<T>)

}