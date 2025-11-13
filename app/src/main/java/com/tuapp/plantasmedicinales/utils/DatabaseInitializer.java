package com.tuapp.plantasmedicinales.utils;

import android.content.Context;
import android.util.Log;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para inicializar la base de datos local con las 5 plantas del modelo
 */
public class DatabaseInitializer {
    private static final String TAG = "DatabaseInitializer";

    public static void initializeWithDefaultPlants(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantDao plantDao = AppDatabase.getInstance(context).plantDao();

                    // Verificar si ya hay plantas en la BD
                    List<Plant> existingPlants = plantDao.getAllPlantsSync();
                    if (existingPlants != null && !existingPlants.isEmpty()) {
                        Log.d(TAG, "La base de datos ya contiene " + existingPlants.size() + " plantas");
                        return;
                    }

                    Log.d(TAG, "Inicializando base de datos con plantas por defecto...");

                    List<Plant> defaultPlants = getDefaultPlants();

                    for (Plant plant : defaultPlants) {
                        plantDao.insert(plant);
                        Log.d(TAG, "Planta insertada: " + plant.getCommon_name());
                    }

                    Log.d(TAG, "Base de datos inicializada exitosamente con " + defaultPlants.size() + " plantas");

                } catch (Exception e) {
                    Log.e(TAG, "Error al inicializar base de datos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static List<Plant> getDefaultPlants() {
        List<Plant> plants = new ArrayList<>();

        // 1. ALSTROEMERIA
        Plant alstroemeria = new Plant();
        alstroemeria.setCommon_name("Alstroemeria");
        alstroemeria.setScientific_name("Alstroemeria spp.");
        alstroemeria.setFamily("Alstroemeriaceae");
        alstroemeria.setDescription("Planta herbácea perenne originaria de Sudamérica, conocida popularmente como \"Lirio de los Incas\" o \"Amancay\". Presenta flores vistosas de diversos colores con manchas características.");
        alstroemeria.setMedicinal_uses("Aunque principalmente ornamental, algunas especies se usan tradicionalmente para tratar inflamaciones leves y como diurético natural. Sus raíces tuberosas contienen compuestos con propiedades antiinflamatorias.");
        alstroemeria.setPreparation("Infusión: 1-2 cucharaditas de raíz seca por taza de agua hirviendo. Dejar reposar 10 minutos. Tomar 1-2 veces al día.");
        alstroemeria.setPrecautions("No consumir sin supervisión médica. Puede causar reacciones alérgicas en personas sensibles. Evitar durante el embarazo y lactancia.");
        alstroemeria.setSynced(false);
        plants.add(alstroemeria);

        // 2. MUÑA
        Plant muna = new Plant();
        muna.setCommon_name("Muña");
        muna.setScientific_name("Minthostachys mollis");
        muna.setFamily("Lamiaceae");
        muna.setDescription("Planta aromática andina originaria de Perú, Bolivia y Ecuador. Crece entre 2,700 y 3,400 msnm. Tiene hojas pequeñas con un aroma mentolado intenso y flores moradas o blancas.");
        muna.setMedicinal_uses("Excelente para tratar problemas digestivos, gases, cólicos y mal de altura (soroche). Tiene propiedades carminativas, antiespasmódicas y expectorantes. Ayuda a aliviar el dolor de estómago y mejora la digestión.");
        muna.setPreparation("Infusión: 3-5 hojas frescas o 1 cucharada de hojas secas por taza de agua hirviendo. Dejar reposar 5-7 minutos. Tomar después de las comidas o cuando se sienta malestar estomacal.");
        muna.setPrecautions("No consumir en exceso. Evitar durante el embarazo. Puede interactuar con medicamentos anticoagulantes.");
        muna.setSynced(false);
        plants.add(muna);

        // 3. OPUNTIA
        Plant opuntia = new Plant();
        opuntia.setCommon_name("Opuntia");
        opuntia.setScientific_name("Opuntia ficus-indica");
        opuntia.setFamily("Cactaceae");
        opuntia.setDescription("Cactus conocido como \"Nopal\" o \"Tuna\". Planta suculenta con tallos aplanados (cladodios) en forma de raqueta, espinas y flores amarillas o rojas. Fruto comestible llamado \"tuna\".");
        opuntia.setMedicinal_uses("Excelente para controlar diabetes tipo 2 (reduce glucosa en sangre), tratar colesterol alto, obesidad y problemas digestivos. Rico en fibra, antioxidantes y mucílagos. Ayuda a mejorar la función digestiva.");
        opuntia.setPreparation("Consumo fresco: Limpiar las pencas (nopales) eliminando espinas, cortar en trozos y consumir en ensaladas o licuados. Jugo: Licuar 1 penca mediana con agua y limón. Tomar en ayunas.");
        opuntia.setPrecautions("Personas con diabetes deben monitorear sus niveles de glucosa al consumirlo. Puede causar diarrea si se consume en exceso. Lavar muy bien para eliminar espinas microscópicas.");
        opuntia.setSynced(false);
        plants.add(opuntia);

        // 4. ROMERO
        Plant romero = new Plant();
        romero.setCommon_name("Romero");
        romero.setScientific_name("Rosmarinus officinalis");
        romero.setFamily("Lamiaceae");
        romero.setDescription("Arbusto aromático perenne originario del Mediterráneo. Hojas aciculares verde oscuro, muy aromáticas. Flores pequeñas de color azul, violeta o blanco. Puede alcanzar hasta 2 metros de altura.");
        romero.setMedicinal_uses("Estimulante circulatorio, mejora la memoria y concentración. Propiedades antioxidantes, antiinflamatorias y digestivas. Alivia dolores musculares y articulares. Fortalece el cabello y estimula el crecimiento capilar.");
        romero.setPreparation("Infusión: 1 cucharadita de hojas secas por taza de agua hirviendo. Dejar reposar 10 minutos. Tomar 2-3 veces al día. Uso externo: Decocción concentrada para masajes o baños relajantes.");
        romero.setPrecautions("Evitar durante el embarazo y lactancia. Puede elevar la presión arterial en personas hipertensas. No usar aceite esencial puro sobre la piel sin diluir. Personas con epilepsia deben evitarlo.");
        romero.setSynced(false);
        plants.add(romero);

        // 5. SÁBILA
        Plant sabila = new Plant();
        sabila.setCommon_name("Sábila");
        sabila.setScientific_name("Aloe vera / Aloe barbadensis");
        sabila.setFamily("Asphodelaceae");
        sabila.setDescription("Planta suculenta con hojas carnosas verde-grisáceas dispuestas en roseta. Las hojas contienen un gel transparente rico en nutrientes. Puede alcanzar 60-90 cm de altura. Flores tubulares amarillas.");
        sabila.setMedicinal_uses("Cicatrizante excepcional para quemaduras, heridas y problemas de piel. Hidratante natural, antiinflamatorio y regenerador celular. Útil para gastritis, estreñimiento, diabetes. Mejora la salud digestiva y fortalece el sistema inmunológico.");
        sabila.setPreparation("Uso tópico: Cortar una hoja, extraer el gel y aplicar directamente sobre la piel. Uso interno: 1-2 cucharadas de gel puro en ayunas (sin la corteza amarilla). Se puede mezclar con jugo de naranja o agua.");
        sabila.setPrecautions("NO consumir la corteza amarilla (látex) que es laxante fuerte y tóxico. Evitar durante embarazo y lactancia. Puede bajar los niveles de glucosa, personas con diabetes deben consultar médico. No usar en heridas profundas.");
        sabila.setSynced(false);
        plants.add(sabila);

        return plants;
    }
}
