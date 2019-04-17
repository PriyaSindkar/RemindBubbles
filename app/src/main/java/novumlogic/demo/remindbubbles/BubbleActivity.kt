package novumlogic.demo.remindbubbles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bubble.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri


class BubbleActivity : AppCompatActivity() {

//    var phoneNumber : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)
        setSupportActionBar(toolbar)

//        if (intent != null && intent.hasExtra("PHONE_NUMBER")) {
//            phoneNumber = intent.getStringExtra("PHONE_NUMBER")
//            btnCall.text = "Call $phoneNumber"
//        }

        btnCall.setOnClickListener {
//            if (!phoneNumber.isNullOrEmpty()) {
//                val uri = "tel:" + phoneNumber!!.trim()
//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse(uri)
//                startActivity(intent)
//            }
        }
    }
}
