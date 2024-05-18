package com.jeizard.quizapp.data.supabase.base

import com.jeizard.domain.repositories.BaseRepository
import com.jeizard.quizapp.data.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

abstract class BaseRepositorySupabaseImpl<SBEntity, Entity>(
    private val mapper: Mapper<SBEntity, Entity>,
    private val tableName: String
) : BaseRepository<Entity> {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://pzgliumvftkgbmgjmyyg.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB6Z2xpdW12ZnRrZ2JtZ2pteXlnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUzMzQ3MjMsImV4cCI6MjAzMDkxMDcyM30.9y_XD99dBG1I7Q3sDaoqx4cajiTT3KVjg03sjYSO2Gs"
    ) {
        install(Postgrest)
    }

    var allItems: List<Entity> = emptyList()
    private val listeners: MutableSet<BaseRepository.OnDataChangedListener<Entity>> = HashSet()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            allItems = withContext(Dispatchers.IO) { getAll()}
            notifyChanges()
        }
    }

    override fun insert(item: Entity) {
        GlobalScope.launch(Dispatchers.IO) {
            allItems = withContext(Dispatchers.IO) { getAll() }
            notifyChanges()
        }
    }

    override fun update(item: Entity) {
        GlobalScope.launch(Dispatchers.IO) {
            allItems = withContext(Dispatchers.IO) { getAll() }
            notifyChanges()
        }
    }

    override fun delete(item: Entity) {
        GlobalScope.launch(Dispatchers.IO) {
            allItems = withContext(Dispatchers.IO) { getAll() }
            notifyChanges()
        }
    }

    override fun deleteAll() {
        GlobalScope.launch(Dispatchers.IO) {
            allItems = withContext(Dispatchers.IO) { getAll() }
            notifyChanges()
        }
    }

    override fun getAll(): List<Entity> {
        val items = mutableListOf<Entity>()
        val result = supabase.from(tableName).select().decodeSingle<SBEntity>()
        items.addAll(items)
        allItems = mapper.mapFromDBEntity(items)
        notifyChanges()
        return allItems
    }

    override fun addListener(listener: BaseRepository.OnDataChangedListener<Entity>) {
        listeners.add(listener)
        listener.onChanged(allItems)
    }

    override fun removeListener(listener: BaseRepository.OnDataChangedListener<Entity>) {
        listeners.remove(listener)
    }

    fun notifyChanges() {
        for (listener in listeners) {
            listener.onChanged(allItems)
        }
    }

}