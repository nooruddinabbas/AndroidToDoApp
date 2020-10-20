package io.realm.androidtodoapp.model


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.Date

open class Item(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var _partition: String = "",
    var body: String = "",
    var isDone: Boolean = false,
    var timestamp: Date = Date()
): RealmObject() {}