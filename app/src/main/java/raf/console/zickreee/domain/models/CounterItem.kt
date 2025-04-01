package raf.console.zickreee.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "counters")
data class CounterItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "target")
    var target: Int = 0,

    @ColumnInfo(name = "progress")
    var progress: Int = 0,

    @ColumnInfo(name = "completed")
    var completed: Boolean = false,

    @ColumnInfo(name = "counterType")
    var counterType: CounterType? = null
) : Serializable {

    @Ignore
    constructor(title: String, target: Int, progress: Int) : this(
        0, // id
        title,
        target,
        progress
    )

    @Ignore
    constructor(title: String, target: Int, progress: Int, counterType: CounterType) : this(
        0, // id
        title,
        target,
        progress,
        counterType = counterType
    )

    @Ignore
    constructor(id: Long, title: String, target: Int, progress: Int) : this(
        id,
        title,
        target,
        progress,
        false, // completed
        null   // counterType
    )

    @Ignore
    constructor(id: Long, title: String, target: Int, progress: Int, counterType: CounterType) : this(
        id,
        title,
        target,
        progress,
        false, // completed
        counterType
    )
}