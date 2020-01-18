package de.ueen.liveevent

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class LiveEvent<T>: MediatorLiveData<LiveEvent.Once<T>>() {

    fun post(value: T? = null) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.setValue(Once(value))
        } else {
            this.postValue(Once(value))
        }
    }

    fun forward(source: LiveEvent<T>, observer: Observer<T>? = null): LiveEvent<T> {
        this.addSource(source, {
            if (observer != null && it.receive(observer.hashCode())) {
                observer.onChanged(it.message)
            }
            this.post(it.message)
        })
        return this
    }

    fun observe(owner: LifecycleOwner, onEvent: (T) -> Unit) {
        this.value = null
        this.observe(owner, Observer {
            if (it != null && it.receive(onEvent.hashCode()) && it.message != null) {
                onEvent(it.message)
            }
        })
    }

    class Once<T> (val message: T? = null) {
        private val received = mutableListOf<Int>()
        fun receive(hash: Int): Boolean {
            if (!received.contains(hash)) {
                received.add(hash)
                return true
            }
            return false
        }
    }

}