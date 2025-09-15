package edu.adf.profe.tabs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityTabsBinding

class TabsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTabsBinding
    lateinit var adapterTabs: AdapterTabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.adapterTabs = AdapterTabs(this)
        this.binding.vpt.adapter = this.adapterTabs
        //asocio al tablayout el viewpager
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt){
            tl, n -> tl.text = "VISTA ${n+1}"

        }.attach()
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt, fun (tl, n){
            tl.text = "VISTA ${n+1}"
        }).attach()

    }
}