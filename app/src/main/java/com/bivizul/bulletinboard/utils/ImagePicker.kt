package com.bivizul.bulletinboard.utils

import android.net.Uri
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio


// Здесь будем получать картинки, чтобы после показать их в списке,там перетаскивать и т.д.
object ImagePicker {

    val options = Options().apply{
        ratio = Ratio.RATIO_AUTO                           //Image/video соотношение захвата
        count = 3                                          //Количество изображений для ограничения выбора count
        spanCount = 4                                      //Количество столбцов в сетке
        path = "Pix/Camera"                                //Пользовательский путь для хранилища мультимедиа
        isFrontFacing = false                              //Фронтовая камера при запуске
        videoOptions.videoDurationLimitInSeconds = 10      //Длительность видео записи
        mode = Mode.All                                    //Возиможность выбора только изображения или видео или все сразу
        flash = Flash.Auto                                 //Возможность выбора место памяти
        preSelectedUrls = ArrayList<Uri>()                 //Предварительно выбранные URL-адреса изображений

    }


/*    fun getImages(imageCounter:Int){
        val options = Options().apply{
            ratio = Ratio.RATIO_AUTO                           //Image/video соотношение захвата
            count = imageCounter                               //Количество изображений для ограничения выбора count
            spanCount = 4                                      //Количество столбцов в сетке
            path = "Pix/Camera"                                //Пользовательский путь для хранилища мультимедиа
            isFrontFacing = false                              //Фронтовая камера при запуске
            videoOptions.videoDurationLimitInSeconds = 10      //Длительность видео записи
            mode = Mode.All                                    //Возиможность выбора только изображения или видео или все сразу
            flash = Flash.Auto                                 //Возможность выбора место памяти
            preSelectedUrls = ArrayList<Uri>()                 //Предварительно выбранные URL-адреса изображений

        }

    }*/


}