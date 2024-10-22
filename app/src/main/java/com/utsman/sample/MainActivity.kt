package com.utsman.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utsman.hiraishin.HiraishinNoJutsu
import com.utsman.hiraishin.hiraishinIntent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val composite = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_hello.setOnClickListener {
            val intent = Intent().apply {
                putExtra("event", "whatever")
            }
            HiraishinNoJutsu.getInstance().postIntent("oy", intent)
        }

        composite.hiraishinIntent("oy", this::subscriber)

        HiraishinNoJutsu.getInstance().observer { key, composite ->

        }

        HiraishinNoJutsu.getInstance().observerIntent { key, intent, composite ->

        }
    }

    private fun subscriber(intent: Intent) {
        toast(intent.getStringExtra("event"))
    }
}
