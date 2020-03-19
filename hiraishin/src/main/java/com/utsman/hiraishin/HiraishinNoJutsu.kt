package com.utsman.hiraishin

import android.content.Intent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class HiraishinNoJutsu {

    companion object {
        private var instance: HiraishinNoJutsu? = null
        fun getInstance(): HiraishinNoJutsu {
            if (instance == null) {
                instance = HiraishinNoJutsu()
            }
            return instance!!
        }
    }

    private val eventSubject = PublishSubject.create<String>()
    private val intentSubject = PublishSubject.create<IntentKey>()

    private var composite = CompositeDisposable()
    private var compositeIntent = CompositeDisposable()

    fun observer(func: (key: String, composite: CompositeDisposable) -> Unit): Disposable? {
        val dis = eventSubject.subscribeOn(Schedulers.io())
            .subscribe({
                func.invoke(it, composite)
            }, {
                it.printStackTrace()
            })

        if (composite.isDisposed) {
            composite = CompositeDisposable()
        }
        composite.add(dis)
        return dis
    }

    fun observerIntent(func: (key: String?, intent: Intent, composite: CompositeDisposable) -> Unit): Disposable? {
        val dis = intentSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                func.invoke(it.key, it.intent, composite)
            }, {
                it.printStackTrace()
            })

        if (compositeIntent.isDisposed) {
            compositeIntent = CompositeDisposable()
        }
        compositeIntent.add(dis)
        return dis
    }

    fun post(key: String) = eventSubject.onNext(key)
    fun postIntent(key: String?, intent: Intent) = intentSubject.onNext(IntentKey(key, intent))

    fun stop() = composite.dispose()
    fun stopIntentObserver() = compositeIntent.dispose()
}

fun CompositeDisposable.hiraishinIntent(action: String, i: (Intent) -> Unit) {
    HiraishinNoJutsu.getInstance().observerIntent { key, intent, composite ->
        this.add(composite)
        if (action == key) i.invoke(intent)
    }
}

data class IntentKey(
    val key: String?,
    val intent: Intent
)
