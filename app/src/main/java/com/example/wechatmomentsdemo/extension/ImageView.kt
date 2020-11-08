package  com.example.wechatmomentsdemo.extension

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Glide加载图片，可以指定圆角弧度。
 *
 * @param url 图片地址
 * @param round 圆角，单位dp
 * @param cornerType 圆角角度
 */
fun ImageView.load(
    url: String,
    round: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL
) {
    if (round == 0f) {
        Glide.with(this.context).load(url).into(this)
    } else {
        val option = RequestOptions.bitmapTransform(
            RoundedCornersTransformation(
                dp2px(round),
                0,
                cornerType
            )
        )
        Glide.with(this.context).load(url).apply(option).into(this)
    }
}

/**
 * 使用ImageLoader显示图片
 * @param imageView
 * @param url
 */
@BindingAdapter(value = ["url","radius"],requireAll = false)
fun imageLoader(imageView: ImageView, url: String?,radius:Float?) {
    if (!TextUtils.isEmpty(url)) {
        if (radius != null){
            imageView.load(url!!,radius)
        }else {
            imageView.load(url!!)
        }
    }
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param url 图片地址
 * @param options 配置参数
 */
fun ImageView.load(url: String, options: RequestOptions.() -> RequestOptions) {
    Glide.with(this.context).load(url).apply(RequestOptions().options()).into(this)
}