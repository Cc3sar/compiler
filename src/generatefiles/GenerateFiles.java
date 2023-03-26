package generatefiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFiles {
    public static void main(String[] args) throws Exception {
        String absolutePath = System.getProperty("user.dir");
        String path = absolutePath + "/src/analyzer/MathLexer.flex";
        String path2 = absolutePath + "/src/analyzer/Lexer.flex";
        String[] paths = {"-parser", "Sintax", absolutePath + "/src/analyzer/MathParser.cup"};
        generate(path, path2, paths, absolutePath);
    }

    public static void generate(String path, String path2, String[] paths, String absolutePath) throws IOException, Exception{
        /* Rutas */
        Path pathSym = Paths.get(absolutePath + "/sym.java");
        Path pathSin = Paths.get(absolutePath + "/Sintax.java");

        /* Generar archivos */
        File file;
        file = new File(path);
        JFlex.Main.generate(file);
        file = new File(path2);
        JFlex.Main.generate(file);
        java_cup.Main.main(paths);

        /* Mover archivos */
        Files.move(
                pathSym,
                Paths.get(absolutePath + "/src/analyzer/sym.java")
        );

        Files.move(
                pathSin,
                Paths.get(absolutePath + "/src/analyzer/Sintax.java")
        );
    }
}
