package com.example.juancastrejonautomotriz.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.example.juancastrejonautomotriz.R
import com.squareup.picasso.Picasso

class ImgPagerAdapter(var context: Context, var images:ArrayList<String>):PagerAdapter(){
   lateinit var layoutInflater: LayoutInflater
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.item_img,container,false)
        val imageView = view.findViewById<View>(R.id.imageViewVehicle) as ImageView
        container.addView(view,0)
        Picasso.get().load(images[position]).fit()
            .into(imageView)
        imageView.setOnClickListener{
            Toast.makeText(context,"image view",Toast.LENGTH_SHORT).show()
        }
        return view
    }
    init{
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}