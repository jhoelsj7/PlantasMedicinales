package com.tuapp.plantasmedicinales.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.tuapp.plantasmedicinales.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static void loadPlantImage(Context context, String imageUrl, ImageView imageView) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  // No cachear en disco
                    .skipMemoryCache(true)  // No cachear en memoria
                    .signature(new ObjectKey(System.currentTimeMillis()))  // Forzar recarga
                    .centerCrop();

            Glide.with(context)
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    public static void loadPlantImageCircular(Context context, String imageUrl, ImageView imageView) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  // No cachear en disco
                    .skipMemoryCache(true)  // No cachear en memoria
                    .signature(new ObjectKey(System.currentTimeMillis()))  // Forzar recarga
                    .circleCrop();

            Glide.with(context)
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    public static void clearCache(Context context) {
        try {
            Glide.get(context).clearMemory();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(context).clearDiskCache();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda un Bitmap en almacenamiento interno de la app y retorna la ruta
     */
    public static String saveBitmapToInternalStorage(Context context, Bitmap bitmap, String plantName) {
        if (bitmap == null || plantName == null) {
            return null;
        }

        try {
            // Crear directorio de imágenes si no existe
            File imageDir = new File(context.getFilesDir(), "plant_images");
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }

            // Crear archivo con timestamp para evitar duplicados
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = plantName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".jpg";
            File imageFile = new File(imageDir, fileName);

            // Guardar bitmap como JPEG
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Carga un Bitmap desde una ruta de archivo
     */
    public static Bitmap loadBitmapFromPath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                return BitmapFactory.decodeFile(imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Carga una imagen desde una ruta local en un ImageView
     */
    public static void loadLocalImage(Context context, String imagePath, ImageView imageView) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(context)
                        .load(imageFile)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .centerCrop()
                        .into(imageView);
            } else {
                imageView.setImageResource(R.mipmap.ic_launcher_round);
            }
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}
