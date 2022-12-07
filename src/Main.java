import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {

        String pathSaveGame = "F:\\Game\\savegames\\"; // Путь к файлу и имя файла для записи
        String pathZipFile = "F:\\Game\\savegames\\archive_save.zip"; // Путь к архиву

        GameProgress[] savedGame = {
                new GameProgress(80, 4, 2, 100),
                new GameProgress(70, 5, 1, 150),
                new GameProgress(85, 7, 5, 400)
        };

        for (int i = 0; i < savedGame.length; i++) {
            saveGame(pathSaveGame + "save_" + i + ".dat", savedGame[i]); // Вызов метода сохрнанения состояния
        }

        ArrayList <String> listFiles = new ArrayList<>(); // Список файлов для архивации в каталоге
        File dir = new File(pathSaveGame);
            for (File item : dir.listFiles()) {           // Наполнение списка файлов каталога
                listFiles.add(item.getPath());
//                System.out.println(listFiles);
        }
        zipSave(pathZipFile, listFiles); // Вызов архивации

        for (File fileDel: dir.listFiles()) { // Удаление файлов из каталога
            if (fileDel.delete()) System.out.println("File " + fileDel.getName() + " deleted");
               else System.out.println("File " + fileDel.getName() + " NOT deleted");
        }
    }

    public static void saveGame(String path, GameProgress saved) { //запись сохранения в файл
        try {
            FileOutputStream fos = new FileOutputStream(path, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saved);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void zipSave(String pathZip, ArrayList <String> listFiles) { // Архивация файлов
        try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip));
            for (String fileName: listFiles) {
                FileInputStream fis = new FileInputStream(fileName);
                ZipEntry entry = new ZipEntry(fileName);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
