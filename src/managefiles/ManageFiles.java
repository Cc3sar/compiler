package managefiles;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManageFiles {
    public static String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();

        // Solo se pueden seleccionar archivos de texto
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);

        // Se abre el cuadro de dialogo para seleccionar el archivo
        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Se obtiene el archivo seleccionado y se retorna su ruta
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String readFile(String path) throws IOException {
        String text = "";
        BufferedReader bf = null;
        try {
            // Se crea un BufferedReader para poder leer el archivo
            bf = new BufferedReader(new FileReader(path));
            String temp = "";
            String bfRead;
            // Se lee el archivo y se almacena el texto en una variable
            while ((bfRead = bf.readLine()) != null) {
                temp = temp + bfRead + "\n";
            }
            text = temp;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Se cierra el archivo
            if (bf != null) {
                bf.close();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Se retorna el texto del archivo
        return text;
    }
}
