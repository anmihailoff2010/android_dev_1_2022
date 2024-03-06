package com.example.myapplication2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.myapplication2.databinding.CustomRelativeViewBinding

class CustomRelativeViewBinding
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    val binding = CustomRelativeViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setText (topString: String, bottomString: String) {
        binding.topString.text = topString
        binding.bottomString.text = bottomString
    }
}