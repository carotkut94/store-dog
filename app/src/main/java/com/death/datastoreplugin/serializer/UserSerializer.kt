package com.death.datastoreplugin.serializer
import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import androidx.datastore.dataStore
import com.death.datastoreplugin.UserStore

object UserStoreSerializer : Serializer<UserStore> {

    override val defaultValue: UserStore = UserStore.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserStore {
        try {
            return UserStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserStore, output: OutputStream) = t.writeTo(output)
}


private val USER_DATA_STORE_FILE_NAME = "user_store.pb"

val Context.userDataStore: DataStore<UserStore> by dataStore(
    fileName = USER_DATA_STORE_FILE_NAME,
    serializer = UserStoreSerializer
)